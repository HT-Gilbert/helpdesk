package com.help.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleDto {
    private String rolePriority;
    private String roleName;

    public RoleDto(String rolePriority, String roleName){
        this.rolePriority = rolePriority;
        this.roleName = roleName;
    }
}


