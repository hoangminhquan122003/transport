package com.transporthc.controller;

import com.transporthc.entity.ChatMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class ChatController {
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public ChatMessage receiveMessage(@Payload ChatMessage chatMessage){
        log.info("connect to chatroom public");
        return chatMessage;
    }

    @MessageMapping("/private-message")
    public ChatMessage receivePrivateMessage(@Payload ChatMessage chatMessage){
        simpMessagingTemplate.convertAndSendToUser(chatMessage.getReceiverName(),"/private",chatMessage);
        log.info("connect to chatroom private");
        return chatMessage;
    }

    @MessageMapping("/group-message")
    public ChatMessage receiveGroupMessage(@Payload ChatMessage chatMessage,@DestinationVariable String groupId){
        simpMessagingTemplate.convertAndSend("/group/"+groupId,chatMessage);
        return chatMessage;
    }
}
