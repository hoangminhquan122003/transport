package com.transporthc.controller;

import com.transporthc.dto.response.ApiResponse;
import com.transporthc.entity.ChatMessage;
import com.transporthc.repository.impl.ChatMessageRepositoryImpl;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/messages")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ChatMessageController {
    ChatMessageRepositoryImpl chatMessageRepository;

    @GetMapping("/history")
    public ApiResponse<List<ChatMessage>> getChatMessageHistory(@RequestParam String user1,@RequestParam String user2){
        return ApiResponse.<List<ChatMessage>>builder()
                .message("get chat history")
                .result(chatMessageRepository.getAllMessageBetweenUsers(user1,user2))
                .build() ;
    }

    @GetMapping("/search")
    public ApiResponse<List<ChatMessage>> findByMessage(@RequestParam String keyword){
        return  ApiResponse.<List<ChatMessage>>builder()
                .message("search message")
                .result(chatMessageRepository.findByMessage(keyword))
                .build();
    }
}
