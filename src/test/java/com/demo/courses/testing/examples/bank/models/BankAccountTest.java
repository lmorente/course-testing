package com.demo.courses.testing.examples.bank.models;

import com.demo.courses.testing.examples.bank.exceptions.AccountBalanceException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class BankAccountTest {

    private static final String ERROR_NULL_ACCOUNT_VALUE = "Null value on account";
    private static final String ERROR_INSUFFICIENT_MONEY = "Insufficient money in the account";

    private BankAccount account;

    @BeforeEach
    void initTestMethod(){
        this.account = new BankAccount("John Smith", new BigDecimal("15234.78"));
    }

    @AfterEach
    void tearDown(){
        System.out.println("End test method");
    }

    @Test
    void debitAccount() throws AccountBalanceException {
        account.debit(new BigDecimal(1000));
        assertEquals(new BigDecimal("14234.78"), account.getBalance(), () -> "Debit method error");
    }

    @Test
    void debitAccountNull() {
        BankAccount johnAccount = new BankAccount("John Smith", null);
        Exception e = assertThrows(AccountBalanceException.class, () -> johnAccount.debit(new BigDecimal("100.00")));
        assertEquals(ERROR_NULL_ACCOUNT_VALUE, e.getMessage());
    }

    @Test
    void debitAccountWithNullAmount() {
        Exception e = assertThrows(AccountBalanceException.class, () -> account.debit(null));
        assertEquals(ERROR_NULL_ACCOUNT_VALUE, e.getMessage());
    }


    @Test
    void debitAccountInsufficient() {
        Exception e = assertThrows(AccountBalanceException.class, () -> account.debit(new BigDecimal("100000.00")));
        assertEquals(ERROR_INSUFFICIENT_MONEY, e.getMessage());
    }

    @Test
    void creditAccount() throws AccountBalanceException {
        account.credit(new BigDecimal("1000"));
        assertEquals(new BigDecimal("16234.78"), account.getBalance(), "Credit method error");
    }

    @Test
    void creditNullAccount() throws AccountBalanceException {
        BankAccount johnAccount = new BankAccount("John Doe", null);
        johnAccount.credit(new BigDecimal("1000"));
        assertEquals(new BigDecimal("1000"), johnAccount.getBalance(), () -> "Credit error with null value account");
    }

    @Test
    void creditNullAmount() throws AccountBalanceException {
        BankAccount johnAccount = new BankAccount("John Doe", null);
        Exception e = assertThrows(AccountBalanceException.class, () -> johnAccount.credit(null));
        assertEquals(ERROR_NULL_ACCOUNT_VALUE, e.getMessage());
    }

    @DisplayName("Test random amount")
    @RepeatedTest(value = 5, name = "{displayName} - Repeared number  {currentRepetition} / {totalRepetition} ")
    void debitAccountRandom() throws AccountBalanceException {
        //format random value
        //info.getCurrentRepetition
        Double amountDouble  = Math.random() * (10+100);
        DecimalFormat df = new DecimalFormat("0.00");
        String amountString = df.format(amountDouble);
        amountString = amountString.replace(",", ".");

        BigDecimal amount = new BigDecimal(amountString);
        BigDecimal expected = account.getBalance().subtract(amount);

        account.debit(amount);
        assertEquals(expected, account.getBalance(), () -> "Debit method error");
    }

    @Tag("param")
    @ParameterizedTest
    @ValueSource(strings = {"1234.56", "6543.23", "6343.2", "2425", "134"})
    void creditAccountParametrized(String value) throws AccountBalanceException {
        account.credit(new BigDecimal(value));
        assertTrue((account.getBalance().compareTo(BigDecimal.ZERO) > 0));
    }


    @Tag("env")
    @Nested
    @DisplayName("Environment")
    class DevEnvironmentTest {
        @Test
        @DisplayName("Test debit account on dev environment")
        void debitAccountDev() throws AccountBalanceException {
            Boolean isdev = "DEV".equals(System.getProperty("ENV"));
            assumeTrue(isdev);
            account.debit(new BigDecimal(1000));
            assertEquals(new BigDecimal("14234.78"), account.getBalance(), () -> "Debit method error");
        }

        @Test
        @DisplayName("Test debit account on dev environment 2")
        void debitAccountDev2() {
            Boolean isdev = "DEV".equals(System.getProperty("ENV"));
            assumingThat(isdev, ()-> {
                account.debit(new BigDecimal(1000));
                assertEquals(new BigDecimal("14234.78"), account.getBalance(), () -> "Debit method error");
            });
        }
    }
}