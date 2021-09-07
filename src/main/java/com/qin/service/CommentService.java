package com.qin.service;

import com.alibaba.druid.util.StringUtils;
import com.qin.dao.CommentDao;
import com.qin.entity.Comment;
import com.qin.entity.User;
import com.qin.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentDao commentDao;

    @Autowired
    private UserService userService;

    /**
     * 查出所有一级评论，通过blog的id，以（时间）逆序方式返回
     * @param blogId
     * @return
     */
    private List<Comment> getRank1CommentByBlogIdDesc(Integer blogId){
        return commentDao.getRank1CommentByBlogIdDesc(blogId);
    }

    private List<Comment> getRank2CommentByCIdDesc(Integer commentId){
        return commentDao.getRank2CommentByCIdDesc(commentId);
    }

    public int insert(Comment comment){
        return commentDao.insert(comment);
    }

    /**
     * 插入一条评论
     * @param blogId
     * @param content
     * @param userId
     * @param idx
     * @param _commentId
     * @return 成功返回true
     */
    public boolean insertComment(Integer blogId, String content, Integer userId, Integer idx, String _commentId){
        // 创建一个评论
        Comment comment = new Comment();
        comment.setBlogId(blogId);
        comment.setContent(content);
        comment.setSenderId(userId);
        comment.setIdx(idx);

        if(idx == 2 && !StringUtils.isNumber(_commentId)){
            return false;
        }

        // 如果时二级评论则需要添加额外的信息
        if(idx == 2 ){
            int commentId = Integer.parseInt(_commentId);
            Comment commentById = getCommentById(commentId);
            User userById = userService.getUserById(commentById.getSenderId());

            // 设置接受评论人的名字
            comment.setReceiverName(userById.getName());
            // 设置回复评论的id
            comment.setCommentId(commentId);
        }

        insert(comment);

        return true;
    }

    /**
     * 根据博客名查出所有的评论
     * @param blogId 要查询博客的id
     * @return
     */
    public List<CommentVO> getCommentByBlogId(Integer blogId){
        return getRank1CommentByBlogIdDesc(blogId).stream().map(m1->{
            List<Comment> children = new ArrayList<>();
            setComment(children, m1.getId());

            CommentVO cm1 = new CommentVO(m1);
            cm1.setSenderName(userService.getUserById(cm1.getSenderId()).getName());
            cm1.setChildren(children.stream().sorted(Comparator.comparing(Comment::getCreateTime).reversed()).map(m2->{
                CommentVO cm2 = new CommentVO(m2);
                cm2.setSenderName(userService.getUserById(m2.getSenderId()).getName());
                return cm2;
            }).collect(Collectors.toList()));
            return cm1;
        }).collect(Collectors.toList());
    }

    /**
     * 递归设置二级children
     * @param child
     * @param id
     */
    public void setComment(List<Comment> child, Integer id){
        List<Comment> cms = getRank2CommentByCIdDesc(id);
        if(cms.isEmpty() || null == cms){
            return;
        }else{
            child.addAll(cms);
            cms.forEach(comment -> setComment(child,comment.getId()) );
        }

    }

    public Comment getCommentById(Integer id) {
        return commentDao.getCommentById(id);
    }
}
