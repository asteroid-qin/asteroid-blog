package com.qin.entity;

import lombok.*;

import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private Integer id;
    private String name;
    private String password;
    private boolean isExit;
    private Date createTime;

    public User(String name, String password){
        this.name = name;
        this.password = password;
    }
}
