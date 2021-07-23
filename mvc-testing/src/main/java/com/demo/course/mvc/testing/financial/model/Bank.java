package com.demo.course.mvc.testing.financial.model;

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
@Table(name="banks")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    @Column(name = "total_transfer")
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
