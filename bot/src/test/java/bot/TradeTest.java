package bot;

import com.github.yurivin.bot.client.ExchangeClient;
import com.github.yurivin.bot.env.Environment;
import com.github.yurivin.bot.env.YamlPropertiesFactory;
import info.bitrich.xchangestream.binance.BinanceStreamingExchange;
import lombok.extern.slf4j.Slf4j;
import org.junit.Ignore;
import org.junit.Test;
import org.knowm.xchange.binance.BinanceExchange;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.BinanceExchangeInfo;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.Filter;
import org.knowm.xchange.binance.dto.meta.exchangeinfo.Symbol;
import org.knowm.xchange.binance.service.BinanceTradeService;
import org.knowm.xchange.currency.CurrencyPair;
import org.knowm.xchange.dto.Order;
import org.knowm.xchange.dto.trade.MarketOrder;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
@Slf4j
public class TradeTest {

    @Test
    @Ignore
    public void testBuy() throws IOException {
        Environment env = new Environment();
        env.setPropertiesFactory(new YamlPropertiesFactory());
        env.setProperties(env.getPropertiesFactory().readProperties("application.yml"));
        env.setExchangeClient(new ExchangeClient(env, BinanceStreamingExchange.class.getName(), new BinanceExchange()));

        BinanceTradeService tradeService = (BinanceTradeService) env.getExchangeClient().getTradeService();
        MarketOrder marketOrder =
                new MarketOrder(Order.OrderType.BID, new BigDecimal("0.0001"), CurrencyPair.BTC_USDT, new Date());
        tradeService.placeMarketOrder(marketOrder);
    }

    @Test
    @Ignore
    public void testExchangeInfo() throws IOException {
        Environment env = new Environment();
        env.setPropertiesFactory(new YamlPropertiesFactory());
        env.setProperties(env.getPropertiesFactory().readProperties("application.yml"));
        env.setExchangeClient(new ExchangeClient(env, BinanceStreamingExchange.class.getName(), new BinanceExchange()));

        BinanceExchangeInfo binanceInfo = ((BinanceExchange)env.getExchangeClient().getExchange()).getExchangeInfo();
        for(Symbol symbol: binanceInfo.getSymbols()) {
            for(Filter filter : symbol.getFilters()) {
                if(filter.getFilterType().contentEquals("LOT_SIZE")) {
                    DecimalFormat fmt = new DecimalFormat("0.#");
                    log.info(symbol.getSymbol() + filter.getFilterType() + " " + filter.getStepSize() + " " + filter.getStepSize().indexOf('1'));
                }
            }
        }
    }
}
