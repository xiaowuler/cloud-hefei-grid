package com.pingchuan.mongodb.service.impl;

import com.pingchuan.contants.TimeFormat;
import com.pingchuan.dto.base.Element;
import com.pingchuan.dto.base.ElementCode;
import com.pingchuan.dto.base.Forecast;
import com.pingchuan.dto.base.WeatherElement;
import com.pingchuan.dto.base.forecast.Day;
import com.pingchuan.dto.base.forecast.Detail;
import com.pingchuan.dto.base.forecast.ForecastElement;
import com.pingchuan.dto.base.forecast.Hour;
import com.pingchuan.model.ElementInfo;
import com.pingchuan.model.Station;
import com.pingchuan.parameter.base.*;
import com.pingchuan.mongodb.dao.*;
import com.pingchuan.mongodb.service.BaseSearchService;
import com.pingchun.utils.Calc;
import com.pingchun.utils.TimeUtil;
import com.pingchun.utils.WeatherDecodeLib;
import com.pingchun.utils.WeatherPhenomenon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BaseSearchServiceImpl implements BaseSearchService {

    @Autowired
    private ElementInfoDao elementInfoDao;

    @Autowired
    private TrapezoidDao trapezoidDao;

    @Autowired
    private ForecastInfoDao forecastInfoDao;

    @Autowired
    private ElementValueDao elementValueDao;

    @Autowired
    private BaseSearchDao baseSearchDao;

    @Autowired
    private ForecastDao forecastDao;

    @Autowired
    private StationDao stationDao;

    @Override
    public List<Element> findPointValue(TimeEffectParameter timeEffectParameter) {
        List<AggregationOperation> elementInfos = elementInfoDao.findOne(timeEffectParameter.getElementCode(), timeEffectParameter.getInitialDate(), timeEffectParameter.getModeCode(), timeEffectParameter.getOrgCode(), timeEffectParameter.getForecastInterval(), timeEffectParameter.getForecastLevel());
        List<AggregationOperation> trapezoids = trapezoidDao.findLocationIdByElementInfo(timeEffectParameter.getLocations(), timeEffectParameter.getElementCode(), timeEffectParameter.getInitialDate(), timeEffectParameter.getModeCode(), timeEffectParameter.getOrgCode(), timeEffectParameter.getForecastInterval(), timeEffectParameter.getForecastLevel());
        List<AggregationOperation> forecastInfos = forecastInfoDao.findByTimeEffect(TimeUtil.CovertDateToString(TimeFormat.REAL_COLLECTION_NAME, timeEffectParameter.getInitialDate()), timeEffectParameter.getTimeEffect());
        List<AggregationOperation> elementValues = elementValueDao.findById(TimeUtil.CovertDateToString(TimeFormat.ELEMENT_VALUES_NAME, timeEffectParameter.getInitialDate()));
        elementInfos.addAll(trapezoids);
        elementInfos.addAll(forecastInfos);
        elementInfos.addAll(elementValues);
        return baseSearchDao.findPointValue(elementInfos);
    }

    @Override
    public List<Element> findLineValues(TimeRangeParameter timeRangeParameter) {
        List<AggregationOperation> elementInfos = elementInfoDao.findOne(timeRangeParameter.getElementCode(), timeRangeParameter.getInitialDate(), timeRangeParameter.getModeCode(), timeRangeParameter.getOrgCode(), timeRangeParameter.getForecastInterval(), timeRangeParameter.getForecastLevel());
        List<AggregationOperation> trapezoids = trapezoidDao.findLocationIdByElementInfo(timeRangeParameter.getLocations(), timeRangeParameter.getElementCode(), timeRangeParameter.getInitialDate(), timeRangeParameter.getModeCode(), timeRangeParameter.getOrgCode(), timeRangeParameter.getForecastInterval(), timeRangeParameter.getForecastLevel());
        List<AggregationOperation> forecastInfos = forecastInfoDao.findByTimeRange(TimeUtil.CovertDateToString(TimeFormat.REAL_COLLECTION_NAME, timeRangeParameter.getInitialDate()), timeRangeParameter.getStartForecastDate(), timeRangeParameter.getEndForecastDate());
        List<AggregationOperation> elementValues = elementValueDao.findById(TimeUtil.CovertDateToString(TimeFormat.ELEMENT_VALUES_NAME, timeRangeParameter.getInitialDate()));
        elementInfos.addAll(trapezoids);
        elementInfos.addAll(forecastInfos);
        elementInfos.addAll(elementValues);
        return baseSearchDao.findLineValues(elementInfos);
    }

    @Override
    public List<Element> findRegionValues(TimeEffectParameter timeEffectParameter) {
        List<AggregationOperation> elementInfos = elementInfoDao.findOne(timeEffectParameter.getElementCode(), timeEffectParameter.getInitialDate(), timeEffectParameter.getModeCode(), timeEffectParameter.getOrgCode(), timeEffectParameter.getForecastInterval(), timeEffectParameter.getForecastLevel());
        List<AggregationOperation> forecastInfos = forecastInfoDao.findOne(TimeUtil.CovertDateToString(TimeFormat.REAL_COLLECTION_NAME, timeEffectParameter.getInitialDate()), timeEffectParameter.getTimeEffect());
        elementInfos.addAll(forecastInfos);
        return baseSearchDao.findRegionValues(elementInfos);
    }

    @Override
    public List<ForecastElement> findWeatherForecast(ForecastParameter forecastParameter) {
        List<Station> stations = new ArrayList<>();
        if (forecastParameter.getStations() != null){
            stations = stationDao.getAllByStations(forecastParameter.getStations());
            List<double[]> locations = stations.stream().map(l -> l.getLoc()).collect(Collectors.toList());
            forecastParameter.setLocations(locations);
        }

        List<ElementInfo> elementInfos = getInitialTime(forecastParameter.getInitialDate());
        if (elementInfos.size() < 7)
            return new ArrayList<>();

        Date initialTime = elementInfos.get(0).getInitialTime();
        String trapezoidInfoId = elementInfos.get(0).getTrapezoidInfoId();
        List<AggregationOperation> elementInfo = elementInfoDao.findOneById(elementInfos.stream().map(e -> e.getId()).collect(Collectors.toList()));
        List<AggregationOperation> trapezoidInfos = trapezoidDao.findByLocation(forecastParameter.getLocations(), trapezoidInfoId);

        if (StringUtils.isEmpty(forecastParameter.getStartForecastDate()) || StringUtils.isEmpty(forecastParameter.getEndForecastDate())){
            forecastParameter.setStartForecastDate(initialTime);
            forecastParameter.setEndForecastDate(TimeUtil.addDay(initialTime, 7));
        }

        List<AggregationOperation> forecastInfos = forecastInfoDao.findByTimeRange(TimeUtil.CovertDateToString(TimeFormat.REAL_COLLECTION_NAME, initialTime), forecastParameter.getStartForecastDate(), forecastParameter.getEndForecastDate());
        List<AggregationOperation> elementValues = elementValueDao.findById(TimeUtil.CovertDateToString(TimeFormat.ELEMENT_VALUES_NAME, initialTime));
        elementInfo.addAll(trapezoidInfos);
        elementInfo.addAll(forecastInfos);
        elementInfo.addAll(elementValues);
        List<WeatherElement> weatherElements = baseSearchDao.findWeatherForecast(elementInfo);
        weatherElements = setStation(weatherElements, stations);
        return calcWeatherPhenomena(weatherElements);
    }

    private List<WeatherElement> setStation(List<WeatherElement> weatherElements, List<Station> stations){
        if (stations.size() == 0){
            return weatherElements;
        }

        List<WeatherElement> newWeather = new ArrayList<>();
        for(Station station : stations){
            for(int x = 0; x < weatherElements.size(); x++){
                if (!StringUtils.isEmpty(weatherElements.get(x).getStationCode())){
                    continue;
                }

                double[] loc = weatherElements.get(x).getLoc();
                double[] stationLoc = station.getLoc();
                if (loc[0] >= (stationLoc[0] - 0.025) && loc[0] <= (stationLoc[0] + 0.025) && loc[1] >= (stationLoc[1] - 0.025) && loc[1] <= (stationLoc[1] + 0.025)){
                    WeatherElement weatherElement = weatherElements.get(x);
                    weatherElement.setStationCode(station.getId());
                    newWeather.add(weatherElement);
                }
            }
        }

        return newWeather;
    }

    @Override
    public List<ForecastElement> findWeatherForecastByNewest(ForecastParameter forecastParameter) {
        List<ForecastElement> forecastElements = forecastDao.findOneWeekByLocation(forecastParameter.getLocations());
        return forecastElements;
    }

    private List<ForecastElement> calcWeatherPhenomena(List<WeatherElement> elements){

        WeatherDecodeLib weatherDecodeLib = new WeatherDecodeLib();
        WeatherPhenomenon[] weatherPhenomenas = weatherDecodeLib.conditionMaker(3);
        List<ForecastElement> forecastElements = new ArrayList<>();
        for(WeatherElement weatherElement : elements){
            Date startTime = weatherElement.getInitialTime();
            Date endTime = TimeUtil.addDay(startTime, 7);
            Date endDateTime;

            List<Day> days = new ArrayList<>();
            int index = 0;
            for (Date time = startTime; endTime.compareTo(time) == 1; time = endDateTime){
                endDateTime = TimeUtil.addHour(time, 12);
                Date finalTime = time;
                Date finalEndDateTime = endDateTime;
                List<Forecast> forecast = weatherElement.getForecasts().stream().filter(w -> w.getForecastTime().compareTo(finalTime) == 1 && w.getForecastTime().compareTo(finalEndDateTime) != 1).collect(Collectors.toList());
                if (index == 0 || index == 1 || index % 2 == 0){
                    Day day = new Day();
                    //day.setDate(time);
                    days.add(calcHalfDay(forecast, weatherDecodeLib, weatherPhenomenas, day, index));
                    index += 2;
                }else {
                    Day day = days.get(days.size() - 1);
                    days.remove(day);
                    days.add(calcHalfDay(forecast, weatherDecodeLib, weatherPhenomenas, day, index));
                }
                index ++;
            }

            ForecastElement forecastElement = new ForecastElement();
            forecastElement.setDays(days);
            forecastElement.setStationCode(weatherElement.getStationCode());
            forecastElement.setInitialTime(weatherElement.getInitialTime());
            forecastElement.setLoc(weatherElement.getLoc());
            forecastElements.add(forecastElement);
        }

        return forecastElements;
    }

    private Day calcHalfDay(List<Forecast> forecasts, WeatherDecodeLib weatherDecodeLib, WeatherPhenomenon[] weatherPhenomenas, Day day, int index){
        List<Double> er03 = new ArrayList<>();
        List<Double> ect = new ArrayList<>();
        List<Double> pph = new ArrayList<>();

        Detail detail = new Detail();
        List<Hour> hours = new ArrayList<>();
        forecasts.sort(Comparator.comparing(Forecast::getForecastTime));
        for (Forecast forecast : forecasts){
            Hour hour = new Hour();
            hour.setDay(TimeUtil.CovertDateToString("dd日HH时", forecast.getForecastTime()));
            List<ElementCode> elementCodes = forecast.getElementCodes();

            for (ElementCode elementCode : elementCodes){
                switch (elementCode.getElementCode()){
                    case "TMP":
                        hour.setTmp(elementCode.getValue() - 274.15);
                        break;
                    case "EDA10":
                        hour.setWindDirection(Calc.windDirection(elementCode.getUValue(), elementCode.getVValue()));
                        hour.setWindSpeed(Calc.windSpeed(elementCode.getUValue(), elementCode.getVValue()));
                        break;
                    case "ER03":
                        er03.add(elementCode.getValue());
                        break;
                    case "PPH":
                        pph.add(elementCode.getValue());
                        break;
                    case "ECT":
                        ect.add(elementCode.getValue());
                        break;
                    case "TMAX":
                        day.setMaxTmp(elementCode.getValue() - 274.15);
                        break;
                    case "TMIN":
                        day.setMinTmp(elementCode.getValue() - 274.15);
                        break;
                }
            }

            hours.add(hour);
        }

        if (hours.size() > 0){
            detail = setMaxWind(hours, detail);
            detail.setHours(hours);
        }

        detail.setWea(weatherDecodeLib.getWeatherElement(weatherPhenomenas, er03, pph, ect));
        if (forecasts.size() > 0){
            //int hour = forecasts.get(0).getForecastTime().getHours();
            if (index % 2 == 0){
                day.setPrev12Hours(detail);
            }else {
                day.setNext12Hours(detail);
            }
        }

        return day;
    }

    private Detail setMaxWind(List<Hour> hours, Detail detail){
        detail.setWindSpeed(hours.get(0).getWindSpeed());
        detail.setWindDirection(hours.get(0).getWindDirection());
        for(Hour hour : hours){
            if (hour.getWindSpeed() > detail.getWindSpeed()){
                detail.setWindSpeed(hour.getWindSpeed());
                detail.setWindDirection(hour.getWindDirection());
            }
        }

        return detail;
    }

    private List<ElementInfo> getInitialTime(Date initialTime){
        if (!StringUtils.isEmpty(initialTime)) {
            return elementInfoDao.findByInitialTime(initialTime);
        }

        List<ElementInfo> initialTimes = elementInfoDao.findSevenDayInitialTimes();

        List<ElementInfo> elementInfos = null;
        for (ElementInfo elementInfo : initialTimes){
            elementInfos = elementInfoDao.findByInitialTime(elementInfo.getInitialTime());
            if (elementInfos.size() == 7){
                break;
            }
        }

        return elementInfos;
    }
}
