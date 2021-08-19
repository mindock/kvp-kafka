package com.kvp.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {
    private String name;
    private long totalPurchaseAmount;
    private Grade grade;

    protected Customer(String name, long amount) {
        this.name = name;
        this.totalPurchaseAmount = amount;
        this.grade = Grade.getGrade(this.totalPurchaseAmount);
    }

    public void addPurchaseAmount(long purchaseAmount) {
        this.totalPurchaseAmount += purchaseAmount;
        this.grade = Grade.getGrade(this.totalPurchaseAmount);
    }

    public static Customer of(PurchaseCustomer purchaseCustomer) {
        return new Customer(purchaseCustomer.getName(), purchaseCustomer.getPurchaseAmount());
    }
}
