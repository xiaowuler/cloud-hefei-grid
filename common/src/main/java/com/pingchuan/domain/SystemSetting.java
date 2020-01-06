package com.pingchuan.domain;

import lombok.Data;

@Data
public class SystemSetting {
    private int id;
    private String name;
    private int value;
    private String description;
}