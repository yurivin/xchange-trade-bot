package bot;

import info.bitrich.xchangestream.binance.BinanceStreamingExchange;
import info.bitrich.xchangestream.core.ProductSubscription;
import info.bitrich.xchangestream.core.StreamingExchange;
import info.bitrich.xchangestream.core.StreamingExchangeFactory;
import org.knowm.xchange.currency.CurrencyPair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Lukas Zaoralek on 15.11.17.
 */
public class ManualExample {
    private static final Logger LOG = LoggerFactory.getLogger(ManualExample.class);

    public static void main(String[] args) {
        StreamingExchange exchange = StreamingExchangeFactory.INSTANCE.createExchange(BinanceStreamingExchange.class.getName());

        ProductSubscription subscription = ProductSubscription.create()
                .addTicker(CurrencyPair.ETH_BTC)
                .addTicker(CurrencyPair.LTC_BTC)
                .addOrderbook(CurrencyPair.LTC_BTC)
                .build();

        exchange.connect(subscription).blockingAwait();

        exchange.getStreamingMarketDataService()
                .getTicker(CurrencyPair.ETH_BTC)
                .subscribe(ticker -> {
                    LOG.info("Ticker: {}", ticker);
                }, throwable -> LOG.error("ERROR in getting ticker: ", throwable));

        exchange.getStreamingMarketDataService()
                .getOrderBook(CurrencyPair.LTC_BTC)
                .subscribe(orderBook -> {
                    LOG.info("Order Book: {}", orderBook);
                }, throwable -> LOG.error("ERROR in getting order book: ", throwable));
    }
}
