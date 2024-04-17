package com.example.runner;

import com.example.mapper.ArticleMapper;
import com.example.pojo.Article;
import com.example.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ViewCountRunner implements CommandLineRunner {
    @Autowired
    private ArticleMapper articleMapper;
    @Autowired
    private RedisCache redisCache;

    @Override
    public void run(String... args) throws Exception {
        //查询博客信息
        List<Article> articles = articleMapper.selectList(null);
        Map<String, Integer> viewCountMap = articles.stream().
                collect(Collectors.toMap(article1 -> article1.getId().toString(), article -> article.getViewCount().intValue()));

        //存到reids
        redisCache.setCacheMap("article:viewCount", viewCountMap);
    }
}
