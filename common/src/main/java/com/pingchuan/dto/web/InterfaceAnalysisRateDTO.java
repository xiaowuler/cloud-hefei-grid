package com.pingchuan.dto.web;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InterfaceAnalysisRateDTO {
    private String explain;
    private double successRate;
    private double failureRate;
    private long invokeCount;
    private double averageTime;
    private long dayBeforeInvokeCount;
    private long yesterdayInvokeCount;
    private long todayInvokeCount;
    private double successAverageTime;
    private double failureAverageTime;
}