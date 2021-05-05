package com.n26.cache;

import com.n26.model.Statistics;
import org.springframework.stereotype.Component;

/**
 * Statistics cache
 *
 * @author Mahmoud Kraiem
 */
@Component
public class StatisticsCache extends Cache<Statistics> {

    public StatisticsCache() {
        super();
    }

}
