package com.example.crypto.repository;

import com.example.crypto.model.ExchangeRate;
import org.springframework.data.cassandra.repository.MapIdCassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface ExchangeRateRepository extends MapIdCassandraRepository<ExchangeRate> {

    @Query(value = "SELECT * FROM exchange_rate " +
            "WHERE from_currency = 'BITCOIN' " +
            "AND to_currency = 'USD' " +
            "ORDER BY exchange_date DESC " +
            "LIMIT 1;")
    ExchangeRate getBitcoinLatestRate();

    @Query(value = "SELECT * FROM exchange_rate " +
            "WHERE from_currency = 'BITCOIN' " +
            "AND to_currency = 'USD' " +
            "AND exchange_date >= :fromDate " +
            "AND exchange_date <= :toDate " +
            "ORDER BY exchange_date DESC")
    List<ExchangeRate> getBitcoinHistoricalRates(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate);

}
