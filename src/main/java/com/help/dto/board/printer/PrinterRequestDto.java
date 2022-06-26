package com.help.dto.board.printer;

import com.help.entity.board.printer.Printer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PrinterRequestDto {
    private Long id;
    private String title;	
    private String content;
    private String tag;
    private int readCnt;
    private String registerId;
    private boolean isNotice;

    public Printer toEntity() {
        return Printer.builder()
            .title(title)
            .content(content)
            .registerId(registerId)
            .tag(tag)
            .isNotice(isNotice)
            .build();
    }

    @Override
    public String toString() {
        return "PrinterRequestDto [id=" + id + ", title=" + title + ", content=" + content + ", tag=" + tag + ", isNotice=" + isNotice + "]";
    } 
}