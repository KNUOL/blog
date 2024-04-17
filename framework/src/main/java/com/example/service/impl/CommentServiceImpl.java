package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constants.SystemConstants;
import com.example.enums.AppHttpCodeEnum;
import com.example.exception.SystemException;
import com.example.pojo.Comment;
import com.example.service.CommentService;
import com.example.mapper.CommentMapper;
import com.example.service.UserService;
import com.example.utils.BeanCopyUtils;
import com.example.vo.CommentVo;
import com.example.vo.PageVo;
import com.example.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 81486
 * @description 针对表【sg_comment(评论表)】的数据库操作Service实现
 * @createDate 2023-10-25 14:59:29
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
        implements CommentService {

    @Autowired
    private UserService userService;

    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {

        //分页查询对应文章的根评论
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType),Comment::getArticleId, articleId);
        queryWrapper.eq(Comment::getRootId, -1);
        queryWrapper.eq(Comment::getType, commentType);
        //进行查询
        Page<Comment> page = new Page(pageNum, pageSize);
        page(page, queryWrapper);

        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());
        //子评论
        commentVoList=commentVoList.stream().map(item->{
           List<CommentVo> children= getChildren(item.getId());
           item.setChildren(children);;
           return item;
        }).collect(Collectors.toList());
        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    @Override
    public ResponseResult addComment(Comment comment) {
        //评论内容不能为空
        if (!StringUtils.hasText(comment.getContent())){
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);
        return ResponseResult.okResult();
    }

    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);
        //遍历集合设置用户昵称
        List<CommentVo> commentVoList = commentVos.stream().map(item -> {
            String nickName = userService.getById(item.getCreateBy()).getNickName();
            item.setUserName(nickName);
            if (item.getToCommentUserId() != -1) {
                String toCommentUserName = userService.getById(item.getToCommentUserId()).getNickName();
                item.setToCommentUserName(toCommentUserName);
                ;
            }
            return item;
        }).collect(Collectors.toList());
        return commentVoList;
    }

    private List<CommentVo> getChildren(Long id) {
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> list = list(queryWrapper);
        List<CommentVo> commentVoList = toCommentVoList(list);
        return commentVoList;
    }
}




