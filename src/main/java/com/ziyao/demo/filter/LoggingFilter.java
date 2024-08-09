package com.ziyao.demo.filter;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 過濾器 LOG
 */
//@Component
public class LoggingFilter implements Filter {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        CachedBodyHttpServletRequest cachedBodyRequest = new CachedBodyHttpServletRequest((HttpServletRequest) request);
        CachedBodyHttpServletResponse cachedBodyResponse = new CachedBodyHttpServletResponse((HttpServletResponse) response);

        logRequest(cachedBodyRequest, cachedBodyResponse);
        chain.doFilter(cachedBodyRequest, cachedBodyResponse);
        logResponse(cachedBodyRequest, cachedBodyResponse);
    }

    private void logRequest(CachedBodyHttpServletRequest request, CachedBodyHttpServletResponse response) {

        log.info("===========================request begin===============================================");
        log.info("URI         : {}", request.getRequestURI());
        log.info("Method      : {}", request.getMethod());
        log.info("Headers     : {}", request.getAllHeader());
        log.info("Request body: {}", request.getBody());
        log.info("===========================request end=================================================");
    }

    private void logResponse(CachedBodyHttpServletRequest request, CachedBodyHttpServletResponse response) {

        log.info("===========================response begin==============================================");
        log.info("URI          : {}", request.getRequestURI());
        log.info("Status code  : {}", response.getStatus());
        log.info("Headers      : {}", response.getAllHeader());
        log.info("Response body: {}", response.getBody());
        log.info("===========================response end================================================");
    }
}
