package com.pingchun.utils;

import java.util.Arrays;
import java.util.List;

public class WeatherDecodeLib {

    //三小时降水量级
    private final float[] RainLevel_3Hour = new float[] { 0.1f, 3f, 10f, 20f, 50f, 70f };

    //三小时降雪量级
    private final float[] SnowLevel_3Hour = new float[] { 0.1f, 0.5f, 1.5f, 4f };

    //六小时降雪量级
    private final float[] SnowLevel_6Hour = new float[] { 0.1f, 2.5f, 5f, 10f };

    //十二小时降水量级
    // public float[] RainLevel_12Hour = new float[] { 0.1f, 5f, 15f, 30f, 70f, 140f };
    private final float[] RainLevel_12Hour = new float[] { 0.1f, 10f, 25f, 50f, 100f, 250f };

    //十二小时降雪量级
    //public float[] SnowLevel_12Hour = new float[] { 0.1f, 1f, 3f, 6f };
    private final float[] SnowLevel_12Hour = new float[] { 0.1f, 2.5f, 5f, 10f };

    //二十四小时降雪量级
    private final float[] SnowLevel_24Hour = new float[] { 0.1f, 2.5f, 5f, 10f };

    //二十四小时降水量级
    private final float[] RainLevel_24Hour = new float[] { 0.1f, 10f, 25f, 50f, 100f, 250f };


