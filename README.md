# xchange-trade-bot

## bot

### Overview
Universal Trade bot for cryptocurrency markets with REST and web socket streams support, based on knowm Xchange(https://github.com/knowm/XChange) and bitrich-info Xchange-Stream(https://github.com/bitrich-info/xchange-stream) libraries.

It is simple and already has everything to start implementing strategy logic and forget about other technical details.

Very light weight and minimum of dependecies. Possible to use a bunch of this bots in one application(Documentation on this case coming soon)


### Parameters Configuration
`rescources/application.yml` - configuration parameters
#### API Connection parameters
Connection parameters are mandatory
`userName: `, `apiKey: `, `secretKey: `

#### Strategy parameters
All strategy parameters dependce on strategy logic

### Strategy Configuration
There are two places to look for:
1. Your implementation of `Configurator` interface. Source code contains complete eaxmple `DefaultConfigurator.java`
2. Your Implementation of `Strategy` interface. Source code contains base example `AbstractStrategy.java` and `DefaultStrategy.java`

#### Implementing Configurator interface
Look at `DefaultConfigurator.java`. In most cases you just need to do 2 things in `configure()` method:
1. Set another `Strategy` interface implementation in place of `env.setStrategy(new DefaultStrategy(env));`
2. Set used exchange Client in place of `env.setExchangeClient(new ExchangeClient(env, BinanceStreamingExchange.class.getName(), new BinanceExchange()));`. Notice that you will automatically receive market data streams after application starts. Not additional configuration needed except to set Market data stream(Look at Implementing Strategy interface section item 2).

Also change in pom.xml `info.bitrich.xchange-stream` dependency if other than Binance exchange client used.

```
public class DefaultConfigurator implements Configurator {

    Environment env;

    public Environment configure() throws Exception {
        env = new Environment();
        env.setPropertiesFactory(new YamlPropertiesFactory());
        env.setProperties(env.getPropertiesFactory().readProperties());
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
```

If you implement `Configurator` interface in other class, you also need to change one line in `Application.java`. Insert your `Configurator` interface realisation in this line `AbstractConfigurator configurator = new DefaultConfigurator();` instead of default one.


#### Implementing Strategy interface
Look at methods in `DefaultStrategy.java`:
1. `List<CurrencyPair> usedCurrencyPairs();` - contains initialisation logic for currancy pairs needed for your strategy. This pairs client will listen from exchange market data stream. You can find example in `DefaultStrategy.java`
2. `MarketStreamType getMarketStreamTypeNeeded();` - Market stream type needed for your strategy. `OrderBook` or `Ticker`. You can find example in `DefaultStrategy.java`
3. `void setup() throws Exception;` - This is setup logic for strategy initialisation which shold be processed before strategy starts execution. 
4. `void start() throws Exception;` - Write here your strategy logic.

```

```

Also Strategy contains `Environment env` object level variable. There you will find all service to realise strategy.

#### Environment
`Environment.java` this class is used for dependency injection of services in `Strategy`.
Services:
1. `ExchangeClient exchangeClient;` - this is an instance of exchange client. There you'll find all services to make API calls. `AccountService`, `TradeService` and other.
2. `Properties properties;` - this is a class `Properties.java` where you should add all your strategy specific properties with same name like in `application.yml` and this properties will be automatically initialised. 
3. `Repository repository;` - This is repository where MarketData stream writes market data automatically after Application starts.

### Using multiple exchanges in one application
If you need to use multiple exchanges in one application, pehaps for arbitrage between exchanges, you steel may you this bot.

Initialize every exchange bot like in `Application.java`. You will get environment for different exchanges. 
Use those environments to work with each exchange. 

```
 Configurator default = new DefaultConfigurator(); 
 Environment defaultEnv = default.configure(); 
 default.initialize();
 
 Configurator other = new OtherConfigurator();
 Environment otherEnv = other.configure();
 other.initialize();
```
 
 