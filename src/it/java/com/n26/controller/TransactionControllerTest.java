package com.n26.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.n26.model.Transaction;
import com.n26.service.impl.StatisticsService;
import com.n26.service.impl.TransactionService;
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

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.Calendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Mahmoud Kraiem
 */
@RunWith(SpringRunner.class)
public class TransactionControllerTest {

    private final ObjectMapper mapper = new ObjectMapper();
    private MockMvc mockMvc;
    @Mock
    private TransactionService transactionService;
    @Mock
    private StatisticsService statisticsService;
    @InjectMocks
    private TransactionController transactionController;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(transactionController).build();
    }

    @Test
    public void testAddTransaction() throws Exception {
        Mockito.doNothing().when(statisticsService).updateStatistics();

        Transaction transaction = createTransaction(1.0, System.currentTimeMillis());

        mockMvc.perform(post(Constants.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transaction)))
                .andExpect(status().is(HttpServletResponse.SC_CREATED));
    }

    @Test
    public void testAddTransactionOutOfRange() throws Exception {
        Mockito.doNothing().when(statisticsService).updateStatistics();

        Transaction transaction = createTransaction(1.0, System.currentTimeMillis() + 61000);

        mockMvc.perform(post(Constants.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transaction)))
                .andExpect(status().is(Constants.FIELD_NOT_PARSABLE_OR_DATE_IN_THE_FUTURE));
    }

    @Test
    public void testAddTransactionInvalidAmount() throws Exception {
        Transaction transaction = createTransaction(-11.0, System.currentTimeMillis());
        mockMvc.perform(post(Constants.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transaction))).andExpect(status().is4xxClientError());
    }

    @Test
    public void testAddTransactionNoTransaction() throws Exception {
        Transaction transaction = createTransaction(1.0, 0);

        mockMvc.perform(post(Constants.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transaction)))
                .andExpect(status().is(HttpServletResponse.SC_NO_CONTENT));
    }

    @Test
    public void testAddTransactionOldTimestamp() throws Exception {
        Transaction transaction = createTransaction(1.0,
                Instant.ofEpochMilli(System.currentTimeMillis() - 61000).atOffset(ZoneOffset.UTC).toEpochSecond());

        mockMvc.perform(post(Constants.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transaction)))
                .andExpect(status().is(HttpServletResponse.SC_NO_CONTENT));
    }

    @Test
    public void deleteAllTransactions() throws Exception {
        Transaction transaction = createTransaction(1.0, System.currentTimeMillis());

        mockMvc.perform(post(Constants.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(transaction)))
                .andExpect(status().is(HttpServletResponse.SC_CREATED));

        mockMvc.perform(delete(Constants.PATH_TRANSACTION).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpServletResponse.SC_NO_CONTENT));
    }


    private Transaction createTransaction(double amount, long timestamp) {
        Transaction transaction = new Transaction();
        transaction.setAmount(BigDecimal.valueOf(amount));
        Calendar calendar = new Calendar.Builder().setInstant(timestamp).build();
        transaction.setTimestamp(calendar);

        return transaction;
    }
}