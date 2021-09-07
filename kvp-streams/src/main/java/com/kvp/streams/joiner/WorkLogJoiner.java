package com.kvp.streams.joiner;

import com.kvp.domain.DailyWorkLog;
import com.kvp.domain.WorkLog;
import org.apache.kafka.streams.kstream.ValueJoiner;

import java.time.Duration;

public class WorkLogJoiner implements ValueJoiner<WorkLog, WorkLog, DailyWorkLog> {

    @Override
    public DailyWorkLog apply(WorkLog workLog, WorkLog offWorkLog) {
        return new DailyWorkLog(workLog.getEmployee(),
                workLog.getWorkDateTime().toLocalDate(),
                Duration.between(workLog.getWorkDateTime(), offWorkLog.getWorkDateTime()).toHours());
    }
}
