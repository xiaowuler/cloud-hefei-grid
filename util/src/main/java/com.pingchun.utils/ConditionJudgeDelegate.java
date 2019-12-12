package com.pingchun.utils;

import java.util.List;

public interface ConditionJudgeDelegate {
    boolean calc(List<Double> er03, List<Double> pph, List<Double> ect);
}
