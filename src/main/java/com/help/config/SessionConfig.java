package com.help.config;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import lombok.extern.slf4j.Slf4j;


//@Slf4j
// @Configuration
// @EnableRedisHttpSession
// public class SessionConfig extends AbstractHttpSessionApplicationInitializer {

//     @Bean	// 접속정보(application에 등록해도됨)
//     public LettuceConnectionFactory connectionFactory() {
//         return new LettuceConnectionFactory("127.0.0.1", 6379); // ip, port
//     }

// }