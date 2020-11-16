package com.example.demo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@EnableMongoRepositories
@SpringBootTest
class AppControllerTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private AccountTypesRepository accountTypesRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AppController appController;

    @BeforeEach
    void configuration() {
        Customer c1 = new Customer("1", "Andrzej", "Kowalski", "1");
        Customer c2 = new Customer("2", "Adam", "Nowak", "10");
        Customer c3 = new Customer("3", "Pawel", "Piotr", "20");
        List<Customer> customers = Arrays.asList(c1, c2, c3);
        Customer generatedCustomer;
        for (Customer c : customers) {
            generatedCustomer = customerRepository.save(c);
        }
        AccountTypes at1 = new AccountTypes("1", "checking account");
        AccountTypes at2 = new AccountTypes("2", "saving account");
        List<AccountTypes> accountTypes = Arrays.asList(at1, at2);
        AccountTypes generatedAccountType;
        for (AccountTypes c : accountTypes) {
            generatedAccountType = accountTypesRepository.save(c);
        }
        Transactions t1 = new Transactions("1", 10D, "1", "1", "2020.10.11");
        Transactions t2 = new Transactions("2", 20D, "1", "2", "2020.10.11");
        Transactions t3 = new Transactions("3", 30D, "2", "3", "2020.10.11");
        List<Transactions> transactions = Arrays.asList(t1, t2, t3);
        Transactions generatedTransactions;
        for (Transactions t : transactions) {
            generatedTransactions = transactionRepository.save(t);
        }
    }

    @Test
    void checkIfAllDataIsReturned() {
        List<String> response1 = Arrays.asList(
                "1. (Data transakcji: 2020.10.11, Identyfikator transakcji: 1, Kwota transakcji: 10,000000 rodzaj rachunku: checking account, Imię zlecającego: Andrzej, Nazwisko Zlecającego: Kowalski",
                "2. (Data transakcji: 2020.10.11, Identyfikator transakcji: 2, Kwota transakcji: 20,000000 rodzaj rachunku: checking account, Imię zlecającego: Adam, Nazwisko Zlecającego: Nowak",
                "3. (Data transakcji: 2020.10.11, Identyfikator transakcji: 3, Kwota transakcji: 30,000000 rodzaj rachunku: saving account, Imię zlecającego: Pawel, Nazwisko Zlecającego: Piotr"
        );
        assertTrue(appController.getTransactions(Set.of("ALL"), Set.of("ALL")).containsAll(response1) &&
                response1.containsAll(appController.getTransactions(Set.of("ALL"), Set.of("ALL"))));
    }

    @Test
    void checkIfAllDataForCustomersIsReturned() {
        List<String> response2 = Arrays.asList(
                "1. (Data transakcji: 2020.10.11, Identyfikator transakcji: 1, Kwota transakcji: 10,000000 rodzaj rachunku: checking account, Imię zlecającego: Andrzej, Nazwisko Zlecającego: Kowalski",
                "2. (Data transakcji: 2020.10.11, Identyfikator transakcji: 2, Kwota transakcji: 20,000000 rodzaj rachunku: checking account, Imię zlecającego: Adam, Nazwisko Zlecającego: Nowak"
        );
        assertTrue(appController.getTransactions(Set.of("1"), Set.of()).containsAll(response2) &&
                response2.containsAll(appController.getTransactions(Set.of("1"), Set.of())));
    }

    @Test
    void checkIfAllDataForAccountTypeIsReturned() {
        List<String> response3 = Collections.singletonList(
                "1. (Data transakcji: 2020.10.11, Identyfikator transakcji: 1, Kwota transakcji: 10,000000 rodzaj rachunku: checking account, Imię zlecającego: Andrzej, Nazwisko Zlecającego: Kowalski"
        );
        assertTrue(appController.getTransactions(Set.of(), Set.of("1")).containsAll(response3) &&
                response3.containsAll(appController.getTransactions(Set.of(), Set.of("1"))));
    }

    @Test
    void checkIfAllDataForSpecificCustomerIsReturned() {
        List<String> response4 = Collections.singletonList(
                "1. (Data transakcji: 2020.10.11, Identyfikator transakcji: 3, Kwota transakcji: 30,000000 rodzaj rachunku: saving account, Imię zlecającego: Pawel, Nazwisko Zlecającego: Piotr"
        );
        assertTrue(appController.getTransactions(Set.of(), Set.of("3")).containsAll(response4) &&
                response4.containsAll(appController.getTransactions(Set.of(), Set.of("3"))));
    }

    @Test
    void checkWhatHappensWhenUserSendWrongData() {
        List<String> wrongArgumentsResponse = Collections.singletonList(
                "No records were found in this input combination"
        );
        assertTrue(appController.getTransactions(Set.of("ala ma kota"), Set.of("a kot ma ale")).containsAll(wrongArgumentsResponse) &&
                wrongArgumentsResponse.containsAll(appController.getTransactions(Set.of("ala ma kota"), Set.of("a kot ma ale"))));
    }

    @Test
    void checkWhatHappensWhenUserSendsWrongCustomerIdOrAccountType() {
        List<String> wrongArgumentsResponse = Collections.singletonList(
                "No records were found in this input combination"
        );
        assertTrue(appController.getTransactions(Set.of("5"), Set.of("5")).containsAll(wrongArgumentsResponse) &&
                wrongArgumentsResponse.containsAll(appController.getTransactions(Set.of("5"), Set.of("5"))));
    }
}