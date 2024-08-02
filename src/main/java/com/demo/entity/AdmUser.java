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
@Table(name = "ADM_USER")
public class AdmUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false, length = 20)
    private Long id;

    @Column(name = "USER_ID", nullable = false, length = 50)
    private String userId;

    @Column(name = "USER_NAME", length = 100)
    private String userName;

    @Column(name = "CREATE_TIME")
    private LocalDateTime createTime;

    @Column(name = "CREATE_BY", length = 50)
    private String createBy;

    @Column(name = "UPDATE_TIME")
    private LocalDateTime updateTime;

    @Column(name = "UPDATE_BY", length = 50)
    private String updateBy;
}
