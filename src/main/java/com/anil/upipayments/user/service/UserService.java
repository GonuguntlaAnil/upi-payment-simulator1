package com.anil.upipayments.user.service;

import com.anil.upipayments.entity.BankAccount;
import com.anil.upipayments.repository.BankAccountRepository;
import com.anil.upipayments.user.dto.UserProfileResponse;
import com.anil.upipayments.entity.UserEntity;
import com.anil.upipayments.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BankAccountRepository bankAccountRepository;

    public UserService(UserRepository userRepository,
                       BankAccountRepository bankAccountRepository) {
        this.userRepository = userRepository;
        this.bankAccountRepository = bankAccountRepository;
    }

    public UserProfileResponse getProfile(String mobile) {

        UserEntity user = userRepository.findByMobile(mobile)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        BankAccount account = bankAccountRepository.findByUserId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("Account not found"));

        return new UserProfileResponse(
                user.getName(),
                user.getMobile(),
                account.getBalance()
        );
    }
}
