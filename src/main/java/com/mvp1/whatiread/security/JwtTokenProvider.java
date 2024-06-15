package com.mvp1.whatiread.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import javax.crypto.SecretKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class JwtTokenProvider {

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenProvider.class);

  @Value(value = "${app.jwtSecret}")
  private String jwtSecret;

  @Value(value = "${app.jwtExpirationInMs}")
  private int jwtExpirationInMs;

  public String getJwtFromHeader(HttpServletRequest request) {
    String bearerToken = request.getHeader("Authorization");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public String generateTokenFromUsername(UserDetails userDetails) {
    Date now = new Date();
    Date expiryDate = new Date(now.getTime() + jwtExpirationInMs);

    return Jwts.builder()
        .subject(userDetails.getUsername())
        .issuedAt(now)
        .expiration(expiryDate)
        .signWith(key())
        .compact();
  }

  public String getUserNameFromToken(String authToken) {
    return Jwts.parser().verifyWith((SecretKey) key())
        .build().parseSignedClaims(authToken).getPayload().getSubject();
  }

  private Key key() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
  }

  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().verifyWith((SecretKey) key())
          .build().parseSignedClaims(authToken);
      LOGGER.info("Validated successfully");
      return true;
    } catch (SignatureException ex) {
      LOGGER.error("Invalid JWT signature: {}", ex.getMessage());
    } catch (MalformedJwtException ex) {
      LOGGER.error("JWT token is malformed: {}", ex.getMessage());
    } catch (ExpiredJwtException ex) {
      LOGGER.error("JWT token is expired: {}", ex.getMessage());
    } catch (UnsupportedJwtException ex) {
      LOGGER.error("JWT token is unsupported: {}", ex.getMessage());
    } catch (IllegalArgumentException ex) {
      LOGGER.error("JWT claims string is empty: {}", ex.getCause());
    }
    return false;
  }
}
