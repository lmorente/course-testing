package com.demo.course.mvc.testing.financial.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class DataTransferDTO {

    private Long origin;

    private Long destination;

    private Long bank;

    private BigDecimal amount;
}
