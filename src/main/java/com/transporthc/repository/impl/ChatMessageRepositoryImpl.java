package com.transporthc.repository.impl;

import com.transporthc.entity.ChatMessage;
import com.transporthc.entity.QChatMessage;
import com.transporthc.repository.BaseRepository;
import com.transporthc.repository.ChatMessageRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ChatMessageRepositoryImpl extends BaseRepositoryImpl<ChatMessage,Long> implements ChatMessageRepository {
    final QChatMessage chatMessage=QChatMessage.chatMessage;
    public ChatMessageRepositoryImpl( EntityManager em) {
        super(ChatMessage.class, em);
    }

    @Override
    public List<ChatMessage> getAllMessageBetweenUsers(String user1, String user2) {
        return query.selectFrom(chatMessage)
                .where((chatMessage.senderName.eq(user1).and(chatMessage.receiverName.eq(user2)))
                .or(chatMessage.receiverName.eq(user1).and(chatMessage.senderName.eq(user2))))
                .fetch();
    }

    @Override
    public List<ChatMessage> findByMessage(String keyword) {
        return query.selectFrom(chatMessage)
                .where(chatMessage.message.likeIgnoreCase("%"+keyword+"%"))
                .fetch();
    }
}
