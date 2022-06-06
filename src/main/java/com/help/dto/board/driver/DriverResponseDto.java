package com.help.dto.board.driver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import com.help.entity.board.driver.Driver;
import lombok.Getter;

@Getter
public class DriverResponseDto {
    private Long id;
	private String title;	
	private String content;
    private String tag;
	private int readCnt;
	private String registerId;
    private LocalDateTime registerTime;

    public DriverResponseDto(Driver entity){
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.tag = entity.getTag();
        this.readCnt = entity.getReadCnt();
        this.registerId = entity.getRegisterId();
        this.registerTime = entity.getRegisterTime();       
    }

    @Override
    public String toString() {
        return "DriverResponseDto [id=" + id + ", title="  + title + ", content="  + content + ", tag="  + tag
               + ", readCnt="  + readCnt + ", registerId="  + registerId + ", registerTime= " + registerTime + "]";
    }
 
    public String getRegisterTime() {
        return toStringDateTime(this.registerTime);
    }

    public static String toStringDateTime(LocalDateTime localDateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return Optional.ofNullable(localDateTime)
                .map(formatter::format)
                .orElse("");
    }
}
