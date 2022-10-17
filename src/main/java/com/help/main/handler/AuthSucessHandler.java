package com.help.main.handler;

import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.help.main.repository.MemberRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class AuthSucessHandler extends SimpleUrlAuthenticationSuccessHandler {
	
	private final MemberRepository memberRepository;
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        
        memberRepository.updateMemberLastLogin(authentication.getName(), LocalDateTime.now());
        setDefaultTargetUrl("/");
        
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
