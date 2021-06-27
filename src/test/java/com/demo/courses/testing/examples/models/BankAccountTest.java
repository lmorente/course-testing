package com.demo.courses.testing.examples.models;

import com.demo.courses.testing.examples.exceptions.AccountBalanceException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private static final String ERROR_NULL_ACCOUNT_VALUE = "Null value on account";
    private static final String ERROR_INSUFFICIENT_MONEY = "Insufficient money in the account";

    @Test
    void debitAccount() throws AccountBalanceException {
        BankAccount account = new BankAccount("John Smith", new BigDecimal("15234.78"));
        account.debit(new BigDecimal(1000));
        assertEquals(new BigDecimal("14234.78"), account.getBalance());
    }

    @Test
    void debitAccountNull() {
        BankAccount account = new BankAccount("John Smith", null);
        Exception e = assertThrows(AccountBalanceException.class, () -> account.debit(new BigDecimal("100.00")));
        assertEquals(ERROR_NULL_ACCOUNT_VALUE, e.getMessage());
    }

    @Test
    void debitAccountWithNullAmount() {
        BankAccount account = new BankAccount("John Smith", new BigDecimal("15234.78"));
        Exception e = assertThrows(AccountBalanceException.class, () -> account.debit(null));
        assertEquals(ERROR_NULL_ACCOUNT_VALUE, e.getMessage());
    }


    @Test
    void debitAccountInsufficient() {
        BankAccount account = new BankAccount("John Smith", new BigDecimal("10.78"));
        Exception e = assertThrows(AccountBalanceException.class, () -> account.debit(new BigDecimal("100.00")));
        assertEquals(ERROR_INSUFFICIENT_MONEY, e.getMessage());
    }

    @Test
    void creditAccount() throws AccountBalanceException {
        BankAccount account = new BankAccount("John Doe", new BigDecimal("21278.33"));
        account.credit(new BigDecimal("1000"));
        assertEquals(new BigDecimal("22278.33"), account.getBalance());
    }

    @Test
    void creditNullAccount() throws AccountBalanceException {
        BankAccount account = new BankAccount("John Doe", null);
        account.credit(new BigDecimal("1000"));
        assertEquals(new BigDecimal("1000"), account.getBalance());
    }

    @Test
    void creditNullAmount() throws AccountBalanceException {
        BankAccount account = new BankAccount("John Doe", null);
        Exception e = assertThrows(AccountBalanceException.class, () -> account.credit(null));
        assertEquals(ERROR_NULL_ACCOUNT_VALUE, e.getMessage());
    }

}