package com.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.constants.SystemConstants;
import com.example.pojo.Link;
import com.example.service.LinkService;
import com.example.mapper.LinkMapper;
import com.example.utils.BeanCopyUtils;
import com.example.vo.LinkVo;
import com.example.vo.PageVo;
import com.example.vo.ResponseResult;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
* @author 81486
* @description 针对表【sg_link(友链)】的数据库操作Service实现
* @createDate 2023-10-24 15:14:25
*/
@Service
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link>
    implements LinkService{

    @Override
    public ResponseResult getAllLink() {
        //查询所以审核通过的
        LambdaQueryWrapper<Link> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> list = list(queryWrapper);
        List<LinkVo> vs = BeanCopyUtils.copyBeanList(list, LinkVo.class);
        return ResponseResult.okResult(vs);
    }

    @Override
    public ResponseResult SystemList(Integer pageNum, Integer pageSize, String name, String status) {
        LambdaQueryWrapper<Link> wrapper=new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(name), Link::getName, name)
                .eq(StringUtils.hasText(status), Link::getStatus, status);
        Page<Link> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        PageVo pageVo=new PageVo(page.getRecords(),page.getTotal());
        return ResponseResult.okResult(pageVo);

    }
}




