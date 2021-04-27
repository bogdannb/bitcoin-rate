CREATE KEYSPACE crypto
  WITH REPLICATION = {
   'class' : 'SimpleStrategy',
   'replication_factor' : 1
  };


CREATE TABLE crypto.exchange_rate (
  from_currency text,
  to_currency text,
  exchange_date timestamp,
  rate double,
  PRIMARY KEY ((from_currency, to_currency), exchange_date)
  );