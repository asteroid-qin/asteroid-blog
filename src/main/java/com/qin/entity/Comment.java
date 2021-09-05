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
public class Comment {

    private Integer id;
    private Integer blogId;
    private Integer idx;
    private Integer senderId;
    private String receiverName;
    // 要回复评论的id
    private Integer commentId;
    private String content;
    private Boolean isExit;
    private Date createTime;
}
