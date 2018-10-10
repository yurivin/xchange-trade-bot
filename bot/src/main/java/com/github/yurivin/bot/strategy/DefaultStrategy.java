package com.github.yurivin.bot.strategy;

import com.github.yurivin.bot.client.MarketStreamType;
import com.github.yurivin.bot.env.Environment;
import java.util.ArrayList;
import java.util.List;
import org.knowm.xchange.currency.CurrencyPair;

public class DefaultStrategy extends AbstractStrategy {

  public DefaultStrategy(Environment env) {
    super(env);
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
