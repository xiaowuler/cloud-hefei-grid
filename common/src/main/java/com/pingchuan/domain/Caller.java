package com.pingchuan.domain;

import lombok.Data;
import java.util.Date;

@Data
public class Caller {
    private String code;
    private String department;
    private String loginName;
    private String realName;
    private String loginPassword;
    private String role;
    private Date updateTime;
    private byte enabled;
}