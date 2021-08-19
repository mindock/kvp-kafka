package com.kvp.domain;

import java.util.Arrays;

// 참조) https://velog.io/@kyle/%EC%9E%90%EB%B0%94-Enum-%EA%B8%B0%EB%B3%B8-%EB%B0%8F-%ED%99%9C%EC%9A%A9
public enum Grade {
    BRONZE(0, 100_000L),
    SILVER(100_000L, 200_000L),
    GOLD(200_000L, 500_000L),
    Platinum(500_000L, 1_000_000L),
    VIP(1_000_000L, Long.MAX_VALUE);

    private final long min;
    private final long max;

    Grade(long min, long max) {
        this.min = min;
        this.max = max;
    }

    public static Grade getGrade(long amount) {
        return Arrays.stream(values())
                .filter(grade -> grade.min < amount && grade.max >= amount)
                .findFirst()
                .orElse(Grade.BRONZE);
    }
}
