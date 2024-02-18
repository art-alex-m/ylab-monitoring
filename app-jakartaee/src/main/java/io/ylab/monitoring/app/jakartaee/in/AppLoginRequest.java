package io.ylab.monitoring.app.jakartaee.in;

import io.ylab.monitoring.domain.auth.in.UserLoginInputRequest;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AppLoginRequest implements UserLoginInputRequest {
    @JsonbProperty
    @NotEmpty
    private String username;

    @JsonbProperty
    @NotEmpty
    private String password;
}
