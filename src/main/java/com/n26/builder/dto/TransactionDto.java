package com.n26.builder.dto;

import lombok.Data;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Calendar;

@Data
public class TransactionDto {

    @NotNull(message = "amount is required")
    @Digits(integer = 1000, fraction = 10, message = "amount is not valid")
    private BigDecimal amount;

    @NotNull(message = "timestamp is required")
    private Calendar timestamp;
}
