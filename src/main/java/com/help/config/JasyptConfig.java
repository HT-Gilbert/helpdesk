package com.help.config;

import lombok.RequiredArgsConstructor;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@RequiredArgsConstructor
@Configuration
public class JasyptConfig {

    /*
     * 키값 가져오는 방법
     */
    // 1)  -Djasypt.encryptor.key=XXXXX
    // 2) java -jar file.jar --jasypt.encryptor.key=XXXXX &
    //@Value("${jasypt.encryptor.key}")
    //private String encryptKey;
    //config.setPassword(encryptKey);

    // 3) 환경변수
    //System.getenv("JASYPT_KEY");
    //config.setPassword(System.getenv("JASYPT_KEY"));

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();        
        config.setPassword(System.getenv("JASYPT_KEY"));
        //config.setPassword(encryptKey);      키주입시 사용
        //config.setPassword("helpdesk");
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");
        encryptor.setConfig(config);
        return encryptor;
    }
 }

