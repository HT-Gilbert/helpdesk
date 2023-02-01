package com.help.content.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.help.account.entity.LoginUser;
import com.help.admin.service.BoardManageService;
import com.help.board.dto.SearchDto;
import com.help.board.service.BoardService;
import com.help.content.service.ContentService;
import com.help.main.dto.MessageDto;
import com.help.main.service.ContactService;
import com.help.main.service.MenuService;
import com.help.main.service.TagService;
import com.help.main.util.ClientUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RequiredArgsConstructor
@Controller
@Slf4j
public class ContentController {

	private final TagService tagService;
	private final MenuService menuService;
	private final ContentService contentService;
	
	@GetMapping("/content")
	public String getContentOne(Model model
	, @AuthenticationPrincipal LoginUser loginUser
	, @RequestParam(required = true) long contentId
	) throws Exception {

		try {

				// 로그인한 대상의 권한 파악
				String strRole;
				if(loginUser == null){
					log.error("========getMainPage LoginUser :: null", loginUser);
					strRole = "ROLE_ANONYMOUS";
				}
				else{
					log.error("========getMainPage LoginUser :: {}", loginUser);
					//List<GrantedAuthority> authorities = (List<GrantedAuthority>) loginUser.getAuthorities();
					strRole = loginUser.getUser().getUserRole();
				}
			
			
			model.addAttribute("topMenuList", menuService.getTopMenu());
			model.addAttribute("leftMenuList", menuService.getLeftMenu(strRole));

			 model.addAttribute("tagList", tagService.getTag());			 
			 model.addAttribute("resultMap", contentService.findById(contentId));
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}

		return "content/content";
	}
} 
