package com.help.main.entity;

import java.io.Serializable;
//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.*;
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
//    @Column(name = "user_email")
//    private String userEmail;
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
	public Member(String userId, String userName, String userNick, String userGroup, String userRole,  String password, int loginFailCount, String pwdUpdateTime) {
		super();
		//this.id = id;
		this.userId = userId;
        this.userName = userName;
        this.userName = userNick;
        this.userName = userGroup;
        this.userRole = userRole;
		this.password = password;
        this.loginFailCount = loginFailCount;
		this.pwdUpdateTime = pwdUpdateTime;
	}

	//  //계정이 갖고있는 권한 목록은 리턴
    // @Override
    // public Collection<? extends GrantedAuthority> getAuthorities() {
        
    //     Collection<GrantedAuthority> collectors = new ArrayList<>();
    //     collectors.add(() -> {
    //         return "계정별 등록할 권한";
    //     });
        
    //     //collectors.add(new SimpleGrantedAuthority("Role"));
        
    //     return collectors;
    // }
    
	// //계정이 만료되지 않았는지 리턴 (true: 만료 안됨)
    // @Override
    // public boolean isAccountNonExpired() {
    //     return true;
    // }

    // //계정이 잠겨있는지 않았는지 리턴. (true: 잠기지 않음)
    // @Override
    // public boolean isAccountNonLocked() {
    //     return true;
    // }

    // //비밀번호가 만료되지 않았는지 리턴한다. (true: 만료 안됨)
    // @Override
    // public boolean isCredentialsNonExpired() {
    //     return true;
    // }

    // //계정이 활성화(사용가능)인지 리턴 (true: 활성화)
    // @Override
    // public boolean isEnabled() {
    //     return true;
    // }
}
