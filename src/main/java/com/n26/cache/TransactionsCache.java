package com.n26.cache;

import com.n26.model.Transaction;
import org.springframework.stereotype.Component;

/**
 * Transactions cache
 *
 * @author Mahmoud Kraiem
 */
@Component
public class TransactionsCache extends Cache<Transaction> {

    public TransactionsCache() {
        super();
    }
}
