package com.help.entity.main;

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
    @Column(name = "menu_lv")
    private int menuLv;
    @Column(name = "menu_dislist")
    private int menuDislist;
    @Column(name = "menu_upperid")
    private int menuUpperid;
    @Column(name = "menu_role")
    private int menuRole;
    @Column(name = "menu_link")
    private String menuLink;

    @Builder
    public Menu(int menuId, String menuName, int menuLv, int menuDislist, int menuUpperid, int menuRole, String menuLink) {
        this.menuId = menuId;
        this.menuName = menuName;
        this.menuLv = menuLv;
        this.menuDislist = menuDislist;
        this.menuUpperid = menuUpperid;
        this.menuRole = menuRole;
        this.menuLink = menuLink;
    }

}
