package com.example.service;

import com.example.pojo.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.ResponseResult;

/**
* @author 81486
* @description 针对表【sg_comment(评论表)】的数据库操作Service
* @createDate 2023-10-25 14:59:29
*/
public interface CommentService extends IService<Comment> {

    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    ResponseResult addComment(Comment comment);
}
