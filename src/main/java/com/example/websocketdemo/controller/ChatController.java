//package com.example.websocketdemo.controller;
//
//import com.example.websocketdemo.model.ChatMessage;
//import lombok.RequiredArgsConstructor;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Controller;
//
///**
// * Created by rajeevkumarsingh on 24/07/17.
// */
//@Controller
//@RequiredArgsConstructor
//public class ChatController {
//
//    private final SimpMessagingTemplate template;
//
//    @MessageMapping("/chat.sendMessage")
////    @SendTo("/topic/public")
//    public void sendMessage( ChatMessage chatMessage) {
//        template.convertAndSend("/topic/public/" + chatMessage.getRoomid(), chatMessage);
//    }
//
//    @MessageMapping("/chat.addUser")
//    @SendTo("/topic/public")
//    public void addUser( ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
//
//        // Add username in web socket session
//        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
//        template.convertAndSend("/topic/public/" + chatMessage.getRoomid(), chatMessage);
//    }
//
//}
