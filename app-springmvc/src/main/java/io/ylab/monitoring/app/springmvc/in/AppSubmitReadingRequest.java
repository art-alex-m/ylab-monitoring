package io.ylab.monitoring.app.springmvc.in;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @Min(1)
    @Max(12)
    private int month;

    @Positive
    private int year;

    @Positive
    private long value;

    @JsonProperty("meter_name")
    @NotEmpty
    private String meterName;

    @JsonIgnore
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
