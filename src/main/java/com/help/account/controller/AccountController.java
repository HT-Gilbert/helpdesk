package com.help.account.controller;

import com.help.account.dto.LoginDto;
import com.help.account.entity.LoginUser;
import com.help.account.entity.Member;
import com.help.account.form.PasswordForm;
import com.help.account.form.PasswordFormValidator;
import com.help.account.form.ProfileForm;
import com.help.account.form.SignUpForm;
import com.help.account.form.SignUpFormValidator;
import com.help.account.service.AccountService;
import com.help.main.service.MenuService;
import com.help.main.util.TokenUtils;

import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Controller
@Slf4j
public class AccountController {
	
    private final AccountService accountService;
    private final SignUpFormValidator signUpFormValidator; // (1)
    private final PasswordFormValidator passwordFormValidator; // (1)
    private final MenuService menuService;

 	@GetMapping("/login")
	public String loginViewPage(Model model,
			@RequestParam(value = "error", required = false) String error, 
			@RequestParam(value = "exception", required = false) String exception) {
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		return "account/login";
	}

    @PostMapping("/login/change")
	public String passwordChangePage(Model model,
			@RequestParam(value = "error", required = false) String error, 
			@RequestParam(value = "exception", required = false) String exception) {
		model.addAttribute("error", error);
		model.addAttribute("exception", exception);
		return "account/change";
	}
    
	/**
     * 회원가입 폼
     * @return
     */
    @GetMapping("/signup")
    public String signUpForm(Model model) {
        model.addAttribute(new SignUpForm());
        return "account/signup";
        // public ResponseEntity<String> loginSuccess(@RequestBody final LoginDto loginDto) {
        //     Optional<MemberDto> memberDto =  memberService.loginFindByUsername(loginDto.getLoginId());
        //     return memberDto.isPresent()
        //             ? ResponseEntity.badRequest().build()
        //             : ResponseEntity.ok(TokenUtils.generateJwtToken(memberService.logIn(memberDto)));
        // }
    }

    @PostMapping("/signup")
    public String signUpSubmit(@Valid @ModelAttribute SignUpForm signUpForm, Errors errors) {
        if (errors.hasErrors()) {
            return "account/signup";
        }
        signUpFormValidator.validate(signUpForm, errors);
        if (errors.hasErrors()) {
            return "account/signup";
        }
        Member member = accountService.signUp(signUpForm);
        // TODO: accountService.login(account); // 자동로그인 미구현
        //  member.generateToken();
        //  memberService.sendVerificationEmail(member);
        return "redirect:/";
    }

     /*
     * 프로필 보기
     */
    @GetMapping("/profile/{id}")
    public String viewProfile(@PathVariable String id, Model model
        //, @CurrentUser LoginUser loginUser
		, @AuthenticationPrincipal LoginUser loginUser) {
        Member member  = accountService.findByUserId(id);
        if (member == null) { // (1)
            throw new IllegalArgumentException(id + "에 해당하는 사용자가 없습니다.");
        }
        model.addAttribute(member); // (2)
        model.addAttribute("isOwner", member.equals(loginUser.getUser())); // (3)
        return "account/profile";
    }

    /*
     * 프로필 수정
     */
    public static final String SETTINGS_PROFILE_VIEW_NAME = "account/profile"; // (1)
    public static final String SETTINGS_PROFILE_URL = "/" + SETTINGS_PROFILE_VIEW_NAME; // (1)
    @GetMapping(SETTINGS_PROFILE_URL)
    public String profileUpdateForm(Model model
        //, @CurrentUser LoginUser loginUser
		, @AuthenticationPrincipal LoginUser loginUser) {
        model.addAttribute(loginUser);
       // model.addAttribute(Profile.from(loginUser));
        return "SETTINGS_PROFILE_VIEW_NAME";
    }

    @PostMapping(SETTINGS_PROFILE_URL) // (2)
    public String updateProfile(@Valid ProfileForm profile, Errors errors, Model model
        //, @CurrentUser LoginUser loginUser
		, @AuthenticationPrincipal LoginUser loginUser) { // (3)
        if (errors.hasErrors()) { // (4) 
            model.addAttribute(loginUser);
            return SETTINGS_PROFILE_VIEW_NAME;
        }
        accountService.updateProfile(loginUser.getUser(), profile); // (5)
        return "redirect:" + SETTINGS_PROFILE_URL; // (6)
    }


        static final String SETTINGS_PASSWORD_VIEW_NAME = "setting/password"; // (1)
        static final String SETTINGS_PASSWORD_URL = "/" + SETTINGS_PASSWORD_VIEW_NAME; // (1)
    
     
        // @InitBinder("passwordForm")
        // public void initBinder(WebDataBinder webDataBinder) { // (2) 
        //     webDataBinder.addValidators(new PasswordFormValidator());
        // }
    
        // 생략
    
        @GetMapping(SETTINGS_PASSWORD_URL) // (3)
        public String passUpdateForm(Model model
            //, @CurrentUser LoginUser loginUser
            , @AuthenticationPrincipal LoginUser loginUser) {
            model.addAttribute("loginUser", loginUser);
            model.addAttribute(new PasswordForm());
            return SETTINGS_PASSWORD_VIEW_NAME;
        }
    
        @PostMapping(SETTINGS_PASSWORD_URL) // (4) 
        public String updatePassword(@Valid PasswordForm passwordForm, Errors errors, Model model, RedirectAttributes attributes
            //, @CurrentUser LoginUser loginUser
            , @AuthenticationPrincipal LoginUser loginUser) {
            if (errors.hasErrors()) {
                model.addAttribute(loginUser);
                return SETTINGS_PASSWORD_VIEW_NAME;
            }
            passwordFormValidator.validate(passwordForm, errors); 
            if (errors.hasErrors()) {
                return SETTINGS_PASSWORD_VIEW_NAME;
            }
            //accountService.updatePassword(loginUser.getUser(), passwordForm.getNewPassword()); // (5)
            accountService.updatePassword(loginUser.getLoginId(), passwordForm.getNewPassword()); // (5)
            attributes.addFlashAttribute("message", "패스워드를 변경했습니다.");
            return "redirect:" + SETTINGS_PASSWORD_URL;
        }
 
    /* TODO: 미구현
    @GetMapping("/checkemailtoken")
    public String verifyEmail(String token, String email, Model model) { // (1)
        Member member= memberService.findIdByUserEmail(email); // (2)
        if (member == null) { // (3)
            model.addAttribute("error", "wrong.email");
            return "account/emailverification";
        }
        if (token.equals(member.getUserEmailToken())) { // (4)
            model.addAttribute("error", "wrong.token");
            return "account/emailverification";
        }
        accountService.verify(member);
        model.addAttribute("numberOfUsers", accountRepository.count()); // (6)
        model.addAttribute("nickname", member.getUserNick()); // (6)
        return "account/emailverification"; // (7)
    }

    @GetMapping("/checkemail")
    public String checkMail(@CurrentUser Account account, Model model) { // (1)
        model.addAttribute("email", account.getEmail());
        return "account/check-email";
    }

    @GetMapping("/resendemail")
    public String resendEmail(@CurrentUser Account account, Model model) { // (2)
        if (!account.enableToSendEmail()) {
            model.addAttribute("error", "인증 이메일은 5분에 한 번만 전송할 수 있습니다.");
            model.addAttribute("email", account.getEmail());
            return "account/check-email";
        }
        accountService.sendVerificationEmail(account);
        return "redirect:/";
    }
     */
}