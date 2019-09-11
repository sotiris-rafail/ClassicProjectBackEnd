package com.classic.project.model.user.verification;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationRepository extends JpaRepository<Verification, String> {

    Verification findByCode(String code);
}
