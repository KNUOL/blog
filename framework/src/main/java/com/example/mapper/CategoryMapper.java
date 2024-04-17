package com.example.mapper;

import com.example.pojo.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author 81486
* @description 针对表【sg_category(分类表)】的数据库操作Mapper
* @createDate 2023-10-23 14:12:04
* @Entity com.example.pojo.Category
*/
@Mapper
public interface CategoryMapper extends BaseMapper<Category> {

}




