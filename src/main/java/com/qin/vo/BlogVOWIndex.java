package com.qin.vo;

import com.qin.entity.Blog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BlogVOWIndex {

    private String title;
    private Integer authorId;
    private String authorName;
    private String categoryName;
    private Boolean isExit;
    private String createTime;
    private String content;

    public BlogVOWIndex(Blog blog){
        title = blog.getTitle();
        isExit = blog.getIsExit();
        authorId = blog.getAuthorId();
        content = blog.getContent();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        createTime = dateFormat.format(blog.getCreateTime());
    }
}
