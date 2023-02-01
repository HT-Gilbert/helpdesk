package com.help.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mobile.device.DeviceResolverHandlerInterceptor;
import org.springframework.mobile.device.switcher.SiteSwitcherHandlerInterceptor;
import org.springframework.mobile.device.view.LiteDeviceDelegatingViewResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import com.help.main.interceptor.LoggerInterceptor;
import com.help.security.filter.HeaderFilter;
import com.help.security.interceptor.JwtTokenInterceptor;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new LoggerInterceptor())
		.excludePathPatterns("/css/**", "/fonts/**", "/login/**", "/plugin/**", "/scripts/**", "/js/**", "/error/**", "/font/**", "/webfonts/**", "/summernote/**");
    registry.addInterceptor(new DeviceResolverHandlerInterceptor());
    registry.addInterceptor(jwtTokenInterceptor())
    .excludePathPatterns("/**", "/fonts/**", "/login/**", "/plugin/**", "/scripts/**", "/js/**", "/error/**", "/font/**", "/webfonts/**", "/summernote/**");
	}

    @Bean
    MappingJackson2JsonView jsonView() {
      return new MappingJackson2JsonView();
    }

		//web root가 아닌 외부 경로에 있는 리소스를 url로 불러올 수 있도록 설정
    //현재 localhost:8090/summernoteImage/1234.jpg
    //로 접속하면 C:/summernote_image/1234.jpg 파일을 불러온다.
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/summernoteImage/**")
                .addResourceLocations("file:///summernote_image/");
    }

    /*
     **** 디바이스별 화면 추가한다면 아래 내용 공부해서 적용하자('/'경로는 pc, '/m'경로는 모바일 및 태블릿) *****
     * DeviceResolverHandlerInterceptor 빈(bean) 생성
        - resolve the device that originated the web request
        - 웹 요청으로부터 디바이스를 식별해내는 기능을 제공
      SiteSwitcherHandlerInterceptor 빈(bean) 생성
        - 모바일, 태블릿, 루트(root)에 따른 API 경로 prefix를 지정
        - 모바일과 태블릿로 접근 시 /m 경로가 앞에 자동적으로 추가
        - PC로 접근 시 루트인 / 경로부터 시작합니다.
        - urlPath() 메소드의 파라미터 개수에 따라 각 의미가 달라집니다.
           1개인 경우 모바일 URL 경로 지정
           2개인 경우 순서대로 모바일, 루트 URL 경로 지정
           3개인 경우 순서대로 모바일, 태블릿, 루트 URL 경로 지정
      LiteDeviceDelegatingViewResolver 빈(bean) 생성
        - 디바이스 별로 사용되는 페이지에 대한 자원 경로를 지정
        - 모바일은 /mobile 폴더 안에 위치한 JSP 자원을 사용
        - 태블릿은 /mobile 폴더 안에 위치한 JSP 자원을 사용
        - 일반은 /pc 폴더 안에 위치한 JSP 자원을 사용
     */
    /*
    @Bean
    public DeviceResolverHandlerInterceptor deviceResolverHandlerInterceptor() {
        return new DeviceResolverHandlerInterceptor();
    }

    @Bean
    public SiteSwitcherHandlerInterceptor siteSwitcherHandlerInterceptor() {
        return SiteSwitcherHandlerInterceptor.urlPath("/m", "/m", "/");
    }

    @Bean
    public LiteDeviceDelegatingViewResolver liteDeviceAwareViewResolver() {
        InternalResourceViewResolver delegate = new InternalResourceViewResolver();
        delegate.setPrefix("/WEB-INF/jsp/");
        delegate.setSuffix(".jsp");
        LiteDeviceDelegatingViewResolver resolver = new LiteDeviceDelegatingViewResolver(delegate);
        resolver.setMobilePrefix("mobile/");
        resolver.setTabletPrefix("mobile/");
        resolver.setNormalPrefix("pc/");
        return resolver;
    }
    */


    @Bean
    public FilterRegistrationBean<HeaderFilter> getFilterRegistrationBean() {
        FilterRegistrationBean<HeaderFilter> registrationBean = new FilterRegistrationBean<>(createHeaderFilter());
        registrationBean.setOrder(Integer.MIN_VALUE);
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }

    @Bean
    public HeaderFilter createHeaderFilter() {
        return new HeaderFilter();
    }

    @Bean
    public JwtTokenInterceptor jwtTokenInterceptor() {
        return new JwtTokenInterceptor();
    }
}