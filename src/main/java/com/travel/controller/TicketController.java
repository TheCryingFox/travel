package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.domain.Ticket;
import com.travel.service.TicketService;
import com.travel.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 门票类别管理
 */
@RestController
@RequestMapping("/ticket")
@Slf4j
public class TicketController {
    @Autowired
    private TicketService ticketService;

    /**
     * 新增分类
     * @param ticket
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody Ticket ticket){
        log.info("category:{}",ticket);
        ticketService.save(ticket);
        return R.success("新增分类成功");
    }

    /**
     * 分类管理的分页请求
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        log.info("page = {},pageSize = {}",page,pageSize);

        //构造分页构造器
        Page<Ticket> pageInfo = new Page<>(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Ticket> queryWrapper = new LambdaQueryWrapper<>();
        //添加排序条件,根据srot进行排序
        queryWrapper.orderByAsc(Ticket::getSort);

        //执行查询
        ticketService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 根据id删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(Integer id){
        log.info("删除分类，id为：{}",id);

//        categoryService.removeById(id);
        ticketService.remove(id);

        return R.success("分类信息删除成功");
    }

    /**
     * 根据id修改分类信息
     * @param ticket
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Ticket ticket) {
        log.info("修改分类信息：{}", ticket);

        ticketService.updateById(ticket);

        return R.success("修改分类信息成功");
    }

    /**
     * 根据条件查询分类数据
     * @param category
     * @return
     */
    @GetMapping("/list")
    public R<List<Ticket>> list(Ticket category){
        //条件构造器
        LambdaQueryWrapper<Ticket> queryWrapper = new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getName() != null,Ticket::getName,category.getName());
        //添加排序条件
        queryWrapper.orderByAsc(Ticket::getSort);

        List<Ticket> list = ticketService.list(queryWrapper);
        return R.success(list);
    }
}
