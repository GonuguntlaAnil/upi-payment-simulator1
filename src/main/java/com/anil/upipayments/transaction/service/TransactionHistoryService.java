package com.anil.upipayments.transaction.service;

import com.anil.upipayments.transaction.dto.TransactionHistoryResponse;
import com.anil.upipayments.transaction.entity.UpiTransaction;
import com.anil.upipayments.transaction.repository.UpiTransactionRepository;
import com.anil.upipayments.upi.entity.UpiAccount;
import com.anil.upipayments.upi.repository.UpiAccountRepository;
import com.anil.upipayments.entity.UserEntity;
import com.anil.upipayments.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TransactionHistoryService {

    private final UserRepository userRepository;
    private final UpiAccountRepository upiAccountRepository;
    private final UpiTransactionRepository transactionRepository;

    public TransactionHistoryService(UserRepository userRepository,
                                     UpiAccountRepository upiAccountRepository,
                                     UpiTransactionRepository transactionRepository) {
        this.userRepository = userRepository;
        this.upiAccountRepository = upiAccountRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<TransactionHistoryResponse> getTransactionHistory(
            String mobile,
            Pageable pageable) {

        // 1️⃣ Get user
        UserEntity user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 2️⃣ Get user's UPI ID
        UpiAccount upiAccount = upiAccountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("UPI ID not found"));

        String myUpiId = upiAccount.getUpiId();

        // 3️⃣ Fetch sent & received transactions
        Page<UpiTransaction> sent =
                transactionRepository.findBySenderUpiIdOrderByCreatedAtDesc(
                        myUpiId, pageable);

        Page<UpiTransaction> received =
                transactionRepository.findByReceiverUpiIdOrderByCreatedAtDesc(
                        myUpiId, pageable);

        // 4️⃣ Map to response DTO
        List<TransactionHistoryResponse> history = new ArrayList<>();

        for (UpiTransaction tx : sent) {
            history.add(new TransactionHistoryResponse(
                    "SENT",
                    tx.getReceiverUpiId(),
                    tx.getAmount(),
                    tx.getStatus(),
                    tx.getCreatedAt()
            ));
        }

        for (UpiTransaction tx : received) {
            history.add(new TransactionHistoryResponse(
                    "RECEIVED",
                    tx.getSenderUpiId(),
                    tx.getAmount(),
                    tx.getStatus(),
                    tx.getCreatedAt()
            ));
        }

        return history;
    }
}
