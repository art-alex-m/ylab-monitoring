package io.ylab.monitoring.app.springmvc.service;

import io.ylab.monitoring.app.springmvc.aspect.TimeProfileLog;
import io.ylab.monitoring.app.springmvc.model.AppUserDetails;
import io.ylab.monitoring.domain.auth.out.UserLoginInputResponse;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Менеджер токенов авторизации
 */
@TimeProfileLog
@Component
public class AuthTokenManager {
    private final static int TOKEN_BYTES_LENGTH = 48;

    private final Map<UUID, AppUserDetails> userDetails = new ConcurrentHashMap<>();
    private final Map<UUID, String> uuidToToken = new ConcurrentHashMap<>();
    private final Map<String, UUID> tokenToUuid = new ConcurrentHashMap<>();

    private final Random random = new SecureRandom();


    public String createToken(UserLoginInputResponse loginInputResponse) {
        String key = createToken(TOKEN_BYTES_LENGTH);
        UUID userId = loginInputResponse.getId();
        AppUserDetails details = new AppUserDetails(loginInputResponse);

        String currentKey = Optional.ofNullable(uuidToToken.putIfAbsent(userId, key)).orElse(key);
        userDetails.putIfAbsent(userId, details);
        tokenToUuid.putIfAbsent(currentKey, userId);

        return currentKey;
    }

    public AppUserDetails findByKey(String key) {
        String preparedKey = prepareToken(key);
        return Optional.ofNullable(tokenToUuid.get(preparedKey))
                .map(userDetails::get)
                .orElse(null);
    }

    public boolean revokeToken(String key) {
        String preparedKey = prepareToken(key);

        UUID userId = tokenToUuid.remove(preparedKey);
        if (userId != null) {
            uuidToToken.remove(userId);
            userDetails.remove(userId);
            return true;
        }
        return false;
    }


    public String createToken(int tokenLength) {
        byte[] token = new byte[tokenLength];
        random.nextBytes(token);
        return Base64.getEncoder().encodeToString(token);
    }

    public String prepareToken(String rawToken) {
        return rawToken != null
                ? rawToken.replace("Bearer ", "")
                : "";
    }
}
