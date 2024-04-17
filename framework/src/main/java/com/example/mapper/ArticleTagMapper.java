package com.example.mapper;

import com.example.pojo.ArticleTag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 81486
* @description 针对表【sg_article_tag(文章标签关联表)】的数据库操作Mapper
* @createDate 2023-10-31 15:07:40
* @Entity com.example.pojo.ArticleTag
*/
@Mapper
public interface ArticleTagMapper extends BaseMapper<ArticleTag> {

}




