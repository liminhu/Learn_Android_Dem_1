package com.lmhu.firstcodebooksource.chapter6.broadcastbestpractice.filepersistence;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lmhu.firstcodebooksource.R;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class File_MainActivity extends AppCompatActivity {
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file__main);
        edit=(EditText)findViewById(R.id.edit_File);
        String inputText=loadReadFile();
        if(!TextUtils.isEmpty(inputText)){
            edit.setText(inputText);
            edit.setSelection(inputText.length());
            Toast.makeText(this,"Restoring succeeded", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this,"file is null", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("hook_onDestroy", "test save file");
        String inputText=testGSONData();
        saveDataToFile(inputText);
    }



    public String loadReadFile(){
        FileInputStream in=null;
        BufferedReader reader=null;
        StringBuilder sb=new StringBuilder();
        try{
            in=openFileInput("data");
            //Log.d("hook_read",in.g)
            reader=new BufferedReader(new InputStreamReader(in));
            String line="";
            while ((line=reader.readLine())!=null){
                sb.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(reader!=null){
                try{
                    reader.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    public void saveDataToFile(String data){
        FileOutputStream out=null;
        BufferedWriter writer=null;
        try{
            out=openFileOutput("data", Context.MODE_PRIVATE);
            writer=new BufferedWriter(new OutputStreamWriter(out));
            writer.write(data);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(writer!=null){
                    writer.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    private String testGSONData(){
        StudentBean stu1=new StudentBean("01","lmhu","男", 20);
        StudentBean stu2=new StudentBean("02","sw","女", 22);
        List<StudentBean> list=new ArrayList<StudentBean>();
        list.add(stu1);
        list.add(stu2);
        Gson gson=new Gson();
        String str=gson.toJson(list);
        Log.e("hook_gson",str);


        String data="[{\"sno\":\"01\",\"name\":\"lmhu\",\"sex\":\"男\",\"age\":20},{\"sno\":\"02\",\"name\":\"sw\",\"sex\":\"女\",\"age\":22}]";
        List<StudentBean> list1=gson.fromJson(data, new TypeToken<List<StudentBean>>(){}.getType());
        for(int i=0; i<list1.size(); i++){
            StudentBean stu=list1.get(i);
            Log.e("hook_stu"+i, "sno:"+stu.getSno()+"\tname:"+stu.getName());
        }
        return str;
    }
}
