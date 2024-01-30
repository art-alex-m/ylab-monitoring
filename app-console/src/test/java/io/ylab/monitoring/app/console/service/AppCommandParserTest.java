package io.ylab.monitoring.app.console.service;

import io.ylab.monitoring.app.console.model.AppCommand;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AppCommandParserTest {
    public static Stream<Arguments> givenVariousTextCommands_whenParse_thenSuccess() {
        return Stream.of(
                Arguments.of("test 1 2 3", new AppCommand(List.of("test", "1", "2", "3"))),
                Arguments.of("test  ", new AppCommand(List.of("test"))),
                Arguments.of("", new AppCommand(List.of(""))),
                Arguments.of("some-test <arg1>   <arg2>", new AppCommand(List.of("some-test", "<arg1>", "<arg2>")))
        );
    }

    @ParameterizedTest
    @MethodSource
    void givenVariousTextCommands_whenParse_thenSuccess(String text, AppCommand expected) {
        AppCommandParser sut = new AppCommandParser();

        AppCommand result = sut.parse(text);

        assertThat(result.hashCode()).isEqualTo(expected.hashCode());
    }
}
