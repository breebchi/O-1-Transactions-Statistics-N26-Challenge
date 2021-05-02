package com.n26.builder.controller;

import com.n26.model.StatisticsResponse;
import com.n26.service.impl.StatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

import static com.n26.util.Constants.PATH_STATISTICS;

@RestController
public class StatisticsController {
    private static final Logger LOG = LoggerFactory.getLogger(StatisticsController.class);
    @Autowired
    StatisticsService statisticsService;

    /**
     * query statistics for a predefined epoch
     *
     * @return returns Statistics for a predefined epoch
     */
    @GetMapping(value = PATH_STATISTICS, produces = {"application/json", "application/xml"})
    public ResponseEntity<StatisticsResponse> statistics(HttpServletResponse response) {
        StatisticsResponse statistics = statisticsService.getStatistics();

        return new ResponseEntity<>(statistics, statistics.getCount() > 0 ? HttpStatus.OK : HttpStatus.NO_CONTENT);
    }
}
