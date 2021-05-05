package com.n26.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * A model for Transaction objects
 *
 * @author Mahmoud Kraiem
 */
@Getter
@Setter
public class Transaction {
    private BigDecimal amount;
    private Calendar timestamp;
}
