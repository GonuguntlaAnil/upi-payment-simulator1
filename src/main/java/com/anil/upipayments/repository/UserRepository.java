package com.anil.upipayments.repository;

import com.anil.upipayments.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserEntity> findByMobile(String mobile);

    Optional<UserEntity> findByEmail(String email);
}
