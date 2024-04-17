package com.example.mapper;

import com.example.pojo.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 81486
* @description 针对表【sg_comment(评论表)】的数据库操作Mapper
* @createDate 2023-10-25 14:59:29
* @Entity com.example.pojo.Comment
*/
@Mapper
public interface CommentMapper extends BaseMapper<Comment> {

}




