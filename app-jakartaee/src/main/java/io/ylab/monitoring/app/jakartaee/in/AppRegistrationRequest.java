package io.ylab.monitoring.app.jakartaee.in;

import io.ylab.monitoring.domain.auth.in.UserRegistrationInputRequest;
import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AppRegistrationRequest implements UserRegistrationInputRequest {
    @JsonbProperty
    @NotEmpty
    private String username;

    @JsonbProperty
    @NotEmpty
    private String password;
}
