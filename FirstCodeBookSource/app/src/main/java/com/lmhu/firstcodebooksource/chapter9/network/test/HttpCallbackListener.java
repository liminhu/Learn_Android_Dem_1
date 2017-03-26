package com.lmhu.firstcodebooksource.chapter9.network.test;

/**
 * Created by hulimin on 2017/3/25.
 */

public interface HttpCallbackListener {
    void onFinish(String response);
    void onError(Exception e);
}
