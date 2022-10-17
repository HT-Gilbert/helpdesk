package com.help.board.controller;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.apache.commons.io.FileUtils;

import com.google.gson.JsonObject;
import com.help.board.dto.BoardAttachRequestDto;
import com.help.board.dto.BoardBasicRequestDto;
import com.help.board.dto.SearchDto;
import com.help.board.service.BoardService;
import com.help.main.dto.MessageDto;
import com.help.main.entity.LoginUser;
import com.help.main.entity.Menu;
import com.help.main.service.MenuService;
import com.help.main.service.TagService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
@Slf4j
public class BoardController {
	
	private final BoardService boardService;
	private final MenuService menuService;
	private final TagService tagService;

	// 사용자에게 메시지를 전달하고, 페이지를 리다이렉트 한다.
	private String showMessageAndRedirect(final MessageDto params, Model model) {
		model.addAttribute("params", params);
		return "common/messageRedirect";
	}

	// 오류남..ㅠㅠ
	@Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.PARAMETER)
    @AuthenticationPrincipal(expression = "#this == 'anonymousUser' ? null : loginUser")
    public @interface CurrentUser {
    }
	
	@GetMapping("/board/search")
	public String getBoardSearchPage(Model model
			, @ModelAttribute("params") final SearchDto params
			, @RequestParam final Map<String, Object> queryParams
			//, @CurrentUser LoginUser loginUser) throws Exception {
			, @AuthenticationPrincipal LoginUser loginUser
			) throws Exception {
		
		try {
				log.error("Slf4j Test");
				log.error("========getBoardSearchPage params :: {}", params.toString());
				log.error("========getBoardSearchPage queryParams :: {}", queryParams);

				// 로그인한 대상의 권한 파악
				String strRole;
				if(loginUser == null){
					log.error("========getMainBoardListPage LoginUser :: null", loginUser);
					strRole = "ROLE_ANONYMOUS";
				}
				else{
					log.error("========getBoardSearchPage LoginUser :: {}", loginUser);
					//List<GrantedAuthority> authorities = (List<GrantedAuthority>) loginUser.getAuthorities();
					strRole = loginUser.getAuthorities().toString().replaceAll("[^\\w+]", "");					
				}
				model.addAttribute("resultMap", boardService.searchFindAll(params, strRole));

				model.addAttribute("menuList", menuService.getMenu());
				model.addAttribute("loginUser", loginUser);
				
		} catch (Exception e) {
			
		}
		
		return "board/search";
	}

	@GetMapping("/board/list")
	public String getBoardListPage(Model model
			, @ModelAttribute("params") final SearchDto params
			, @RequestParam final Map<String, Object> queryParams
			, @RequestParam(required = false, defaultValue = "") String boardTitle
			, @AuthenticationPrincipal LoginUser loginUser
			) throws Exception {
		
		try {
				log.error("========getBoardListPage params :: {}", params.toString());
				log.error("========getBoardListPage queryParams :: {}", queryParams);
				//model.addAttribute("boardTable", boardTable);
				model.addAttribute("resultMap", boardService.findAll(params));
				model.addAttribute("menuList", menuService.getMenu());
				model.addAttribute("tagList", tagService.getTag(params.getBoardId()));
				model.addAttribute("loginUser", loginUser);
				log.error("========getBoardListPage getTag :: {}", tagService.getTag(params.getBoardId()));
				
		} catch (Exception e) {
			
		}
		
		return "board/list";
	}
	
	@GetMapping("/board/write")
	public String getBoardWritePage(Model model
		,@RequestParam final Map<String, Object> queryParams
		, @RequestParam(value = "id", required = false) final Long id
		, @AuthenticationPrincipal LoginUser loginUser
		, BoardBasicRequestDto boardBasicRequestDto) throws Exception {
		try {
			if (id != null)
				model.addAttribute("resultMap", boardService.findById((String)queryParams.get("boardTable"), boardBasicRequestDto.getId(), "1"));
			//model.addAttribute("boardTable", boardTable);
			model.addAttribute("menuList", menuService.getMenu());
			model.addAttribute("tagList", tagService.getTag((String)queryParams.get("boardId")));
			model.addAttribute("loginUser", loginUser);
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 		
		}
		return "board/write";
	}
	
	@GetMapping("/board/view")
	public String getBoardViewPage(Model model
		, @RequestParam final Map<String, Object> queryParams
		, @AuthenticationPrincipal LoginUser loginUser
		, BoardBasicRequestDto boardBasicRequestDto) throws Exception {
		
		try {
			if (boardBasicRequestDto.getId() != null) {
				model.addAttribute("resultMap", boardService.findById((String)queryParams.get("boardTable"), boardBasicRequestDto.getId(), "0"));
				model.addAttribute("menuList", menuService.getMenu());
				model.addAttribute("loginUser", loginUser);
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		return "board/view";
	}
	
	@PostMapping("/board/save")
	public String boardSave(Model model
		, @RequestParam final Map<String, Object> queryParams
		, BoardBasicRequestDto boardBasicRequestDto
		//, MultipartHttpServletRequest multiRequest
		, @RequestParam(value = "afiles", required = false) List<MultipartFile> files
		, RedirectAttributes redirectAttributes) throws Exception {
		
		MessageDto message;
		String boardTable = (String)queryParams.get("boardTable");	// RequestParam 으로는 중복의 가능성이 있어 map에서 가져옴
		String qs= "/board/list?boardId="+ boardService.getBoardTableId(boardTable);
		redirectAttributes.addAttribute("ps",qs);		

		try {
			model.addAttribute("boardTable", boardTable);
			//logger.error("action boardBasicRequestDto :: {}", boardBasicRequestDto);
			//if (!boardService.save(boardTable, boardBasicRequestDto, multiRequest, files)) {
			if (!boardService.save(boardTable, boardBasicRequestDto, files)) {
				message = new MessageDto("게시글 등록에 실패하였습니다.", qs, RequestMethod.GET, null);
			}
		} catch (Exception e) {
			message = new MessageDto("시스템에 문제가 발생하였습니다.", qs, RequestMethod.GET, null);
		}
		
		message = new MessageDto("게시글 생성이 완료되었습니다.", qs, RequestMethod.GET, null);
        return showMessageAndRedirect(message, model);
	}
	
	@PostMapping("/board/update")
	public String boardUpdate(Model model
		, @RequestParam final Map<String, Object> queryParams
		, BoardBasicRequestDto boardBasicRequestDto, List<MultipartFile> files
		, RedirectAttributes redirectAttributes) throws Exception {
		
		try {
			boolean result = boardService.updateBoard((String)queryParams.get("boardTable"), boardBasicRequestDto, files);
			queryParams.remove("id");
			queryParams.remove("isNotice");
			queryParams.remove("isFileChange");
			queryParams.remove("title");
			queryParams.remove("content");
			queryParams.remove("registerId");
			queryParams.remove("fileIds");
			
			if (!result) {
				throw new Exception("#Exception boardViewAction!");
			}
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		MessageDto message = new MessageDto("게시글 수정이 완료되었습니다.", "/board/list", RequestMethod.GET, queryParams);
        return showMessageAndRedirect(message, model);

		//String qs= "boardTable="+ boardTable;
		//redirectAttributes.addAttribute("ps",qs);
		//return "redirect:/board/list?{ps}";
	}
	
	@PostMapping("/board/view/delete")
	public String boardViewDeleteAction(Model model
		, @RequestParam final Map<String, Object> queryParams
		, @RequestParam() Long id, RedirectAttributes redirectAttributes) throws Exception {
		
		try {
			boardService.deleteById((String)queryParams.get("boardTable"), id);
			queryParams.remove("id");
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}

		MessageDto message = new MessageDto("게시글 삭제가 완료되었습니다.", "/board/list", RequestMethod.GET, queryParams);
        return showMessageAndRedirect(message, model);
		
		//String qs= "boardTable="+ boardTable;
		//redirectAttributes.addAttribute("ps",qs);
		//return "redirect:/board/list?{ps}";
	}
	
	@PostMapping("/board/delete")
	public String boardDataDeleteAction(Model model
		, @RequestParam final Map<String, Object> queryParams
		, @RequestParam() Long[] deleteId, RedirectAttributes redirectAttributes) throws Exception {
		
		try {			
			boardService.deleteAll((String)queryParams.get("boardTable"), deleteId);
			queryParams.remove("deleteId");
		} catch (Exception e) {
			throw new Exception(e.getMessage()); 
		}
		
		MessageDto message = new MessageDto("게시글 삭제가 완료되었습니다.", "/board/list", RequestMethod.GET, queryParams);
        return showMessageAndRedirect(message, model);

		//String qs= "boardTable="+ boardTable;
		//redirectAttributes.addAttribute("ps",qs);
		//return "redirect:/board/list?{ps}";
	}

	@GetMapping("/board/download")
	public void downloadAttachFile(@RequestParam() Long id, HttpServletResponse response) {

		if (id == null) throw new RuntimeException("올바르지 않은 접근입니다.");

		BoardAttachRequestDto fileInfo = boardService.getAttachDetail(id);
		if (fileInfo == null || fileInfo.isBdelete()) {
			throw new RuntimeException("파일 정보를 찾을 수 없습니다.");
		}

		String tempStr = fileInfo.getRegisterTime();
		String uploadDate = tempStr.substring(2,4) + tempStr.substring(5,7) + tempStr.substring(8,10);
		//String uploadPath = Paths.get("C:", "temp", "upload", uploadDate).toString();
		String uploadPath = File.separator + "temp" + File.separator + "upload" + File.separator + uploadDate;

		String filename = fileInfo.getOriginalName();
		File file = new File(uploadPath, fileInfo.getSaveName());

		try {
			byte[] data = FileUtils.readFileToByteArray(file); 
			response.setContentType("application/octet-stream");
			response.setContentLength(data.length);
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(filename, "UTF-8") + "\";");

			response.getOutputStream().write(data);
			response.getOutputStream().flush();
			response.getOutputStream().close();

		} catch (IOException e) {
			throw new RuntimeException(e.getMessage());

		} catch (Exception e) {
			throw new RuntimeException("시스템에 문제가 발생하였습니다.");
		}
	}

		// summernote 이미지업로드, 어디에
		@PostMapping(value="/uploadSummernoteImageFile", produces = "application/json; charset=utf8")
		@ResponseBody
		public JsonObject uploadSummernoteImageFile(@RequestParam("imgFile") MultipartFile multipartFile) {
			
			JsonObject jsonObject = new JsonObject();
			
			log.error("=====================================action uploadSummernoteImageFile===============================");
			String fileRoot = File.separator + "summernote_image" + File.separator;	//저장될 외부 파일 경로
			log.error("fileRoot :: {}", fileRoot);
			
			String originalFileName = multipartFile.getOriginalFilename();	//오리지날 파일명
			String extension = originalFileName.substring(originalFileName.lastIndexOf("."));	//파일 확장자
					
			String savedFileName = UUID.randomUUID() + extension;	//저장될 파일 명
			
			File targetFile = new File(fileRoot + savedFileName);				
			
			try {
				InputStream fileStream = multipartFile.getInputStream();
				FileUtils.copyInputStreamToFile(fileStream, targetFile);	//파일 저장
				jsonObject.addProperty("url", "/summernoteImage/"+savedFileName);
				jsonObject.addProperty("responseCode", "success");
					
			} catch (IOException e) {
				FileUtils.deleteQuietly(targetFile);	//저장된 파일 삭제
				jsonObject.addProperty("responseCode", "error");
				e.printStackTrace();
			}
			
			log.error("jsonObject :: {}", jsonObject);
			log.error("jsonObject.toString :: {}", jsonObject.toString());
			return jsonObject;
		}
}
