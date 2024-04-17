package com.example.controller;

import com.example.pojo.Article;
import com.example.service.ArticleService;
import com.example.vo.ResponseResult;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
@Tag(name = "文章",description = "关于文章的Api")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @Operation(summary = "热门文章",description = "查询十条热门文章")
    @GetMapping("/hotArticleList")
    public ResponseResult hotArticle() {
//查询热门文章
        ResponseResult result = articleService.hotArticle();
        return result;
    }

    @GetMapping("/articleList")
    @Parameters({
            @Parameter(name = "pageNum", description = "页码"),
            @Parameter(name = "pageSize", description = "页容量"),
            @Parameter(name = "categoryId", description = "分类id")
    }
    )
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        return articleService.articleList(pageNum, pageSize, categoryId);
    }
    @GetMapping("/{id}")
    public ResponseResult getArticleDetail(@PathVariable("id") Long id){
        return articleService.getArticleDetail(id);
    }
    @PutMapping("/updateViewCount/{id}")
    public ResponseResult updateViewCount(@PathVariable("id") Long id){
        return articleService.updateViewCount(id);

    }

}
