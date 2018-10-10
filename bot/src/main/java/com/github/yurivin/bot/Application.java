package com.github.yurivin.bot;

import com.github.yurivin.bot.env.Environment;
import com.github.yurivin.bot.env.DefaultConfigurator;

/**
 * Created by Yuriy.Vinogradov on 03.06.2015.
 * 
 */
public class Application {

    public static void main(String... args) throws Exception {

        DefaultConfigurator configurator = new DefaultConfigurator();
        Environment env = configurator.configure();
        configurator.initialize();
    }
}
