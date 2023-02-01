package com.help.admin.repository;

import java.util.List;

import com.help.admin.dto.MemberDto;

public interface MemberManageMapper {
    List<MemberDto> findAll(); 
}
