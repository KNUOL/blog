package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constants.SystemConstants;
import com.example.dto.AddArticleDto;
import com.example.pojo.Article;
import com.example.pojo.ArticleTag;
import com.example.pojo.Category;
import com.example.service.ArticleService;
import com.example.mapper.ArticleMapper;
import com.example.service.ArticleTagService;
import com.example.service.CategoryService;
import com.example.utils.BeanCopyUtils;

import com.example.vo.*;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author 81486
* @description 针对表【sg_article(文章表)】的数据库操作Service实现
* @createDate 2023-10-23 13:04:20
*/
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article>
    implements ArticleService{

    @Autowired
    private CategoryService categoryService;
//    @Autowired
//    private RedisCache redisCache;
    @Autowired
    private ArticleTagService articleTagService;

    @Override
    public ResponseResult hotArticle() {
        //查询热门文章
        LambdaQueryWrapper<Article> query = new LambdaQueryWrapper<>();
        query.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        query.orderByDesc(Article::getViewCount);
        Page<Article> page=new Page<>(1,10);
        page(page,query);
        List<Article> articles=page.getRecords();
        List<HotArticleVo> articleVos = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //分页查询
        LambdaQueryWrapper<Article> query = new LambdaQueryWrapper<>();
        query.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId).
                eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL).
                orderByDesc(Article::getIsTop)
                .orderByDesc(Article::getCreateTime);

        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page,query);//这里是地址引用直接修改了page参数的值，并未创建新对象

        List<Article> articles = page.getRecords();
        articles=articles.stream().map(item->{
            Category category = categoryService.getById(item.getCategoryId());
            item.setCategoryName(category.getName());
            return item;
        }).collect(Collectors.toList());


        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(articles, ArticleListVo.class);


        PageVo pageVo = new PageVo(articleListVos,page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //从redis查浏览次数
//        Integer viewCount = redisCache.getCacheMapValue("article:viewCount", id.toString());
//        article.setViewCount(viewCount.longValue());

        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if (category!=null){
            articleDetailVo.setCategoryName(category.getName());
        }


        return ResponseResult.okResult(articleDetailVo);
    }

    @Override
    public ResponseResult updateViewCount(Long id) {
//        redisCache.incrementCacheMapValue("article:viewCount",id.toString(),1);
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult addArticle(AddArticleDto addArticle) {
        Article article = BeanCopyUtils.copyBean(addArticle, Article.class);
        save(article);
        List<ArticleTag> articleTags = addArticle.getTags().stream()
                .map(tagId -> new ArticleTag(article.getId(), tagId)).collect(Collectors.toList());
        articleTagService.saveBatch(articleTags);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult list(Integer pageNum, Integer pageSize, String title, String summary) {
        LambdaQueryWrapper<Article> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Article::getStatus,SystemConstants.ARTICLE_STATUS_NORMAL);
        wrapper.like(StringUtils.hasText(title),Article::getTitle,title).like(StringUtils.hasText(summary),Article::getSummary,summary);
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        PageVo pageVo=new PageVo(page.getRecords(),page.getTotal());

        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult selectById(Long id) {
        Article article = getById(id);
        LambdaQueryWrapper<ArticleTag> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId,id);
        List<ArticleTag> list = articleTagService.list(wrapper);
        List<Long> collect = list.stream().map(item -> item.getTagId()).collect(Collectors.toList());
        article.setTags(collect);
        return ResponseResult.okResult(article);
    }

    @Override
    @Transactional
    public ResponseResult updateArticle(Article upArticle) {
        updateById(upArticle);
        List<Long> tags = upArticle.getTags();
        LambdaQueryWrapper<ArticleTag>wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(ArticleTag::getArticleId,upArticle.getId());
        articleTagService.remove(wrapper);
        if(tags!=null){
            List<ArticleTag> articleTags = tags.stream().map(tagId -> new ArticleTag(upArticle.getId(), tagId)).collect(Collectors.toList());
            articleTagService.saveBatch(articleTags);
        }
        return ResponseResult.okResult();
    }

    @Override
    @Transactional
    public ResponseResult deleteById(Long id) {
        removeById(id);
        return ResponseResult.okResult();

    }

    
}




