package com.travel.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.dao.TicketDao;
import com.travel.domain.ScenicTicket;
import com.travel.domain.Ticket;
import com.travel.service.ScenicTicketService;
import com.travel.service.TicketService;
import com.travel.utils.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TicketServiceImpl extends ServiceImpl<TicketDao, Ticket> implements TicketService {
    @Autowired
    private ScenicTicketService scenicTicketService;

    /**
     * 根据id删除分类，删除之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Integer id) {
        LambdaQueryWrapper<ScenicTicket> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(ScenicTicket::getTicketId,id);
        int count = scenicTicketService.count(dishLambdaQueryWrapper);


        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        if(count > 0){
            //已经关联菜品，抛出一个业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        //正常删除分类
        super.removeById(id);
    }
}
