package com.github.yurivin.bot.env;

import com.github.yurivin.bot.client.ExchangeClient;
import com.github.yurivin.bot.client.MarketStreamType;
import com.github.yurivin.bot.repo.Repository;
import com.github.yurivin.bot.strategy.Strategy;
import lombok.Getter;

@Getter
public class Environment {

    private ExchangeClient exchangeClient;
    private PropertiesFactory propertiesFactory;
    private Properties properties;
    private Strategy strategy;
    private Repository repository;

    public void setExchangeClient(ExchangeClient exchangeClient) {
        if (this.exchangeClient == null)
            this.exchangeClient = exchangeClient;
        else throw new RuntimeException("exchangeClient already set");
    }

    public void setProperties(Properties properties) {
        if (this.properties == null)
            this.properties = properties;
        else throw new RuntimeException("properties already set");
    }

    public void setStrategy(Strategy strategy) {
        if (this.strategy == null)
            this.strategy = strategy;
        else throw new RuntimeException("strategy already set");
    }

    public void setRepository(Repository repository) {
        if (this.repository == null)
            this.repository = repository;
        else throw new RuntimeException("repository already set");
    }

    public void setPropertiesFactory(PropertiesFactory propertiesFactory) {
        if (this.propertiesFactory == null)
            this.propertiesFactory = propertiesFactory;
        else throw new RuntimeException("PropertiesFactory already set");
    }

    public MarketStreamType getMarketStreamNeeded() {
        return this.strategy.getMarketStreamTypeNeeded();
    }
}
