package com.mvp1.whatiread.exception;

import com.mvp1.whatiread.dto.ApiResponse;
import java.io.Serial;
import org.springframework.http.ResponseEntity;

public class ResponseEntityErrorException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  private final transient ResponseEntity<ApiResponse> apiResponse;

  public ResponseEntityErrorException(ResponseEntity<ApiResponse> apiResponse) {
    this.apiResponse = apiResponse;
  }
}
