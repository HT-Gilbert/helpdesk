package com.help.main.controller;

import com.help.main.dto.LoginDto;
import com.help.main.entity.LoginUser;
import com.help.main.service.MemberService;
import com.help.main.service.MenuService;
import com.help.main.util.TokenUtils;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class MemberController {
	
    MemberService memberService;
    private final MenuService menuService;

 	@GetMapping("/login")
	public String loginViewPage(Model model,
			@RequestParam(value = "error", required = false) String error, 
			@RequestParam(value = "exception", required = false) String exception) {
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
        //model.addAttribute("menuList", menuService.getMenu());
		return "member/login";
	}

    @PostMapping("/login/success")

    
	/**
     * 회원가입 폼
     * @return
     */
    @GetMapping("/signUp")
    public String signUpForm() {
        return "signUp";
        // public ResponseEntity<String> loginSuccess(@RequestBody final LoginDto loginDto) {
        //     Optional<MemberDto> memberDto =  memberService.loginFindByUsername(loginDto.getLoginId());
        //     return memberDto.isPresent()
        //             ? ResponseEntity.badRequest().build()
        //             : ResponseEntity.ok(TokenUtils.generateJwtToken(memberService.logIn(memberDto)));
        // }
    }
    /*
    @PostMapping("/signUp")
    public String signUp(Member member) {
        memberService.joinUser(member);
        return "redirect:/login";
    }
    */

    // //회원가입 페이지 이동
    // @GetMapping("/user/signup")
    // public String signupForm(@AuthenticationPrincipal UserDetailsImpl userDetails, Model model) {
    //     try {
    //         User user = userDetails.getUser();
    //         model.addAttribute("user", user);
    //     }catch (NullPointerException e){
    //         return "signup";
    //     }
    //     return "signup";
    // }

    // //회원가입
    // @ResponseBody
    // @PostMapping("/user/signup")
    // public User signUp(@RequestBody UserDto userDto) {
    //     User user = userService.signup(userDto);

    //     return user;
    // }
}