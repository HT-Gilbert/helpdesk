package com.help.entity.member;

//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.help.entity.BaseTimeEntity;
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
public class Member implements UserDetails {
	
	private static final long serialVersionUID = 1L;

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	//private Long id;
    @Column(name = "userid")
	private String username;
    @Column(name = "username")
	private String name;
    @Column(name = "password")
	private String pwd;
    @Column(name = "encpwd")
    private String encpwd;
    @Column(name = "userrole")
    private String role;
    @Column(name = "lastlogintime")
	private String lastLoginTime;
	
    @Builder
	public Member(String userid, String username, String pwd, String userrole, String encpwd, String lastLoginTime) {
		super();
		//this.id = id;
		this.username = userid;
        this.name = username;
        this.role = userrole;
		this.pwd = pwd;
        this.encpwd = encpwd;
		this.lastLoginTime = lastLoginTime;
	}

	@Override
	public String getPassword() {
		return this.getPwd();
	}
	
	@Override
	public String getUsername() {
		return this.username;
	}
    
	
	 //계정이 갖고있는 권한 목록은 리턴
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        
        Collection<GrantedAuthority> collectors = new ArrayList<>();
        collectors.add(() -> {
            return "계정별 등록할 권한";
        });
        
        //collectors.add(new SimpleGrantedAuthority("Role"));
        
        return collectors;
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
