package com.github.yurivin.bot.env;

import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@ToString
@Slf4j
public class Properties {

    public String test;
    public String userName;
    public String apiKey;
    public String secretKey;
    public boolean doTrade;
    public float minPricePercentageDifference;
    public int orderType;
    public int balanceDivisor;
    public int minLimitAvailableMultiplicant;
}
