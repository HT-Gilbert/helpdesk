package com.help.account.entity;

import java.io.Serializable;
//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.*;

import org.hibernate.Hibernate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.databind.ser.std.StdArraySerializers.IntArraySerializer;

import lombok.AccessLevel;
import lombok.Builder;
//import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import javax.persistence.Table;


//@EqualsAndHashCode(of= {"id"}) // equals, hashCode 자동 생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@Entity(name = "user")
@Table(name = "user")
//public class Member extends BaseTimeEntity implements UserDetails {
//public class Member implements UserDetails {
public class Member { //implements Serializable {
	
	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private Long id;
    @Column(name = "user_id")
	private String userId;
    @Column(name = "user_name")
	private String userName;
    @Column(name = "user_nick")
	private String userNick;
    @Column(name = "user_group")
    private String userGroup;
    @Column(name = "user_role")
    private String userRole;
    @Column(name = "user_status")
    private String userStatus;
    @Column(name = "user_email")
    private String userEmail;
//    @Column(name = "user_phone")
//    private String userPhone;
    @Column(name = "password")
	private String password;    
//    @Column(name = "total_login_count")
//	private int totalLoginCount;
    @Column(name = "login_fail_count")
	private int loginFailCount;
//    @Column(name = "login_time")
//	private String loginTime;
//    @Column(name = "register_time")
//	private String registerTime;
//    @Column(name = "update_time")
//	private String updateTime;
    @Column(name = "pwd_update_time")
	private String pwdUpdateTime;
//    @Column(name = "login_ip")
//	private String loginIp;
	
    @Builder
	public Member(String userId, String userName, String userNick, String userGroup, String userRole, String userStatus, String userEmail, String password, int loginFailCount, String pwdUpdateTime) {
		super();
		//this.id = id;
		this.userId = userId;
        this.userName = userName;
        this.userName = userNick;
        this.userName = userGroup;
        this.userRole = userRole;
        this.userStatus = userStatus;
        this.userEmail = userEmail;
		this.password = password;
        this.loginFailCount = loginFailCount;
		this.pwdUpdateTime = pwdUpdateTime;
	}

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        Member account = (Member) o;
        return userId != null && Objects.equals(userId, account.userId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    public void updatePassword(String newPassword) {
        this.password = newPassword;
    }
    /* TODO: 미구현

    public void updateProfile(io.lcalmsky.app.settings.controller.Profile profile) {
    if (this.profile == null) {
        this.profile = new Profile();
    }
    this.profile.bio = profile.getBio();
    this.profile.url = profile.getUrl();
    this.profile.job = profile.getJob();
    this.profile.location = profile.getLocation();
    this.profile.image = profile.getImage();
    }
    @Embedded  // 해당 클래스의 필드들이 DB에서는 개별 컬럼에 매핑하는 에너테이션
    private Profile profile;
    private LocalDateTime joinedAt;

    @Embedded
    private NotificationSetting notificationSetting;

    public void generateToken() {
        this.emailToken = UUID.randomUUID().toString();
        this.emailTokenGeneratedAt = LocalDateTime.now();
    }

    public boolean enableToSendEmail() {
    return this.emailTokenGeneratedAt.isBefore(LocalDateTime.now().minusMinutes(5));
    }

    public void verified() {
        this.isValid = true;
        joinedAt = LocalDateTime.now();
    }

    @PostLoad
    private void init() { // (1)
        if (profile == null) {
            profile = new Profile();
        }
        if (notificationSetting == null) {
            notificationSetting = new NotificationSetting();
        }
    }

    @Embeddable // @Embedded와 매핑되는 에너테이션으로 해당 클래스가 개별 Entity가 아닌 다른 Entity에 귀속
    @NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder @Getter @ToString
    public static class Profile {
        private String bio;
        //@Convert(converter = ListStringConverter.class)  // List를 DB 컬럼 하나에 매핑하기 위해 Converter
        private String url;
        private String job;
        private String location;
        private String company;
        @Lob @Basic(fetch = FetchType.EAGER)
        private String image;
    }

    @Embeddable 
    @NoArgsConstructor(access = AccessLevel.PROTECTED) @AllArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder @Getter @ToString
    public static class NotificationSetting {
        private boolean studyCreatedByEmail;
        private boolean studyCreatedByWeb;
        private boolean studyRegistrationResultByEmailByEmail;
        private boolean studyRegistrationResultByEmailByWeb;
        private boolean studyUpdatedByEmail;
        private boolean studyUpdatedByWeb;
    }
     */

}
