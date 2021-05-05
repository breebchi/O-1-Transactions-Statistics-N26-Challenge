package com.n26.service.impl;

import com.n26.builder.TransactionBuilder;
import com.n26.cache.TransactionsCache;
import com.n26.dto.TransactionDto;
import com.n26.model.Transaction;
import com.n26.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Statistics service
 *
 * @author Mahmoud Kraiem
 */
@Service
public class TransactionService implements ITransactionService {
    @Autowired
    StatisticsService statisticsService;
    @Autowired
    TransactionsCache transactionsCache;

    ReentrantLock lock = new ReentrantLock();

    @Override
    public boolean addTransaction(TransactionDto transactionDto) {
        boolean transactionAdded;
        lock.lock();
        try {
            Transaction transaction = TransactionBuilder.aTransaction().withAmount(transactionDto.getAmount()).withTimestamp(transactionDto.getTimestamp()).build();
            transaction.setAmount(transaction.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
            transactionAdded = this.transactionsCache.addToQueue(transaction);
            if (transactionAdded) {
                statisticsService.updateStatistics();
            }
        } finally {
            lock.unlock();
        }
        return transactionAdded;
    }

    @Override
    public void deleteAllTransactions() {
        lock.lock();
        try {
            this.transactionsCache.clearQueue();
        } finally {
            lock.unlock();
        }
    }

    @Scheduled(fixedRate = 1000)
    public synchronized void deleteOldRecords() {
        lock.lock();
        try {
            if (!this.transactionsCache.getQueue().isEmpty()) {
                this.transactionsCache.getQueue().poll();
            }
        } finally {
            lock.unlock();
        }

    }

}
