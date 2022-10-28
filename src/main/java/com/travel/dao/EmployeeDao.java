package com.travel.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.domain.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeDao extends BaseMapper<Employee> {
}
