package com.n26.util;

import org.springframework.stereotype.Component;

/**
 * Constants used across the code.
 *
 * @author Mahmoud Kraiem
 */
@Component
public class Constants {
    public static final int TRANSACTION_AGE_LIMIT = 60000;
    public static final int CASH_SIZE = 10000;
    public static final String PATH_STATISTICS = "/statistics";
    public static final String PATH_TRANSACTION = "/transactions";
    public static final int FIELD_NOT_PARSABLE_OR_DATE_IN_THE_FUTURE = 422;

}
