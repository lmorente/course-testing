package com.demo.course.mvc.testing.financial.repository;

import com.demo.course.mvc.testing.financial.model.Bank;

import java.util.List;

public interface BankRepository {

    List<Bank> findAll();

    Bank findById(Long id);

    void update(Bank bank);

}
