package com.qin.controller;

import com.alibaba.druid.util.StringUtils;
import com.qin.entity.R;
import com.qin.entity.User;
import com.qin.service.BlogService;
import com.qin.service.CommentService;
import com.qin.service.UserService;
import com.qin.vo.BlogVOWIndex;
import com.qin.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @RequestMapping("blog/{id}")
    public String toBlog(@PathVariable("id") Integer id){
        // 有且仅有一条记录才能去blog页
        if(blogService.getCountBlogById(id) == 1){
            return "blog";
        }

        return "/";
    }

    @RequestMapping("blog")
    @ResponseBody
    public R getBlog(@RequestParam("id")String _id){
        if(StringUtils.isNumber(_id)){
            int id = Integer.parseInt(_id);

            BlogVOWIndex blog = blogService.getBlogPageBlog(id);

            return R.ok("已查到博客！", blog);

        }

        return R.no(null);
    }

    @RequestMapping("blog/comment/{id}")
    @ResponseBody
    public R getComment(@PathVariable("id")String _id){
        if(StringUtils.isNumber(_id)){
            int blogId = Integer.parseInt(_id);

            List<CommentVO> comments = commentService.getCommentByBlogId(blogId);

            return R.ok("返回所有博客的评论！", comments);
        }

        return R.no(null);
    }

    @RequestMapping(value = "blog/comment/{id}", method = RequestMethod.POST)
    @ResponseBody
    public R insert(@PathVariable("id")Integer blogId,
                    @RequestParam("idx")String _idx,
                    @RequestParam("content")String content,
                    @RequestParam("commentId")String _commentId,
                    HttpServletRequest request){
        // 判断当前用户是否合法
        String name = userService.getUser(request);
        if(StringUtils.isEmpty(name)){
            return R.no("当前用户不合法！", null);
        }

        // 判断内容是否合法
        if(StringUtils.isEmpty(content) || content.length() > 255){
            return R.no("用户"+name+",评论内容不合法！", null);
        }

        User user = userService.getUserByName(name);
        if(StringUtils.isNumber(_idx)){
            int idx = Integer.parseInt(_idx);

            boolean flag = commentService.insertComment(blogId, content, user.getId(), idx, _commentId);

            if(flag)
                return R.ok("用户"+name+",发布评论成功！", null);
        }

        return R.no("数据不合法！",null);
    }
}
