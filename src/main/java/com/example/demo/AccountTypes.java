package com.example.demo;

import org.springframework.data.annotation.Id;

public class AccountTypes {

    @Id
    private String accountType;
    private String name;

    public AccountTypes(String accountType, String name) {
        this.accountType = accountType;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
