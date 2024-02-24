package io.ylab.monitoring.app.springboot.in;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Year;
import java.time.ZoneOffset;

/**
 * Запрос получения показаний счетчиков за период
 *
 * <p>
 * <a href="https://stackoverflow.com/questions/33503374/merge-two-query-params-in-one-single-object-with-jax-rs">Merge two query params in one single object with JAX-RS</a>
 * </p>
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Hidden
public class AppMonthReadingRequest {

    @Schema(example = "2")
    @Min(1)
    @Max(12)
    private int month;

    /**
     * Если поле указано, то оно должно быть больше нуля.
     * Если не введено или 0, то используется значение текущего года
     */
    @Min(0)
    private int year;

    @JsonIgnore
    public Instant getPeriod() {
        int currentYear = year;

        if (currentYear == 0) {
            currentYear = Year.now().getValue();
        }

        return LocalDate.of(currentYear, month, 1)
                .atStartOfDay()
                .toInstant(ZoneOffset.UTC);
    }
}
