package com.demo.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests()
//                .antMatchers(SysConst.WHITE_LIST.toArray(new String[0]))
//                .permitAll()
//                .anyRequest().authenticated()
//                .and()
//                .exceptionHandling()
//                .and()
//                .headers(headers -> headers
//                        .cacheControl(HeadersConfigurer.CacheControlConfig::disable)
//                        .addHeaderWriter(new StaticHeadersWriter("X-Content-Type-Options","nosniff"))
//                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
//                        .httpStrictTransportSecurity()
//                        .includeSubDomains(true)
//                        .maxAgeInSeconds(31536000)
//                        .and()
//                        .contentTypeOptions(HeadersConfigurer.ContentTypeOptionsConfig::disable)
//                        .xssProtection(xssProtection -> xssProtection.xssProtectionEnabled(true))
//                )
//        ;
        return http.build();
    }
}
