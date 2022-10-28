package com.travel.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.dao.EmployeeDao;
import com.travel.domain.Employee;
import com.travel.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeDao, Employee> implements EmployeeService {
}
