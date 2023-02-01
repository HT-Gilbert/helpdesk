package com.help.account.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.help.account.entity.LoginUser;
import com.help.account.entity.Member;
import com.help.account.form.ProfileForm;
import com.help.account.form.SignUpForm;
import com.help.account.repository.AccountRepository;

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
@Transactional
public class AccountService implements UserDetailsService {

    //private final JavaMailSender mailSender;

	// 회원가입 시 저장시간을 넣어줄 DateTime형
    SimpleDateFormat format = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:sss");
    Date time = new Date();
    String localTime = format.format(time);

    private final AccountRepository accountRepository;

    //@PersistenceContext
    //private static EntityManager entityManager;

    //@Autowired 
    private final BCryptPasswordEncoder passwordEncoder;


	@Override
    @Transactional(readOnly = true)
	public LoginUser loadUserByUsername(String userId) throws UsernameNotFoundException {
        
		Member member = accountRepository.findByUsername(userId);
        
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
        accountRepository.updateMemberLastLogin(userId, LocalDateTime.now());        
	}

    @Transactional
    public Member signUp(SignUpForm signUpForm){
        Member newMember = saveNewMember(signUpForm);

        //newMember.generateToken();
        //sendVerificationEmail(newMember);
        return newMember;
    }

    public Member findByUserId(String id){
        return accountRepository.findByUsername(id);
    }

    public Member findByUserNick(String nickname){
        return accountRepository.findByUserNick(nickname);
    }

    private Member saveNewMember(SignUpForm signUpForm){
        Member member =Member.builder()
                .userId(signUpForm.getId())
                .userName(signUpForm.getName())
                .userNick(signUpForm.getNickname())
                .userGroup(signUpForm.getGroup())
                .userRole("ROLE_USER")
                .userStatus("R")
                .userEmail(signUpForm.getEmail())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                // .notificationSetting(Member.NotificationSetting.builder()
                // .studyCreatedByWeb(true)
                // .studyUpdatedByWeb(true)
                // .studyRegistrationResultByWeb(true)
                // .build())
                .build();
        return accountRepository.save(member);
    }

    public void updateProfile(Member member, ProfileForm profile) {
      //  member.updateProfile(profile);
        accountRepository.save(member);
    }

    // public void updatePassword(Member member, String newPassword) {
    //     member.updatePassword(passwordEncoder.encode(newPassword));        
    //     accountRepository.save(member);
    // }

    public void updatePassword(String userId, String newPassword) {
        accountRepository.updatePassword(userId, passwordEncoder.encode(newPassword));        
	}

    
    /* TODO: 미구현 
    public void sendVerificationEmail(Member newAccount) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(newAccount.getUserEmail());
        mailMessage.setSubject("회원 가입 인증");
        mailMessage.setText(String.format("/checkemailtoken?token=%s&email=%s",// newAccount.getEmailToken(),
                newAccount.getUserEmail()));
        mailSender.send(mailMessage);
    }

    public Member findIdByUserEmail(String email) {
        return accountRepository.findIdByEmail(email); // (1)
    }

        public void verify(Account account) { // (3)
        account.verified();
        login(account);
    }
    */
}

