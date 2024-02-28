package io.ylab.monitoring.app.springboot.in;

import io.swagger.v3.oas.annotations.media.Schema;
import io.ylab.monitoring.domain.auth.in.UserLoginInputRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AppLoginRequest implements UserLoginInputRequest {
    @Schema(example = "alex")
    @NotEmpty
    private String username;

    @Schema(example = "123")
    @NotEmpty
    private String password;
}
