package com.mvp1.whatiread.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

  @Value("${openapi.dev.url}")
  private String serverUrl;

  @Bean
  public OpenAPI myOpenAPI() {
    Server server = new Server();
    server.setUrl(serverUrl);
    server.setDescription("Server URL");

    Contact contact = new Contact();
    contact.setEmail("someone@gmail.com");
    contact.setName("Developer");
    contact.setUrl("https://www.developer.com");

    License mitLicense = new License().name("MIT License")
        .url("https://choosealicense.com/licenses/mit/");

    Info info = new Info()
        .title("WhatIRead API")
        .version("1.0")
        .contact(contact)
        .description("This API exposes endpoints to Property management.")
        .termsOfService("https://www.developer.com/terms")
        .license(mitLicense);

    return new OpenAPI().info(info).servers(List.of(server));
  }
}
