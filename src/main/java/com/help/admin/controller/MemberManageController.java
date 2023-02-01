package com.help.admin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.dao.DataAccessException;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.help.admin.dto.BoardListDto;
import com.help.admin.service.BoardManageService;
import com.help.admin.service.MemberManageService;
import com.help.main.service.MenuService;
import com.help.main.service.TagService;
import com.help.board.repository.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MemberManageController {
    private final MenuService menuService;
    private final MemberManageService memberManageService;
	private final BoardMapper boardMapper;

    @GetMapping("/admin/member")
	public String getMemberControlPage(Model model) throws Exception {
		
		try {
			model.addAttribute("memberList", memberManageService.getMemberList());
			model.addAttribute("menuList", menuService.getMenu());

		return "admin/member";
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
	}
}
