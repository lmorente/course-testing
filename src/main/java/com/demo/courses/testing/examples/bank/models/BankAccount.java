package com.demo.courses.testing.examples.bank.models;

import com.demo.courses.testing.examples.bank.exceptions.AccountBalanceException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@NoArgsConstructor
public class BankAccount  {

    private String customer;

    private BigDecimal balance;

    private Bank bank;

    public BankAccount(String customer, BigDecimal balance) {
        this.customer = customer;
        this.balance = balance;
    }

    public void debit(BigDecimal amount) throws AccountBalanceException {
        if(Objects.nonNull(this.balance) && Objects.nonNull(amount)){
            if(!(amount.compareTo(this.balance) > -1)) {
                this.balance = this.balance.subtract(amount);
            }else {
                throw new AccountBalanceException("Insufficient money in the account");
            }
        } else {
            throw new AccountBalanceException("Null value on account");
        }
    }

    public void credit(BigDecimal amount) throws AccountBalanceException {
        if(Objects.nonNull(amount)){
            if(Objects.nonNull(this.balance)) {
                this.balance = this.balance.add(amount);
            } else {
                this.balance = amount;
            }
        } else {
            throw new AccountBalanceException("Null value on account");
        }
    }
}
