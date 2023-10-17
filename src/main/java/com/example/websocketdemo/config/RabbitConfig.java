package com.example.websocketdemo.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfig {

    private static final String KWORKS_DOWONLOAD_EXCEL_QUEUE_NAME = "kworks.download.excel.queue";
    private static final String KWORKS_DOWONLOAD_EXCEL_EXCHANGE_NAME = "kworks.download.excel.exchange";
    private static final String KWORKS_DOWONLOAD_EXCEL_ROUTING_KEY = "kworks.download.excel.*";


    /** excel download */
    @Bean
    public Queue excelDownloadQueue(){ return new Queue(KWORKS_DOWONLOAD_EXCEL_QUEUE_NAME, true); }

    @Bean
    public TopicExchange excelDownloadExchange(){ return new TopicExchange(KWORKS_DOWONLOAD_EXCEL_EXCHANGE_NAME); }

    @Bean
    public Binding excelDownloadBinding(Queue excelDownloadQueue, TopicExchange excelDownloadExchange) {
        return BindingBuilder.bind(excelDownloadQueue).to(excelDownloadExchange).with(KWORKS_DOWONLOAD_EXCEL_ROUTING_KEY);
    }

    /* messageConverter를 커스터마이징 하기 위해 Bean 새로 등록 */
    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleMessageListenerContainer container(){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory());
        container.setMessageListener(null);
        return container;
    }

    //Spring에서 자동생성해주는 ConnectionFactory는 SimpleConnectionFactory인가? 그건데
    //여기서 사용하는 건 CachingConnectionFacotry라 새로 등록해줌
    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("localhost");
        factory.setUsername("guest");
        factory.setPassword("guest");
        return factory;
    }

    @Bean
    public Jackson2JsonMessageConverter jsonMessageConverter(){
        //LocalDateTime serializable을 위해
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
        objectMapper.registerModule(dateTimeModule());

        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter(objectMapper);

        return converter;
    }

    @Bean
    public Module dateTimeModule(){
        return new JavaTimeModule();
    }
}
