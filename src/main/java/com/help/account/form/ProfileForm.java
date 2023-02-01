package com.help.account.form;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

import org.hibernate.validator.constraints.Length;

import com.help.account.entity.Member;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProfileForm {
    @Length(max = 35)
    private String bio;
    @Length(max = 50)
    private String url;
    @Length(max = 50)
    private String job;
    @Length(max = 50)
    private String location;
    private String image;

    // public static ProfileForm from(Member member) {
    //     return new Profile(member);
    // }

    // protected ProfileForm(Member member) {
    //     this.bio = Optional.ofNullable(member.getProfile()).map(member.Profile::getBio).orElse(null);
    //     this.url = Optional.ofNullable(member.getProfile()).map(member.Profile::getUrl).orElse(null);
    //     this.job = Optional.ofNullable(member.getProfile()).map(member.Profile::getJob).orElse(null);
    //     this.location = Optional.ofNullable(member.getProfile()).map(member.Profile::getLocation).orElse(null);
    //      this.image = Optional.ofNullable(account.getProfile()).map(Account.Profile::getImage).orElse(null);
    // }
}