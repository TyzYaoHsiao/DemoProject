package com.demo.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ConnectTimeoutException;
import org.springframework.ws.client.WebServiceClientException;
import org.springframework.ws.client.support.interceptor.ClientInterceptor;
import org.springframework.ws.context.MessageContext;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.nio.charset.StandardCharsets;

/**
 * webservice req res log
 */
@Slf4j
public class LoggingInterceptor implements ClientInterceptor {

    @Override
    public boolean handleRequest(MessageContext messageContext) throws WebServiceClientException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            messageContext.getRequest().writeTo(out);
            String outStr = out.toString(StandardCharsets.UTF_8);
            log.info("== req == messageContext:{}", outStr);
        } catch (IOException e) {
            log.error("handleRequest error: ", e);
        }

        return true;
    }

    @Override
    public boolean handleResponse(MessageContext messageContext) throws WebServiceClientException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            messageContext.getResponse().writeTo(out);
            String outStr = out.toString(StandardCharsets.UTF_8);
            log.info("== res == messageContext:{}", outStr);
        } catch (IOException e) {
            log.error("handleResponse error: ", e);
        }
        return true;
    }

    @Override
    public boolean handleFault(MessageContext messageContext) throws WebServiceClientException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            messageContext.getResponse().writeTo(out);
            String outStr = out.toString(StandardCharsets.UTF_8);
            log.info("== fault == messageContext:{}", outStr);
        } catch (IOException e) {
            log.error("handleFault error: ", e);
        }
        return true;
    }

    @Override
    public void afterCompletion(MessageContext messageContext, Exception ex)
            throws WebServiceClientException {
        if (ex != null) {
            if (ex instanceof ConnectException) {
                log.info("== afterComplete == do with Connect error...");
            } else if (ex instanceof ConnectTimeoutException) {
                log.info("== afterComplete == do with Connect timeout error...");
            } else if (ex instanceof SocketTimeoutException) {
                log.info("== afterComplete == do with Read timeout ...");
            } else {
                log.error("== afterComplete == do with other error ...", ex);
            }
        } else {
            log.info("== afterComplete == do something ...");
        }
    }
}
