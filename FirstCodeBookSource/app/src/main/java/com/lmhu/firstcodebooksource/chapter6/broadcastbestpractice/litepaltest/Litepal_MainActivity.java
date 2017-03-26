package com.lmhu.firstcodebooksource.chapter6.broadcastbestpractice.litepaltest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lmhu.firstcodebooksource.R;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

public class Litepal_MainActivity extends AppCompatActivity {
    private Button createData;
    private Button updateData;
    private Button addData;
    private Button deleteData;
    private Button queryData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litepal__main);
        createData=(Button)findViewById(R.id.create_database);
        createData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Connector.getDatabase();
                Log.e("hook","creatre database");
            }
        });

        addData=(Button)findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Book book=new Book();
                book.setName("The this first Code");
                book.setAuthor("Dan Brown");
                book.setPages(454);
                book.setPrice(16.96);
                book.setPress("Unknows");
                book.save();
                Log.e("hook","add data");
            }
        });

        updateData=(Button)findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Book book=new Book();
                book.setPrice(14.86);
                book.setPress("Anchor");
                book.updateAll("author = ?", "Dan Brown");
                Log.e("hook","update data");
            }
        });

        deleteData=(Button)findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataSupport.deleteAll(Book.class, "price < ?","15");
            }
        });
        queryData=(Button)findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> books=DataSupport.findAll(Book.class);
                int i=0;
                for(Book book : books){
                    Log.d("hook_MainActivity", "book name is " + book.getName());
                    Log.d("hook_MainActivity", "book author is " + book.getAuthor());
                    Log.d("hook_MainActivity", "book pages is " + book.getPages());
                    Log.d("hook_MainActivity", "book price is " + book.getPrice());
                    Log.d("hook_MainActivity", "book press is " + book.getPress());
                }
            }
        });


    }
}
