package com.mvp1.whatiread.exception;

import com.mvp1.whatiread.dto.ApiResponse;
import java.io.Serial;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class UnauthorizedException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;
  private ApiResponse apiResponse;
  private String message;

  public UnauthorizedException(ApiResponse apiResponse) {
    super();
    this.apiResponse = apiResponse;
  }

  public UnauthorizedException(String message) {
    super(message);
    this.message = message;
    apiResponse = new ApiResponse(Boolean.FALSE, message);
  }

  public UnauthorizedException(String message, Throwable cause) {
    super(message, cause);
    apiResponse = new ApiResponse(Boolean.FALSE, message);
  }

}
