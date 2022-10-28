package com.travel.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.travel.dao.ScenicDao;
import com.travel.domain.Scenic;
import com.travel.domain.ScenicPicture;
import com.travel.dto.ScenicDto;
import com.travel.service.ScenicPictureService;
import com.travel.service.ScenicService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ScenicServiceImpl extends ServiceImpl<ScenicDao, Scenic> implements ScenicService {

    @Autowired
    private ScenicPictureService scenicPictureService;

    @Transactional
    public void saveWithFlavor(ScenicDto scenicDto) {
        //保存景点的基本信息到景点表scenic
        this.save(scenicDto);

        Long scenicId = scenicDto.getId();//景点id

        //景点图片
        List<ScenicPicture> flavors = scenicDto.getPictures();
        flavors = flavors.stream().map((item) -> {
            item.setScenicId(scenicId);
            return item;
        }).collect(Collectors.toList());

        //保存景点图片数据到景点图片表scenic_picture
        scenicPictureService.saveBatch(flavors);

    }

    /**
     * 根据id查询景点信息和对应的图片信息
     * @param id
     * @return
     */
    public ScenicDto getByIdWithFlavor(Long id) {
        //查询菜品基本信息，从dish表查询
        Scenic scenic = this.getById(id);

        ScenicDto scenicDto = new ScenicDto();
        BeanUtils.copyProperties(scenic,scenicDto);

        //查询当前菜品对应的口味信息，从dish_flavor表查询
        LambdaQueryWrapper<ScenicPicture> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ScenicPicture::getScenicId,scenic.getId());
        List<ScenicPicture> flavors = scenicPictureService.list(queryWrapper);
        scenicDto.setPictures(flavors);

        return scenicDto;
    }

    @Override
    @Transactional
    public void updateWithFlavor(ScenicDto scenicDto) {
        //更新dish表基本信息
        this.updateById(scenicDto);

        //清理当前菜品对应口味数据---dish_flavor表的delete操作
        LambdaQueryWrapper<ScenicPicture> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(ScenicPicture::getScenicId,scenicDto.getId());

        scenicPictureService.remove(queryWrapper);

        //添加当前提交过来的口味数据---dish_flavor表的insert操作
        List<ScenicPicture> pictures = scenicDto.getPictures();

        pictures = pictures.stream().map((item) -> {
            item.setScenicId(scenicDto.getId());
            return item;
        }).collect(Collectors.toList());

        scenicPictureService.saveBatch(pictures);
    }


}
