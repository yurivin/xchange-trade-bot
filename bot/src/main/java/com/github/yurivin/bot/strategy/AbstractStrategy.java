package com.github.yurivin.bot.strategy;

import com.github.yurivin.bot.env.Environment;

public abstract class AbstractStrategy implements Strategy {

    Environment env;

    public AbstractStrategy(Environment env) {
        this.env = env;
    }
}
