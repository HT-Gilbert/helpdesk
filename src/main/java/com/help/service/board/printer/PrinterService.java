package com.help.service.board.printer;

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

import com.help.dto.board.printer.PrinterRequestDto;
import com.help.dto.board.printer.PrinterResponseDto;
import com.help.entity.board.printer.Printer;
import com.help.entity.board.printer.PrinterRepository;
import com.help.service.board.BoardFileService;
import lombok.RequiredArgsConstructor;
 
@RequiredArgsConstructor
@Service
public class PrinterService  {

    private final PrinterRepository printerRepository;
    private final BoardFileService boardFileService;

    @Transactional
    public boolean save(PrinterRequestDto printerRequestDto, MultipartHttpServletRequest multiRequest) throws Exception{

        Printer result = printerRepository.save(printerRequestDto.toEntity());

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

        Page<Printer> printerList = printerRepository.findAll(PageRequest.of(page, size, sort));

        resultMap.put("printerList", printerList.stream().map(PrinterResponseDto::new).collect(Collectors.toList()));
		resultMap.put("paging", printerList.getPageable());
		resultMap.put("totalCnt", printerList.getTotalElements());
		resultMap.put("totalPage", printerList.getTotalPages());
        resultMap.put("curPage", printerList.getNumber());

        return resultMap;
    }

    public HashMap<String, Object> findById(Long id) throws Exception {
		
		HashMap<String, Object> resultMap = new HashMap<String, Object>(); 
		
		printerRepository.updatePrinterReadCntInc(id);
		
		PrinterResponseDto info = new PrinterResponseDto(printerRepository.findById(id).get());
		
		resultMap.put("info", info);
		resultMap.put("fileList", boardFileService.findByBoardId(info.getId()));
		
		return resultMap;
	}

    public boolean updateDriver(PrinterRequestDto driverRequestDto, MultipartHttpServletRequest multiRequest) throws Exception {
		
		int result = printerRepository.updatePrinter(driverRequestDto);
		
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
		printerRepository.deleteById(id);
	}
	
	public void deleteAll(Long[] deleteIdList) throws Exception {
		boardFileService.deleteBoardFileYn(deleteIdList);
		printerRepository.deleteDriver(deleteIdList);
	}    
}
