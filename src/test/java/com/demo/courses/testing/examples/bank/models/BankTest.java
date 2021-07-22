package com.demo.courses.testing.examples.bank.models;

import com.demo.courses.testing.examples.bank.exceptions.AccountBalanceException;
import org.junit.jupiter.api.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Init class test");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("end class test");
    }

    @Test
    @DisplayName("Test transfer test money: assessment of origin and destination account")
    void toTransferMoney() throws AccountBalanceException {
        BankAccount accountOrigen = new BankAccount("John Smith", new BigDecimal("15234.78"));
        BankAccount accountDestination = new BankAccount("John Doe", new BigDecimal("23456.10"));

        Bank bank = new Bank(null, "Santander");
        bank.toTransfer(accountOrigen, accountDestination, new BigDecimal("2000"));

        assertEquals(new BigDecimal("13234.78"), accountOrigen.getBalance(),
                () -> "Transfer error in origin account");
        assertEquals(new BigDecimal("25456.10"), accountDestination.getBalance(),
                () -> "Transfer error in destination account");
    }

    @Test
    @DisplayName("Test related account")
    void relatedAccounts(){
        BankAccount accountOne = new BankAccount("John Smith", new BigDecimal("15234.78"));
        BankAccount accountTwo = new BankAccount("John Doe", new BigDecimal("23456.10"));
        BankAccount accountThree = new BankAccount("Clarice Williams", new BigDecimal("15234.78"));
        BankAccount accountFour = new BankAccount("Alisha Murphy", new BigDecimal("23456.10"));

        Bank bank = new Bank();
        bank.setName("BBVA");
        bank.add(accountOne);
        bank.add(accountTwo);
        bank.add(accountThree);
        bank.add(accountFour);

        assertAll(
                ()-> assertEquals(4, bank.getAccounts().size()),
                ()-> assertEquals("BBVA", accountOne.getBank().getName()),
                ()-> assertTrue(bank.getAccounts().stream().anyMatch(c -> c.getCustomer().equals("Clarice Williams")))
        );
    }
}