package com.demo.course.mvc.testing.financial.service.impl;

import com.demo.course.mvc.testing.financial.data.FakeData;
import com.demo.course.mvc.testing.financial.exception.BankException;
import com.demo.course.mvc.testing.financial.model.Account;
import com.demo.course.mvc.testing.financial.model.Bank;
import com.demo.course.mvc.testing.financial.repository.AccountRepository;
import com.demo.course.mvc.testing.financial.repository.BankRepository;
import com.demo.course.mvc.testing.financial.service.BankOperationsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BankOperationsServiceTest {

    private FakeData data;

    @Mock
    AccountRepository accountRepository;

    @Mock
    BankRepository bankRepository;

    @InjectMocks
    BankOperationsService service = new BankOperationsServiceImpl(accountRepository, bankRepository);

    @BeforeEach
    void initData(){
        this.data = new FakeData();
    }

    @Test
    void testFindAccountById() {
        when(accountRepository.findById(2L)).thenReturn(Optional.of(data.getAccount002()));
        Account account = service.findAccountById(2L);
        assertSame(data.getAccount002(), account);
    }

    @Test
    void testGetTotalTransferBank() {
        when(bankRepository.findById(1L)).thenReturn(Optional.of(data.getBank001()));
        when(bankRepository.findById(2L)).thenReturn(Optional.of(data.getBank002()));

        int totalOne = service.getTotalTransferBank(1L);
        int totalTwo = service.getTotalTransferBank(2L);

        verify(bankRepository, times(2)).findById(anyLong());
        assertEquals(500, totalOne);
        assertEquals(250, totalTwo);
    }

   @Test
    void testGetBalanceAccount() {
       when(accountRepository.findById(3L)).thenReturn(Optional.of(data.getAccount003()));
       when(accountRepository.findById(4L)).thenReturn(Optional.of(data.getAccount004()));

       BigDecimal accountThree = service.getBalanceAccount(3L);
       BigDecimal accountFour = service.getBalanceAccount(4L);

       verify(accountRepository, times(2)).findById(anyLong());

       assertEquals(new BigDecimal("56345.11"), accountThree);
       assertEquals(new BigDecimal("567.04"), accountFour);
    }

    @Test
    void testCorrectTransfer() throws BankException {
        //Given
        when(bankRepository.findById(anyLong())).thenReturn(Optional.of(data.getBank001()));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(data.getAccount001()));
        when(accountRepository.findById(4L)).thenReturn(Optional.of(data.getAccount004()));

        //When
        service.transfer(1L, 4L, new BigDecimal("3500"), 1L);

        //Then
        InOrder order = inOrder(bankRepository, accountRepository);
        order.verify(bankRepository).findById(1L);
        order.verify(bankRepository).save(any(Bank.class));;

        verify(accountRepository, times(2)).save(any(Account.class));

        BigDecimal balanceOrigen = service.getBalanceAccount(1L);
        BigDecimal balanceDestination = service.getBalanceAccount(4L);

        assertEquals(new BigDecimal("8845.67"), balanceOrigen);
        assertEquals(new BigDecimal("4067.04"), balanceDestination);
    }

    @Test
    void testErrorTransfer() {
        //Given
        when(bankRepository.findById(anyLong())).thenReturn(Optional.of(data.getBank001()));
        when(accountRepository.findById(1L)).thenReturn(Optional.of(data.getAccount001()));
        when(accountRepository.findById(4L)).thenReturn(Optional.of(data.getAccount004()));

        //When
        assertThrows(BankException.class, ()->{
            service.transfer(4L, 1L, new BigDecimal("3500"), 1L);
        });

        int totalTransfer = service.getTotalTransferBank(1L);
        BigDecimal balanceOrigen = service.getBalanceAccount(4L);
        BigDecimal balanceDestination = service.getBalanceAccount(1L);

        verify(bankRepository, never()).save(any(Bank.class));

        assertEquals(new BigDecimal("567.04"), balanceOrigen);
        assertEquals(new BigDecimal("12345.67"), balanceDestination);
    }
}