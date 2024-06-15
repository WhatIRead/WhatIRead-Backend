package com.mvp1.whatiread.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.mvp1.whatiread.security.JwtAuthenticationEntryPoint;
import com.mvp1.whatiread.security.JwtAuthenticationFilter;
import com.mvp1.whatiread.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer.FrameOptionsConfig;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
    securedEnabled = true,
    jsr250Enabled = true,
    prePostEnabled = true)
public class SecurityConfig {

  private final CustomUserDetailsService customUserDetailsService;
  private final JwtAuthenticationEntryPoint unauthorizedHandler;
  private final JwtAuthenticationFilter jwtAuthenticationFilter;

  public SecurityConfig(CustomUserDetailsService customUserDetailsService,
      JwtAuthenticationEntryPoint unauthorizedHandler,
      JwtAuthenticationFilter jwtAuthenticationFilter) {
    this.customUserDetailsService = customUserDetailsService;
    this.unauthorizedHandler = unauthorizedHandler;
    this.jwtAuthenticationFilter = jwtAuthenticationFilter;
  }

  @Bean
  @Order(1)
  public SecurityFilterChain configureUserSecurity(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests(auth -> {
          auth.requestMatchers("/api/users/checkUsernameAvailability",
                  "/api/users/checkEmailAvailability",
                  "/api/auth/**",
                  "/h2-console/**",
                  "/swagger-ui/**").permitAll()
              .anyRequest().authenticated();
        })
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
        .headers(headers ->
            headers.frameOptions(FrameOptionsConfig::sameOrigin))
        .csrf(AbstractHttpConfigurer::disable)
        .httpBasic(withDefaults());
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authConfig) throws Exception {
    return authConfig.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  //  Allow CORS configuration
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins("*");
//            .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD")
//            .maxAge(3600)
//            .allowedHeaders("Requestor-Type", "Authorization")
//            .exposedHeaders("X-Get-Header");;
      }
    };
  }
}
