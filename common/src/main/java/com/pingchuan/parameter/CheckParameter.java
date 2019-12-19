package com.pingchuan.parameter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pingchuan.contants.TimeFormat;
import com.pingchun.utils.TimeUtil;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CheckParameter {

    private final ObjectMapper mapper = new ObjectMapper();
    public List<String> errors = new ArrayList<>();

    public void checkInteger(Integer parameter, String parameterName){
        if (StringUtils.isEmpty(parameter)){
            errors.add(String.format("%s不能为空", parameterName));
        }
    }

    public Date checkTime(String timeStr, String parameterName){
        if (StringUtils.isEmpty(timeStr))
        {
            errors.add(String.format("%s不能为空", parameterName));
            return null;
        }

        Date time = convertTimeStr(timeStr);
        if (StringUtils.isEmpty(time)) {
            errors.add(String.format("%s 格式不正确，日期格式：%s", parameterName, TimeFormat.PARAMETER_TIME));
        } else {
            return time;
        }

        return null;
    }

    public Date checkDate(String timeStr, String parameterName){
        if (StringUtils.isEmpty(timeStr))
        {
            errors.add(String.format("%s不能为空", parameterName));
            return null;
        }

        Date time = convertDateStr(timeStr);
        if (StringUtils.isEmpty(time)) {
            errors.add(String.format("%s 格式不正确，日期格式：%s", parameterName, TimeFormat.ELEMENT_VALUES_NAME));
        } else {
            return time;
        }

        return null;
    }

    public boolean checkTimeEffect(Integer timeEffect){
        if (StringUtils.isEmpty(timeEffect))
        {
            errors.add("timeEffect不能为空");
            return false;
        }

        return true;
    }

    public String checkString(String parameter, String parameterName){
        if (StringUtils.isEmpty(parameter))
        {
            errors.add(String.format("%s不能为空", parameterName));
            return null;
        }

        return parameter.toUpperCase();
    }

    public List<double[]> checkLocation(String location){

        if (StringUtils.isEmpty(location))
        {
            errors.add("location不能为空");
            return null;
        }

        try{
            return mapper.readValue(location, new TypeReference<List<double[]>>(){});
        }catch (Exception e){
            errors.add("location 格式不正确，格式应为'[[lon,lat],...,[lon,lat]]'");
        }

        return null;
    }

    public List<double[]> checkThreshold(String threshold){

        if (StringUtils.isEmpty(threshold))
        {
            errors.add("threshold不能为空");
            return null;
        }

        try{
            return mapper.readValue(threshold, new TypeReference<List<double[]>>(){});
        }catch (Exception e){
            errors.add("threshold 格式不正确，格式应为'[[startValue, endValue],...,[startValue, endValue]]'");
        }

        return null;
    }

    private Date convertTimeStr(String timeStr){
        try {
            return TimeUtil.CovertStringToDate(TimeFormat.PARAMETER_TIME, timeStr);
        } catch (ParseException e) {

        }
        return null;
    }

    private Date convertDateStr(String timeStr){
        try {
            return TimeUtil.CovertStringToDate(TimeFormat.ELEMENT_VALUES_NAME, timeStr);
        } catch (ParseException e) {

        }
        return null;
    }

    public Date ConvertForecastTime(Date startTime, int timeEffect){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.HOUR_OF_DAY, timeEffect);
        return calendar.getTime();
    }

    public List<String> checkStation(String station) {

        if (StringUtils.isEmpty(station))
        {
            errors.add("station 和 loction 不能同时为空");
            return null;
        }

        try{
            return mapper.readValue(station, new TypeReference<List<String>>(){});
        }catch (Exception e){
            errors.add("threshold 格式不正确，格式应为'[station1,...,station1]'");
        }

        return null;

    }
}
