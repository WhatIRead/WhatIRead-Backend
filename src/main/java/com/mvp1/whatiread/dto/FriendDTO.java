package com.mvp1.whatiread.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.mvp1.whatiread.utils.RequestStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Data Transfer Object for Friend request")
public class FriendDTO {

  @NotBlank(message = "UserName is required")
  @Schema(description = "UserName of friend", example = "user1")
  private String userName;
  @Schema(description = "Name of friend.")
  private String name;
  @Schema(description = "Request Message for friend",
      example = "Hey user1! I am user2, please accept my friend request.")
  private String requestMessage;
  private RequestStatus status = RequestStatus.PENDING;
}
