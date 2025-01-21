package com.transporthc.repository;

import com.transporthc.entity.ChatMessage;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatMessageRepository extends BaseRepository<ChatMessage,Long> {

    List<ChatMessage> getAllMessageBetweenUsers(String user1, String user2);

    List<ChatMessage> findByMessage(String keyword);
}
