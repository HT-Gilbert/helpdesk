package com.help.controller.board.driver;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

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

    @GetMapping("/board/driver/list")
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
		
		return "/board/driver/list";
	}

    @GetMapping("/board/driver/write")
	public String getDriverWritePage(Model model, DriverRequestDto driverRequestDto) throws Exception {
		try {
			model.addAttribute("menuList", menuService.getMenu());
			model.addAttribute("tagList", tagService.getTag());
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 		
		}
		return "/board/driver/write";
	}

	@GetMapping("/board/driver/view")
	public String getDriverViewPage(Model model, DriverRequestDto driverRequestDto) throws Exception {
		
		try {
			if (driverRequestDto.getId() != null) {
				model.addAttribute("resultMap", driverService.findById(driverRequestDto.getId()));
				model.addAttribute("menuList", menuService.getMenu());
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "/board/driver/view";
	}

	@PostMapping("/board/driver/write/action")
	public String driverWriteAction(Model model, DriverRequestDto driverRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		
		try {
			if (!driverService.save(driverRequestDto, multiRequest)) {
				throw new Exception("#Exception boardWriteAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/board/driver/list";
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
