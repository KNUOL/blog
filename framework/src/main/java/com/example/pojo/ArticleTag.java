package com.example.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文章标签关联表
 * @TableName sg_article_tag
 */
@TableName(value ="sg_article_tag")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleTag implements Serializable {
    /**
     * 文章id
     */
    @TableId
    private Long articleId;

    /**
     * 标签id
     */
    private Long tagId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}