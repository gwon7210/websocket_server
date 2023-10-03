package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.ChatDTO;
import com.example.websocketdemo.model.ChatMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
@Log4j2
public class StompRabbitController {

    private final RabbitTemplate template;

    private final static String CHAT_EXCHANGE_NAME = "chat.exchange";
    private final static String CHAT_QUEUE_NAME = "chat.queue";

    @MessageMapping("chat.enter.{chatRoomId}")
    public void enter(ChatDTO chat, @DestinationVariable String chatRoomId){
        chat.setMessage(chat.getSender() + " 입장하셨습니다.");
        chat.setRegDate(LocalDateTime.now());
        chat.setType(ChatMessage.MessageType.JOIN);
        template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chat); // exchange
    }

    @MessageMapping("chat.message.{chatRoomId}")
    public void send(ChatDTO chat, @DestinationVariable String chatRoomId){
        chat.setRegDate(LocalDateTime.now());

        template.convertAndSend(CHAT_EXCHANGE_NAME, "room." + chatRoomId, chat);
    }

    @RabbitListener(queues = CHAT_QUEUE_NAME)
    public void receive(ChatDTO chat){
        System.out.println("received : " + chat.getMessage());
    }
}

