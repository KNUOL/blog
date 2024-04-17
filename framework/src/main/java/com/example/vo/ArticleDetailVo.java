package com.example.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDetailVo {
    private Long id;

    private String title;

    private String content;
    private String summary;

    private Long categoryId;

    private String categoryName;

    private String thumbnail;

    private Long viewCount;

    private Date createTime;


}
