package com.example.controller;

import com.example.pojo.Link;
import com.example.service.LinkService;
import com.example.vo.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/content/link")
public class LinkController {
    @Autowired
    private LinkService linkService;

    @GetMapping("/list")
    public ResponseResult list(Integer pageNum, Integer pageSize,String name,String status){
        return linkService.SystemList(pageNum,pageSize,name,status);
    }
    @PostMapping
    public ResponseResult add(@RequestBody Link link){
        linkService.save(link);
        return ResponseResult.okResult();
    }
    @GetMapping("/{id}")
    public ResponseResult getById(@PathVariable("id") Integer id){
        Link byId = linkService.getById(id);
        return ResponseResult.okResult(byId);
    }
    @PutMapping
    public ResponseResult update(@RequestBody Link link){
        linkService.updateById(link);
        return ResponseResult.okResult();
    }
    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable("id") Integer id){
        linkService.removeById(id);
        return ResponseResult.okResult();
    }
}
