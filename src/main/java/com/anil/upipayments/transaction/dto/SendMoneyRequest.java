package com.anil.upipayments.transaction.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class SendMoneyRequest {

    @NotBlank(message = "Receiver UPI ID is required")
    @Pattern(
            regexp = "^[a-zA-Z0-9._]{3,}@[a-zA-Z]{3,}$",
            message = "Invalid UPI ID format"
    )
    private String receiverUpiId;

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "1.00", message = "Minimum amount is 1")
    private BigDecimal amount;

    @NotBlank(message = "UPI PIN is required")
    @Pattern(
            regexp = "^[0-9]{4,6}$",
            message = "UPI PIN must be 4 or 6 digits"
    )
    private String upiPin;

    // getters & setters
}
