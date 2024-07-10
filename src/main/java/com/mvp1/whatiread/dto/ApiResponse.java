package com.mvp1.whatiread.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.io.Serial;
import java.io.Serializable;
import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
@JsonPropertyOrder({"success", "message"})
public class ApiResponse implements Serializable {

  @Serial
  @JsonIgnore
  private static final long serialVersionUID = 7702134516418120340L;

  @JsonProperty("success")
  private Boolean success;

  @JsonProperty("message")
  private String message;

  @JsonIgnore
  private HttpStatus status;

  public ApiResponse() {

  }

  public ApiResponse(Boolean success, String message) {
    this.success = success;
    this.message = message;
  }

  public ApiResponse(Boolean success, String message, HttpStatus httpStatus) {
    this.success = success;
    this.message = message;
    this.status = httpStatus;
  }

  public boolean isSuccess() {
    return success;
  }
}
