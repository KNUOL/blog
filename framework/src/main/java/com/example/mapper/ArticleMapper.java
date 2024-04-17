package com.example.mapper;

import com.example.pojo.Article;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 81486
* @description 针对表【sg_article(文章表)】的数据库操作Mapper
* @createDate 2023-10-31 16:19:57
* @Entity com.example.pojo.Article
*/
@Mapper
public interface ArticleMapper extends BaseMapper<Article> {

}




