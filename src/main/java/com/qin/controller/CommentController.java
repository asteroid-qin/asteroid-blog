package com.qin.controller;

import com.alibaba.druid.util.StringUtils;
import com.qin.entity.Comment;
import com.qin.entity.R;
import com.qin.entity.User;
import com.qin.service.CommentService;
import com.qin.service.UserService;
import com.qin.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

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
    public R insert(@PathVariable("id")String _id,
                    @RequestParam("idx")String _idx,
                    @RequestParam("content")String content,
                    @RequestParam("commentId")String _commentId,
                    HttpServletRequest request){
        // 判断当前用户是否合法
        String name = userService.getUser(request);
        if(StringUtils.isEmpty(name)){
            return R.no("用户不合法！", null);
        }

        User user = userService.getUserByName(name);
        if(StringUtils.isNumber(_id) && StringUtils.isNumber(_idx)
                && StringUtils.isNumber(_commentId)){
            int blogId = Integer.parseInt(_id);
            int idx = Integer.parseInt(_idx);
            int commentId = Integer.parseInt(_commentId);


            Comment comment = new Comment();
            comment.setBlogId(blogId);

            comment.setContent(content);
            comment.setSenderId(user.getId());
            comment.setIdx(idx);

            if(idx == 2){
                Comment commentById = commentService.getCommentById(commentId);
                User userById = userService.getUserById(commentById.getSenderId());

                comment.setReceiverName(userById.getName());
                comment.setCommentId(commentId);
            }

            commentService.insert(comment);
            return R.ok("发布评论成功！", null);
        }

        return R.no("数据不合法！",null);
    }
}
