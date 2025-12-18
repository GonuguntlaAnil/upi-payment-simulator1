package com.anil.upipayments.transaction.controller;

import com.anil.upipayments.transaction.dto.TransactionHistoryResponse;
import com.anil.upipayments.transaction.service.TransactionHistoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/upi")
public class TransactionHistoryController {

    private final TransactionHistoryService historyService;

    public TransactionHistoryController(TransactionHistoryService historyService) {
        this.historyService = historyService;
    }

    @GetMapping("/transactions")
    public List<TransactionHistoryResponse> getTransactions(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            Authentication authentication) {

        String mobile = authentication.getName(); // from JWT
        Pageable pageable = PageRequest.of(page, size);

        return historyService.getTransactionHistory(mobile, pageable);
    }
}
