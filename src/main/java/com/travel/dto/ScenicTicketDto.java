package com.travel.dto;


import com.travel.domain.ScenicTicket;
import lombok.Data;

@Data
public class ScenicTicketDto extends ScenicTicket {

    private String scenicName;

    private String ticketName;

}
