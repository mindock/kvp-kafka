package com.kvp.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Developer {
    private static final int JUNIOR_YEAR_LIMIT = 3;
    private String name;
    private Long age;
    private Language language;
    private int year;

    public boolean isJunior() {
        return year <= JUNIOR_YEAR_LIMIT;
    }
    public boolean isSenior() { return !isJunior(); }

    public boolean compare(Language language) {
        return this.language == language;
    }

    public SimpleDeveloper toSimple() {
        return new SimpleDeveloper(age, year);
    }
}
