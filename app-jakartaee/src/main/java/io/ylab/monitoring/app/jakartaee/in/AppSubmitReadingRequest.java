package io.ylab.monitoring.app.jakartaee.in;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.MonthDay;
import java.time.Year;
import java.time.ZoneOffset;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AppSubmitReadingRequest {
    @JsonbProperty
    @Min(1)
    @Max(12)
    private int month;

    @JsonbProperty
    @Positive
    private int year;

    @JsonbProperty
    @Positive
    private long value;

    @JsonbProperty("meter_name")
    @NotEmpty
    private String meterName;

    public Instant getPeriod() {
        int currentYear = year;
        int currentMonth = month;

        if (currentYear <= 0) {
            currentYear = Year.now().getValue();
        }

        if (currentMonth <= 0) {
            currentMonth = MonthDay.now().getMonthValue();
        }

        return LocalDate.of(currentYear, currentMonth, 1)
                .atStartOfDay()
                .toInstant(ZoneOffset.UTC);
    }
}
