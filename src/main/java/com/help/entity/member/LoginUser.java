package com.help.entity.member;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginUser implements UserDetails {
 
    private static final long serialVersionUID = 1L;
    
    private String password;
    private String memberName;
    
    /*UserDetails 기본 상속 변수 */
    private Collection<? extends GrantedAuthority> authorities;
    private boolean isEnabled = true;
    private String username;
    private boolean isCredentialsNonExpired = true;
    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
 
    public LoginUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super();
		//this.id = id;
		this.username = username;
		this.password = password;
    this.authorities = authorities;
	}

  
}
