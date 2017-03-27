package com.lmhu.firstcodebooksource.chapter10.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
       Log.e("hook_MyIntentSer","Thread id is "+Thread.currentThread());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("hook_MyIntentSer", "onDestroy executed");
    }
}
