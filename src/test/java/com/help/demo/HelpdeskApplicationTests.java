package com.help.demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

@SpringBootTest
@AutoConfigureMockMvc
class HelpdeskApplicationTests {

	@Autowired MockMvc mockMvc;

    /* 
    @Test
    @DisplayName("회원 가입 화면 진입 확인")
    void signUpForm() throws Exception {
        mockMvc.perform(get("/signup"))
                .andDo(print())
                .andExpect(status().isOk()) // (1)
                .andExpect(view().name("account/signup")) // (2)
                .andExpect(model().attributeExists("signUpForm")); // (3)
    }
    */

    // 프로퍼티 값 암호화 함수
    /*
    @Test
    public void encryptDecryptTest() {
        String text = "plain-text"; // 암호화 대상 평문
 
        StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
        jasypt.setPassword(System.getenv("JASYPT_KEY")); // Jasypt를 이용하여 암호화하는 키 (프로퍼티의 jasypt.encryptor.password)
        jasypt.setAlgorithm("PBEWithMD5AndDES"); // Jasypt를 이용한 암호화 알고리즘
 
        String encryptedText = jasypt.encrypt(text);
        System.out.println("enc : " + encryptedText);
        String decryptedText = jasypt.decrypt(encryptedText);
        System.out.println("dec : " + decryptedText);
    }
    */

}
