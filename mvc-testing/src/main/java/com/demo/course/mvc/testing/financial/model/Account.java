package com.demo.course.mvc.testing.financial.model;

import com.demo.course.mvc.testing.financial.exception.BankException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="accounts")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String person;

    private BigDecimal balance;

    public void debit(BigDecimal amount) throws BankException {
        BigDecimal newBalance = this.balance.subtract(amount);
        if(newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new BankException("The account balance is insufficient for this type of operation");
        }
        this.balance = this.balance.subtract(amount);
    }

    public void credit(BigDecimal amount) {
        this.balance = this.balance.add(amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account that = (Account) o;
        return Objects.equals(id, that.id) && Objects.equals(person, that.person) && Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, person, balance);
    }

}
