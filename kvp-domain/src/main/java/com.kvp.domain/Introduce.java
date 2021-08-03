package com.kvp.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder(toBuilder = true)
public class Introduce {
    private String name;
    private Long age;

    public void addAge() {
        age += 10;
    }

    public void maskingName() {
        if (name.length() <= 2) {
            return;
        }

        name = name.replace(name.substring(1, name.length() - 1), "*");
    }
}
