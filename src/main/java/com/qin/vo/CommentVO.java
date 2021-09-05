package com.qin.vo;

import com.qin.entity.Comment;
import lombok.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CommentVO {

    private Integer id;
    private Integer blogId;
    private Integer idx;
    private Integer senderId;
    private String senderName;
    private String receiverName;
    // 要回复评论的id
    private Integer commentId;
    private String content;
    private Boolean isExit;
    private String createTime;
    private List<CommentVO> children;

    public CommentVO(Comment comment){
        id = comment.getId();
        blogId = comment.getBlogId();
        idx = comment.getIdx();
        senderId = comment.getSenderId();
        receiverName = comment.getReceiverName();
        commentId = comment.getCommentId();
        content = comment.getContent();
        isExit = comment.getIsExit();

        // 方便前端，对日期进行转换
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd hh:mm");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        createTime = dateFormat.format(comment.getCreateTime());
    }
}
