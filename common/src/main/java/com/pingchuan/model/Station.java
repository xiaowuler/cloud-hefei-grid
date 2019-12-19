package com.pingchuan.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
public class Station {
    private double[] loc;

    @Field("_id")
    private String id;
}
