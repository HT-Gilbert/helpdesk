package com.help.main.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import com.help.main.entity.LoginUser;
import com.help.main.entity.Member;
import com.help.main.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.transaction.annotation.Transactional;
//import javax.persistence.EntityManager;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberService implements UserDetailsService {


	// 회원가입 시 저장시간을 넣어줄 DateTime형
    SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:sss");
    Date time = new Date();
    String localTime = format.format(time);

    private final MemberRepository memberRepository;

    //@PersistenceContext
    //private static EntityManager entityManager;

    //@Autowired 
    PasswordEncoder passwordEncoder;


	@Override
	public LoginUser loadUserByUsername(String userId) throws UsernameNotFoundException {
        
		Member member = memberRepository.findByUsername(userId);
        
		if (member == null)
        {
            System.out.println("아이디 없음 ");            
            throw new UsernameNotFoundException("Not Found account."); 
        }
      //  LoginUser loginUser = null;
//		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
//		authorities.add(new SimpleGrantedAuthority(member.getUserRole()));
		//loginUser = new LoginUser(member.getUserId(), member.getUserName(), member.getUserGroup(), member.getPassword() ,authorities);
     //   loginUser = new LoginUser(member);

		return new LoginUser(member);
	}

	// public Optional<MemberDto> loginFindByUsername(String userId) throws UsernameNotFoundException {

	// 	return memberRepository.loginFindByUsername(userId);
	// }

    // public MemberDto signup(final MemberDto memberDto) {
    //     final MemberDto mDto = MemberDto.builder()
    //             .userId(memberDto.getUserId())
    //             .password(passwordEncoder.encode(memberDto.getPassword()))
    //             .userRole(memberDto.getUserRole())
    //             .build();

    //     return userRepository.save(user);
    // }

    public void updateMemberLastLogin(String userId) {
        memberRepository.updateMemberLastLogin(userId, LocalDateTime.now());        
	}

    @Transactional
    public void joinUser(Member member){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setUserRole("USER");
        //member.setLoginTime(localTime);
        //member.setUpdateDate(localTime);
        //memberRepository.save(member);
    }
}

