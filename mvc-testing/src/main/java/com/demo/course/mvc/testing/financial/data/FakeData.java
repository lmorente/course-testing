package com.demo.course.mvc.testing.financial.data;

import com.demo.course.mvc.testing.financial.model.Account;
import com.demo.course.mvc.testing.financial.model.Bank;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class FakeData {

    private final Account account001 = new Account(1L, "Johnny Smith", new BigDecimal("12345.67"));

    private final Account account002 = new Account(2L, "Emma Taylor", new BigDecimal("24567.21"));

    private final Account account003 = new Account(2L, "Anya Flynn", new BigDecimal("56345.11"));

    private final Account account004 = new Account(2L, "Callum Goth", new BigDecimal("567.04"));

    private final Bank bank001 = new Bank(1L, "PicBank", 500, new BigDecimal("567896432358854.90"));

    private final Bank bank002 = new Bank(2L, "TicBank", 250, new BigDecimal("123896432358854.45"));
}
