package com.demo.course.mvc.testing.financial.service.impl;

import com.demo.course.mvc.testing.financial.exception.BankException;
import com.demo.course.mvc.testing.financial.model.Account;
import com.demo.course.mvc.testing.financial.model.Bank;
import com.demo.course.mvc.testing.financial.repository.AccountRepository;
import com.demo.course.mvc.testing.financial.repository.BankRepository;
import com.demo.course.mvc.testing.financial.service.BankOperationsService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@NoArgsConstructor
@AllArgsConstructor
public class BankOperationsServiceImpl implements BankOperationsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private BankRepository bankRepository;

    @Override
    public Account findAccountById(Long id) {
        return accountRepository.findById(id).orElseThrow();
    }

    @Override
    public int getTotalTransferBank(Long id) {
        Optional<Bank> bankOpt = bankRepository.findById(id);
        return bankOpt.isPresent() ? bankOpt.get().getTotalTransfer() : 0;
    }

    @Override
    public BigDecimal getBalanceAccount(Long id) {
        Optional<Account> accountOpt = accountRepository.findById(id);
        return accountOpt.isPresent() ? accountOpt.get().getBalance() : new BigDecimal("0");
    }

    @Override
    public void transfer(Long originAccountId, Long destiantionAccountId,
                         BigDecimal amount, Long bankId) throws BankException {
        Optional<Account> originOpt = accountRepository.findById(originAccountId);
        Optional<Account> destinationOpt = accountRepository.findById(destiantionAccountId);

        if(originOpt.isPresent() && destinationOpt.isPresent()) {
            Account origin = originOpt.get();
            origin.debit(amount);
            accountRepository.save(origin);

            Account destination = destinationOpt.get();
            destination.credit(amount);
            accountRepository.save(destination);

            Bank bank = bankRepository.findById(bankId).orElseThrow();
            int total = bank.getTotalTransfer();
            bank.setTotalTransfer(++total);
            bankRepository.save(bank);
        }
    }
}
