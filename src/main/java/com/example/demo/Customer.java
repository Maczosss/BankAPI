package com.example.demo;

import org.springframework.data.annotation.Id;

public class Customer {
    @Id
    public String id;
    public String name;
    public String lastname;
    public String bankLastLoginBalance;

    public Customer(){}


    public Customer(String id, String name, String lastname, String bankLastLoginBalance) {
        this.id = id;
        this.name = name;
        this.lastname = lastname;
        this.bankLastLoginBalance = bankLastLoginBalance;
    }
    public String getName() {
        return name;
    }

    public String getLastname() {
        return lastname;
    }
}
