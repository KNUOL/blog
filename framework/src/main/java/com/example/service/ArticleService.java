package com.example.service;

import com.example.dto.AddArticleDto;
import com.example.pojo.Article;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.ResponseResult;

/**
* @author 81486
* @description 针对表【sg_article(文章表)】的数据库操作Service
* @createDate 2023-10-23 13:04:20
*/
public interface ArticleService extends IService<Article> {

    ResponseResult hotArticle();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);

    ResponseResult updateViewCount(Long id);

    ResponseResult addArticle(AddArticleDto addArticle);


    ResponseResult list(Integer pageNum, Integer pageSize, String title, String summary);

    ResponseResult selectById(Long id);

    ResponseResult updateArticle(Article upArticle);

    ResponseResult deleteById(Long id);
}
