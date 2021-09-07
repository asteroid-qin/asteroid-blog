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
public class BlogVO {

    private Integer id;
    private String title;
    private String authorName;
    private String description;
    private Integer commentCount;
    private Boolean isExit;
    private String createTime;

    public BlogVO(Blog blog){
        id = blog.getId();
        title = blog.getTitle();
        isExit = blog.getIsExit();
        // 方便前端，对日期进行转换
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
        createTime = dateFormat.format(blog.getCreateTime());
    }
}
