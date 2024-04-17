package com.example.controller;

import com.example.dto.AddArticleDto;
import com.example.pojo.Article;
import com.example.service.ArticleService;
import com.example.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/article")
public class AdminArticleController {
    @Autowired
    private ArticleService articleService;


    @PostMapping
    public ResponseResult addArticle(@RequestBody AddArticleDto addArticle) {

        return articleService.addArticle(addArticle);
    }
    @GetMapping("/list")
    public ResponseResult list(Integer pageNum,Integer pageSize,String title,String summary){
        return
                articleService.list(pageNum,pageSize,title,summary);
    }
    @GetMapping("/{id}")
    public ResponseResult selectById(@PathVariable("id")Long id){
        return articleService.selectById(id);
    }
    @PutMapping
    public ResponseResult updateArticle(@RequestBody Article upArticle){
        return articleService.updateArticle(upArticle);
    }
    @DeleteMapping("/{id}")
    public ResponseResult deleteById(@PathVariable("id")Long id){
        return articleService.deleteById(id);
    }
}


