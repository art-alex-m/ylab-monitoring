package io.ylab.monitoring.app.servlets.in;

import io.ylab.monitoring.domain.auth.in.UserRegistrationInputRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AppRegistrationRequest implements UserRegistrationInputRequest {
    @NotEmpty
    private String username;

    @NotEmpty
    private String password;
}
