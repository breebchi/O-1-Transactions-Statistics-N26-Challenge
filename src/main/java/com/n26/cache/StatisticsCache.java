package com.n26.cache;

import com.n26.model.Statistics;
import org.springframework.stereotype.Component;

/**
 * Every @param(cycle), cache is incremented.
 * When the cache has reached its maximum size @param(maxSize), the first item on the queue is evicted.
 */
@Component
public class StatisticsCache extends Cache<Statistics> {

    public StatisticsCache() {
        super();
    }

}
