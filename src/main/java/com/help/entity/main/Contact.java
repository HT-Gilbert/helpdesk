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
@Entity(name = "contact_list")
@Table(name = "contact_list")
public class Contact {

    @Id
    @Column(name = "dept_name")
	private String deptName;    
    @Column(name = "ptb_name")
	private String ptbName;
    @Column(name = "ptb_tel_no")
	private String ptbTelNo;
    @Column(name = "task_name")
    private String taskName;
    @Column(name = "show_order")
    private int showOrder;

    @Builder
	public Contact(String deptName, String ptbName, String ptbTelNo, String taskName, int showOrder) {
		super();
		this.deptName = deptName;
        this.ptbName = ptbName;
        this.ptbTelNo = ptbTelNo;
		this.taskName = taskName;
        this.showOrder = showOrder;
	}

}
