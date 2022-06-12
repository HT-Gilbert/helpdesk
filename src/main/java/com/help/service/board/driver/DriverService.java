package com.help.service.board.driver;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.help.dto.board.driver.DriverRequestDto;
import com.help.dto.board.driver.DriverResponseDto;
import com.help.entity.board.driver.Driver;
import com.help.entity.board.driver.DriverRepository;
import com.help.service.board.BoardFileService;
import lombok.RequiredArgsConstructor;
 
@RequiredArgsConstructor
@Service
public class DriverService {

    private final DriverRepository driverRepository;
    private final BoardFileService boardFileService;

    @Transactional
    public boolean save(DriverRequestDto driverRequestDto, MultipartHttpServletRequest multiRequest) throws Exception{

        Driver result = driverRepository.save(driverRequestDto.toEntity());

        boolean resultFlag = false;


        if (result != null) {
			boardFileService.uploadFile(multiRequest, result.getId(), 14);
			resultFlag = true;
		}

        return resultFlag;
    }

    @Transactional(readOnly = true)
    public HashMap<String, Object> findAll(Integer page, Integer size) throws Exception {

        HashMap<String, Object> resultMap = new HashMap<String, Object>();

        List<Order> sortList = new LinkedList<>();
        sortList.add(Order.desc("isNotice"));
		sortList.add(Order.desc("registerTime"));
        Sort sort = Sort.by(sortList);

        Page<Driver> driverList = driverRepository.findAll(PageRequest.of(page, size, sort));

        resultMap.put("driverList", driverList.stream().map(DriverResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", driverList.getPageable());
		resultMap.put("totalCnt", driverList.getTotalElements());
		resultMap.put("totalPage", driverList.getTotalPages());
        resultMap.put("curPage", driverList.getNumber());

        return resultMap;
    }

    public HashMap<String, Object> findById(Long id) throws Exception {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>(); 
		
		driverRepository.updateDriverReadCntInc(id);
		
		DriverResponseDto info = new DriverResponseDto(driverRepository.findById(id).get());
		
		resultMap.put("info", info);
		resultMap.put("fileList", boardFileService.findByBoardId(info.getId()));
		
		return resultMap;
	}

    public boolean updateDriver(DriverRequestDto driverRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		
		int result = driverRepository.updateDriver(driverRequestDto);
		
		boolean resultFlag = false;
		
		if (result > 0) {
			boardFileService.uploadFile(multiRequest, driverRequestDto.getId(), 14);
			resultFlag = true;
		}
		
		return resultFlag;
	}

    public void deleteById(Long id) throws Exception {
		Long[] idArr = {id};
		boardFileService.deleteBoardFileYn(idArr);
		driverRepository.deleteById(id);
	}
	
	public void deleteAll(Long[] deleteIdList) throws Exception {
		boardFileService.deleteBoardFileYn(deleteIdList);
		driverRepository.deleteDriver(deleteIdList);
	}    
}
