package com.mvp1.whatiread.config;

import static org.springframework.security.config.Customizer.withDefaults;

import com.mvp1.whatiread.security.JwtAuthenticationEntryPoint;
import com.mvp1.whatiread.security.JwtAuthenticationFilter;
import com.mvp1.whatiread.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
//  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//    http.csrf().disable()
//        .authorizeRequests()
//        .anyRequest().permitAll(); // Disable security
//    return http.build();
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
          auth.requestMatchers("/h2-console/**").permitAll()
              .requestMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
//              .requestMatchers(AUTH_WHITELIST).permitAll()
              .requestMatchers(HttpMethod.GET, "/api/users/checkUsernameAvailability",
                  "/api/users/checkEmailAvailability").permitAll()
              .anyRequest().authenticated();
        })
        .httpBasic(withDefaults())
        .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.disable()));
    http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }

  //  @Bean
//  CorsConfigurationSource corsConfigurationSource() {
//    CorsConfiguration configuration = new CorsConfiguration();
//    configuration.setAllowedOrigins(List.of("http://localhost:8085"));
//    configuration.setAllowedMethods(List.of("GET", "POST"));
//    configuration.setAllowedHeaders(List.of("Authorization", "Content-type"));
//    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//    source.registerCorsConfiguration("/**", configuration);
//    return source;
//  }
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
