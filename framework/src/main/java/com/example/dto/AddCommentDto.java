package com.example.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "添加评论dto")
public class AddCommentDto {
    private Long id;
    @Schema(description = "评论类型(0代表评论文章，1代表评论友链")
    private String type;

    private Long articleId;


    private Long rootId;


    private String content;

    private Long toCommentUserId;


    private Long toCommentId;



    private Long createBy;



    private Date createTime;



    private Long updateBy;



    private Date updateTime;


    private Integer delFlag;

}
