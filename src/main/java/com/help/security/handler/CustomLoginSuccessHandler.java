package com.help.security.handler;

import java.io.IOException;
import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.help.account.entity.LoginUser;
import com.help.account.repository.AccountRepository;
import com.help.main.util.TokenUtils;
import com.help.security.constants.AuthConstants;
import com.help.main.util.ClientUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {	

    //form
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final LoginUser loginUser = ((LoginUser) authentication.getPrincipal());
        ClientUtil.getInfo(request);
        log.error("========CustomLoginSuccessHandler success :: {}", authentication);
        if (loginUser.getLoginStatus().equals("C")){
            log.error("======== 비밀번호 변경 대상 ======");
            response.sendRedirect("/setting/password");
        }
        else 
            response.sendRedirect("/");
    }
    //JWT
    // @Override
    // public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
    //                                     Authentication authentication) throws IOException {        
    //     final LoginUser loginUser = ((LoginUser) authentication.getPrincipal());
    //     final String token = TokenUtils.generateJwtToken(loginUser);
    //     response.addHeader(AuthConstants.AUTH_HEADER, AuthConstants.TOKEN_TYPE + " " + token);
    //     ClientUtil.getInfo(request);
    //     log.error("========token :: {}", token);
    //     log.error("========response :: {}", response);
    //     response.sendRedirect("/");
    // }
    

    
}