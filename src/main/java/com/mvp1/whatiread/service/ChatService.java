package com.mvp1.whatiread.service;

import com.mvp1.whatiread.entity.Conversation;
import com.mvp1.whatiread.entity.Message;
import com.mvp1.whatiread.entity.user.User;
import com.mvp1.whatiread.repository.ConversationRepository;
import com.mvp1.whatiread.repository.MessageRepository;
import com.mvp1.whatiread.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

  private UserRepository userRepository;

  private MessageRepository messageRepository;

  private ConversationRepository conversationRepository;

  public ChatService(UserRepository userRepository, MessageRepository messageRepository,
      ConversationRepository conversationRepository) {
    this.userRepository = userRepository;
    this.messageRepository = messageRepository;
    this.conversationRepository = conversationRepository;
  }

  public Message sendMessage(Long senderId, Long conversationId, String content) {
    User sender = userRepository.findById(senderId)
        .orElseThrow(() -> new RuntimeException("User not found"));
    Conversation conversation = conversationRepository.findById(conversationId)
        .orElseThrow(() -> new RuntimeException("Conversation not found"));

    Message message = new Message();
    message.setSender(sender);
    message.setConversation(conversation);
    message.setContent(content);
    message.setTimestamp(LocalDateTime.now());

    return messageRepository.save(message);
  }

  public List<Message> getMessages(Long conversationId) {
    Conversation conversation = conversationRepository.findById(conversationId)
        .orElseThrow(() -> new RuntimeException("Conversation not found"));
    return conversation.getMessages();
  }
}

