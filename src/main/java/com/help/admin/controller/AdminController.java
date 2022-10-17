package com.help.admin.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.help.main.service.MenuService;
import com.help.main.service.TagService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class AdminController {

	private final TagService tagService;
	//private final CategoryService categoryService;
	private final MenuService menuService;
	
    @GetMapping("/admin/main")
	@Secured("ROLE_ADMIN")
	public String getMainControlPage(Model model) throws Exception {
		
		try {
//			model.addAttribute("tagList", tagService.getTag());
//			model.addAttribute("categoryList", categoryService.getCategory());
			model.addAttribute("menuList", menuService.getMenu());
		return "admin/main";
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
	}

	/*- @PostMapping
- @GetMapping
- @PutMapping
- @DeleteMapping 
- @PatchMapping
 */
	/*
	@RequestMapping(value = "findAll", method = RequestMethod.POST)
    public ResponseEntity<?> getBoardListbyMB() {
        ResponseDTO  responseDto = new BoardListDto();
        responseDto.setResultCode("S0001");
        responseDto.setRes(boardListRepository.findAll());
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
	 */

    @GetMapping("/admin/addCategory")
	@Secured("ROLE_ADMIN")
	public String getCategoryEditPage(Model model) throws Exception {
		
		try {
			//model.addAttribute("tagList", tagService.getTag());
//			model.addAttribute("categoryList", categoryService.getCategory());
			model.addAttribute("menuList", menuService.getMenu());
			return "admin/addCategory";
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}		
	}

	@GetMapping("/admin/addContact")
	@Secured("ROLE_ADMIN")
	public String getContactEditPage(Model model) throws Exception {
		
		try {
			//model.addAttribute("tagList", tagService.getTag());
//			model.addAttribute("categoryList", categoryService.getCategory());
			model.addAttribute("menuList", menuService.getMenu());
			return "redirect:/admin/main";
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
	}

	@GetMapping("/admin/popup")
	@Secured("ROLE_ADMIN")
	public String getPopupTestPage(Model model) throws Exception {
		
		try {
			//model.addAttribute("tagList", tagService.getTag());
			//model.addAttribute("categoryList", categoryService.getCategory());
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
