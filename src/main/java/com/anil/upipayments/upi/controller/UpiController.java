package com.anil.upipayments.upi.controller;

import com.anil.upipayments.upi.dto.CreateUpiRequest;
import com.anil.upipayments.upi.dto.PayRequest;
import com.anil.upipayments.upi.dto.SetUpiPinRequest;
import com.anil.upipayments.upi.dto.ValidateUpiPinRequest;
import com.anil.upipayments.upi.service.UpiService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/upi")
public class UpiController {

    private final UpiService upiService;

    public UpiController(UpiService upiService) {
        this.upiService = upiService;
    }

    @PostMapping("/create")
    public String createUpiId(
            @Valid @RequestBody CreateUpiRequest request,
            Authentication authentication) {

        String mobile = authentication.getName();
        upiService.createUpiId(mobile, request);

        return "UPI ID created successfully";
    }@PostMapping("/set-pin")
    public String setUpiPin(
            @Valid @RequestBody SetUpiPinRequest request,
            Authentication authentication) {

        String mobile = authentication.getName();
        upiService.setUpiPin(mobile, request.getUpiPin());

        return "UPI PIN set successfully";
    }@PostMapping("/validate-pin")
    public String validateUpiPin(
            @Valid @RequestBody ValidateUpiPinRequest request,
            Authentication authentication) {

        String mobile = authentication.getName();
        upiService.validateUpiPin(mobile, request.getUpiPin());

        return "UPI PIN validated successfully";
    }




}
