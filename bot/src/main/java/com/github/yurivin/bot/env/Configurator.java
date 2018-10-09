package com.github.yurivin.bot.env;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public interface Configurator {

    Logger log = LoggerFactory.getLogger(Configurator.class);

    Environment configure() throws Exception;

    void initialize() throws Exception;

}
