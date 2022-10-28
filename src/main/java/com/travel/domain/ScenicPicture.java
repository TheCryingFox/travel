package com.travel.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class ScenicPicture implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long scenicId;

    private String image;

    private Integer sort;


}