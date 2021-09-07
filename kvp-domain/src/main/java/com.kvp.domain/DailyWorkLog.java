package com.kvp.domain;

import java.time.Duration;
import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DailyWorkLog {
    private static int BASE_WORK_HOUR = 9;
    private Employee employee;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate workDate;
    private long workHour;

    public boolean isOverWork() {
        return getOverWorkTime() > 0L;
    }

    public long getOverWorkTime() {
        return workHour - BASE_WORK_HOUR;
    }
}
