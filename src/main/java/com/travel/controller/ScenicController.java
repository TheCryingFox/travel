package com.travel.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.travel.domain.Scenic;
import com.travel.dto.ScenicDto;
import com.travel.service.ScenicPictureService;
import com.travel.service.ScenicService;
import com.travel.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 景点管理
 */
@Slf4j
@RestController
@RequestMapping("/scenic")
public class ScenicController {

    @Autowired
    private ScenicService scenicService;

    @Autowired
    private ScenicPictureService scenicPictureService;

    /**
     * 添加景点
     * @param scenicDto
     * @return
     */
    @PostMapping
    public R<String> save(@RequestBody ScenicDto scenicDto){
        log.info(scenicDto.toString());

        scenicService.saveWithFlavor(scenicDto);

        return R.success("新增景点成功");
    }

    /**
     * 景点信息分页查询
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name){

        //构造分页构造器对象
        Page<Scenic> pageInfo = new Page<>(page,pageSize);
        Page<ScenicDto> scenicDtoPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<Scenic> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null,Scenic::getName,name);
        //添加排序条件
        queryWrapper.orderByDesc(Scenic::getUpdateTime);

        //执行分页查询
        scenicService.page(pageInfo,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo,scenicDtoPage,"records");

        List<Scenic> records = pageInfo.getRecords();

        List<ScenicDto> list = records.stream().map((item) -> {
            ScenicDto dishDto = new ScenicDto();

            BeanUtils.copyProperties(item,dishDto);

            return dishDto;
        }).collect(Collectors.toList());

        scenicDtoPage.setRecords(list);

        return R.success(scenicDtoPage);
    }


    /**
     * 根据id查询景点信息和对应的图片信息
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<ScenicDto> get(@PathVariable Long id){

        ScenicDto scenicDto = scenicService.getByIdWithFlavor(id);

        return R.success(scenicDto);
    }

    /**
     * 修改菜品
     * @param scenicDto
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody ScenicDto scenicDto){
        log.info(scenicDto.toString());

        scenicService.updateWithFlavor(scenicDto);

        return R.success("修改菜品成功");
    }

    /**
     * 查询景点（用户端）
     * @param scenic
     * @return
     */
    @GetMapping("/list")
    public  R<List<Scenic>> list(Scenic scenic){

        //构造查询条件
        LambdaQueryWrapper<Scenic> queryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件,查询状态为1（起售状态）的菜品
        queryWrapper.eq(Scenic::getStatus,1);

        //添加排序条件
        queryWrapper.orderByAsc(Scenic::getSort).orderByDesc(Scenic::getUpdateTime);

        List<Scenic> list = scenicService.list(queryWrapper);

        return R.success(list);
    }

    /**
     * 停售菜品
     * @param status
     * @param ids
     * @return
     */
    @PostMapping("/status/{status}")
    public  R<String> status(@PathVariable Integer status, @RequestParam List<Long> ids){
        log.info("status:{}",status);
        log.info("ids:{}",ids);

        return R.success("停售菜品成功");
    }

    /**
     * 删除套餐
     * @param ids
     * @return
     */
    @DeleteMapping
    public R<String> delete(@RequestParam List<Long> ids){
        log.info("ids:{}",ids);

        return R.success("套餐数据删除成功");
    }

}
