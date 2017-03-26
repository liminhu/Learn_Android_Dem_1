package com.lmhu.firstcodebooksource.chapter9.network.test;

import android.util.Log;

import com.google.gson.Gson;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hulimin on 2017/3/25.
 */

public class ContentHandler extends DefaultHandler {
    private String nodeName;
    private StringBuilder id;
    private StringBuilder name;
    private StringBuilder version;

    List<App> list=new ArrayList<App>();

    @Override
    public void startDocument() throws SAXException {
        id=new StringBuilder();
        name=new StringBuilder();
        version=new StringBuilder();
    }


    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //记录当前结点名
        nodeName=localName;
    }


    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if("id".equals(nodeName)){
            id.append(ch,start,length);
        }else if("name".equals(nodeName)){
            name.append(ch,start,length);
        }else if("version".equals(nodeName)){
            version.append(ch,start, length);
        }
    }


    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if("app".equals(localName)){
            Log.d("hook_ContentHandler", "id is " + id.toString().trim());
            Log.d("hook_ContentHandler", "name is " + name.toString().trim());
            Log.d("hook_ContentHandler", "version is " + version.toString().trim());
            // 最后要将StringBuilder清空掉
            App app=new App();
            app.setId(id.toString().trim());
            app.setName(name.toString().trim());
            app.setVersion(version.toString().trim());
            list.add(app);

            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
        }
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }


    public void printfList(){
        Gson gson=new Gson();
        String str=gson.toJson(list);
        Log.e("hook_gson",str);
    }


}
