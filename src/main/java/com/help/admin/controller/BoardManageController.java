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
import com.help.main.service.MenuService;
import com.help.main.service.TagService;
import com.help.board.repository.BoardMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class BoardManageController {
    
	private final TagService tagService;
	private final MenuService menuService;
    private final BoardManageService boardManageService;
	private final BoardMapper boardMapper;

    @GetMapping("/admin/board")
	public String getBoardControlPage(Model model) throws Exception {
		
		try {
			//model.addAttribute("tagList", tagService.getTag());
			model.addAttribute("boardList", boardManageService.getBoardListbyMB());
			model.addAttribute("menuList", menuService.getMenu());
			model.addAttribute("roleList", boardMapper.getRoleList(null));

		return "admin/board";
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
	}

	@GetMapping("/admin/boardCreate")
	public String createBoard(Model model) throws Exception{

		try{
			//model.addAttribute("tagList", tagService.getTag());
			model.addAttribute("boardList", boardManageService.getBoardListbyMB());
			model.addAttribute("menuList", menuService.getMenu());
			model.addAttribute("roleList", boardMapper.getRoleList(null));
			return "admin/boardCreate";
		} catch (Exception e ) {
			throw new Exception(e.getMessage());
		}
	}

	@PostMapping("/admin/boardCreate/action")
	public String createBoardAction(Model model, BoardListDto boardListDto) throws Exception {
		try {
			if (!boardManageService.createBoardTable(boardListDto)) {
				throw new Exception("#Exception createBoardAction!");
			}
		}  catch (DataAccessException e) {
			throw new Exception(e.getMessage()); 

		} 
		catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/admin/board";
	}

	@PostMapping("/admin/board/delete")
	public String boardListDeleteAction(Model model, 
		@RequestParam() Long[] deleteId) throws Exception {

		try {
			boardManageService.deleteAll(deleteId);
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/admin/board";
	}

	@PostMapping("/admin/board/edit")
	public String boardListEditAction(Model model, 
		@RequestParam() Long[] deleteId) throws Exception {

		try {
			boardManageService.deleteAll(deleteId);
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/admin/board";
	}
}
