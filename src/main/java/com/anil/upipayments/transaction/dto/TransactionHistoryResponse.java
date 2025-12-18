package com.anil.upipayments.transaction.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class TransactionHistoryResponse {

    private String type; // SENT or RECEIVED
    private String upiId; // receiver for SENT, sender for RECEIVED
    private BigDecimal amount;
    private String status;
    private LocalDateTime date;

    public TransactionHistoryResponse(
            String type,
            String upiId,
            BigDecimal amount,
            String status,
            LocalDateTime date) {

        this.type = type;
        this.upiId = upiId;
        this.amount = amount;
        this.status = status;
        this.date = date;
    }

    // getters
}
