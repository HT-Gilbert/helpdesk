package com.help.entity.main;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface ContactRepository  extends JpaRepository<Contact, Long> {
 
    /*
    static final String UPDATE_CONTACT_LIST = "UPDATE Board "
    + "SET TITLE = :#{#boardRequestDto.title}, "
    + "CONTENT = :#{#boardRequestDto.content}, "
    + "REGISTER_ID = :#{#boardRequestDto.registerId}, "
    + "isNotice = :#{#boardRequestDto.notice}, "
    + "UPDATE_TIME = NOW() "			
    + "WHERE ID = :#{#boardRequestDto.id}";

    static final String UPDATE_BOARD_READ_CNT_INC = "UPDATE Board "
        + "SET READ_CNT = READ_CNT + 1 "
        + "WHERE ID = :id";

    @Transactional
	@Modifying
	@Query(value = UPDATE_CONTACT_LIST, nativeQuery = true)
	public int updateBoard(@Param("contactDto") ContactDto contactDto);

    */
    static final String DELETE_PTB_NAME = "DELETE FROM contact_list "
        + "WHERE PTB_NAME IN (:deletePtbName)";

	@Transactional
	@Modifying
	@Query(value = DELETE_PTB_NAME, nativeQuery = true)
	public int deleteList(@Param("deletePtbName") Long[] deleteIdList);
    
}
