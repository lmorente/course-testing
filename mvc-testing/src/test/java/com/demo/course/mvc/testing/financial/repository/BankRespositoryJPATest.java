package com.demo.course.mvc.testing.financial.repository;

import com.demo.course.mvc.testing.financial.model.Bank;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class BankRespositoryJPATest {

    /**IMPORTANT: DataJpaTEST meets the independence of testing. (To prevent rollback @Rollback(false))**/

    @Autowired
    private BankRepository bankRepository;

    @Test
    void testFindById() {
        Bank bank = bankRepository.findById(2L).orElseThrow();
        assertEquals("Bank Pic", bank.getName());
    }

    @Test
    public void testFindAll(){
        List<Bank> accounts = (List<Bank>) bankRepository.findAll();
        assertFalse(accounts.isEmpty());
    }

    @Test
    public void testSaveBank() {
        Bank bank = new Bank();
        bank.setTotalTransfer(0);
        bank.setBalance(new BigDecimal("345345.56"));
        bank.setName("New bank");

        Bank save = bankRepository.save(bank);

        assertEquals(bank, save);
    }


    @Test
    public void testUpdateData(){
        Bank bank = bankRepository.findById(2L).orElseThrow();
        bank.setName("New Pic bank");

        Bank modifyBank = bankRepository.save(bank);

        assertEquals(bank, modifyBank);
    }

    @Test
    void deleteAccount() {
        bankRepository.deleteById(2L);
        Optional<Bank> opt = bankRepository.findById(2L);
        assertFalse(opt.isPresent());
    }
}


