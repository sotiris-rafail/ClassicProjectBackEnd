package com.classic.project.model.user.verification;

import com.classic.project.model.user.User;

import java.util.Date;

public interface VerificationService {

    public void saveVerification(User user);

    Verification save(Verification verification);

    Verification findByCode(String code);

    void deleteByUserId(int userId);
}
