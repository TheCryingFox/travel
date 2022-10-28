package com.travel.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Data
public class ShoppingCart implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer number;

    private BigDecimal amount;

    private Date bookingTime;

    private Long scenicId;

    private Integer ticketId;

    private Long userId;


}