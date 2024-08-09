package com.demo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * restTemplate log content
 */
@Slf4j
public class RestTemplateLoggingInterceptor implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        traceRequestAndGetDatabaseLogger(request, body);
        ClientHttpResponse response = execution.execute(request, body);
        traceResponse(request, response);
        return response;
    }

    private void traceRequestAndGetDatabaseLogger(HttpRequest request, byte[] body) {
        log.info("===========================request begin================================================");
        log.info("URI         : {}", request.getURI());
        log.info("Method      : {}", request.getMethod());
        log.info("Headers     : {}", request.getHeaders());
        log.info("Request body: {}", new String(body, StandardCharsets.UTF_8));
        log.info("==========================request end================================================");
    }

    private void traceResponse(HttpRequest request, ClientHttpResponse response) {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getBody(), StandardCharsets.UTF_8))) {
            StringBuilder inputStringBuilder = new StringBuilder();
            String line = bufferedReader.readLine();

            inputStringBuilder.append("HttpCode: ").append(response.getStatusCode().value()).append("; ");

            while (line != null) {
                inputStringBuilder.append(line);
                inputStringBuilder.append('\n');
                line = bufferedReader.readLine();
            }
            String responseMsg = inputStringBuilder.toString();

            log.info("============================response begin==========================================");
            log.info("URI          : {}", request.getURI());
            log.info("Status code  : {}", response.getStatusCode());
            log.info("Status text  : {}", response.getStatusText());
            log.info("Headers      : {}", response.getHeaders());
            log.info("Response body: {}", responseMsg);
            log.info("=======================response end=================================================");

        } catch (IOException e) {
            log.error("Log RestTemplate response failed: ", e);
        }
    }

}
