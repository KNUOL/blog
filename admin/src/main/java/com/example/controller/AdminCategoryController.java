package com.example.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.constants.SystemConstants;
import com.example.pojo.Category;
import com.example.service.CategoryService;
import com.example.utils.BeanCopyUtils;
import com.example.vo.CategoryAdminVo;
import com.example.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/content/category")
public class AdminCategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/listAllCategory")
    public ResponseResult listAllCategory() {
        LambdaQueryWrapper<Category> wrapper=new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemConstants.NORMAL);
        List<Category> list = categoryService.list(wrapper);
        List<CategoryAdminVo> vos = BeanCopyUtils.copyBeanList(list, CategoryAdminVo.class);
        return ResponseResult.okResult(vos);
    }
}
