package com.help.entity.board.printer;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.help.dto.board.printer.PrinterRequestDto;

public interface PrinterRepository  extends JpaRepository<Printer, Long>{

    static final String DELETE_PRINTER_ID = "DELETE FROM printer "
    + "WHERE ID IN (:deleteIdList)";

    static final String UPDATE_PRINTER = "UPDATE printer "
			+ "SET TITLE = :#{#driverRequestDto.title}, "
			+ "CONTENT = :#{#driverRequestDto.content}, "
            + "TAG = :#{#driverRequestDto.tag}, "
			+ "REGISTER_ID = :#{#driverRequestDto.registerId}, "
			+ "isNotice = :#{#driverRequestDto.notice}, "
			+ "UPDATE_TIME = NOW() "			
			+ "WHERE ID = :#{#driverRequestDto.id}";

    static final String UPDATE_PRINTER_READ_CNT_INC = "UPDATE printer "
    + "SET READ_CNT = READ_CNT + 1 "
    + "WHERE ID = :id";

    @Transactional
	@Modifying
	@Query(value = UPDATE_PRINTER, nativeQuery = true)
	public int updatePrinter(@Param("driverRequestDto") PrinterRequestDto printerRequestDto);

    @Transactional
    @Modifying
    @Query(value = DELETE_PRINTER_ID, nativeQuery = true)
    public int deleteDriver(@Param("deleteIdList") Long[] deleteIdList);

    @Transactional
	@Modifying
	@Query(value = UPDATE_PRINTER_READ_CNT_INC, nativeQuery = true)
	public int updatePrinterReadCntInc(@Param("id") Long id);
}
