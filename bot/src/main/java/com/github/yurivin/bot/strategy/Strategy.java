package com.github.yurivin.bot.strategy;

import com.github.yurivin.bot.client.MarketStreamType;
import org.knowm.xchange.currency.CurrencyPair;

import java.util.List;

public interface Strategy {


    List<CurrencyPair> usedCurrencyPairs();

    void start() throws Exception;
    void setup() throws Exception;

    MarketStreamType getMarketStreamTypeNeeded();
}
