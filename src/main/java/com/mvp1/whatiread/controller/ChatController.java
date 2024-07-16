package com.mvp1.whatiread.controller;

import com.mvp1.whatiread.dto.MessageRequest;
import com.mvp1.whatiread.entity.Message;
import com.mvp1.whatiread.service.ChatService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

  private final ChatService chatService;

  public ChatController(ChatService chatService) {
    this.chatService = chatService;
  }

  @PostMapping("/send")
  public ResponseEntity<Message> sendMessage(@RequestBody MessageRequest request) {
    Message message = chatService.sendMessage(request.getSenderId(), request.getConversationId(),
        request.getContent());
    return ResponseEntity.ok(message);
  }

  @GetMapping("/messages/{conversationId}")
  public ResponseEntity<List<Message>> getMessages(@PathVariable Long conversationId) {
    List<Message> messages = chatService.getMessages(conversationId);
    return ResponseEntity.ok(messages);
  }
}
