package com.n26.dto;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Calendar;

/**
 * Data transfer object for the Transaction class
 *
 * @author Mahmoud Kraiem
 */
@Data
public class TransactionDto {

    @NotNull(message = "amount is required")
    @DecimalMin(value = "0", message = "amount should not be negative")
    @Digits(integer = 1000, fraction = 10, message = "amount is not valid")
    private BigDecimal amount;

    @NotNull(message = "timestamp is required")
    private Calendar timestamp;
}
