package io.ylab.monitoring.app.servlets.in;

import jakarta.servlet.http.HttpServletRequest;
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
import java.util.Optional;

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
public class AppMonthReadingRequest {
    @Min(1)
    @Max(12)
    private int month;

    @Min(0)
    private int year;

    public AppMonthReadingRequest(HttpServletRequest request) {
        this.month = intParser(request.getParameter("month"));
        this.year = intParser(request.getParameter("year"));
    }

    public Instant getPeriod() {
        int currentYear = year;

        if (currentYear <= 0) {
            currentYear = Year.now().getValue();
        }

        return LocalDate.of(currentYear, month, 1)
                .atStartOfDay()
                .toInstant(ZoneOffset.UTC);
    }

    private int intParser(String rawInt) {
        return Optional.ofNullable(rawInt)
                .filter(raw -> !raw.isEmpty())
                .map(Integer::parseInt)
                .orElse(0);
    }
}
