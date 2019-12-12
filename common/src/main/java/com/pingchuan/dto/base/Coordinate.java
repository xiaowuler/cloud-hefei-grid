package com.pingchuan.dto.base;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.math.BigDecimal;

@Data
public class Coordinate {
    @Field("start_lon")
    private BigDecimal StartLon;

    @Field("end_lon")
    private BigDecimal EndLon;

    @Field("start_lat")
    private BigDecimal StartLat;

    @Field("end_lat")
    private BigDecimal EndLat;
}
