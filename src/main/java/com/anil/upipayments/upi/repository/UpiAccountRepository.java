package com.anil.upipayments.upi.repository;

import com.anil.upipayments.upi.entity.UpiAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UpiAccountRepository extends JpaRepository<UpiAccount, Long> {

    Optional<UpiAccount> findByUpiId(String upiId);

    Optional<UpiAccount> findByUserId(Long userId);
}
