package com.pingchun.utils;

public class WeatherPhenomenon {

    public WeatherPhenomenon(int wc, int pri, ConditionJudgeDelegate judgeFunc)
    {
        this.WeatherCode = wc;
        this.Priority = pri;
        this.ConditionJudgeFunctions = judgeFunc;
    }

    /// <summary>
    /// 天气现象代码
    /// </summary>
    public int WeatherCode = 9999;

    /// <summary>
    /// 优先级
    /// </summary>
    public int Priority = 9999;

    public int getPriority() {
        return Priority;
    }

    public void setPriority(int priority) {
        Priority = priority;
    }

    /// <summary>
    /// 判断函数
    /// </summary>
    public ConditionJudgeDelegate ConditionJudgeFunctions;
}
