package com.n26.service;

import com.n26.dto.TransactionDto;

/**
 * Transaction interface
 *
 * @author Mahmoud Kraiem
 */
public interface ITransactionService {
    boolean addTransaction(TransactionDto transactionDto);

    void deleteAllTransactions();
}
