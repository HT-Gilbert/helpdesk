package com.help.main.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.help.main.util.ControllerUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MainController {

	private final BoardService boardService;
	private final ContentService contentService;
	private final TagService tagService;
	private final MenuService menuService;
	private final BoardManageService boardManageService;
	
	@Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : loginUser")
    public @interface CurrentUser {
    }
	
	@GetMapping("/")
	public String getMainPage(Model model
			, @RequestParam(required = false, defaultValue = "공지사항") String boardTitle
			, @ModelAttribute("params") final SearchDto params
			, HttpServletRequest request
			//, @CurrentUser LoginUser loginUser
			, @AuthenticationPrincipal LoginUser loginUser
			) throws Exception {
		
		try {

				// 로그인한 대상의 권한 파악
				String strRole = ControllerUtil.getRole(loginUser);

			ClientUtil.getInfo(request);

			params.setPageSize(8);
			params.setRecordSize(8);
			params.setBoardTitle(boardTitle);
			model.addAttribute("topMenuList", menuService.getTopMenu());
			model.addAttribute("leftMenuList", menuService.getLeftMenu(strRole));
			model.addAttribute("resultMap", boardService.findAll(params));
			model.addAttribute("boardList", boardManageService.getBoardListbyMB());
			model.addAttribute("loginUser", loginUser);
			model.addAttribute("tagList", tagService.getUserTag(strRole));
			model.addAttribute("contactList", contentService.findById((long) 2));

		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		//return "/layout/index";
		return "layout/index";	//jre
	}

	@GetMapping("/construction")
	public String showUnderConstruction(Model model
		, @AuthenticationPrincipal LoginUser loginUser) throws Exception {
		
		try {
				// 로그인한 대상의 권한 파악
				String strRole = ControllerUtil.getRole(loginUser);
			
			model.addAttribute("topMenuList", menuService.getTopMenu());
			model.addAttribute("leftMenuList", menuService.getLeftMenu(strRole));
			model.addAttribute("tagList", tagService.getTag());
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		//return "/layout/index";
		return "layout/construction";	//jre
	}
} 
