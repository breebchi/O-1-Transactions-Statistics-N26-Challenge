package com.n26.service.impl;

import com.n26.builder.StatisticsBuilder;
import com.n26.cache.StatisticsCache;
import com.n26.cache.TransactionsCache;
import com.n26.model.Statistics;
import com.n26.model.StatisticsResponse;
import com.n26.model.Transaction;
import com.n26.service.IStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import static com.n26.util.Constants.TRANSACTION_AGE_LIMIT;

@Service
public class StatisticsService implements IStatisticsService {

    private static final Logger LOG = LoggerFactory.getLogger(StatisticsService.class);

    @Autowired
    TransactionsCache transactionsCache;
    @Autowired
    StatisticsCache statisticsCache;

    @Override
    public StatisticsResponse getStatistics() {
        AtomicReference<StatisticsCache> statisticsCacheAtomicReference = new AtomicReference<>(this.statisticsCache);
        Statistics statistics = this.statisticsCache != null && !this.statisticsCache.getQueue().isEmpty() ?
                statisticsCacheAtomicReference.get().getQueue().get(statisticsCache.getQueue().size() - 1)
                : StatisticsBuilder.aStatistics().build();
        return new StatisticsResponse(statistics);
    }

    public void updateStatistics() {
        ReentrantLock lock = new ReentrantLock();
        try {
            lock.lock();
            LOG.info("Updating Statistics Cache");
            List<BigDecimal> amounts = getAmountsForTransactionsNotOlderThan(this.transactionsCache, TRANSACTION_AGE_LIMIT);
            long count = amounts.size();
            BigDecimal min = amounts.stream().min(Comparator.naturalOrder()).orElse(BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal max = amounts.stream().max(Comparator.naturalOrder()).orElse(BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal sum = amounts.stream().reduce(BigDecimal::add).orElse(BigDecimal.ZERO).setScale(2, BigDecimal.ROUND_HALF_UP);
            BigDecimal avg = count > 0 ? sum.divide(BigDecimal.valueOf(count), BigDecimal.ROUND_HALF_UP) : BigDecimal.ZERO;
            Statistics statistics = StatisticsBuilder.aStatistics().withMax(max).withMin(min).withCount(count).withAvg(avg).withSum(sum).build();
            this.statisticsCache.addToQueue(statistics);
            LOG.info("Statistics Cache updated with values : " + statistics);
        } finally {
            lock.unlock();
        }

    }

    private List<BigDecimal> getAmountsForTransactionsNotOlderThan(TransactionsCache transactionsCache, long age) {
        return transactionsCache.getQueue()
                .stream()
                .filter(transaction -> transaction.getTimestamp().getTime().after(Date.from(Instant.now().minus(age, ChronoUnit.MILLIS))))
                .map(Transaction::getAmount)
                .collect(Collectors.toList());
    }
}
