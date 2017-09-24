package com.lmhu.mylearncoolweather.gson;

/**
 * Created by hulimin on 2017/8/27.
 */

public class AQI {
    public AQICity city;

    public class AQICity{
        public String aqi;
        public String pm25;
    }

}
