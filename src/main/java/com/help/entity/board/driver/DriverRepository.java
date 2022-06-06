package com.help.entity.board.driver;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface DriverRepository extends JpaRepository<Driver, Long>{

    static final String DELETE_DRIVER_ID = "DELETE FROM driver "
    + "WHERE ID IN (:deleteIdList)";

    static final String UPDATE_DRIVER_READ_CNT_INC = "UPDATE driver "
    + "SET READ_CNT = READ_CNT + 1 "
    + "WHERE ID = :id";

    @Transactional
    @Modifying
    @Query(value = DELETE_DRIVER_ID, nativeQuery = true)
    public int deleteDriver(@Param("deleteIdList") Long[] deleteIdList);

    @Transactional
	@Modifying
	@Query(value = UPDATE_DRIVER_READ_CNT_INC, nativeQuery = true)
	public int updateDriverReadCntInc(@Param("id") Long id);
}
