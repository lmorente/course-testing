package com.demo.course.mvc.testing.financial.service;

import com.demo.course.mvc.testing.financial.exception.BankException;
import com.demo.course.mvc.testing.financial.model.Account;

import java.math.BigDecimal;

public interface BankOperationsService {

    Account findAccountById(Long id);

    int getTotalTransferBank(Long id);

    BigDecimal getBalanceAccount(Long id);

    void transfer(Long originAccountId, Long destiantionAccountId, BigDecimal amount, Long bankId) throws BankException;

}
