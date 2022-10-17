package com.help.main.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import groovy.transform.builder.Builder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class HostnameDto {

    private String num;

    private String name;
  
    private String nameExam;

    private String etc;
}
