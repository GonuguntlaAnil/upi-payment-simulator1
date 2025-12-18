package com.anil.upipayments.service;

import com.anil.upipayments.dto.LoginRequest;
import com.anil.upipayments.dto.RegisterRequest;
import com.anil.upipayments.entity.BankAccount;
import com.anil.upipayments.repository.BankAccountRepository;
import com.anil.upipayments.entity.UserEntity;
import com.anil.upipayments.repository.UserRepository;
import com.anil.upipayments.security.JwtUtil;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository,
                       BankAccountRepository bankAccountRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public void register(RegisterRequest request) {

        // 1. Check mobile uniqueness
        if (userRepository.findByMobile(request.getMobile()).isPresent()) {
            throw new IllegalArgumentException("Mobile number already registered");
        }

        // 2. Check email uniqueness (if provided)
        if (request.getEmail() != null &&
                userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already registered");
        }

        // 3. Create User
        UserEntity user = new UserEntity();
        user.setName(request.getName());
        user.setMobile(request.getMobile());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        UserEntity savedUser = userRepository.save(user);

        // 4. Create Bank Account
        BankAccount account = new BankAccount();
        account.setAccountNumber("ACC" + savedUser.getId());
        account.setBalance(BigDecimal.ZERO);
        account.setUser(savedUser);

        bankAccountRepository.save(account);
    }public String login(LoginRequest request) {

        UserEntity user = userRepository.findByMobile(request.getMobile())
                .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        return JwtUtil.generateToken(user.getId(), user.getMobile());
    }


}
