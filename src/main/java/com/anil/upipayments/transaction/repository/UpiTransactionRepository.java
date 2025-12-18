package com.anil.upipayments.transaction.repository;

import com.anil.upipayments.transaction.entity.UpiTransaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UpiTransactionRepository
        extends JpaRepository<UpiTransaction, Long> {

    // Sent transactions
    Page<UpiTransaction> findBySenderUpiIdOrderByCreatedAtDesc(
            String senderUpiId,
            Pageable pageable
    );

    // Received transactions
    Page<UpiTransaction> findByReceiverUpiIdOrderByCreatedAtDesc(
            String receiverUpiId,
            Pageable pageable
    );
}
