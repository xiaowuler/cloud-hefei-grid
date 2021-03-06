package com.pingchuan.domain;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class InterfaceLog {
    private Integer id;
    private Integer interfaceId;
    private String parameters;
    private byte state;
    private String callerCode;
    private String errorMessage;
    private Timestamp requestStartTime;
    private Timestamp executeStartTime;
    private Timestamp executeEndTime;
    private Timestamp requestEndTime;
    private String requestType;
    private String hostAddress;
    private String regionCode;
    private Integer resultCode;
}