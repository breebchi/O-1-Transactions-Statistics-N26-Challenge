package com.n26.service.impl;

import com.n26.builder.TransactionBuilder;
import com.n26.builder.dto.TransactionDto;
import com.n26.cache.TransactionsCache;
import com.n26.model.Transaction;
import com.n26.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class TransactionService implements ITransactionService {
    @Autowired
    StatisticsService statisticsService;
    @Autowired
    TransactionsCache transactionsCache;

    @Override
    public boolean addTransaction(TransactionDto transactionDto) {
        Transaction transaction = TransactionBuilder.aTransaction().withAmount(transactionDto.getAmount()).withTimestamp(transactionDto.getTimestamp()).build();
        AtomicReference<TransactionsCache> transactionsCacheAtomicReference = new AtomicReference<>(this.transactionsCache);
        transaction.setAmount(transaction.getAmount().setScale(2, BigDecimal.ROUND_HALF_UP));
        boolean transactionAdded = transactionsCacheAtomicReference.get().addToQueue(transaction);
        if (transactionAdded) {
            statisticsService.updateStatistics();
        }
        return transactionAdded;
    }

    @Override
    public void deleteAllTransactions() {
        AtomicReference<TransactionsCache> transactionsCacheAtomicReference = new AtomicReference<>(this.transactionsCache);
        transactionsCacheAtomicReference.get().clearQueue();
    }

    @Scheduled(fixedRate = 1000)
    @Async
    public void deleteOldRecords() {
        ReentrantLock lock = new ReentrantLock();
        lock.lock();
        try {
            if (!this.transactionsCache.getQueue().isEmpty()) {
                System.out.println(this.transactionsCache.getQueue().get(0).getAmount());
                this.transactionsCache.getQueue().remove(this.transactionsCache.getQueue().get(0));
                System.out.println(this.transactionsCache.getQueue().get(0).getAmount());
            }
        } finally {
            lock.unlock();
        }
    }

}
