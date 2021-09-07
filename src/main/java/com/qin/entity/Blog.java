package com.qin.entity;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Blog {

    private Integer id;
    private String title;
    private Integer authorId;
    private Integer categoryId;
    private String content;
    private Boolean isExit;
    private Date createTime;
}
