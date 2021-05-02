package com.n26.cache;

import com.n26.model.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionsCache extends Cache<Transaction> {

    public TransactionsCache() {
        super();
    }
}
