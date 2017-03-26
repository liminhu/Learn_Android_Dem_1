package com.lmhu.firstcodebooksource.chapter9.network.test;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.Inet4Address;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.internal.tls.OkHostnameVerifier;

/**
 * Created by hulimin on 2017/3/20.
 */

public class HttpUtils {
    public static void sendHttpRequest(final String address, final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                BufferedReader reader=null;
                try{
                  //  Log.e("hook_sendHttpRequest", address);
                    URL url=new URL(address);
                    connection=(HttpURLConnection)url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    //如果打算使用 URL 连接进行输入，则将 DoInput 标志设置为 true；
                    connection.setDoInput(true);
                    connection.setDoOutput(true);

                    if(connection.getResponseCode()==200){
                        InputStream in=connection.getInputStream();
                        reader=new BufferedReader(new InputStreamReader(in));
                        StringBuilder response=new StringBuilder();
                        String line;
                 //       Log.d("hook_sendHttpRequest", "200");
                        while ((line=reader.readLine())!=null){
                      //      Log.e("hook_line", line+"\t"+line.length());
                            response.append(line);
                        }
                        if(listener!=null){
                         //   Log.e("hook_Http_res", response.toString());
                            listener.onFinish(response.toString());
                        }
                    }
                 //   Log.d("hook_getResponseCode", connection.getResponseCode()+"");
                }catch (Exception e){
                    Log.e("hook_HttpException",e.getMessage());
                    if(listener!=null){
                        listener.onError(e);
                    }
                }finally {
                 //   Log.e("hook_sendHttpRequest", "finall");
                    if(reader !=null){
                        try {
                            reader.close();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

    public static void sendOkHttpRequest(final String address, final okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

}
