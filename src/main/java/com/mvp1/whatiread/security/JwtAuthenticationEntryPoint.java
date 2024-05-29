package com.mvp1.whatiread.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

  @Override
  public void commence(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, AuthenticationException e)
      throws IOException, ServletException {
    e.printStackTrace();
//    LOGGER.error("Responding with unauthorized error. Message - {} || {}", httpServletRequest, e.getStackTrace());
    httpServletResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED,
        "Sorry, You're not authorized to access this resource.");
  }
}
