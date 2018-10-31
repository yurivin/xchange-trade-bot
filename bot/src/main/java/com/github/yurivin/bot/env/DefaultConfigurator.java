package com.github.yurivin.bot.env;

import com.github.yurivin.bot.client.ExchangeClient;
import com.github.yurivin.bot.repo.CacheRepository;
import com.github.yurivin.bot.strategy.AbstractStrategy;
import com.github.yurivin.bot.strategy.DefaultStrategy;
import com.github.yurivin.bot.thread.StrategyRunnable;
import info.bitrich.xchangestream.binance.BinanceStreamingExchange;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.binance.BinanceExchange;

@Slf4j
public class DefaultConfigurator implements Configurator {

    Environment env;

    public Environment configure() throws Exception {
        env = new Environment();
        env.setPropertiesFactory(new YamlPropertiesFactory());
        env.setProperties(env.getPropertiesFactory().readProperties("application.yml"));
        env.setExchangeClient(new ExchangeClient(env, BinanceStreamingExchange.class.getName(), new BinanceExchange()));
        env.setStrategy(new DefaultStrategy(env));
        env.setRepository(new CacheRepository());
        return env;
    }

    public void initialize() throws Exception {
        env.getExchangeClient().initStreams(env.getStrategy().usedCurrencyPairs());
        env.getExchangeClient().writeData();
        env.getStrategy().setup();
        Runnable runnable = new StrategyRunnable(env);
        Thread strategyThread = new Thread(runnable, "Strategy thread");
        strategyThread.start();
    }
}
