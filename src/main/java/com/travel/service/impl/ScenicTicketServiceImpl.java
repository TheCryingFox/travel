package com.travel.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.dao.ScenicTicketDao;
import com.travel.domain.ScenicTicket;
import com.travel.service.ScenicTicketService;
import org.springframework.stereotype.Service;

@Service
public class ScenicTicketServiceImpl extends ServiceImpl<ScenicTicketDao, ScenicTicket> implements ScenicTicketService {
}
