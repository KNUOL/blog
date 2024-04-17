package com.example.controller;

import com.example.pojo.Category;
import com.example.service.CategoryService;
import com.example.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize,String name,String status) {
       return categoryService.SystemList(pageNum,pageSize,name,status);
    }
    @GetMapping("/{id}")
    public ResponseResult getById(@PathVariable("id")Long id){
        Category category = categoryService.getById(id);
        return ResponseResult.okResult(category);
    }
    @PostMapping
    public ResponseResult add(@RequestBody Category category) {

        categoryService.save(category);
        return ResponseResult.okResult();
    }
    @PutMapping
    public ResponseResult update(@RequestBody Category category) {
        categoryService.updateById(category);
        return ResponseResult.okResult();
    }
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id")Long id) {
        categoryService.removeById(id);
        return ResponseResult.okResult();
    }
}
