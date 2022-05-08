package com.help.service.member;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import com.help.entity.member.Member;
import com.help.entity.member.LoginUser;
import com.help.entity.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequiredArgsConstructor
@Service
public class MemberService implements UserDetailsService {


	// 회원가입 시 저장시간을 넣어줄 DateTime형
    SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:sss");
    Date time = new Date();
    String localTime = format.format(time);

    private static final Logger logger = LoggerFactory.getLogger(MemberService.class);

	private final MemberRepository memberRepository;

    //@PersistenceContext
    private static EntityManager entityManager;

    //@Autowired 
    PasswordEncoder passwordEncoder;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
		Member member = memberRepository.findByUsername(username);

        
        /*
        Member activeUser = (Member)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        logger.info("AUserid :: {}", activeUser.getUsername());
        logger.info("AUsername :: {}", activeUser.getName());
        logger.info("Apwd :: {}", activeUser.getPwd());        
        logger.info("Aencpwd :: {}", activeUser.getEncpwd());
        logger.info("Arole :: {}", activeUser.getRole());
        */
        
		if (member == null)
        {
            System.out.println("아이디 없음 ");            
            throw new UsernameNotFoundException("Not Found account."); 
        }

        logger.info("username :: {}", username);
        logger.info("Userid :: {}", member.getUsername());
        logger.info("Username :: {}", member.getName());
        logger.info("pwd :: {}", member.getPwd());        
        logger.info("encpwd :: {}", member.getEncpwd());
        logger.info("role :: {}", member.getRole());
		
        LoginUser loginUser = null;
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority(member.getRole()));
		loginUser = new LoginUser(member.getUsername(), member.getPwd() ,authorities);

        logger.info("LUserid :: {}", loginUser.getUsername());        
        logger.info("Lpwd :: {}", loginUser.getPassword());
        logger.info("Lauthorities :: {}", loginUser.getAuthorities());

		return loginUser;
	}

    @Transactional
    public void joinUser(Member member){
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        member.setEncpwd(passwordEncoder.encode(member.getPassword()));
        member.setRole("USER");
        member.setLastLoginTime(localTime);
        //member.setUpdateDate(localTime);
        //memberRepository.saveUser(member);
    }
}

