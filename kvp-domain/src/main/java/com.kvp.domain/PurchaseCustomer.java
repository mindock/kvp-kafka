package com.kvp.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PurchaseCustomer {
    private String name;
    private Long purchaseAmount;
}
