package com.help.controller.board.driver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.help.service.main.TagService;
import com.help.service.main.MenuService;
import com.help.service.board.driver.DriverService;
import com.help.dto.board.driver.DriverRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class DriverController {
    
	private final TagService tagService;
	private final MenuService menuService;
	private final DriverService driverService;

    @GetMapping("/board/driver")
	public String getDriverListPage(Model model
			, @RequestParam(required = false, defaultValue = "0") Integer page
			, @RequestParam(required = false, defaultValue = "10") Integer size) throws Exception {
		
		try {
			model.addAttribute("resultMap", driverService.findAll(page, size));
			model.addAttribute("tagList", tagService.getTag());
			//model.addAttribute("categoryList", categoryService.getCategory());
			model.addAttribute("menuList", menuService.getMenu());
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "/board/driver";
	}

    @GetMapping("/board/driver/write")
	public String getDriverWritePage(Model model, DriverRequestDto driverRequestDto) {
		return "/board/driver/write";
	}
	
	@GetMapping("/board/driver/view")
	public String getDriverViewPage(Model model, DriverRequestDto driverRequestDto) throws Exception {
		
		try {
			if (driverRequestDto.getId() != null) {
				model.addAttribute("dresultMap", driverService.findById(driverRequestDto.getId()));
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "/board/driver/view";
	}

	@PostMapping("/board/driver/delete")
	public String boardDeleteAction(Model model, @RequestParam() Long[] deleteId) throws Exception {
		
		try {
			driverService.deleteAll(deleteId);
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/board/driver";
	}
}
