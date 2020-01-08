package com.pingchuan.domain;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Caller {
    private String code;
    private String department;
    private String loginName;
    private String realName;
    private String loginPassword;
    private String role;
    private Date updateTime;
    private int enabled;
}