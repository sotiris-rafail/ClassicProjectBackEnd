package com.classic.project.model.user.verification.response;

import com.classic.project.model.user.verification.Verification;

import java.util.Date;

public class ResponseVerification {

    private String verificationStatus;
    private boolean isExpired;

    public ResponseVerification(String verificationStatus, boolean isExpired) {
        this.verificationStatus = verificationStatus;
        this.isExpired = isExpired;
    }

    public String getVerificationStatus() {
        return verificationStatus;
    }

    public void setVerificationStatus(String verificationStatus) {
        this.verificationStatus = verificationStatus;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public static ResponseVerification convertForUser(Verification verification) {
        return new ResponseVerification(verification.getStatus().name(), isExpired(verification.getExpirationDate()));
    }

    private static boolean isExpired(Date expirationDate) {
        return expirationDate != null && !expirationDate.after(new Date());
    }
}
