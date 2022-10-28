package com.travel.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.domain.Ticket;

public interface TicketService extends IService<Ticket> {
    public void remove(Integer id);
}
