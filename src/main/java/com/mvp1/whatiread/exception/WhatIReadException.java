package com.mvp1.whatiread.exception;

import java.io.Serial;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class WhatIReadException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  private final HttpStatus status;
  private final String message;

  public WhatIReadException(HttpStatus status, String message) {
    super();
    this.status = status;
    this.message = message;
  }

  public WhatIReadException(HttpStatus status, String message, Throwable exception) {
    super(exception);
    this.status = status;
    this.message = message;
  }
}
