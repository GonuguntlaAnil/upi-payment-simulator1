package com.anil.upipayments.transaction.controller;

import com.anil.upipayments.transaction.dto.SendMoneyRequest;
import com.anil.upipayments.transaction.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/upi")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/pay")
    public String sendMoney(
            @Valid @RequestBody SendMoneyRequest request,
            Authentication authentication) {

        String senderMobile = authentication.getName();

        paymentService.sendMoney(senderMobile, request);

        return "Payment successful";
    }

}
