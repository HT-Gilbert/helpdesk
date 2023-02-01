package com.help.config;

import org.springframework.context.annotation.Bean;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.help.account.service.AccountService;
import com.help.security.exception.CustomAccessDeniedHandler;
import com.help.security.exception.CustomAuthenticationEntryPoint;
import com.help.security.filter.CustomAuthenticationFilter;
import com.help.security.filter.CustomAuthenticationProvider;
import com.help.security.handler.CustomAuthFailureHandler;
import com.help.security.handler.CustomLoginSuccessHandler;

//@RequiredArgsConstructor
//@EnableWebSecurity //(debug = true) 콘솔에 로그 출력값 // 시큐리티 필터 등록
@EnableGlobalMethodSecurity(prePostEnabled = true) // 특정 페이지에 특정 권한이 있는 유저만 접근을 허용할 경우 권한 및 인증을 미리 체크하겠다는 설정(ex. @Secured("ROLE_ADMIN"))을 활성화
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig{
	
	@Autowired
	private final AccountService accountService;
	private final BCryptPasswordEncoder passwordEncoder;
	private SessionRegistry sessionRegistry;

	/*
	@Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private AuthenticationEntryPoint authenticationEntryPoint;
    @Autowired
    private AccessDeniedHandler accessDeniedHandler;
	 */
	
	// BCryptPasswordEncoder는 Spring Security에서 제공하는 비밀번호 암호화 객체 (BCrypt라는 해시 함수를 이용하여 패스워드를 암호화 한다.)
	// 회원 비밀번호 등록시 해당 메서드를 이용하여 암호화해야 로그인 처리시 동일한 해시로 비교한다.
	// @Bean
    // public BCryptPasswordEncoder bCryptPasswordEncoder() {
    //     return new BCryptPasswordEncoder();
    // }

	@Bean
    public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManager(null));
        customAuthenticationFilter.setFilterProcessesUrl("/login/action");
		customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler());
		customAuthenticationFilter.setAuthenticationFailureHandler(customAuthFailureHandler());
        //customAuthenticationFilter.setAuthenticationSuccessHandler(customLoginSuccessHandler);		
        customAuthenticationFilter.afterPropertiesSet();
        return customAuthenticationFilter;
    }

	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

	 @Bean
     public CustomLoginSuccessHandler customLoginSuccessHandler() {
         return new CustomLoginSuccessHandler();
     }

	 @Bean
     public CustomAuthFailureHandler customAuthFailureHandler() {
         return new CustomAuthFailureHandler();
     }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(accountService, passwordEncoder);
    }

	@Bean
    public WebSecurityCustomizer configure() {
        return (web) -> web.ignoring().antMatchers("/css/**", "/js/**", "/img/**", "/lib/**", "/resources/**")
		.requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		/*
		 csrf 토큰 활성화시 사용
		 쿠키를 생성할 때 HttpOnly 태그를 사용하면 클라이언트 스크립트가 보호된 쿠키에 액세스하는 위험을 줄일 수 있으므로 쿠키의 보안을 강화할 수 있다.
		*/
		// URL 인증여부 설정
        http.csrf().disable()	// csrf 토큰을 비활성화
        	.authorizeRequests() // 요청 URL에 따라 접근 권한을 설정
			.antMatchers("/admin/**").hasRole("ADMIN")
			.antMatchers("/","/**","/login/**","/login/action/**","/js/**","/css/**","/scripts/**","/image/**","/board/**","/excel/**","/content/**","/main/**").permitAll() // 해당 경로들은 접근을 허용
			.anyRequest()
			.authenticated(); // 인증된 유저만 접근을 허용

		// 로그인 관련 설정
			http.formLogin() // 로그인 폼은
			.loginPage("/login") // 해당 주소로 로그인 페이지를 호출한다.
			.successForwardUrl("/")
			.failureForwardUrl("/login")			// 로그인 실패 URL을 설정함
			.permitAll()
			.and()
			.addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		//로그아웃 설정
			http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) // 로그아웃 URL
		    .logoutSuccessUrl("/") // 성공시 리턴 URL
		    .invalidateHttpSession(true) // 인증정보를 지우하고 세션을 무효화
		    .deleteCookies("JSESSIONID") // JSESSIONID 쿠키 삭제
			.permitAll()
			.and()
        	.sessionManagement()
			.sessionFixation().changeSessionId()
			.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
            .maximumSessions(1) // 세션 최대 허용 수 1, -1인 경우 무제한 세션 허용
            .maxSessionsPreventsLogin(true) // true면 중복 로그인을 막고, false면 이전 로그인의 세션을 해제
            .expiredUrl("/login?error=true&exception=Have been attempted to login from a new place. or session expired")  // 세션이 만료된 경우 이동 할 페이지를 지정			
        	.and()
	        .and().rememberMe() // 로그인 유지
	        .alwaysRemember(false) // 항상 기억할 것인지 여부
	        .tokenValiditySeconds(43200) // in seconds, 12시간 유지
	        .rememberMeParameter("remember-me");
		// //비인가자 요청시 보낼 Api URI
        // http.exceptionHandling().accessDeniedPage("/forbidden");

		/* JWT인증 테스트(Json Web Token)
		// http.csrf().disable().authorizeRequests()				
        //         // 토큰을 활용하는 경우 모든 요청에 대해 접근이 가능하도록 함
		// 		.antMatchers("/","/**","/login/**","/login/action/**","/js/**","/css/**","/scripts/**","/image/**","/board/**","/excel/**","/content/**","/main/**").permitAll() // 해당 경로들은 접근을 허용
		// 		.anyRequest()
		// 		.authenticated(); // 인증된 유저만 접근을 허용

		// //Oauth2 설정
		// //private final OAuth2SuccessHandler oAuth2SuccessHandler;
		// // http.oauth2Login().userInfoEndpoint().userService(new OAuth2UserServiceImpl());
		// // http.oauth2Login().successHandler(oAuth2SuccessHandler);

		// // 토큰을 활용하면 세션이 필요 없으므로 STATELESS로 설정하여 Session을 사용하지 않는다.
        // http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// // form 기반의 로그인에 대해 비활성화 한다.
        // http.formLogin()
        //      .disable();
		// //JwtFilter 추가
		// http.addFilterBefore(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

		// //JwtAuthentication exception handling
		// http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());

		// //access Denial handler
		// http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedHandler());
		*/
		return http.build();
    }
}