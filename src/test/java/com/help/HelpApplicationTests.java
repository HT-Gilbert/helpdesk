package com.help;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@SpringBootTest
public class HelpApplicationTests {
  

   private static final Logger logger = LoggerFactory.getLogger(HelpApplicationTests.class);

   @Autowired
   //private PasswordEncoder passwordEncoder;
   private BCryptPasswordEncoder passwordEncoder;

   @Test
   @DisplayName("패스워드 암호화 테스트")
   void passwordEncode() {
      // given
      String rawPassword = "hanabank1!";

      // when
      String encodedPassword = passwordEncoder.encode(rawPassword);

      logger.info("username ::[{}]", encodedPassword);

      // then
      assertAll(
            () -> assertNotEquals(rawPassword, encodedPassword),
            () -> assertTrue(passwordEncoder.matches(rawPassword, encodedPassword))
      );
   } 
}