package com.n26.model;

import lombok.Data;

import java.math.BigDecimal;

/**
 * This is a model for Statistics objects
 *
 * @author Mahmoud Kraiem
 */
@Data
public class Statistics {
    private BigDecimal sum;
    private BigDecimal avg;
    private BigDecimal max;
    private BigDecimal min;
    private long count;
}
