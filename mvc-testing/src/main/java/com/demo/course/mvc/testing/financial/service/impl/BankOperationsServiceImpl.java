package com.demo.course.mvc.testing.financial.service.impl;

import com.demo.course.mvc.testing.financial.exception.BankException;
import com.demo.course.mvc.testing.financial.model.Account;
import com.demo.course.mvc.testing.financial.model.Bank;
import com.demo.course.mvc.testing.financial.repository.AccountRepository;
import com.demo.course.mvc.testing.financial.repository.BankRepository;
import com.demo.course.mvc.testing.financial.service.BankOperationsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class BankOperationsServiceImpl implements BankOperationsService {

    private AccountRepository accountRepository;

    private BankRepository bankRepository;

    @Override
    public Account findAccountById(Long id) {
        return accountRepository.findById(id);
    }

    @Override
    public int getTotalTransferBank(Long id) {
        Bank bank = bankRepository.findById(id);
        return Objects.nonNull(bank) ? bank.getTotalTransfer() : 0;
    }

    @Override
    public BigDecimal getBalanceAccount(Long id) {
        Account account = accountRepository.findById(id);
        return Objects.nonNull(account) ? account.getBalance() : new BigDecimal("0");
    }

    @Override
    public void transfer(Long originAccountId, Long destiantionAccountId,
                         BigDecimal amount, Long bankId) throws BankException {
        Account origin = accountRepository.findById(originAccountId);
        origin.debit(amount);
        accountRepository.update(origin);

        Account destination = accountRepository.findById(destiantionAccountId);
        destination.credit(amount);
        accountRepository.update(destination);

        Bank bank = bankRepository.findById(bankId);
        int total = bank.getTotalTransfer();
        bank.setTotalTransfer(++total);
        bankRepository.update(bank);

    }
}
