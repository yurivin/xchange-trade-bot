package com.github.yurivin.bot.strategy;

import com.github.yurivin.bot.client.MarketStreamType;
import com.github.yurivin.bot.env.Environment;
import org.knowm.xchange.currency.CurrencyPair;

import java.util.ArrayList;
import java.util.List;

public class AbstractStrategy implements Strategy {

    Environment env;

    public AbstractStrategy(Environment env) {
        this.env = env;
    }

    @Override
    public List<CurrencyPair> usedCurrencyPairs() {
        List<CurrencyPair> usedPairs = new ArrayList<>();
        usedPairs.add(CurrencyPair.BTC_USDT);
        return usedPairs;
    }

    @Override
    public void start() throws Exception {

    }

    @Override
    public void setup() throws Exception {

    }

    @Override
    public MarketStreamType getMarketStreamTypeNeeded() {
        return MarketStreamType.Ticker;
    }
}
