package com.help.admin.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@NoArgsConstructor
public class MemberDto {
    private String userId;
    private String userName;
    private String userNick;
    private String userGroup;
    private String userRole;
    private String userStatus;
    private String userEmail;   
    private String userPhone;
    private String password;
    private int totalLoginCount;
    private int loginFailCount;
    private String loginTime;
    private String registerTime;
    private String updateTime;
    private String pwdUpdateTime;
    private String loginIp;

    public MemberDto(String userId, String userName, String userNick, String userGroup, String userRole, String userStatus, 
                    String userEmail, String userPhone, String password, int totalLoginCount, int loginFailCount, 
                    String loginTime, String registerTime, String updateTime, String pwdUpdateTime, String loginIp){
        this.userId= userId;
        this.userName = userName;
        this.userNick =userNick ;
        this.userGroup = userGroup;
        this.userRole = userRole;
        this.userStatus = userStatus;
        this.userEmail = userEmail;
        this.userPhone = userPhone;
        this.password = password;
        this.totalLoginCount = totalLoginCount;
        this.loginFailCount = loginFailCount;
        this.loginTime = loginTime;
        this.registerTime = registerTime;
        this.updateTime = updateTime;
        this.pwdUpdateTime = pwdUpdateTime;
        this.loginIp = loginIp;
    }
}
