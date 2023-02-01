package com.help.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.help.admin.repository.RoleMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleMapper roleMapper;
    
    public String getPriorityOne(String roleName){
        return roleMapper.selectRolePriority(roleName);
    }    
}
