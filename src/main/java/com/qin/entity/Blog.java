package com.qin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Blog {

    private Integer id;
    private String title;
    private Integer authorId;
    private Integer categoryId;
    private String content;
    private Boolean isExit;
    private Date createTime;
}
