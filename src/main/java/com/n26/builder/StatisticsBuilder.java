package com.n26.builder;

import com.n26.model.Statistics;

import java.math.BigDecimal;

/**
 * Build a Statisitcs object
 *
 * @author Mahmoud Kraiem
 */
public final class StatisticsBuilder {
    private BigDecimal sum;
    private BigDecimal avg;
    private BigDecimal max;
    private BigDecimal min;
    private long count;

    private StatisticsBuilder() {
    }

    public static StatisticsBuilder aStatistics() {
        return new StatisticsBuilder();
    }

    public StatisticsBuilder withSum(BigDecimal sum) {
        this.sum = sum;
        return this;
    }

    public StatisticsBuilder withAvg(BigDecimal avg) {
        this.avg = avg;
        return this;
    }

    public StatisticsBuilder withMax(BigDecimal max) {
        this.max = max;
        return this;
    }

    public StatisticsBuilder withMin(BigDecimal min) {
        this.min = min;
        return this;
    }

    public StatisticsBuilder withCount(long count) {
        this.count = count;
        return this;
    }

    public Statistics build() {
        Statistics statistics = new Statistics();
        statistics.setSum(sum);
        statistics.setAvg(avg);
        statistics.setMax(max);
        statistics.setMin(min);
        statistics.setCount(count);
        return statistics;
    }
}
