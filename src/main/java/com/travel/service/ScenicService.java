package com.travel.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.travel.domain.Scenic;
import com.travel.dto.ScenicDto;

public interface ScenicService extends IService<Scenic> {

    //添加景点，同时插入景点对应的图片，需要操作两张表：scenic、scenic_picture
    public void saveWithFlavor(ScenicDto scenicDto);

    //根据id查询景点信息和对应的图片信息
    public ScenicDto getByIdWithFlavor(Long id);

    //更新景点信息
    public void updateWithFlavor(ScenicDto scenicDto);
}
