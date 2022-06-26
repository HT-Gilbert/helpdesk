package com.help.controller.board.printer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.help.service.main.TagService;
import com.help.service.main.MenuService;
import com.help.service.board.printer.PrinterService;
import com.help.dto.board.printer.PrinterRequestDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class PrinterController {
    
	private final TagService tagService;
	private final MenuService menuService;
	private final PrinterService printerService;

    @GetMapping("/board/printer/list")
	public String getPrinterListPage(Model model
			, @RequestParam(required = false, defaultValue = "0") Integer page
			, @RequestParam(required = false, defaultValue = "10") Integer size) throws Exception {
		
		try {
			model.addAttribute("resultMap", printerService.findAll(page, size));
			model.addAttribute("tagList", tagService.getTag());
			//model.addAttribute("categoryList", categoryService.getCategory());
			model.addAttribute("menuList", menuService.getMenu());
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "/board/printer/list";
	}

    @GetMapping("/board/printer/write")
	public String getPrinterWritePage(Model model, PrinterRequestDto printerRequestDto) throws Exception {
		try {
			model.addAttribute("menuList", menuService.getMenu());
			model.addAttribute("tagList", tagService.getTag());
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 		
		}
		return "/board/printer/write";
	}

	@GetMapping("/board/printer/view")
	public String getPrinterViewPage(Model model, PrinterRequestDto printerRequestDto) throws Exception {
		
		try {
			if (printerRequestDto.getId() != null) {
				model.addAttribute("resultMap", printerService.findById(printerRequestDto.getId()));
				model.addAttribute("menuList", menuService.getMenu());
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "/board/printer/view";
	}

	@PostMapping("/board/printer/write/action")
	public String printerWriteAction(Model model, PrinterRequestDto printerRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		
		try {
			if (!printerService.save(printerRequestDto, multiRequest)) {
				throw new Exception("#Exception boardWriteAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/board/printer/list";
	}

	@PostMapping("/board/printer/delete")
	public String printerDeleteAction(Model model, @RequestParam() Long[] deleteId) throws Exception {
		
		try {
			printerService.deleteAll(deleteId);
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "redirect:/board/printer";
	}
}
