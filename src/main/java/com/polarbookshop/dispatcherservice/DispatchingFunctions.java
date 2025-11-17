package com.polarbookshop.dispatcherservice;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Flux;

import java.util.function.Function;

@Configuration
public class DispatchingFunctions {

    private static final Logger log = LoggerFactory.getLogger(DispatchingFunctions.class);

    @Bean
    public Function<OrderAcceptedMessage, Long> pack() {//Функция, реализующая бизнес-логику упаковки заказов
        return orderAcceptedMessage -> {//В качестве входных данных принимает объект OrderAcceptedMessage
            log.info("The order with id {} is packed.", orderAcceptedMessage.orderId());
            return orderAcceptedMessage.orderId();//Возвращает идентификатор заказа (Long) в качестве вывода
        };
    }

    @Bean
    public Function<Flux<Long>, Flux<OrderDispatchedMessage>> label() {
        return orderFlux -> orderFlux.map(orderId -> {//В качестве входных данных принимает идентификатор заказа
            log.info("The order with id {} is labeled.", orderId);
            return new OrderDispatchedMessage(orderId);//Возвращает OrderDispatchedMessage в качестве вывода

        });
    }

}
