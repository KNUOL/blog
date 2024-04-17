package com.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.pojo.ArticleTag;
import com.example.service.ArticleTagService;
import com.example.mapper.ArticleTagMapper;
import org.springframework.stereotype.Service;

/**
* @author 81486
* @description 针对表【sg_article_tag(文章标签关联表)】的数据库操作Service实现
* @createDate 2023-10-31 15:07:40
*/
@Service
public class ArticleTagServiceImpl extends ServiceImpl<ArticleTagMapper, ArticleTag>
    implements ArticleTagService{

}




