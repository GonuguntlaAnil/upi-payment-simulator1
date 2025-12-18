package com.anil.upipayments.upi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class CreateUpiRequest {

    @NotBlank(message = "UPI ID is required")
    @Pattern(
            regexp = "^[a-zA-Z0-9._]{3,}@[a-zA-Z]{3,}$",
            message = "Invalid UPI ID format"
    )
    private String upiId;

    // getters & setters
}
