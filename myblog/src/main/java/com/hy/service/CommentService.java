package com.hy.service;

import com.hy.entity.Comment;

import java.util.List;

/**
 * @author :C3006248
 * @Description:博客评论业务层接口
 * @create : 2020/11/25 17:15
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    int saveComment(Comment comment);

//    查询子评论
//    List<Comment> getChildComment(Long blogId,Long id);

    //删除评论
    void deleteComment(Comment comment, Long id);

}