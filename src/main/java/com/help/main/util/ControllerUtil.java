package com.help.main.util;

import org.springframework.ui.Model;

import com.help.account.entity.LoginUser;
import com.help.main.dto.MessageDto;

import lombok.extern.slf4j.Slf4j;

/*
 *  Controller 에서 사용할 공통 함수
 */
@Slf4j
public class ControllerUtil {
    public static String getRole(LoginUser loginUser) {
		String strRole;
		if(loginUser == null){
			log.error("========getRole LoginUser :: null", loginUser);
			strRole = "ROLE_ANONYMOUS";
		}
		else{
			log.error("========getRole LoginUser :: {}", loginUser);
			//List<GrantedAuthority> authorities = (List<GrantedAuthority>) loginUser.getAuthorities();
			strRole = loginUser.getUser().getUserRole();
		}
		return strRole;
	}
}
