package com.example.demo;

import org.springframework.data.annotation.Id;

public class Transactions {

    @Id
    private String transactionId;
    private Double transactionAmount;
    private String accountType;
    private String customerId;
    private String transactionDate;

    public String getTransactionId() {
        return transactionId;
    }

    public Double getTransactionAmount() {
        return transactionAmount;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getTransactionDate() {
        return transactionDate;
    }

    public Transactions(String transactionId, Double transactionAmount, String accountType, String customerId, String transactionDate) {
        this.transactionId = transactionId;
        this.transactionAmount = transactionAmount;
        this.accountType = accountType;
        this.customerId = customerId;
        this.transactionDate = transactionDate;
    }
}
