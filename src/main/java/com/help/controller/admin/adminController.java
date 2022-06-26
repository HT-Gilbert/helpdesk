package com.help.controller.admin;

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
public class AdminController {

	private final TagService tagService;
	private final CategoryService categoryService;
	private final MenuService menuService;

	
    @GetMapping("/admin/main")
	public String getMainControlPage(Model model) throws Exception {
		
		try {
			model.addAttribute("tagList", tagService.getTag());
			model.addAttribute("categoryList", categoryService.getCategory());
			model.addAttribute("menuList", menuService.getMenu());
		return "/admin/main";
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
	}

    @GetMapping("/admin/addCategory")
	public String getCategoryEditPage(Model model) throws Exception {
		
		try {
			model.addAttribute("tagList", tagService.getTag());
			model.addAttribute("categoryList", categoryService.getCategory());
			model.addAttribute("menuList", menuService.getMenu());
			return "/admin/addCategory";
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}		
	}

	@GetMapping("/admin/addContact")
	public String getContactEditPage(Model model) throws Exception {
		
		try {
			model.addAttribute("tagList", tagService.getTag());
			model.addAttribute("categoryList", categoryService.getCategory());
			model.addAttribute("menuList", menuService.getMenu());
			return "redirect:/admin/main";
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
	}

	@GetMapping("/admin/popup")
	public String getPopupTestPage(Model model) throws Exception {
		
		try {
			model.addAttribute("tagList", tagService.getTag());
			model.addAttribute("categoryList", categoryService.getCategory());
			model.addAttribute("menuList", menuService.getMenu());
			return "make_popup";
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}		
	}

	/*
	@Controller
@RequestMapping("/daily/*")
public class PopupController {
    @RequestMapping(value="/ListPopup", method=RequestMethod.GET)
    public void popupGet(Model model) throws Exception {
        System.out.println("Popup");
    }
}

@RequestMapping(value="/daily/ListPopup", method=RequestMethod.GET)
    public ModelAndView popupGet(Model model) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("ListPopup");
        System.out.println("Popup1");

        return mav;
    }
 */
}
