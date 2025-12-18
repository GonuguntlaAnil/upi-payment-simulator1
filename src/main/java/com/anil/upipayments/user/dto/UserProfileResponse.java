package com.anil.upipayments.user.dto;

import lombok.Data;

import java.math.BigDecimal;
@Data
public class UserProfileResponse {

    private String name;
    private String mobile;
    private BigDecimal balance;

    public UserProfileResponse(String name, String mobile, BigDecimal balance) {
        this.name = name;
        this.mobile = mobile;
        this.balance = balance;
    }

    // getters
}
