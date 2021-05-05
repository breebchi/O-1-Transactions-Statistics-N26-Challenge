package com.n26.model;

import lombok.Data;
import org.springframework.lang.NonNull;

/**
 * A response object for the Statistics endpoint
 */
@Data
public class StatisticsResponse {
    private String sum;
    private String avg;
    private String max;
    private String min;
    private long count;

    public StatisticsResponse(@NonNull Statistics statistics) {
        sum = statistics.getSum() != null ? statistics.getSum().toString() : null;
        avg = statistics.getAvg() != null ? statistics.getAvg().toString() : null;
        max = statistics.getMax() != null ? statistics.getMax().toString() : null;
        min = statistics.getMin() != null ? statistics.getMin().toString() : null;
        count = statistics.getCount();
    }
}
