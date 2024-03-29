package io.ylab.monitoring.db.jdbc.repository;

import io.ylab.monitoring.db.jdbc.exeption.JdbcDbException;
import io.ylab.monitoring.db.jdbc.model.JdbcAuthUser;
import io.ylab.monitoring.db.jdbc.service.JdbcTestHelper;
import io.ylab.monitoring.db.jdbc.service.TestConnection;
import io.ylab.monitoring.db.jdbc.service.TestDatabaseExtension;
import io.ylab.monitoring.domain.auth.model.AuthUser;
import io.ylab.monitoring.domain.core.model.DomainRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.Connection;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

@ExtendWith(TestDatabaseExtension.class)
class JdbcAuthUserDbRepositoryTest {

    private final SqlQueryRepository queryRepository = new SqlQueryResourcesRepository();

    @TestConnection
    private Connection connection;

    private JdbcAuthUserDbRepository sut;

    public static Stream<Arguments> givenUsername_whenFindByUsername_thenExpected() {
        return Stream.of(
                Arguments.of(JdbcTestHelper.testAuthUserA.getUsername(), JdbcTestHelper.testAuthUserA),
                Arguments.of(JdbcTestHelper.testAuthUserB.getUsername(), JdbcTestHelper.testAuthUserB),
                Arguments.of("undefined", null)
        );
    }

    @BeforeEach
    void setUp() {
        sut = new JdbcAuthUserDbRepository(queryRepository, connection);
    }

    @ParameterizedTest
    @MethodSource
    void givenUsername_whenFindByUsername_thenExpected(String username, AuthUser expected) {
        Optional<AuthUser> result = sut.findByUsername(username);

        Optional<AuthUser> expectedOptional = Optional.ofNullable(expected);
        assertThat(result.isPresent()).isEqualTo(expectedOptional.isPresent());
        if (result.isEmpty() || expectedOptional.isEmpty()) return;
        AuthUser resultUser = result.get();
        assertThat(resultUser.getId()).isEqualTo(expected.getId());
        assertThat(resultUser.getRole()).isEqualTo(expected.getRole());
        assertThat(resultUser.getUsername()).isEqualTo(expected.getUsername());
        assertThat(resultUser.getPassword()).isNotEmpty();
    }

    @ParameterizedTest
    @CsvSource({"testUserA,true", "admin,false"})
    void givenUsername_whenExistsByUsername_thenExpected(String username, boolean expected) {
        boolean result = sut.existsByUsername(username);

        assertThat(result).isEqualTo(expected);
    }

    @Test
    void givenAuthUser_whenCreate_thenSuccess() {
        AuthUser authUser = JdbcAuthUser.builder()
                .id(UUID.randomUUID())
                .role(DomainRole.USER)
                .username("test")
                .password("some-test-password")
                .build();

        boolean result = sut.create(authUser);

        assertThat(result).isTrue();
    }

    @Test
    void givenExistUuid_whenCreate_thenException() {
        AuthUser authUser = JdbcAuthUser.builder()
                .id(JdbcTestHelper.testUserIdA)
                .role(DomainRole.USER)
                .username("test")
                .password("some-test-password")
                .build();

        Throwable result = catchThrowable(() -> sut.create(authUser));

        assertThat(result).isInstanceOf(JdbcDbException.class);
    }

    @Test
    void givenExistsUsername_whenCreate_thenException() {
        AuthUser authUser = JdbcAuthUser.builder()
                .id(UUID.randomUUID())
                .role(DomainRole.USER)
                .username(JdbcTestHelper.testAuthUserB.getUsername())
                .password("some-test-password")
                .build();

        Throwable result = catchThrowable(() -> sut.create(authUser));

        assertThat(result).isInstanceOf(JdbcDbException.class);
    }
}
