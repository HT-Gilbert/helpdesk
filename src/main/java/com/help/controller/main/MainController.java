package com.help.controller.main;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.help.service.board.BoardService;
import com.help.service.main.ContactService;
import com.help.service.main.TagService;
import com.help.service.main.CategoryService;
import com.help.service.main.MenuService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class MainController {

	private final BoardService boardService;
    private final ContactService contactService;
	private final TagService tagService;
	private final CategoryService categoryService;
	private final MenuService menuService;
	
	@GetMapping("/")
	public String getMainBoardListPage(Model model
			, @RequestParam(required = false, defaultValue = "0") Integer page
			, @RequestParam(required = false, defaultValue = "8") Integer size) throws Exception {
		
		try {
			model.addAttribute("resultMap", boardService.findAll(page, size));
			model.addAttribute("contactList", contactService.getContacts());
			model.addAttribute("tagList", tagService.getTag());
			model.addAttribute("categoryList", categoryService.getCategory());
			model.addAttribute("menuList", menuService.getMenu());
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "/index";
	}
} 
