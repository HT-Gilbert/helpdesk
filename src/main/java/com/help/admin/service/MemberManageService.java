package com.help.admin.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.help.admin.dto.MemberDto;
import com.help.admin.repository.MemberManageMapper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Service
@Slf4j
public class MemberManageService {

    private final MemberManageMapper memberManageMapper;

    public List<MemberDto> getMemberList() {
        
        return memberManageMapper.findAll();
    }
}
