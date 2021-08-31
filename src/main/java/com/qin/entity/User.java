package com.qin.entity;

import lombok.Data;

import java.util.Date;

@Data
public class User {

    private Integer id;
    private String name;
    private String password;
    private boolean isExit;
    private Date createTime;
}
