package com.help.account.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.help.account.entity.Member;

public interface AccountRepository extends JpaRepository<Member, Long>{

	static final String UPDATE_MEMBER_LAST_LOGIN = "UPDATE user "
			+ "SET login_time = :loginTime "
			+ "WHERE user_id = :userName";

	static final String UPDATE_PASSWORD = "UPDATE user "
			+ "SET password = :newPassword, user_status = 'Y' "
			+ "WHERE user_id = :userName";
	
	@Transactional
	@Modifying
	@Query(value=UPDATE_MEMBER_LAST_LOGIN, nativeQuery = true)
	public int updateMemberLastLogin(@Param("userName") String userName, @Param("loginTime") LocalDateTime loginTime);
	//public Member findByUserid(String userid);

	static final String SELECT_ID= "SELECT user_id, user_name, user_nick, user_group, user_role, user_status, user_email, password, "
			+ "login_fail_count, pwd_update_time FROM user WHERE user_id = :userName";

	static final String SELECT_ID_BYNICK= "SELECT user_id, user_name, user_nick, user_group, user_role, user_status, user_email, password, "
			+ "login_fail_count, pwd_update_time FROM user WHERE user_nick = :userNick";

	@Query(value = SELECT_ID, nativeQuery = true)
	public Member findByUsername(@Param("userName") String userName);	

	@Query(value = SELECT_ID_BYNICK, nativeQuery = true)
	public Member findByUserNick(@Param("userNick") String userNick);

	static final String LOGIN_SELECT_ID= "SELECT user_id, user_role"
			+ "login_fail_count, pwd_update_time FROM user WHERE user_id = :userName";

	boolean existsByUserId(String id);

	boolean existsByUserEmail(String email);

	boolean existsByUserNick(String nickname);

	@Transactional
	@Modifying
	@Query(value=UPDATE_PASSWORD, nativeQuery = true)
	public int updatePassword(@Param("userName") String userName, @Param("newPassword") String newPassword);

//	@Query(value = LOGIN_SELECT_ID, nativeQuery = true)
//	Optional<MemberDto> loginFindByUsername(@Param("userName") String userName);	

//	void saveUser(Member member);
}
