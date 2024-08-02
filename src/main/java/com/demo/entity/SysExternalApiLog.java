package com.demo.entity;

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

    @Column(name = "MSG_TYPE", nullable = false, length = 1)
    private String msgType;

    @Column(name = "MSG_CONTENT", length = 3000)
    private String msgContent;

    @Column(name = "MSG_TIME", nullable = false)
    private LocalDateTime msgTime;
}
