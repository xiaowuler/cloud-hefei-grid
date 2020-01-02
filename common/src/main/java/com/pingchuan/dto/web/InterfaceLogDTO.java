package com.pingchuan.dto.web;

import lombok.Data;
import java.util.Date;

@Data
public class InterfaceLogDTO {
    private String name;
    private String state;
    private String department;
    private Date executeStartTime;
    private Date executeEndTime;
    private double elapsedTime;
}