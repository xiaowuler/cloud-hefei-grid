package com.pingchuan.parameter.base;

import com.pingchuan.parameter.Parameter;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class TimeRangeParameter extends BaseParameter implements Parameter {

    private String startForecastTime;

    private String endForecastTime;

    private Date startForecastDate;

    private Date endForecastDate;

    @Override
    public List<String> checkCode(boolean isNeed) {
        super.checkCode(isNeed);

        startForecastDate = check.checkTime(startForecastTime, "startForecastTime");
        endForecastDate = check.checkTime(endForecastTime, "endForecastTime");
        return check.errors;
    }

    @Override
    public String getAreaCode() {
        return null;
    }

}
