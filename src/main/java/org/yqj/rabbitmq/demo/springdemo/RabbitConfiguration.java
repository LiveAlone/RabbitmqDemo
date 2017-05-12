package org.yqj.rabbitmq.demo.springdemo;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yaoqijun on 2017-05-12.
 */
@Configuration
public class RabbitConfiguration {

    @Bean
    public ConnectionFactory connectionFactory(){
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public AmqpAdmin amqpAdmin(){
        AmqpAdmin amqpAdmin = new RabbitAdmin(connectionFactory());
        amqpAdmin.declareQueue(myQueue());
        return amqpAdmin;
    }

    @Bean
    public RabbitTemplate rabbitTemplate(){
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue myQueue(){
        return new Queue("myQueue");
    }
}
