package com.travel.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.travel.domain.Scenic;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ScenicDao extends BaseMapper<Scenic> {
}
