package com.demo.course.mvc.testing.financial.repository;

import com.demo.course.mvc.testing.financial.model.Bank;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankRepository extends CrudRepository<Bank, Long> {

    Optional<Bank> findById(Long id);

}
