package com.demo.config;

import com.demo.api.model.res.BaseRes;
import com.demo.constant.Const;
import com.demo.constant.MessageConst;
import com.demo.filter.JwtAuthenticationFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.StaticHeadersWriter;

import javax.servlet.http.HttpServletResponse;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests()
                .antMatchers(Const.WHITE_LIST.toArray(new String[0]))
                .permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler())
                .and()
                .headers(headers -> headers
                        .cacheControl(HeadersConfigurer.CacheControlConfig::disable)
                        .addHeaderWriter(new StaticHeadersWriter("X-Content-Type-Options","nosniff"))
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
                        .httpStrictTransportSecurity()
                        .includeSubDomains(true)
                        .maxAgeInSeconds(31536000)
                        .and()
                        .contentTypeOptions(HeadersConfigurer.ContentTypeOptionsConfig::disable)
                        .xssProtection(xssProtection -> xssProtection.xssProtectionEnabled(true))
//                        .contentSecurityPolicy("default-src 'self'")
//                        .and()
//                        .referrerPolicy(referrerPolicy -> referrerPolicy.policy(ReferrerPolicy.NO_REFERRER))
//                        .permissionsPolicy(permissionsPolicy -> permissionsPolicy.policy("geolocation=(),midi=(),sync-xhr=(),microphone=(),camera=(),magnetometer=(),gyroscope=(),fullscreen=(self),payment=()"))
                )
        ;
        return http.build();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, ex) -> {
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            BaseRes baseRes = BaseRes.builder()
                    .code(MessageConst.RtnCode.FORBIDDEN.getCode())
                    .msg(MessageConst.ACCESS_DENIED)
                    .result(new Object())
                    .build();
            response.getWriter().write(objectMapper.writeValueAsString(baseRes));
            throw ex;
        };
    }
}
