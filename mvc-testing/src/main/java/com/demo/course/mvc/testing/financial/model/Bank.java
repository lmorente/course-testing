package com.demo.course.mvc.testing.financial.model;

import com.demo.course.mvc.testing.financial.exception.BankException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Bank {

    private Long id;

    private String name;

    private int totalTransfer;

    private BigDecimal balance;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bankDTO = (Bank) o;
        return totalTransfer == bankDTO.totalTransfer && Objects.equals(id, bankDTO.id) && Objects.equals(name, bankDTO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, totalTransfer);
    }
}
