// Source code is decompiled from a .class file using FernFlower decompiler.
package com.camunda.demo.bit;

import com.camunda.demo.bit.storage.StorageProperties;
import com.camunda.demo.bit.storage.StorageService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
@EnableConfigurationProperties({StorageProperties.class})
public class BitApplication {
   public BitApplication() {
   }

   public static void main(String[] args) {
      SpringApplication.run(BitApplication.class, args);
   }

   @Bean
   CommandLineRunner init(StorageService storageService) {
      return (args) -> {
         storageService.init();
      };
   }

   JavaMailSender mailSender() {
      return new JavaMailSenderImpl();
   }
}
