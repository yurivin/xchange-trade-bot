package com.github.yurivin.bot.client;

import com.github.yurivin.bot.env.Environment;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import info.bitrich.xchangestream.core.StreamingMarketDataService;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.knowm.xchange.Exchange;
import org.knowm.xchange.ExchangeFactory;
import org.knowm.xchange.ExchangeSpecification;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.service.account.AccountService;
import org.knowm.xchange.service.trade.TradeService;

import java.util.List;

@Slf4j
@Getter
public class ExchangeClient {

    StreamingExchange exchangeStreams;
    Exchange exchange;
    ProductSubscription subscription;
    Environment env;
    TradeService tradeService;
    AccountService accountService;
    List<CurrencyPair> allCurrencyPairs;
    List<CurrencyPair> usedCurrencies;
    String streamsExchangeClassName;


    public ExchangeClient(Environment env, String streamsExchangeClassName, Exchange exchange) {
        if (env.getProperties() == null) throw new RuntimeException("Properties are not set to the environment");
        this.env = env;
        this.exchange = exchange;
        this.streamsExchangeClassName = streamsExchangeClassName;
        initExchangeApi();
    }

    public void initStreams(List<CurrencyPair> usedCurrencies) {
        this.usedCurrencies = usedCurrencies;
        exchangeStreams = StreamingExchangeFactory.INSTANCE.createExchange(streamsExchangeClassName);
        ProductSubscription.ProductSubscriptionBuilder subscriptionBuilder = ProductSubscription.create();
        usedCurrencies.forEach(c -> {
            if (env.getMarketStreamNeeded().equals(MarketStreamType.Ticker)) {
                subscriptionBuilder.addTicker(c);
            } else if (env.getMarketStreamNeeded().equals(MarketStreamType.OrderBook)) {
                subscriptionBuilder.addOrderbook(c);
            }
        });
        subscription = subscriptionBuilder.build();
        exchangeStreams.connect(subscription).blockingAwait();
    }

    private void initExchangeApi() {
        ExchangeSpecification exSpec = exchange.getDefaultExchangeSpecification();
        exSpec.setUserName(env.getProperties().userName);
        exSpec.setApiKey(env.getProperties().apiKey);
        exSpec.setSecretKey(env.getProperties().secretKey);
        exchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
        tradeService = exchange.getTradeService();
        accountService = exchange.getAccountService();
        allCurrencyPairs = exchange.getExchangeSymbols();
        log.info("All currencies from exchange: {}", allCurrencyPairs.toString());
    }

    public void writeData() {
        StreamingMarketDataService service = exchangeStreams.getStreamingMarketDataService();
        if (env.getStrategy().getMarketStreamTypeNeeded().equals(MarketStreamType.Ticker)) {
            usedCurrencies.forEach(currencyPair ->
                    service.getTicker(currencyPair)
                            .subscribe(ticker ->
                                            env.getRepository().addTiker(ticker)
                                    , throwable -> log.error("ERROR in getting ticker: ", throwable))
            );

        } else if (env.getStrategy().getMarketStreamTypeNeeded().equals(MarketStreamType.OrderBook)) {
            usedCurrencies.forEach(currencyPair ->
                    service.getOrderBook(currencyPair)
                            .subscribe(orderBook ->
                                    env.getRepository().addOrderBook(orderBook, currencyPair)
                                    , throwable -> log.error("ERROR in getting order book: ", throwable)));
        }
    }
}
