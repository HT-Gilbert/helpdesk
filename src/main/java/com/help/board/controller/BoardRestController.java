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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import org.apache.commons.io.FileUtils;

import com.google.gson.JsonObject;
import com.help.account.entity.LoginUser;
import com.help.board.dto.BoardAttachRequestDto;
import com.help.board.dto.BoardBasicRequestDto;
import com.help.board.dto.SearchDto;
import com.help.board.service.BoardService;
import com.help.main.controller.MainController.CurrentUser;
import com.help.main.dto.MessageDto;
import com.help.main.entity.Menu;
import com.help.main.service.MenuService;
import com.help.main.service.TagService;
import com.help.main.util.ControllerUtil;

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
@RestController
@Slf4j
public class BoardRestController {

    private final BoardService boardService;
	private final MenuService menuService;
	private final TagService tagService;
    
    @GetMapping("/restapi")
    public List<Menu> findAllMember() {
        List<Menu> menu = menuService.getMenu();
        return menu;
    }
}
