package com.help.security.filter;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.help.account.entity.LoginUser;
import com.help.account.repository.AccountRepository;
import com.help.account.service.AccountService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final AccountService accountService;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        // AuthenticaionFilter에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
        final String userName = token.getName();
        final String userPw = (String)token.getCredentials();

        final LoginUser loginUser = (LoginUser) accountService.loadUserByUsername(userName);

        if (!passwordEncoder.matches(userPw, loginUser.getUser().getPassword())) {
            throw new BadCredentialsException(loginUser.getUser().getUserName() + "Invalid password");
        }
        //loginUser.setLoginId(userName);
        accountService.updateMemberLastLogin(userName);

        return new UsernamePasswordAuthenticationToken(loginUser, userPw, loginUser.getAuthorities());
        
    }
    //form
    // public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    //     UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
    //     // AuthenticaionFilter에서 생성된 토큰으로부터 아이디와 비밀번호를 조회함
    //     String userName = token.getName();
    //     String userPw = (String)token.getCredentials();

    //     LoginUser loginUser = (LoginUser) accountService.loadUserByUsername(userName);

    //     if (!passwordEncoder.matches(userPw, loginUser.getPassword())) {
    //         throw new BadCredentialsException(loginUser.getUsername() + "Invalid password");
    //     }

    //     return new UsernamePasswordAuthenticationToken(loginUser, userPw, loginUser.getAuthorities());
        
    // }
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

}
