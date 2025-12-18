package com.anil.upipayments.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Table(name = "BANK_ACCOUNT")
@Data
public class BankAccount {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ACCOUNT_ID")
    private Long id;

    @Column(name = "ACCOUNT_NO", unique = true)
    private String accountNumber;

    @Column(name = "BALANCE", precision = 15, scale = 2)
    private BigDecimal balance;

    @OneToOne
    @JoinColumn(name = "USER_ID")
    private UserEntity user;
}

