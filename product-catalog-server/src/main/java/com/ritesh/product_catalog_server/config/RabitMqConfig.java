package com.ritesh.product_catalog_server.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.FanoutExchange;

import org.springframework.amqp.rabbit.connection.ConnectionFactory; // ✅ CORRECT
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabitMqConfig {

    public static final String EXCHANGE = "product-exchange";

    // ✅ ObjectMapper for LocalDateTime support
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }

    // ✅ JSON converter
    @Bean
    public MessageConverter jsonMessageConverter(ObjectMapper objectMapper) {
        return new JacksonJsonMessageConverter(String.valueOf(objectMapper));
    }

    // ✅ RabbitTemplate with JSON
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         MessageConverter messageConverter) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter);
        return template;
    }

    // ✅ Exchange
    @Bean
    public FanoutExchange exchange() {
        return new FanoutExchange(EXCHANGE);
    }

    // ✅ Queue
    @Bean
    public Queue queue() {
        return new Queue("product-cache-queue", true);
    }

    // ✅ Binding
    @Bean
    public Binding binding(Queue queue, FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
}