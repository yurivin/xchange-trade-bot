package com.github.yurivin.bot.repo;

import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.marketdata.OrderBook;
import org.knowm.xchange.dto.marketdata.Ticker;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class CacheRepository implements Repository {
    final Map<CurrencyPair, Ticker> tickersMap = new ConcurrentHashMap<>();
    final Map<CurrencyPair, OrderBook> orderBooksMap = new ConcurrentHashMap<>();

    @Override
    public void addTiker(Ticker ticker) {
        log.debug("Ticker: {}", ticker);
        tickersMap.put(ticker.getCurrencyPair(), ticker);
    }

    @Override
    public void addOrderBook(OrderBook ob, CurrencyPair cp) {
        log.debug("OrderBook: {}", ob);
        orderBooksMap.put(cp, ob);
    }

    @Override
    public Ticker getActualTicker(CurrencyPair currencyPair) {
        return tickersMap.get(currencyPair);
    }

    @Override
    public OrderBook getActualOrderBook(CurrencyPair currencyPair) {
        return orderBooksMap.get(currencyPair);
    }

}
