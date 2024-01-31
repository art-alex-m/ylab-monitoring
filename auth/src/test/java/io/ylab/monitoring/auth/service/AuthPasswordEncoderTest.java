package io.ylab.monitoring.auth.service;

import io.ylab.monitoring.domain.auth.service.PasswordEncoder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AuthPasswordEncoderTest {

    private final static String testPassword = "123456";

    private final static String testSalt = "test-salt";

    private final static String expectedTestWithTestSalt = "CDjNpsPUEY1QDcNMcLSAzYXQMVNacj5N+mLFJLesN+HtIGBjZWZinSM+NZ4eqarlFmTUH5PjgU8COALj6zUHhg==";


    public static Stream<Arguments> givenPasswordAndSalt_whenMatches_thenSuccess() {
        return Stream.of(
                Arguments.of(testPassword, testSalt, expectedTestWithTestSalt, true),
                Arguments.of(null, testSalt, expectedTestWithTestSalt, false),
                Arguments.of(testPassword, testPassword, null, false),
                Arguments.of("", testSalt, expectedTestWithTestSalt, false),
                Arguments.of(testPassword, "", expectedTestWithTestSalt, false),
                Arguments.of("", "", "", false),
                Arguments.of(testPassword, testPassword, "", false),
                Arguments.of(testPassword, "98765432", expectedTestWithTestSalt, false),
                Arguments.of("", "98765432", "", false)
        );
    }

    @Test
    void givenTestSaltAndTestPassword_whenEncode_thenSuccess() {
        PasswordEncoder passwordEncoder = new AuthPasswordEncoder(testSalt.getBytes());

        String result = passwordEncoder.encode(testPassword);

        assertThat(result).isNotNull().isEqualTo(expectedTestWithTestSalt);
    }

    @ParameterizedTest
    @MethodSource
    void givenPasswordAndSalt_whenMatches_thenSuccess(String password, String salt, String hash, boolean expected) {
        PasswordEncoder passwordEncoder = new AuthPasswordEncoder(salt.getBytes());

        boolean result = passwordEncoder.matches(password, hash);

        assertThat(result).isEqualTo(expected);
    }
}