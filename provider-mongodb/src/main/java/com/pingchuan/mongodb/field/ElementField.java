package com.pingchuan.mongodb.field;

import org.springframework.data.mongodb.core.aggregation.Fields;

public class ElementField {

    public static Fields elementInfoFields = Fields.fields("initial_time", "element_code", "mode_code", "org_code", "trapezoid_info_id", "coordinate", "forecast_interval", "forecast_level");

    public static Fields realInfoFields = Fields.fields("real_info.real_time", "real_info.update_time", "real_info.element_code", "real_info.trapezoid_info_id");

    public static Fields trapezoidFields = Fields.fields("initial_time", "element_code", "mode_code", "org_code", "coordinate", "forecast_interval", "forecast_level", "element_info_id", "trapezoid.grid_code", "trapezoid.loc");

    public static Fields forecastInfoFields = Fields.fields("initial_time", "element_code", "mode_code", "org_code", "coordinate", "forecast_interval", "forecast_level", "loc", "forecast_info.forecast_time", "forecast_info.time_effect");

    public static Fields elementValueFields = Fields.fields("initial_time", "element_code", "mode_code", "org_code", "coordinate", "forecast_interval", "forecast_level", "loc", "forecast_time", "time_effect", "element_value.value", "element_value.u_value", "element_value.v_value");
}
