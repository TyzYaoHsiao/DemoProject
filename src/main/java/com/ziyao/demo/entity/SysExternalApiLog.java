package com.ziyao.demo.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "SYS_EXTERNAL_API_LOG")
public class SysExternalApiLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, length = 20)
    private Long id;

    @Column(name = "TXN_SEQ", nullable = false, length = 50)
    private String txnSeq;

    @Column(name = "MSG_ID", length = 50)
    private String msgId;

    @Column(name = "PARAMS", length = 3000)
    private String params;

    @Column(name = "RESULT", length = 3000)
    private String result;

    @Column(name = "ERROR_MSG", length = 3000)
    private String errorMsg;

    @Column(name = "COST_TIME")
    private Long costTime;

    @Column(name = "CREATE_TIME", nullable = false)
    private LocalDateTime createTime;
}
