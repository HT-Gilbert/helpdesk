package com.help.entity.member;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long>{

	static final String UPDATE_MEMBER_LAST_LOGIN = "UPDATE user "
			+ "SET lastlogintime = :lastLoginTime "
			+ "WHERE userid = :username";
	
	@Transactional
	@Modifying
	@Query(value=UPDATE_MEMBER_LAST_LOGIN, nativeQuery = true)
	public int updateMemberLastLogin(@Param("username") String username, @Param("lastLoginTime") LocalDateTime lastLoginTime);
	//public Member findByUserid(String userid);

	static final String SELECT_ID= "SELECT userid, username, password, userrole, encpwd, lastlogintime FROM user WHERE userid = :username";

	@Query(value = SELECT_ID, nativeQuery = true)
	public Member findByUsername(@Param("username") String username);	

//	void saveUser(Member member);
}
