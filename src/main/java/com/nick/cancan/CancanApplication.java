package com.nick.cancan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
public class CancanApplication {

  public static void main(String[] args) {
    SpringApplication.run(CancanApplication.class, args);
  }
}
