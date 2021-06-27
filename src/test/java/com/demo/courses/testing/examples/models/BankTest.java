package com.demo.courses.testing.examples.models;

import com.demo.courses.testing.examples.exceptions.AccountBalanceException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {

    @Test
    void toTransferMoney() throws AccountBalanceException {
        BankAccount accountOrigen = new BankAccount("John Smith", new BigDecimal("15234.78"));
        BankAccount accountDestination = new BankAccount("John Doe", new BigDecimal("23456.10"));

        Bank bank = new Bank(null, "Santander");
        bank.toTransfer(accountOrigen, accountDestination, new BigDecimal("2000"));

        assertEquals(new BigDecimal("13234.78"), accountOrigen.getBalance());
        assertEquals(new BigDecimal("25456.10"), accountDestination.getBalance());
    }

    @Test
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

        assertEquals(4, bank.getAccounts().size());
    }


}