package com.help.main.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.FileUtils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.help.board.dto.BoardAttachDto;
import com.help.main.exception.AttachFileException;

@Component
public class FileUtil {
    /** 오늘 날짜 */
	private final String today = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));

	/** 업로드 경로 */
	//private final String uploadPath = Paths.get("C:", "temp", "upload", today).toString();
	private final String uploadPath = File.separator + "temp" + File.separator + "upload" + File.separator + today + File.separator;

	/**
	 * 서버에 생성할 파일명을 처리할 랜덤 문자열 반환
	 * @return 랜덤 문자열
	 */
	private final String getRandomString() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}

	/**
	 * 서버에 첨부 파일을 생성하고, 업로드 파일 목록 반환
	 * @param files    - 파일 Array
	 * @param listId - 게시글 번호
	 * @param boardId - 게시글 테이블 id
	 * @return 업로드 파일 목록
	 */
	public List<BoardAttachDto> uploadFiles(List<MultipartFile> files, Long listId, int boardId) {		

		/* 업로드 파일 정보를 담을 비어있는 리스트 */
		List<BoardAttachDto> attachList = new ArrayList<>();

		/* uploadPath에 해당하는 디렉터리가 존재하지 않으면, 부모 디렉터리를 포함한 모든 디렉터리를 생성 */
		File dir = new File(uploadPath);
		if (dir.exists() == false) {
			dir.mkdirs();
		}

		/* 파일 개수만큼 forEach 실행 */
		for (MultipartFile file : files) {
			/* 파일 수정이 없으면 continue */
			if (file.getSize() < 1) {
				continue;
			}
			try {
				/* 파일 확장자 */
				final String extension = FilenameUtils.getExtension(file.getOriginalFilename());
				/* 서버에 저장할 파일명 (랜덤 문자열 + 확장자) */
				final String saveName = getRandomString() + "." + extension;

				/* 업로드 경로에 saveName과 동일한 이름을 가진 파일 생성 */
				//File target = new File(uploadPath, saveName);
				//file.transferTo(target.getAbsoluteFile());		// 윈도우와 리눅스의 폴더경로 표시 차이로 인해 아래내용으로 수정 target만 쓰면 오류발생함

				File target = new File(uploadPath + saveName);
				InputStream fileStream = file.getInputStream();
				FileUtils.copyInputStreamToFile(fileStream, target);	//파일 저장

				/* 파일 정보 저장 */
				BoardAttachDto attach = new BoardAttachDto();
				attach.setListId(listId);
				attach.setBoardId(boardId);
				attach.setOriginalName(file.getOriginalFilename());
				attach.setSaveName(saveName);
				attach.setFileExt(extension);
				attach.setFileSize(file.getSize());

				/* 파일 정보 추가 */
				attachList.add(attach);

			} catch (IOException e) {
				throw new AttachFileException("[" + file.getOriginalFilename() + "] failed to save file...");

			} catch (Exception e) {
				throw new AttachFileException("[" + file.getOriginalFilename() + "] failed to save file...");
			}
		} // end of for

		return attachList;
	}
}
