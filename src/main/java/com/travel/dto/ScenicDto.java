package com.travel.dto;

import com.travel.domain.Scenic;
import com.travel.domain.ScenicPicture;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ScenicDto extends Scenic {

    private List<ScenicPicture> pictures = new ArrayList<>();

}
