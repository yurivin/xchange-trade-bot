package com.github.yurivin.bot.repo;

import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;

public interface Repository {

    void addTiker(Ticker ticker);
    void addOrderBook(OrderBook ob, CurrencyPair cp);

    Ticker getActualTicker(CurrencyPair currencyPair);

    OrderBook getActualOrderBook(CurrencyPair currencyPair);
}
