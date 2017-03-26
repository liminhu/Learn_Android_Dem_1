package com.lmhu.firstcodebooksource.chapter9.network.test;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lmhu.firstcodebooksource.R;

import org.xml.sax.InputSource;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;


public class HttpMainActivity extends AppCompatActivity implements View.OnClickListener{
    TextView responseText;
    MyHttpCallBack callBack=null;
    MyHttpCallBack1 myCallBack=null;
    static final String MY_ADDRESS="http://192.168.0.105:8080/AdServerSSH/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        callBack=new MyHttpCallBack();
        myCallBack=new MyHttpCallBack1();
        setContentView(R.layout.activity_http_main);
        Button sendRequest=(Button)findViewById(R.id.send_request);
        responseText=(TextView)findViewById(R.id.response_text);
        sendRequest.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
         if(v.getId() == R.id.send_request){
             sendRequestWithHttpURlConnection();
         }
    }

    private void sendRequestWithHttpURlConnection(){
        Log.i("hook_data", "request");
        String address="http://www.baidu.com";
        // HttpUtils.sendHttpRequest(address, callBack);
        //HttpUtils.sendOkHttpRequest(address,myHttpCallBack1);  //自动帮处理302跳转
        sendRequestWithOkHttp();
    }


    private void sendRequestWithOkHttp(){
        String jsonFile="/files/get_json";
        String xmlFile="/files/get_data.xml";
        StringBuilder address=new StringBuilder(MY_ADDRESS);
        address.append(xmlFile);
      //  HttpUtils.sendOkHttpRequest(address.toString(), myCallBack);


        StringBuilder address1=new StringBuilder(MY_ADDRESS);
        address1.append(jsonFile);
        HttpUtils.sendHttpRequest(address1.toString(), callBack);
    }







    class MyHttpCallBack implements HttpCallbackListener{
        @Override
        public void onFinish(String response) {
            showResponse(response);
            parseJSONWithGSON(response);
        }

        @Override
        public void onError(Exception e) {
            showResponse(e.getMessage());
        }
    }








    class MyHttpCallBack1 implements okhttp3.Callback{
        @Override
        public void onFailure(Call call, IOException e) {
            showResponse(e.getMessage());
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String data=response.body().string();
            showResponse(data);
            //parseXMLWithPull(data);
            //parseXMLWithSAX(data);
        }
    }

    private void parseXMLWithPull(String xmlData){
        try{
            XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser=factory.newPullParser();
            xmlPullParser.setInput(new StringReader(xmlData));
            int eventType=xmlPullParser.getEventType();
            String id="";
            String name="";
            String version="";
            while(eventType != XmlPullParser.END_DOCUMENT){
                String nodeName=xmlPullParser.getName();
                switch (eventType){
                    case XmlPullParser.START_TAG:{
                        if("id".equals(nodeName)){
                            id=xmlPullParser.nextText();
                        }else if("name".equals(nodeName)){
                            name=xmlPullParser.nextText();
                        }else if("version".equals(nodeName)){
                            version=xmlPullParser.nextText();
                        }
                        break;
                    }
                    //完成解析某个结点
                    case XmlPullParser.END_TAG:{
                        if("app".equals(nodeName)){
                            Log.d("hook_id", "id is "+id);
                            Log.d("hook_name", "name is "+name);
                            Log.d("hook_version", "version is "+version);
                        }
                        break;
                    }
                    default:
                        break;
                }
                eventType=xmlPullParser.next();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseXMLWithSAX(String xmlData){
        try{
            SAXParserFactory factory=SAXParserFactory.newInstance();
            XMLReader xmlReader=factory.newSAXParser().getXMLReader();
            ContentHandler handler=new ContentHandler();
            xmlReader.setContentHandler(handler);
            //开始执行解析
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
            handler.printfList();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseJSONWithGSON(String jsonData){
        Gson gson=new Gson();
        List<App> appList=gson.fromJson(jsonData, new TypeToken<List<App>>(){}.getType());
        for(App app : appList){
            Log.e("hook_json_id", "id is "+app.getId());
            Log.e("hook_json_name", "name is "+app.getName());
            Log.e("hook_json_version", "version is "+app.getVersion());
        }
    }




    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //在这里进行UI操作，将结果显示到界面上
                Log.i("hook_showResponse", "showResponse: "+response.length());
                responseText.setText(response);
            }
       });
    }
}
