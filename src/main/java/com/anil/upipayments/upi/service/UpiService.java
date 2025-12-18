package com.anil.upipayments.upi.service;

import com.anil.upipayments.upi.dto.CreateUpiRequest;
import com.anil.upipayments.upi.entity.UpiAccount;
import com.anil.upipayments.upi.repository.UpiAccountRepository;
import com.anil.upipayments.entity.UserEntity;
import com.anil.upipayments.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;


@Service
public class UpiService {

    private final UpiAccountRepository upiAccountRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UpiService(UpiAccountRepository upiAccountRepository,
                      UserRepository userRepository,
                      PasswordEncoder passwordEncoder) {
        this.upiAccountRepository = upiAccountRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public void createUpiId(String mobile, CreateUpiRequest request) {

        // 1️⃣ Get logged-in user
        UserEntity user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 2️⃣ Check if user already has UPI ID
        if (upiAccountRepository.findByUserId(user.getId()).isPresent()) {
            throw new IllegalArgumentException("UPI ID already exists for user");
        }

        // 3️⃣ Check UPI ID uniqueness
        if (upiAccountRepository.findByUpiId(request.getUpiId()).isPresent()) {
            throw new IllegalArgumentException("UPI ID already taken");
        }

        // 4️⃣ Create UPI account (PIN comes later)
        UpiAccount upiAccount = new UpiAccount();
        upiAccount.setUpiId(request.getUpiId());
        upiAccount.setUser(user);
        upiAccount.setUpiPinHash("NOT_SET"); // placeholder

        upiAccountRepository.save(upiAccount);
    }public void setUpiPin(String mobile, String pin) {

        UserEntity user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        UpiAccount upiAccount = upiAccountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("UPI ID not created"));

        // Hash the PIN
        String hashedPin = passwordEncoder.encode(pin);

        upiAccount.setUpiPinHash(hashedPin);
        upiAccountRepository.save(upiAccount);
    }public void validateUpiPin(String mobile, String pin) {

        UserEntity user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        UpiAccount upiAccount = upiAccountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("UPI ID not found"));

        // Check if PIN is set
        if ("NOT_SET".equals(upiAccount.getUpiPinHash())) {
            throw new IllegalArgumentException("UPI PIN not set");
        }

        // Validate PIN
        if (!passwordEncoder.matches(pin, upiAccount.getUpiPinHash())) {
            throw new IllegalArgumentException("Invalid UPI PIN");
        }
    }
    public void pay(
            String senderMobile,
            String receiverUpiId,
            Double amount,
            String upiPin
    ) {
        // your existing payment logic
    }


}
