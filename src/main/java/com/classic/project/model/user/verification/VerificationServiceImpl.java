package com.classic.project.model.user.verification;

import com.classic.project.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Component
public class VerificationServiceImpl implements VerificationService{

    @Autowired
    private VerificationRepository verificationRepository;

    @Override
    public void saveVerification(User user) {
        Verification verification = new Verification();
        verification.setId(user.getUserId());
        verification.setUserVerification(user);
        verification.setStatus(VerificationStatus.ZERO);
        verificationRepository.save(verification);
    }

    @Override
    public Verification save(Verification verification) {
        return verificationRepository.save(verification);
    }

    @Override
    public Verification findByCode(String code) {
        return verificationRepository.findByCode(code);
    }

    @Override
    public void deleteByUserId(int userId) {
        verificationRepository.deleteByUserId(userId);
    }

    public static String generateCode(String email, Date registrationDate) {
        Mac sha512_HMAC;
        byte [] byteKey;
        String result;
        byte [] mac_data = null;
        try {
            byteKey = email.getBytes(StandardCharsets.UTF_8);
            final String HmacSHA128 = "HmacSHA224";
            sha512_HMAC = Mac.getInstance(HmacSHA128);
            SecretKeySpec keySpec = new SecretKeySpec(byteKey, HmacSHA128);
            sha512_HMAC.init(keySpec);
            mac_data = sha512_HMAC.doFinal(registrationDate.toString().getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return mac_data != null ? bytesToHex(mac_data) : "";
    }

    private static String bytesToHex(byte[] bytes) {
        final char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
