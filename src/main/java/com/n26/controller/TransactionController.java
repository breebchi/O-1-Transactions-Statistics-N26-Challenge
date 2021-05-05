package com.n26.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.n26.dto.TransactionDto;
import com.n26.service.impl.TransactionService;
import com.n26.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * @author Mahmoud Kraiem
 */
@RestController
public class TransactionController {
    private static final Logger LOG = LoggerFactory.getLogger(TransactionController.class);
    @Autowired
    TransactionService transactionService;

    /**
     * adding transaction API
     *
     * @param transactionDto new Transaction
     * @param response       HTTP response object
     */
    @PostMapping(value = Constants.PATH_TRANSACTION)
    public void addTransaction(@Valid @RequestBody @NotNull TransactionDto transactionDto, HttpServletResponse response) {
        LOG.info("Transaction amount: {}, transaction date: {}", transactionDto.getAmount(), transactionDto.getTimestamp().getTime());
        // timestamp is older than age limit minute
        if (transactionDto.getTimestamp().getTime().before(Date.from(Instant.now().minus(Constants.TRANSACTION_AGE_LIMIT, ChronoUnit.MILLIS)))) {
            LOG.info("Invalid timestamp - transaction is older than {} ms", Constants.TRANSACTION_AGE_LIMIT);
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
            return;
        }
        // timestamp is in the future or fields not parsable
        if (transactionDto.getTimestamp().getTime().after(Date.from(Instant.now())) || transactionDto.getAmount() == null || transactionDto.getTimestamp() == null) {
            response.setStatus(Constants.FIELD_NOT_PARSABLE_OR_DATE_IN_THE_FUTURE);
            return;
        }
        transactionService.addTransaction(transactionDto);
        response.setStatus(HttpServletResponse.SC_CREATED);
    }

    @DeleteMapping(value = Constants.PATH_TRANSACTION)
    public void deleteAllTransactions(HttpServletResponse response) {
        transactionService.deleteAllTransactions();
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler({InvalidFormatException.class})
    public void handleException() {
    }
}
