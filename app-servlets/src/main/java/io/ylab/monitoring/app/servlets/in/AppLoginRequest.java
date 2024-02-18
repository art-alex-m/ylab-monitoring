package io.ylab.monitoring.app.servlets.in;

import io.ylab.monitoring.domain.auth.in.UserLoginInputRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AppLoginRequest implements UserLoginInputRequest {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
