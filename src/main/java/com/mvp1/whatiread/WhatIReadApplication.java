package com.mvp1.whatiread;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class WhatIReadApplication {

  public static void main(String[] args) {
    SpringApplication.run(WhatIReadApplication.class, args);
  }

}
