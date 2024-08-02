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

    @Column(name = "REQUEST", length = 3000)
    private String request;

    @Column(name = "RESPONSE", length = 3000)
    private String response;

    @Column(name = "CREATE_TIME", length = 20)
    private LocalDateTime createTime;
}
