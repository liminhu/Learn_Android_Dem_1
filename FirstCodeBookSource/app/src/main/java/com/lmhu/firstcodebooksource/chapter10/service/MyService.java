package com.lmhu.firstcodebooksource.chapter10.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.IntDef;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.lmhu.firstcodebooksource.R;

public class MyService extends Service {
    private static  final String TAG="hook_";

    private DownloadBinder mBinder=new DownloadBinder();
    public MyService() {
    }

    class DownloadBinder extends Binder{
        public void startDownload(){
            Log.d(TAG+"MyService", "startDownload executed");
        }

        public int getProgress(){
            Log.d(TAG+"MyService","getProgress executed");
            return 0;
        }
    }



    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG+"MyService", "Oncreate executed");
        Intent intent=new Intent(this, ServiceMainActivity.class);
        //pendingIntent是一种特殊的Intent。主要的区别在于Intent的执行立刻的，而pendingIntent的执行不是立刻的。pendingIntent执行的操作实质上是参数传进来的Intent的操作，但是使用pendingIntent的目的在于它所包含的Intent的操作的执行是需要满足某些条件的。
        PendingIntent pi=PendingIntent.getActivity(this, 0, intent, 0);
        Notification notification=new NotificationCompat.Builder(this)
                .setContentTitle("This is content title")
                .setContentText("This is content text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(
                        getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        startForeground(1,notification);
    }


    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        Log.d(TAG+"MyService", "onStartCommand executed");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG+"MyService", "onDestory executed");
    }
}
