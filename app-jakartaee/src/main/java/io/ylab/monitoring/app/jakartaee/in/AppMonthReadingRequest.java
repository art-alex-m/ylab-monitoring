package io.ylab.monitoring.app.jakartaee.in;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.ws.rs.QueryParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
public class AppMonthReadingRequest {
    @QueryParam("month")
    @Min(1)
    @Max(12)
    private int month;

    @QueryParam("year")
    @Min(0)
    private int year;

    public Instant getPeriod() {
        int currentYear = year;

        if (currentYear <= 0) {
            currentYear = Year.now().getValue();
        }

        return LocalDate.of(currentYear, month, 1)
                .atStartOfDay()
                .toInstant(ZoneOffset.UTC);
    }
}
