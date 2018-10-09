package com.github.yurivin.bot.thread;

import com.github.yurivin.bot.env.Environment;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StrategyRunnable implements Runnable {

    public StrategyRunnable(Environment env) {
        this.env = env;
    }

    private Environment env;

    @Override
    public void run() {
        try {
            env.getStrategy().start();
        } catch (Exception e) {
            log.error("Error during run trading strategy. Exiting application", e);
            System.exit(1);
        }
    }
}
