package io.ylab.monitoring.app.console.model;

import io.ylab.monitoring.auth.model.AuthAuthUser;
import io.ylab.monitoring.domain.auth.model.AuthUser;
import io.ylab.monitoring.domain.core.model.DomainRole;
import io.ylab.monitoring.domain.core.model.DomainUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AppUserContextTest {

    AppUserContext sut;

    public static Stream<Arguments> givenUser_whenGetRole_thenExpectedSuccess() {
        return Stream.of(
                Arguments.of(AuthAuthUser.builder().role(DomainRole.ADMIN).username("test-admin").password("1").build(),
                        DomainRole.ADMIN),
                Arguments.of(AuthAuthUser.builder().role(DomainRole.USER).username("test-user").password("1").build(),
                        DomainRole.USER),
                Arguments.of(AuthAuthUser.builder().role(DomainRole.ANONYMOUS).username("test-anonymous").password(
                        "1").build(), DomainRole.ANONYMOUS),
                Arguments.of(null, DomainRole.ANONYMOUS)
        );
    }

    @BeforeEach
    void setUp() {
        sut = new AppUserContext();
    }

    @ParameterizedTest
    @MethodSource("givenUser_whenGetRole_thenExpectedSuccess")
    void givenAuthUsers_whenSetUser_thenSuccess(AuthUser authUser, DomainRole domainRole) {
        boolean result = sut.setUser(authUser);

        assertThat(result).isTrue();
        if (authUser == null) {
            assertThat(sut.getUser()).isNull();
            return;
        }
        DomainUser resultUser = sut.getUser();
        assertThat(resultUser).isNotNull().isNotEqualTo(authUser);
        assertThat(resultUser.getId()).isEqualTo(authUser.getId());

    }

    @Test
    void givenNothing_whenSetAnonymous_thenSuccess() {
        boolean result = sut.setAnonymous();

        assertThat(result).isTrue();
        assertThat(sut.getRole()).isEqualTo(DomainRole.ANONYMOUS);
        assertThat(sut.getUser()).isNull();
    }

    @ParameterizedTest
    @MethodSource("givenUser_whenGetRole_thenExpectedSuccess")
    void givenAuthUsers_whenGetUser_thenSuccess(AuthUser authUser, DomainRole domainRole) {
        sut.setUser(authUser);

        DomainUser result = sut.getUser();

        if (authUser == null) {
            assertThat(result).isNull();
            return;
        }
        assertThat(result).isNotNull().isNotEqualTo(authUser);
        assertThat(result.getId()).isEqualTo(authUser.getId());
    }

    @ParameterizedTest
    @MethodSource
    void givenUser_whenGetRole_thenExpectedSuccess(AuthUser user, DomainRole expected) {
        sut.setUser(user);

        DomainRole result = sut.getRole();

        assertThat(result).isEqualTo(expected);
    }
}