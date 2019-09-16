package com.classic.project.model.user.verification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface VerificationRepository extends JpaRepository<Verification, String> {

    Verification findByCode(String code);

    @Modifying
    @Transactional
    @Query("delete from Verification where userVerification.userId= ?1")
    void deleteByUserId(int userId);
}
