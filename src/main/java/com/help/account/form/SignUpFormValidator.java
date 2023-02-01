package com.help.account.form;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.help.account.repository.AccountRepository;

@Component // (2)
@RequiredArgsConstructor // (2)
public class SignUpFormValidator implements Validator { // (1)

    private final AccountRepository memberRepository; // (2)

    @Override
    public boolean supports(Class<?> clazz) { // (3)
        return clazz.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) { // (4)
        SignUpForm signUpForm = (SignUpForm) target;
        if (memberRepository.existsByUserId(signUpForm.getId())) {
            errors.rejectValue("id", "invalid.id", new Object[]{signUpForm.getId()},
                    "이미 사용중인 id입니다.");
        }if (memberRepository.existsByUserEmail(signUpForm.getEmail())) {
            errors.rejectValue("email", "invalid.email", new Object[]{signUpForm.getEmail()},
                    "이미 사용중인 이메일입니다.");
        }
        if (memberRepository.existsByUserNick(signUpForm.getNickname())) {
            errors.rejectValue("nickname", "invalid.nickname", new Object[]{signUpForm.getNickname()},
                    "이미 사용중인 닉네임입니다.");
        }
        if (memberRepository.existsByUserNick(signUpForm.getGroup())) {
            errors.rejectValue("group", "invalid.group", new Object[]{signUpForm.getNickname()},
                    "이미 사용중인 닉네임입니다.");
        }

    }
}
