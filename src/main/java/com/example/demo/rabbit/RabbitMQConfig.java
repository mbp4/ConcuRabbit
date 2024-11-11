package com.example.demo.rabbit;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue colaBolas(){
        return new Queue("colaBolas", true);
    }

    @Bean
    public Queue colaNiveles(){
        return new Queue("colaNiveles", true);
    }


}
