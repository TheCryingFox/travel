package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.domain.Employee;
import com.travel.domain.Scenic;
import com.travel.domain.ScenicTicket;
import com.travel.domain.Ticket;
import com.travel.dto.ScenicDto;
import com.travel.dto.ScenicTicketDto;
import com.travel.service.ScenicService;
import com.travel.service.ScenicTicketService;
import com.travel.service.TicketService;
import com.travel.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 门票管理
 */
@Slf4j
@RestController
@RequestMapping("/scenicTicket")
public class ScenicTicketController {
    @Autowired
    private ScenicService scenicService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ScenicTicketService scenicTicketService;

    /**
     * 添加门票
     * @param scenicTicket
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody ScenicTicket scenicTicket){
        log.info(scenicTicket.toString());

        scenicTicketService.save(scenicTicket);

        return R.success("新增门票成功");
    }

    /**
     * 门票信息分页查询
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        log.info("page = {},pageSize = {},name = {}" ,page,pageSize);

        //构造分页构造器
        Page pageInfo = new Page(page,pageSize);

        Page<ScenicTicketDto> dtoPage = new Page<>();

        //构造条件构造器
        LambdaQueryWrapper<ScenicTicket> queryWrapper = new LambdaQueryWrapper();
        //添加排序条件
        queryWrapper.orderByDesc(ScenicTicket::getUpdateTime);
        //执行查询
        scenicTicketService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,dtoPage,"records");

        List<ScenicTicket> records = pageInfo.getRecords();

        List<ScenicTicketDto> list = records.stream().map((item) -> {
            ScenicTicketDto scenicTicketDto = new ScenicTicketDto();
            //对象拷贝
            BeanUtils.copyProperties(item,scenicTicketDto);
            //分类id
            Long scenicId = item.getScenicId();
            Integer ticketId = item.getTicketId();
            //根据分类id查询分类对象
            Scenic scenic = scenicService.getById(scenicId);
            Ticket ticket = ticketService.getById(ticketId);
            if(scenic != null){
                String scenicName = scenic.getName();
                scenicTicketDto.setScenicName(scenicName);
            }
            if(ticket != null){
                String ticketName = ticket.getName();
                scenicTicketDto.setScenicName(ticketName);
            }
            return scenicTicketDto;
        }).collect(Collectors.toList());

        dtoPage.setRecords(list);

        return R.success(pageInfo);
    }

    /**
     * 根据id修改门票信息
     * @param scenicTicket
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody ScenicTicket scenicTicket){
        log.info(scenicTicket.toString());

        long id = Thread.currentThread().getId();
        log.info("线程id为：{}",id);
        scenicTicketService.updateById(scenicTicket);

        return R.success("员工信息修改成功");
    }

    /**
     * 根据id查询门票信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<ScenicTicket> getById(@PathVariable Long id){
        log.info("根据id查询员工信息...");
        ScenicTicket scenicTicket = scenicTicketService.getById(id);
        if(scenicTicket != null){
            return R.success(scenicTicket);
        }
        return R.error("没有查询到对应门票信息");
    }

}
