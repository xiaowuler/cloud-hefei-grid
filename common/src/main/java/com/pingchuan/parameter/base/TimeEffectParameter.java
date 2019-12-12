package com.pingchuan.parameter.base;

import com.pingchuan.parameter.Parameter;
import lombok.Data;

import java.util.List;

@Data
public class TimeEffectParameter extends BaseParameter implements Parameter {

    private Integer timeEffect;

    @Override
    public List<String> checkCode(boolean isNeed) {
        super.checkCode(isNeed);

        check.checkInteger(timeEffect, "timeEffect");
        return check.errors;
    }

    @Override
    public String getAreaCode() {
        return null;
    }
}