    /// <summary>
    /// 获取天气现象条件数组
    /// </summary>
    /// <param name="forecastHour">天气现象代码的时效</param>
    /// <returns></returns>
    public WeatherPhenomenon[] conditionMaker(int forecastHour)
    {

        float[] rainLevle = null;
        float[] snowLevle = null;
        float percent_threshold = 0.75f;

        if (forecastHour == 3)
        {
            rainLevle = RainLevel_3Hour;
            snowLevle = SnowLevel_3Hour;
            percent_threshold = 1;
        }
        else
        {
            rainLevle = RainLevel_12Hour;
            snowLevle = SnowLevel_12Hour;
            percent_threshold = 0.75f;
        }

        //WeatherPhenomenon 特大暴雨 = new WeatherPhenomenon(12, 1, (R, PPH_Code, ECT_Code) => (R.Sum() >= rainLevle[5]) && (PPH_Code.Average() == 1));

        float[] finalRainLevle = rainLevle;
        WeatherPhenomenon 特大暴雨 = new WeatherPhenomenon(12, 1, (er03, pph, ect) -> (er03.stream().reduce(Double::sum).orElse(0d) >= finalRainLevle[5] && pph.stream().mapToInt(Double::intValue).summaryStatistics().getAverage() == 1));
        //WeatherPhenomenon 暴雪 = new WeatherPhenomenon(17, 2, (R, PPH_Code, ECT_Code) => (R.Sum() >= snowLevle[3]) && (PPH_Code.Average() == 3));
        float[] finalSnowLevle = snowLevle;
        WeatherPhenomenon 暴雪 = new WeatherPhenomenon(17, 2, (er03, pph, ect) -> (er03.stream().reduce(Double::sum).orElse(0d) >= finalSnowLevle[3]) && (pph.stream().mapToInt(Double::intValue).summaryStatistics().getAverage() == 3));
        //WeatherPhenomenon 大暴雨 = new WeatherPhenomenon(11, 3, (R, PPH_Code, ECT_Code) => (R.Sum() < rainLevle[5] && R.Sum() >= rainLevle[4]) && (PPH_Code.Average() == 1));
        WeatherPhenomenon 大暴雨 = new WeatherPhenomenon(11, 3, (er03, pph, ect) -> (er03.stream().reduce(Double::sum).orElse(0d) < finalRainLevle[5] && er03.stream().reduce(Double::sum).orElse(0d) >= finalRainLevle[4]) && (pph.stream().mapToInt(Double::intValue).summaryStatistics().getAverage() == 1));
        //WeatherPhenomenon 暴雨 = new WeatherPhenomenon(10, 4, (R, PPH_Code, ECT_Code) => (R.Sum() < rainLevle[4] && R.Sum() >= rainLevle[3]) && (PPH_Code.Average() == 1));
        WeatherPhenomenon 暴雨 = new WeatherPhenomenon(10, 4, (er03, pph, ect) -> (er03.stream().reduce(Double::sum).orElse(0d) < finalRainLevle[4] && er03.stream().reduce(Double::sum).orElse(0d) >= finalRainLevle[3]) && (pph.stream().mapToInt(Double::intValue).summaryStatistics().getAverage() == 1));
        //WeatherPhenomenon 大雪 = new WeatherPhenomenon(16, 6, (R, PPH_Code, ECT_Code) => (R.Sum() < snowLevle[3] && R.Sum() >= snowLevle[2]) && (PPH_Code.Average() == 3));
        WeatherPhenomenon 大雪 = new WeatherPhenomenon(16, 6, (er03, pph, ect) -> (er03.stream().reduce(Double::sum).orElse(0d) < finalSnowLevle[3] && er03.stream().reduce(Double::sum).orElse(0d) >= finalSnowLevle[2]) && (pph.stream().mapToInt(Double::intValue).summaryStatistics().getAverage()== 3));
        //WeatherPhenomenon 中雪 = new WeatherPhenomenon(15, 7, (R, PPH_Code, ECT_Code) => (R.Sum() < snowLevle[2] && R.Sum() >= snowLevle[1]) && (PPH_Code.Average() == 3));
        WeatherPhenomenon 中雪 = new WeatherPhenomenon(15, 7, (er03, pph, ect) -> (er03.stream().reduce(Double::sum).orElse(0d) < finalSnowLevle[2] && er03.stream().reduce(Double::sum).orElse(0d) >= finalSnowLevle[1]) && (pph.stream().mapToInt(Double::intValue).summaryStatistics().getAverage() == 3));
        //WeatherPhenomenon 大雨 = new WeatherPhenomenon(9, 8, (R, PPH_Code, ECT_Code) => (R.Sum() < rainLevle[3] && R.Sum() >= rainLevle[2]) && (PPH_Code.Average() == 1));
        WeatherPhenomenon 大雨 = new WeatherPhenomenon(9, 8, (er03, pph, ect) -> (er03.stream().reduce(Double::sum).orElse(0d) < finalRainLevle[3] && er03.stream().reduce(Double::sum).orElse(0d) >= finalRainLevle[2]) && (pph.stream().mapToInt(Double::intValue).summaryStatistics().getAverage() == 1));
        //WeatherPhenomenon 小雪 = new WeatherPhenomenon(14, 11, (R, PPH_Code, ECT_Code) => (R.Sum() < snowLevle[1] && R.Sum() >= snowLevle[0]) && (PPH_Code.Average() == 3));
        WeatherPhenomenon 小雪 = new WeatherPhenomenon(14, 11, (er03, pph, ect) -> (er03.stream().reduce(Double::sum).orElse(0d) < finalSnowLevle[1] && er03.stream().reduce(Double::sum).orElse(0d) >= finalSnowLevle[0]) && (pph.stream().mapToInt(Double::intValue).summaryStatistics().getAverage() == 3));
        //WeatherPhenomenon 雨夹雪 = new WeatherPhenomenon(6, 11, (R, PPH_Code, ECT_Code) => (R.Sum() > 0) && (((from r in PPH_Code where r == 2 select r).Count() > 0) || (((from r in PPH_Code where r == 1 select r).Count() > 0) && ((from r in PPH_Code where r == 3 select r).Count() > 0))));
        WeatherPhenomenon 雨夹雪 = new WeatherPhenomenon(6, 11, (er03, pph, ect) -> (er03.stream().reduce(Double::sum).orElse(0d) > 0) && ((pph.stream().filter(p -> p == 2d).count() > 0) || ((pph.stream().filter(p -> p == 1d).count() > 0) && (pph.stream().filter(p -> p == 3d).count() > 0))));
        //WeatherPhenomenon 冻雨 = new WeatherPhenomenon(6, 11, (R, PPH_Code, ECT_Code) => (R.Sum() > 0) && (((from r in PPH_Code where r == 4 select r).Count() > 0)));
        WeatherPhenomenon 冻雨 = new WeatherPhenomenon(6, 11, (er03, pph, ect) -> (er03.stream().reduce(Double::sum).orElse(0d) > 0) && ((pph.stream().filter(p -> p == 4d).count() > 0)));
        //WeatherPhenomenon 中雨 = new WeatherPhenomenon(8, 13, (R, PPH_Code, ECT_Code) => (R.Sum() < rainLevle[2] && R.Sum() >= rainLevle[1]) && (PPH_Code.Average() == 1));
        WeatherPhenomenon 中雨 = new WeatherPhenomenon(8, 13, (er03, pph, ect) -> (er03.stream().reduce(Double::sum).orElse(0d) < finalSnowLevle[2] && er03.stream().reduce(Double::sum).orElse(0d) >= finalSnowLevle[1]) && (pph.stream().mapToInt(Double::intValue).summaryStatistics().getAverage() == 1));
        //WeatherPhenomenon 小雨 = new WeatherPhenomenon(7, 18, (R, PPH_Code, ECT_Code) => (R.Sum() < rainLevle[1] && R.Sum() >= rainLevle[0]) && (PPH_Code.Average() == 1));
        WeatherPhenomenon 小雨 = new WeatherPhenomenon(7, 18, (er03, pph, ect) -> (er03.stream().reduce(Double::sum).orElse(0d) < finalSnowLevle[1] && er03.stream().reduce(Double::sum).orElse(0d) >= finalSnowLevle[0]) && (pph.stream().mapToInt(Double::intValue).summaryStatistics().getAverage() == 1));


        //WeatherPhenomenon 晴 = new WeatherPhenomenon(0, 19, (R, PPH_Code, ECT_Code) => (R.Sum() == 0) && (from r in ECT_Code where r <= 30f select r).Count() / (float)ECT_Code.Length >= percent_threshold);
        float finalPercent_threshold = percent_threshold;
        WeatherPhenomenon 晴 = new WeatherPhenomenon(0, 19, (er03, pph, ect) -> ((er03.stream().reduce(Double::sum).orElse(0d) == 0) && ect.stream().filter(e -> e <= 30d).count() / (float)ect.size() >= finalPercent_threshold));
        //WeatherPhenomenon 阴 = new WeatherPhenomenon(2, 19, (R, PPH_Code, ECT_Code) => (R.Sum() == 0) && (from r in ECT_Code where r >= 90f select r).Count() / (float)ECT_Code.Length >= percent_threshold);
        WeatherPhenomenon 阴 = new WeatherPhenomenon(2, 19, (er03, pph, ect)-> (er03.stream().reduce(Double::sum).orElse(0d) == 0) && ect.stream().filter(e -> e >= 90d).count() / (float)ect.size() >= finalPercent_threshold);

        WeatherPhenomenon[] weatherPhenomena = new WeatherPhenomenon[] { 特大暴雨, 暴雪, 大暴雨, 暴雨, 大雪, 中雪, 大雨, 小雪, 雨夹雪, 冻雨, 中雨, 小雨, 晴, 阴 };
        Arrays.sort(weatherPhenomena, (x, y) -> (x.Priority -y.Priority));
        return weatherPhenomena;
    }


    /// <summary>
    /// 获取天气现象代码函数
    /// </summary>
    /// <param name="elements">条件查询数组</param>
    /// <param name="R">分时降水量</param>
    /// <param name="PPH_Code">相位编码</param>
    /// <param name="ECT_Code">云盖编码</param>
    /// <returns></returns>
    public int getWeatherElement(WeatherPhenomenon[] weatherPhenomenas, List<Double> er03, List<Double> pph, List<Double> ect)
    {
        for (WeatherPhenomenon w : weatherPhenomenas)
        {
            if (w.ConditionJudgeFunctions.calc(er03, pph, ect))
                return w.WeatherCode;
        }
        return 01;//如果条件不满足则返回最终多云。
    }
}
