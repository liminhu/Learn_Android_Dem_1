package com.lmhu.mylearncoolweather.gson;

import android.os.NetworkOnMainThreadException;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by hulimin on 2017/8/27.
 */

public class Weather {
    public String status;
    public Basic basic;
    public AQI aqi;
    public Now now;
    public Suggestion suggestion;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;
}
