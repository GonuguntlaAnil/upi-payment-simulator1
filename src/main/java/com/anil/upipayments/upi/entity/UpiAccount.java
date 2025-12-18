package com.anil.upipayments.upi.entity;

import com.anil.upipayments.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(
        name = "upi_account",
        uniqueConstraints = @UniqueConstraint(columnNames = "upi_id")
)
@Data
public class UpiAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "upi_id", nullable = false, unique = true)
    private String upiId;

    @Column(name = "upi_pin_hash", nullable = false)
    private String upiPinHash;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    // getters & setters
}
