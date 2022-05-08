package com.help.entity.member;

import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.help.entity.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
//import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import javax.persistence.Table;

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
