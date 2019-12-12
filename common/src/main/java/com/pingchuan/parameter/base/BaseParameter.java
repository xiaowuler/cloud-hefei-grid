package com.pingchuan.parameter.base;

import com.pingchuan.parameter.CheckParameter;
import com.pingchun.utils.SignUtil;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Data
public class BaseParameter {

    protected String token;

    protected String elementCode;

    protected String initialTime;

    protected Date initialDate;

    protected String modeCode;

    protected String orgCode;

    protected String location;

    protected List<double[]> locations;

    protected Integer forecastInterval;

    protected Integer forecastLevel;

    protected String callerCode;

    protected CheckParameter check;

    public List<String> checkCode(boolean isNeed){
        check = new CheckParameter();
        initialDate = check.checkTime(initialTime, "initialTime");
        elementCode = check.checkString(elementCode, "elementCode");
        modeCode = check.checkString(modeCode, "modeCode");
        orgCode = check.checkString(orgCode, "orgCode");
        check.checkInteger(forecastInterval, "forecastInterval");
        check.checkInteger(forecastLevel, "forecastLevel");

        if (isNeed) {
            locations = check.checkLocation(location);
        }else {
            locations = null;
        }

        return null;
    }

    public boolean verifyToken(){

        if (StringUtils.isEmpty(token)) {
            return false;
        }

        callerCode = SignUtil.getClaim(token, "userCode");
        return SignUtil.verify(token);
    }
}
