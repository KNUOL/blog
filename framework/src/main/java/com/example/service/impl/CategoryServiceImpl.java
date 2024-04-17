package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constants.SystemConstants;
import com.example.pojo.Article;
import com.example.pojo.Category;
import com.example.service.ArticleService;
import com.example.service.CategoryService;
import com.example.mapper.CategoryMapper;
import com.example.utils.BeanCopyUtils;
import com.example.vo.CategoryVo;
import com.example.vo.PageVo;
import com.example.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
* @author 81486
* @description 针对表【sg_category(分类表)】的数据库操作Service实现
* @createDate 2023-10-23 14:12:04
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService{

    @Autowired
    private ArticleService articleService;
    @Override
    public ResponseResult getCategoryList() {
        //查询文章表，状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper=new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类id并且去重
        Set<Long> categoryIds = articleList.stream().map(Article::getCategoryId).collect(Collectors.toSet());

        List<Category> categories = listByIds(categoryIds);
        categories = categories.stream().filter(item -> item.getStatus().equals(SystemConstants.STATUS_NORMAL)).collect(Collectors.toList());
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }

    @Override
    public ResponseResult SystemList(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Category> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(StringUtils.hasText(status),Category::getStatus,status)
                .like(StringUtils.hasText(name),Category::getName,name);
        Page<Category> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        PageVo pageVo=new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);
    }


}




