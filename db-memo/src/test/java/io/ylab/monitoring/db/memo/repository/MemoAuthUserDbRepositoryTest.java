package io.ylab.monitoring.db.memo.repository;

import io.ylab.monitoring.domain.auth.model.AuthUser;
import io.ylab.monitoring.domain.core.model.DomainRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class MemoAuthUserDbRepositoryTest {

    @Test
    void create() {
        AuthUser user = TestAuthUser.builder().username("test-user").build();
        MemoAuthUserDbRepository sut = new MemoAuthUserDbRepository();

        boolean result = sut.create(user);

        assertThat(result).isTrue();
    }

    @Test
    void createTryDoubles() {
        AuthUser user = TestAuthUser.builder().username("test-user").build();
        MemoAuthUserDbRepository sut = new MemoAuthUserDbRepository();
        sut.create(user);

        boolean result = sut.create(user);

        assertThat(result).isFalse();
    }

    @ParameterizedTest
    @CsvSource({"test-user,true", "test-admin,false"})
    void findByUsername(String username, boolean expected) {
        AuthUser user = TestAuthUser.builder().username("test-user").build();
        MemoAuthUserDbRepository sut = new MemoAuthUserDbRepository();
        sut.create(user);

        Optional<AuthUser> result = sut.findByUsername(username);

        assertThat(result.isPresent()).isEqualTo(expected);
    }

    @AllArgsConstructor
    @Builder
    @Getter
    static class TestAuthUser implements AuthUser {
        private final String username;
        private final String password;
        private final DomainRole role;
        @Builder.Default
        private UUID id = UUID.randomUUID();
    }
}
