package com.mvp1.whatiread.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageRequest {
  private Long senderId;
  private Long conversationId;
  private String content;
}
