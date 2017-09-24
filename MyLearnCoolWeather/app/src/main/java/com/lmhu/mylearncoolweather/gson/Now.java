package com.lmhu.mylearncoolweather.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by hulimin on 2017/8/27.
 */

public class Now{
    @SerializedName("tmp")
    public String temperature;

    @SerializedName("cond")
    public More more;

    public class More{
        @SerializedName("txt")
        public String info;
    }
}
