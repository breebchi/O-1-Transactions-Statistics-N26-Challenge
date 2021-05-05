package com.n26.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.builder.StatisticsBuilder;
import com.n26.model.Statistics;
import com.n26.model.StatisticsResponse;
import com.n26.service.impl.StatisticsService;
import com.n26.util.Constants;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Mahmoud Kraiem
 *
 */
@RunWith(SpringRunner.class)
public class StatisticsControllerTest {

    private MockMvc mockMvc;

    @Mock
    private StatisticsService statisticsService;

    @InjectMocks
    private StatisticsController statisticsController;

    private final ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(statisticsController).build();
    }

    @Test
    public void testGetStatistics() throws Exception {
        Statistics statistics = StatisticsBuilder.aStatistics().withCount(5).withSum(BigDecimal.valueOf(20.8)).withAvg(BigDecimal.valueOf(5.6)).withMin(BigDecimal.valueOf(3.4))
                .withMax(BigDecimal.valueOf(6.7)).build();
        Mockito.when(statisticsService.getStatistics()).thenReturn(new StatisticsResponse(statistics));

        mockMvc.perform(get(Constants.PATH_STATISTICS).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(jsonPath("$.count").value(statistics.getCount()))
                .andExpect(jsonPath("$.avg").value(statistics.getAvg()))
                .andExpect(jsonPath("$.sum").value(statistics.getSum()))
                .andExpect(jsonPath("$.min").value(statistics.getMin()))
                .andExpect(jsonPath("$.max").value(statistics.getMax()));
    }
}