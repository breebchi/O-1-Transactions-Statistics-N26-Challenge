package com.n26.builder;

import com.n26.model.Transaction;

import java.math.BigDecimal;
import java.util.Calendar;

public final class TransactionBuilder {
    private BigDecimal amount;
    private Calendar timestamp;

    private TransactionBuilder() {
    }

    public static TransactionBuilder aTransaction() {
        return new TransactionBuilder();
    }

    public TransactionBuilder withAmount(BigDecimal amount) {
        this.amount = amount;
        return this;
    }

    public TransactionBuilder withTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    public Transaction build() {
        Transaction transaction = new Transaction();
        transaction.setAmount(amount);
        transaction.setTimestamp(timestamp);
        return transaction;
    }
}
