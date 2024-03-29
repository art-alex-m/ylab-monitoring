package io.ylab.monitoring.auth.service;

import io.ylab.monitoring.domain.auth.exception.PasswordEncoderException;
import io.ylab.monitoring.domain.auth.service.PasswordEncoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;

public class AuthPasswordEncoder implements PasswordEncoder {

    private final static int SALT_LENGTH = 16;

    private final int iterationsCount = 16000;

    private final int keyLength = 512;

    private final String alogo = "PBKDF2WithHmacSHA1";

    private final SecretKeyFactory keyFactory;

    private final byte[] salt;

    public AuthPasswordEncoder(byte[] salt) {
        try {
            this.keyFactory = SecretKeyFactory.getInstance(alogo);
        } catch (Exception ex) {
            throw new PasswordEncoderException(ex);
        }
        if (salt == null || salt.length == 0) {
            SecureRandom random = new SecureRandom();
            salt = new byte[SALT_LENGTH];
            random.nextBytes(salt);
        }
        this.salt = salt;
    }

    @Override
    public String encode(String rawPassword) throws PasswordEncoderException {
        try {
            KeySpec keySpec = new PBEKeySpec(rawPassword.toCharArray(), salt, iterationsCount, keyLength);
            byte[] hash = keyFactory.generateSecret(keySpec).getEncoded();
            return Base64.getEncoder().encodeToString(hash);
        } catch (Exception ex) {
            throw new PasswordEncoderException(ex);
        }
    }

    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }
        if (rawPassword.isEmpty() || encodedPassword.isEmpty()) {
            return false;
        }
        try {
            String rawHash = encode(rawPassword);
            return encodedPassword.equals(rawHash);
        } catch (Exception ignored) {
        }
        return false;
    }
}
