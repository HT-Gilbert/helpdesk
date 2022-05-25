package com.help.controller.member;

import com.help.service.member.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

import com.help.service.main.MenuService;

@RequiredArgsConstructor
@Controller
public class MemberController {
	
    MemberService memberService;
    private final MenuService menuService;

 	@GetMapping("/login")
	public String getLoginPage(Model model,
			@RequestParam(value = "error", required = false) String error, 
			@RequestParam(value = "exception", required = false) String exception) {
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
        model.addAttribute("menuList", menuService.getMenu());
		return "/member/login";
	}

	/**
     * 회원가입 폼
     * @return
     */
    @GetMapping("/signUp")
    public String signUpForm() {
        return "signUp";
    }
    /*
    @PostMapping("/signUp")
    public String signUp(Member member) {
        memberService.joinUser(member);
        return "redirect:/login";
    }
    */

    /**
     * 로그인 실패 폼
     * @return
     */
    @GetMapping("/access_denied")
    public String accessDenied() {
        return "access_denied";
    }

    /**
     * 유저 페이지
     * @param model
     * @param authentication
     * @return
     */
    /*
    @GetMapping("/user_access")
    public String userAccess(Model model, Authentication authentication) {
        //Authentication 객체를 통해 유저 정보를 가져올 수 있다.
        Member member = (Member) authentication.getPrincipal();  //userDetail 객체를 가져옴
        model.addAttribute("info", member.getUserId() +"의 "+ member.getUserName()+ "님");      //유저 아이디
        return "user_access";
    }
    */
}