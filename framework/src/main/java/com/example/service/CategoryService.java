package com.example.service;

import com.example.pojo.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.vo.ResponseResult;

/**
* @author 81486
* @description 针对表【sg_category(分类表)】的数据库操作Service
* @createDate 2023-10-23 14:12:04
*/
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();


    ResponseResult SystemList(Integer pageNum, Integer pageSize, String name, String status);
}
