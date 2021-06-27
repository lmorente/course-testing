package com.demo.courses.testing.examples.models;

import com.demo.courses.testing.examples.exceptions.AccountBalanceException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class Bank {

    private List<BankAccount> accounts;
    private String name;

    public void add(BankAccount account){
        this.accounts.add(account);
    }

    public void toTransfer(BankAccount origen, BankAccount destination, BigDecimal transfer)
            throws AccountBalanceException {
        origen.debit(transfer);
        destination.credit(transfer);
    }

    public Bank() {
        this.accounts = new ArrayList<>();
        this.name = name;
    }
}
