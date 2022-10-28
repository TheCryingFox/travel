package com.travel.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class Carousel implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private Long scenicId;

    private String title;

    private String image;

    private Integer status;

    private Integer sort;


}