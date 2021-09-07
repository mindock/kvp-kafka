package com.kvp.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class WorkLog {
    private Employee employee;
    private WorkType workType;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime  workDateTime;

    public boolean isWork() {
        return workType == WorkType.WORK;
    }

    public boolean isOffWork() {
        return workType == WorkType.OFF_WORK;
    }
}
