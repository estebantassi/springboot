package com.tools;

import java.nio.charset.StandardCharsets;
import java.util.HexFormat;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EmailHasher {

    private String emailHashKey;

    public EmailHasher(@Value("${email.hashkey}") String emailHashKey) {
        this.emailHashKey = emailHashKey;
    }

    public String hashEmail(String email) {
        try {

            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(
                    emailHashKey.getBytes(StandardCharsets.UTF_8),
                    "HmacSHA256"));

            String normalized = email.trim().toLowerCase();
            byte[] hash = mac.doFinal(normalized.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash);

        } catch (Exception e) {
            throw new IllegalStateException("Email hashing failed", e);
        }
    }

}
