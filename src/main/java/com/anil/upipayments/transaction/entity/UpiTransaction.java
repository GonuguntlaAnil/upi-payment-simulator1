package com.anil.upipayments.transaction.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "upi_transaction")
@Data
public class UpiTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_upi_id", nullable = false)
    private String senderUpiId;

    @Column(name = "receiver_upi_id", nullable = false)
    private String receiverUpiId;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String status; // SUCCESS, FAILED

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // getters & setters
}
