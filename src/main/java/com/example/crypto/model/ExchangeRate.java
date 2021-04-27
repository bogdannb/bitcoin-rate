package com.example.crypto.model;

import lombok.*;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Date;

@AllArgsConstructor
@Builder
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Table("exchange_rate")
public class ExchangeRate {

    @PrimaryKeyColumn(
            name = "from_currency", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private Currency fromCurrency;

    @PrimaryKeyColumn(
            name = "to_currency", ordinal = 1, type = PrimaryKeyType.PARTITIONED)
    private Currency toCurrency;

    @PrimaryKeyColumn(
            name = "exchange_date", ordinal = 2, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private Date exchangeDate;

    @Column
    private double rate;
}
