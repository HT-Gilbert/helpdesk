package com.help.admin.dto;

import com.help.main.entity.Menu;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuDto {
    private int menuId;
    private String menuName;
    private String menuPosition;
    private String menuLv;
    private String menuDislist;
    private String menuUpperid;
    private String menuRole;
    private String menuGroup;
    private String menuLink;    
    private int boardId;

    public Menu toEntity() {
		return Menu.builder()
			.menuId(menuId)
			.menuName(menuName)
			.menuPosition(menuPosition)
			.menuLv(menuLv)
            .menuDislist(menuDislist)
            .menuUpperid(menuUpperid)
            .menuRole(menuRole)
            .menuRole(menuGroup)
            .menuLink(menuLink)
            .boardId(boardId)
			.build();
	}

	@Override
	public String toString() {
		return "MenuDto [menuId=" + menuId + ", menuName=" + menuName + ", menuPosition=" + menuPosition + ", menuLv=" + menuLv
        + ", menuDislist=" + menuDislist + ", menuUpperid=" + menuUpperid + ", menuRole=" + menuRole + ", menuGroup=" + menuGroup 
        + ", menuLink=" + menuLink + ", boardId=" + boardId + "]";
	}
}
