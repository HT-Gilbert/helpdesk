package com.help.account.form;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Data
public class SignUpForm {
    @NotBlank
    @Length(min = 7, max = 20)
    @Pattern(regexp = "^[a-z0-9_-]{7,20}$")
    private String id;

    @NotBlank
    @Length(min = 3, max = 20)
    @Pattern(regexp = "^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$")
    private String nickname;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Length(min = 8, max = 20)
    private String password;

    @NotBlank
    @Length(min = 4, max = 20)
    private String name;
    @NotBlank
    @Length(min = 8, max = 20)
    @Pattern(regexp = "^[a-z0-9_-]{8,20}$")
    private String group;
}