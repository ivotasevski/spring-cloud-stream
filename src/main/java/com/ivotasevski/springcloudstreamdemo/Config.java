package com.ivotasevski.springcloudstreamdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Slf4j
@Configuration
@EnableScheduling
public class Config {

    @Bean
    public Consumer<Message<Item>> nameSink() {
        return msg -> {
            var item = msg.getPayload();
            log.info("Consumer: Headers={}, id={}, timestamp={}", msg.getHeaders(), item.getId(), item.getProcessedTimestamp());
        };
    }

    @Bean
    public Function<String, Message<Item>> processName() {
        return (name -> {
            log.info("Processor: Processing id={}", name);
            return MessageBuilder
                    .withPayload(new Item(name, LocalDate.now()))
                    .setHeader("myCustomHeader", "headerValue")
                    .build();
        });
    }

    @Bean
    public Supplier<String> supplyName() {
        return new Supplier<String>() {
            @Override
            @Scheduled(fixedRate = 5000)
            public String get() {
                var uuid = UUID.randomUUID().toString();
                log.info("Supplier: {}", uuid);
                return uuid;
            }
        };

    }


}
