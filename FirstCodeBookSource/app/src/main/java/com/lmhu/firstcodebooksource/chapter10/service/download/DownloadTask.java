package com.lmhu.firstcodebooksource.chapter10.service.download;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by hulimin on 2017/3/27.
 */

public class DownloadTask extends AsyncTask<String,Intent, Intent>{
    public static final int TYPE_SUCCESS=0;
    public static final int TYPE_FAILED=1;
    public static final int TYPE_PAUSED=2;
    public static final int TYPE_CANCELED=3;

    private DownloadListener listener;
    private boolean isCanceled=false;
    private boolean isPaused=false;
    private int lastProgress;

    public DownloadTask(DownloadListener listener) {
        this.listener = listener;
    }

    @Override
    protected Intent doInBackground(String... params) {
        InputStream is=null;
        RandomAccessFile savedFile=null;
        Intent intent=new Intent();
        File file=null;
        try{
            long downloadedLength=0;
            String downloadUrl=params[0];
            String fileName=downloadUrl.substring(downloadUrl.lastIndexOf("/"));
            String directory= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            file=new File(directory+fileName);
            Log.d("hook_file_path",file.getPath());
            if(file.exists()){
                downloadedLength=file.length();
            }
            long contentLength=getContentLength(downloadUrl);
            if(contentLength == 0){
                intent.putExtra("status",TYPE_FAILED);
                return intent;
            }else if(contentLength == downloadedLength){
                intent.putExtra("status", TYPE_SUCCESS);
                return intent;
            }
            OkHttpClient client=new OkHttpClient();
            Request request=new Request.Builder()
                    .addHeader("RANGE", "bytes="+downloadedLength+"-")
                    .url(downloadUrl).build();
            Response response=client.newCall(request).execute();
            if(response != null){
                is=response.body().byteStream();
                savedFile=new RandomAccessFile(file, "rw");
                savedFile.seek(downloadedLength);  //跳过已下载的字节
                byte[] b=new byte[1024];
                int total=0;
                int len;
                while ((len=is.read(b)) != -1){
                    if(isCanceled){
                        intent.putExtra("status", TYPE_CANCELED);
                        return intent;
                    }else  if(isPaused){
                        intent.putExtra("status", TYPE_PAUSED);
                        return intent;
                    }else{
                        total+=len;
                        savedFile.write(b, 0, len);
                        int progress=(int)((total+downloadedLength) * 100 / contentLength);
                        intent.putExtra("progress",progress);
                        publishProgress(intent);
                    }
                }
                response.body().close();
                intent.putExtra("status", TYPE_SUCCESS);
                return intent;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(is!=null){
                    is.close();
                }
                if(savedFile != null){
                    savedFile.close();
                }
                if(isCanceled && file!=null){
                    file.delete();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        intent.putExtra("status", TYPE_FAILED);
        return intent;
    }

    @Override
    protected void onProgressUpdate(Intent... values) {
        Intent progressIntent=values[0];
        int progress=progressIntent.getIntExtra("progress", -1);
        if(progress > lastProgress){
            listener.onProgress(progress);
            lastProgress=progress;
        }
    }

    //当后台操作结束时，此方法将会被调用，计算结果将做为参数传递到此方法中，直接将结果显示到UI组件上。
    @Override
    protected void onPostExecute(Intent intent) {
        int status=intent.getIntExtra("status", -1);
       switch (status){
           case TYPE_SUCCESS:
               listener.onSuccess();
               break;
           case TYPE_FAILED:
               listener.onFailed();
               break;
           case TYPE_PAUSED:
               listener.onPaused();
               break;
           case TYPE_CANCELED:
               listener.onCanceled();
               break;
       }
    }

    public void pauseDownload(){
        isPaused=true;
    }

    public void cancelDownload(){
        isCanceled=true;
    }


    private long getContentLength(String downloadUrl)throws IOException{
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder()
                .url(downloadUrl).build();
        Response response=client.newCall(request).execute();
        if(response !=null && response.isSuccessful()){
            long contentLength=response.body().contentLength();
            response.close();
            return contentLength;
        }
        return 0;
    }


}
