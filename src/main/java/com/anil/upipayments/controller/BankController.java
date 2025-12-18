package com.anil.upipayments.controller;

import com.anil.upipayments.dto.AddBalanceRequest;
import com.anil.upipayments.dto.BalanceResponse;
import com.anil.upipayments.entity.BankAccount;
import com.anil.upipayments.repository.BankAccountRepository;
import com.anil.upipayments.entity.UserEntity;
import com.anil.upipayments.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/bank")
public class BankController {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;

    public BankController(UserRepository userRepository,
                          BankAccountRepository bankAccountRepository) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    @PostMapping("/add-balance")
    public String addBalance(
            @Valid @RequestBody AddBalanceRequest request,
            Authentication authentication) {

        String mobile = authentication.getName();

        UserEntity user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        BankAccount account = bankAccountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found"));

        BigDecimal newBalance = account.getBalance().add(request.getAmount());
        account.setBalance(newBalance);

        bankAccountRepository.save(account);

        return "Balance added successfully. Current balance: " + newBalance;
    }@GetMapping("/balance")
    public BalanceResponse getBalance(Authentication authentication) {

        String mobile = authentication.getName();

        UserEntity user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        BankAccount account = bankAccountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Bank account not found"));

        return new BalanceResponse(account.getBalance());
    }

}
