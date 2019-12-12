package com.pingchuan.model;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;

@Data
public class ElementInfo {

    @Field("_id")
    private long id;

    @Field("initial_time")
    private Date initialTime;

    @Field("trapezoid_info_id")
    private String trapezoidInfoId;
}
