package com.help.main.entity;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginUser implements UserDetails {
 
//    private static final long serialVersionUID = 1L;
    private final Member user;
    
    private String password;
    private String loginId;
    
    /*UserDetails 기본 상속 변수 */
    private Collection<? extends GrantedAuthority> authorities;
    //private boolean isEnabled = true;
    private String username;
    //private boolean isCredentialsNonExpired = true;
    //private boolean isAccountNonExpired = true;
    //private boolean isAccountNonLocked = true;

    public LoginUser(Member user) {
        this.user = user;
        this.loginId = this.user.getUserId();
    }
 
    public Member getUser() {
        return user;
    }
    // public LoginUser(String username, String loginName, String loginGroup, String password, Collection<? extends GrantedAuthority> authorities) {
	// 	super();
	// 	//this.id = id;
	// 	this.loginId = this.username = username;
    //     this.loginName = loginName;
    //     this.loginGroup = loginGroup;
	// 	this.password = password;
    //     this.authorities = authorities;
	// }

   @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return authorities;
        List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(user.getUserRole().toString());
        
        return authorityList;
    }
    
	//계정이 만료되지 않았는지 리턴 (true: 만료 안됨)
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    //계정이 잠겨있는지 않았는지 리턴. (true: 잠기지 않음)
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    //비밀번호가 만료되지 않았는지 리턴한다. (true: 만료 안됨)
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    //계정이 활성화(사용가능)인지 리턴 (true: 활성화)
    @Override
    public boolean isEnabled() {
        return true;
    }
  
}
