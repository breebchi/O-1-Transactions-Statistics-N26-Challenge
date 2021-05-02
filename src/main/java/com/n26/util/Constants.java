package com.n26.util;

import org.springframework.stereotype.Component;

import java.math.MathContext;
import java.math.RoundingMode;

@Component
public class Constants {
    public static final int ONE_MINUTE_IN_MS = 60000;
    public static final int TRANSACTION_AGE_LIMIT = 60000;
    public static final MathContext HALF_ROUND_UP_MATH_CONTEXT = new MathContext(2, RoundingMode.HALF_UP);
    public static final int CASH_SIZE = 10000;
    public static final String PATH_STATISTICS = "/statistics";
    public static final String PATH_TRANSACTION = "/transactions";
    public static final int FIELD_NOT_PARSABLE_OR_DATE_IN_THE_FUTURE = 422;

}
