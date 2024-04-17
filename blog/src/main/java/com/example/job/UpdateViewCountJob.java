package com.example.job;

import com.example.pojo.Article;
import com.example.service.ArticleService;
import com.example.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UpdateViewCountJob {
    /** 0/5从0秒开始每隔5秒执行一次 后面依次是分、时、日期、月份（1-12）、星期几（1是星期日）、年（可以不写，spring不支持）
     ， - *（可以在任意部分使用，‘，’用来定义列表指多个，比如1，2，3有3个，‘-’表示范围，‘*’任意）
     日期部分的特殊字符：？ L W （‘？’只能在日期和星期用，表示没有具体的值，表示一个设置了值，另一个可能会冲突就必须用？
     ‘W’表示当月最接近某天的工作日，比如31W，如果31号是周六，那31W表示30号，如果31号是周日，则31W表示29号
     ‘L’表示最后，只能在日期和星期中，L-2表示倒数第2天，星期中L表示周六）
     #只能用在星期，表示每月的第几个星期几，比如0（表示第一个星期天）#1（表示第一个星期一）
     **/

    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleService articleService;

    @Scheduled(cron = "0/5 * * * * ?")
    public void updateViewCount(){
        //获取redis数据
        Map<String, Integer> viewCountMap = redisCache.getCacheMap("article:viewCount");

        List<Article> articles = viewCountMap.entrySet().stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新
        articleService.updateBatchById(articles);
    }
}
