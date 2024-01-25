package io.ylab.monitoring.domain.auth.in;

public interface UserLoginInputRequest {
    String getUsername();

    String getPassword();
}
