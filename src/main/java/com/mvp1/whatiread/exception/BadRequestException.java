package com.mvp1.whatiread.exception;

import com.mvp1.whatiread.dto.ApiResponse;
import java.io.Serial;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;
  private ApiResponse apiResponse;

  public BadRequestException(ApiResponse apiResponse) {
    super();
    this.apiResponse = apiResponse;
  }

  public BadRequestException(String message) {
    super(message);
    apiResponse = new ApiResponse(Boolean.FALSE, message);
  }

  public BadRequestException(String message, Throwable cause) {
    super(message, cause);
    apiResponse = new ApiResponse(Boolean.FALSE, message);
  }
}
