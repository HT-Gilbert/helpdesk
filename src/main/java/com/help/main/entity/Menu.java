package com.help.main.entity;

import javax.persistence.Entity;
import javax.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import javax.persistence.Table;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Builder
@Entity(name = "menu_list")
@Table(name = "menu_list")
public class Menu {
    @Id
    @Column(name = "menu_id")
    private int menuId;
    @Column(name = "menu_name")
    private String menuName;
    @Column(name = "menu_position") // "000" : Top, "100" : 좌측상위, "110" : 좌측하위, "200" : 관리자
    private String menuPosition;
    @Column(name = "menu_lv")       // "000" : 상위, "010" : content, "020" : link
    private String menuLv;
    @Column(name = "menu_dislist")
    private String menuDislist;
    @Column(name = "menu_upperid")
    private String menuUpperid;
    @Column(name = "menu_role")
    private String menuRole;
    @Column(name = "menu_group")
    private String menuGroup;
    @Column(name = "menu_link")
    private String menuLink;
    @Column(name = "board_id")
    private int boardId;

    @Builder
    public Menu(int menuId, String menuName, String menuPosition, String menuLv, String menuDislist, String menuUpperid, String menuRole, String menuGroup, String menuLink, int boardId) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuPosition = menuPosition;        
        this.menuLv = menuLv;
        this.menuDislist = menuDislist;
        this.menuUpperid = menuUpperid;
        this.menuRole = menuRole;
        this.menuGroup = menuGroup;
        this.menuLink = menuLink;
        this.boardId = boardId;
    }

}
