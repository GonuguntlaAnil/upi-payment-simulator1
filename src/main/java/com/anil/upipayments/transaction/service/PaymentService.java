package com.anil.upipayments.transaction.service;

import com.anil.upipayments.entity.BankAccount;
import com.anil.upipayments.repository.BankAccountRepository;
import com.anil.upipayments.transaction.dto.SendMoneyRequest;
import com.anil.upipayments.transaction.entity.UpiTransaction;
import com.anil.upipayments.transaction.repository.UpiTransactionRepository;
import com.anil.upipayments.upi.entity.UpiAccount;
import com.anil.upipayments.upi.repository.UpiAccountRepository;
import com.anil.upipayments.upi.service.UpiService;
import com.anil.upipayments.entity.UserEntity;
import com.anil.upipayments.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentService {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final UpiAccountRepository upiAccountRepository;
    private final UpiTransactionRepository transactionRepository;
    private final UpiService upiService;

    public PaymentService(UserRepository userRepository,
                          BankAccountRepository bankAccountRepository,
                          UpiAccountRepository upiAccountRepository,
                          UpiTransactionRepository transactionRepository,
                          UpiService upiService) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.upiAccountRepository = upiAccountRepository;
        this.transactionRepository = transactionRepository;
        this.upiService = upiService;
    }

    @Transactional
    public void sendMoney(String senderMobile, SendMoneyRequest request) {

        // 1️⃣ Validate UPI PIN
        upiService.validateUpiPin(senderMobile, request.getUpiPin());

        // 2️⃣ Get sender user & account
        UserEntity sender = userRepository.findByMobile(senderMobile)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));

        BankAccount senderAccount = bankAccountRepository.findByUserId(sender.getId())
                .orElseThrow(() -> new IllegalArgumentException("Sender bank account not found"));

        // 3️⃣ Check balance
        if (senderAccount.getBalance().compareTo(request.getAmount()) < 0) {
            throw new IllegalArgumentException("Insufficient balance");
        }

        // 4️⃣ Get sender UPI ID
        UpiAccount senderUpi = upiAccountRepository.findByUserId(sender.getId())
                .orElseThrow(() -> new IllegalArgumentException("Sender UPI not found"));

        // 5️⃣ Get receiver UPI account
        UpiAccount receiverUpi = upiAccountRepository.findByUpiId(request.getReceiverUpiId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver UPI not found"));

        // 6️⃣ Get receiver bank account
        BankAccount receiverAccount = bankAccountRepository.findByUserId(
                        receiverUpi.getUser().getId())
                .orElseThrow(() -> new IllegalArgumentException("Receiver bank account not found"));

        // 7️⃣ Debit sender
        senderAccount.setBalance(
                senderAccount.getBalance().subtract(request.getAmount())
        );

        // 8️⃣ Credit receiver
        receiverAccount.setBalance(
                receiverAccount.getBalance().add(request.getAmount())
        );

        // 9️⃣ Save updated balances
        bankAccountRepository.save(senderAccount);
        bankAccountRepository.save(receiverAccount);

        // 🔟 Record transaction
        UpiTransaction transaction = new UpiTransaction();
        transaction.setSenderUpiId(senderUpi.getUpiId());
        transaction.setReceiverUpiId(receiverUpi.getUpiId());
        transaction.setAmount(request.getAmount());
        transaction.setStatus("SUCCESS");
        transaction.setCreatedAt(LocalDateTime.now());

        transactionRepository.save(transaction);
    }
}
