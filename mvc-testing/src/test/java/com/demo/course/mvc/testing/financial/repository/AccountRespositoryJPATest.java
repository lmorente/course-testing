package com.demo.course.mvc.testing.financial.repository;

import com.demo.course.mvc.testing.financial.model.Account;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class AccountRespositoryJPATest {

    /**IMPORTANT: DataJpaTEST meets the independence of testing. (To prevent rollback @Rollback(false))**/

    @Autowired
    private AccountRepository accountRepository;

    @Test
    void testFindById() {
        Optional<Account> accountOpt = accountRepository.findById(5L);
        assertTrue(accountOpt.isPresent());
        assertEquals("Emma Rowe", accountOpt.get().getPerson());
    }

    @Test
    public void testFindAll(){
        List<Account> accounts = (List<Account>) accountRepository.findAll();
        assertFalse(accounts.isEmpty());
    }

    @Test
    public void testSaveAccount() {
        Account account = new Account();
        account.setPerson("Lokesh");
        account.setBalance(BigDecimal.ZERO);
        Account save = accountRepository.save(account);
        assertNotNull(save.getId());
    }

    @Test
    public void testFindByPerson(){
        Optional<Account> accountOpt = accountRepository.findByPerson("Emma Rowe");
        assertTrue(accountOpt.isPresent());
    }

    @Test
    public void testNotFoundPerson(){
        Optional<Account> accountOpt = accountRepository.findByPerson("Rod");
        assertThrows(NoSuchElementException.class, accountOpt::orElseThrow);
        //assertThrows(NoSuchElementException.class, ()-> accountOpt.orElseThrow());
        assertFalse(accountOpt.isPresent());
    }

    @Test
    public void testUpdateData(){
        Account account = accountRepository.findById(3L).orElseThrow();
        account.setBalance(BigDecimal.ZERO);

        Account modifyAccount = accountRepository.save(account);

        assertEquals(BigDecimal.ZERO, modifyAccount.getBalance());
    }

    @Test
    void deleteAccount() {
        accountRepository.deleteById(2L);
        Optional<Account> opt = accountRepository.findById(2L);
        assertFalse(opt.isPresent());
    }
}


