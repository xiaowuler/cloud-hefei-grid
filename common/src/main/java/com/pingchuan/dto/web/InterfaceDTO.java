package com.pingchuan.dto.web;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class InterfaceDTO {
    private int id;
    private String explain;
    private Integer state;
    private Timestamp executeStartTime;
    private Timestamp executeEndTime;
    private Timestamp requestStartTime;
    private Timestamp requestEndTime;
}