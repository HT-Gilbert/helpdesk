package com.help.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	@Bean
    MappingJackson2JsonView jsonView() {
    	return new MappingJackson2JsonView();
    }

    /* application.properties 에서 설정함
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setDefaultEncoding("UTF-8"); // 파일 인코딩 설정
        multipartResolver.setMaxUploadSizePerFile(50 * 1024 * 1024); // 파일당 업로드 크기 제한 (50MB)
        return multipartResolver;
    }
     */
}