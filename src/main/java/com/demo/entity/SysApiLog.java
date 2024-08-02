package com.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SYS_API_LOG")
public class SysApiLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, length = 20)
    private Long id;

    @Column(name = "TXN_SEQ", nullable = false, length = 50)
    private String txnSeq;

    @Column(name = "USER_ID", nullable = false, length = 50)
    private String userId;

    @Column(name = "METHOD", length = 50)
    private String method;

    @Column(name = "PARAMS", length = 3000)
    private String params;

    @Column(name = "RESULT", length = 3000)
    private String result;

    @Column(name = "ERROR_MSG", length = 3000)
    private String errorMsg;

    @Column(name = "CREATE_TIME", length = 20)
    private LocalDateTime createTime;
}