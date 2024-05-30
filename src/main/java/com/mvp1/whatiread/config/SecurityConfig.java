package com.mvp1.whatiread.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.mvp1.whatiread.security.JwtAuthenticationEntryPoint;
import com.mvp1.whatiread.security.JwtAuthenticationFilter;
import com.mvp1.whatiread.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  private static final String[] AUTH_WHITELIST = {
      "/v2/api-docs",
      "/swagger-resources",
      "/swagger-resources/**",
      "/configuration/ui",
      "/configuration/security",
      "/swagger-ui.html",
      "/webjars/**",
      // -- Swagger UI v3 (OpenAPI)
      "/v3/api-docs/**",
      "/swagger-ui/**",
      "/h2-console/**"
  };
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

//  @Bean
//  public SecurityFilterChain temp(HttpSecurity http) throws Exception{
//    return http.cors(cors -> cors.disable()).csrf(csrf -> csrf.disable())
//        .authorizeHttpRequests(auth -> {
//          auth.requestMatchers("/**").permitAll();
//        })
//        .httpBasic(withDefaults())
//        .build();
//  }

  @Bean
  public SecurityFilterChain configureUserSecurity(HttpSecurity http) throws Exception {
    http.cors(cors -> cors.disable())
        .csrf(csrf -> csrf.ignoringRequestMatchers(
            AntPathRequestMatcher.antMatcher("/h2-console/**")))
        .exceptionHandling(ex -> ex.authenticationEntryPoint(unauthorizedHandler))
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers("/api/users/checkUsernameAvailability",
                  "/api/users/checkEmailAvailability", "/api/auth/**", "/h2-console/**",
                  "/swagger-ui/**").permitAll()
              .anyRequest().authenticated();
        })
        .httpBasic(withDefaults())
        .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  @Bean
  public AuthenticationManager authenticationManager(
      AuthenticationConfiguration authenticationConfiguration) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

}
