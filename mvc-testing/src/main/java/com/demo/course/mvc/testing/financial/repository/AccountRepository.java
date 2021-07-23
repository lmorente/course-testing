package com.demo.course.mvc.testing.financial.repository;

import com.demo.course.mvc.testing.financial.model.Account;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Optional<Account> findById(Long id);

    @Query("select c from Account c where c.person=?1")
    Optional<Account> findByPerson(String person);

}
