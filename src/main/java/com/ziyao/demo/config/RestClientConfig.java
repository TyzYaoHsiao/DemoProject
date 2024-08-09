package com.demo.config;

import com.demo.interceptor.RestTemplateLoggingInterceptor;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactoryBuilder;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class RestClientConfig {

    private static final int DEFAULT_READ_TIMEOUT_MILLISECONDS = 60000;
    private static final int DEFAULT_CONNECTIONS_TIMEOUT_MILLISECONDS = 60000;

    @Bean
    public RestTemplate restTemplate() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();
        if (CollectionUtils.isEmpty(interceptors)) {
            interceptors = new ArrayList<>();
        }
        interceptors.add(new RestTemplateLoggingInterceptor());
        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }

    private ClientHttpRequestFactory clientHttpRequestFactory()
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

        return clientHttpRequestFactory(DEFAULT_READ_TIMEOUT_MILLISECONDS,
                DEFAULT_CONNECTIONS_TIMEOUT_MILLISECONDS);
    }

    private ClientHttpRequestFactory clientHttpRequestFactory(int readTimeout, int connetTimeout)
            throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
        HostnameVerifier hostname = (String s, SSLSession sslSession) -> true;

        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(PoolingHttpClientConnectionManagerBuilder.create()
                        .setMaxConnPerRoute(1000)
                        .setMaxConnTotal(1000)
                        .setSSLSocketFactory(SSLConnectionSocketFactoryBuilder.create()
                                .setSslContext(SSLContextBuilder.create()
                                        .loadTrustMaterial(acceptingTrustStrategy)
                                        .build())
                                .setHostnameVerifier(hostname)
                                .build())
                        .build())
                .build();

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        requestFactory.setConnectTimeout(connetTimeout);
        requestFactory.setConnectionRequestTimeout(readTimeout);

        return new BufferingClientHttpRequestFactory(requestFactory);
    }
}