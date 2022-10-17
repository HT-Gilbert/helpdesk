package com.help.main.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.help.main.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{

	static final String UPDATE_MEMBER_LAST_LOGIN = "UPDATE user "
			+ "SET login_time = :loginTime "
			+ "WHERE user_id = :userName";
	
	@Transactional
	@Modifying
	@Query(value=UPDATE_MEMBER_LAST_LOGIN, nativeQuery = true)
	public int updateMemberLastLogin(@Param("userName") String userName, @Param("loginTime") LocalDateTime loginTime);
	//public Member findByUserid(String userid);

	static final String SELECT_ID= "SELECT user_id, user_name, user_nick, user_group, user_role, password, "
			+ "login_fail_count, pwd_update_time FROM user WHERE user_id = :userName";

	@Query(value = SELECT_ID, nativeQuery = true)
	public Member findByUsername(@Param("userName") String userName);	

	static final String LOGIN_SELECT_ID= "SELECT user_id, user_role"
			+ "login_fail_count, pwd_update_time FROM user WHERE user_id = :userName";

//	@Query(value = LOGIN_SELECT_ID, nativeQuery = true)
//	Optional<MemberDto> loginFindByUsername(@Param("userName") String userName);	

//	void saveUser(Member member);
}
