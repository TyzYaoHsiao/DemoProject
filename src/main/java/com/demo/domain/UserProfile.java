package com.demo.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.io.Serializable;

/**
 * 使用者訊息
 */
@Builder
@Data
@Component
@AllArgsConstructor
@NoArgsConstructor
@RequestScope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserProfile implements Serializable {

    /**
     * request ip
     */
    String clientIp;
    /**
     * user id
     */
    String userId;
    /**
     * user name
     */
    String userName;
    /**
     * txn seq
     */
    String txnSeq;
}
