package com.pingchuan.parameter.base;

import com.pingchuan.parameter.CheckParameter;
import com.pingchuan.parameter.Parameter;
import com.pingchun.utils.SignUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Data
public class ForecastParameter implements Parameter {

    private String startForecastTime;

    private String endForecastTime;

    private Date startForecastDate;

    private Date endForecastDate;

    private String location;

    private List<double[]> locations;

    private String station;

    private List<String> stations;

    private String initialTime;

    private Date initialDate;

    private String token;

    private String callerCode;

    @Override
    public List<String> checkCode(boolean isNeed) {
        CheckParameter checkParameter = new CheckParameter();

        if (!StringUtils.isEmpty(location)) {
            locations = checkParameter.checkLocation(location);
        }else {
            stations = checkParameter.checkStation(station);
        }

        if (!StringUtils.isEmpty(initialTime)){
            initialDate = checkParameter.checkTime(initialTime, "initialTime");
        }

        if (!StringUtils.isEmpty(startForecastTime)){
            startForecastDate = checkParameter.checkTime(startForecastTime, "startForecastTime");
        }

        if (!StringUtils.isEmpty(endForecastTime)){
            endForecastDate = checkParameter.checkTime(endForecastTime, "endForecastTime");
        }

        return checkParameter.errors;
    }

    @Override
    public boolean verifyToken() {
        if (StringUtils.isEmpty(token)) {
            return false;
        }

        callerCode = SignUtil.getClaim(token, "userCode");
        return SignUtil.verify(token);
    }

    @Override
    public String getAreaCode() {
        return null;
    }
}
