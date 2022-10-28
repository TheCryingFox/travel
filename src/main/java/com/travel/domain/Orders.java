package com.travel.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
@Data
public class Orders implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String oid;

    private Date ordertime;

    private Double total;

    private Integer state;

    private String address;

    private String name;

    private String telephone;

    private String uid;


}