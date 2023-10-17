package com.example.websocketdemo.controller;

import com.example.websocketdemo.model.ChatDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    private final static String  KWORKS_DOWONLOAD_EXCEL_EXCHANGE = "kworks.download.excel.exchange";

    /** excel download */
    @MessageMapping("kworks.download.excel.{userName}")
    public void downloadFinish_send(ChatDTO chat, @DestinationVariable String userName){
        chat.setRegDate(LocalDateTime.now());
        template.convertAndSend(KWORKS_DOWONLOAD_EXCEL_EXCHANGE, "kworks.download.excel." + userName, chat);
    }



}

