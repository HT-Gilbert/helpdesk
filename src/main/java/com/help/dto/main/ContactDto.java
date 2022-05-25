package com.help.dto.main;

import com.help.entity.main.Contact;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ContactDto {
    private String deptName;
	private String ptbName;
	private String ptbTelNo;
    private String taskName;
    private int showOrder;

    public Contact toEntity() {
		return Contact.builder()
			.deptName(deptName)
			.ptbName(ptbName)
			.ptbTelNo(ptbTelNo)
			.taskName(taskName)
            .showOrder(showOrder)
			.build();
	}

	@Override
	public String toString() {
		return "BoardRequestDto [deptName=" + deptName + ", deptName=" + deptName + ", ptbTelNo=" + ptbTelNo + ", taskName=" + taskName
				+ ", showOrder=" + showOrder + "]";
	}
}
