package com.yangtian.rest;

/**
 * @author Yang Tian
 * @date 21/02/2021 22:36
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(StockRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Stock("apple", 4)));
            log.info("Preloading " + repository.save(new Stock("banana", 5)));
        };
    }
}
