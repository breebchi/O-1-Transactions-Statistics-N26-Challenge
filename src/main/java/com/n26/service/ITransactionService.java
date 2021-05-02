package com.n26.service;

import com.n26.builder.dto.TransactionDto;

public interface ITransactionService {
    boolean addTransaction(TransactionDto transactionDto);

    void deleteAllTransactions();
}
