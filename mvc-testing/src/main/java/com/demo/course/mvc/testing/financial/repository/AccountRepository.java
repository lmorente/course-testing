package com.demo.course.mvc.testing.financial.repository;

import com.demo.course.mvc.testing.financial.model.Account;

import java.util.List;

public interface AccountRepository {

    List<Account> findAll();

    Account findById(Long id);

    void update(Account account);

}
