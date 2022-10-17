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

import com.help.admin.service.BoardManageService;
import com.help.board.dto.SearchDto;
import com.help.board.service.BoardService;
import com.help.main.dto.MessageDto;
import com.help.main.entity.LoginUser;
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
public class MainController {

	private final BoardService boardService;
    private final ContactService contactService;
	private final TagService tagService;
	private final MenuService menuService;
	private final BoardManageService boardListService;
	
	// 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
	private String showMessageAndRedirect(final MessageDto params, Model model) {
		model.addAttribute("params", params);
		return "common/messageRedirect";
	}

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
			//, @CurrentUser LoginUser loginUser) throws Exception {
			, @AuthenticationPrincipal LoginUser loginUser
			) throws Exception {
		
		try {

			if(loginUser == null){
				log.error("========getMainPage LoginUser :: null", loginUser);
			}
			else{
				Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
				log.error("========getBoardSearchPage LoginUser :: {}", loginUser);
			}

			ClientUtil.getInfo(request);

			params.setPageSize(8);
			params.setRecordSize(8);
			params.setBoardTitle(boardTitle);
//			model.addAttribute("resultMap", boardService.findAll(page, size));
//			model.addAttribute("contactList", contactService.getContacts());
			model.addAttribute("tagList", tagService.getTag());
//			model.addAttribute("categoryList", categoryService.getCategory());
			model.addAttribute("menuList", menuService.getMenu());
			model.addAttribute("resultMap", boardService.findAll(params));
			model.addAttribute("boardList", boardListService.getBoardListbyMB());
			model.addAttribute("loginUser", loginUser);
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		//return "/layout/index";
		return "layout/index";	//jre
	}

	@GetMapping("/construction")
	public String showUnderConstruction(Model model) throws Exception {
		
		try {
			model.addAttribute("tagList", tagService.getTag());
			model.addAttribute("menuList", menuService.getMenu());
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		//return "/layout/index";
		return "layout/construction";	//jre
	}
} 
