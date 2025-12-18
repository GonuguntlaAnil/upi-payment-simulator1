package com.anil.upipayments.entity;

import jakarta.persistence.*;
import lombok.Data;


import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "USER_MASTER")
public class UserEntity {
     @Id
     @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    @Column(name = "NAME", nullable = false)
    private String name;
    @Column(name = "MOBILE", nullable = false, unique = true)
    private String mobile;
    @Column(name = "EMAIL", unique = true)
    private String email;

    @Column(name = "PASSWORD_HASH", nullable = false)
    private String password;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;

}
