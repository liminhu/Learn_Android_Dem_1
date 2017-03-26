package com.lmhu.firstcodebooksource.chapter7.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.lmhu.firstcodebooksource.R;

public class DataBaseProviderMainActivity extends AppCompatActivity {
    private String newId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_litepal__main);
        Button addData=(Button)findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("content://"+DatabaseProvider.AUTHORITY+"/book");
                ContentValues values=new ContentValues();
                values.put("name", "A Clash of Kings");
                values.put("author", "George Martin");
                values.put("pages", 1040);
                values.put("price", 55.53);
                Uri newUri=getContentResolver().insert(uri,values);
                newId=newUri.getPathSegments().get(1);
                Log.e("hook_add","newId: "+newId);
            }
        });


        Button queryData=(Button)findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://" + DatabaseProvider.AUTHORITY + "/book");
                Cursor cursor = getContentResolver().query(uri, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));

                        Log.d("hook_query_name", "Book name is " + name);
                        Log.d("hook_query_author", "Book author is " + author);
                        Log.e("hook_query_price", "Book price is " + price);
                        Log.e("hook_query_pages", "Book pages is " + pages);
                    }
                    cursor.close();
                }
            }
        });

        Button updateData=(Button)findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri=Uri.parse("content://"+DatabaseProvider.AUTHORITY+"/book/"+newId);
                ContentValues values=new ContentValues();
                values.put("name", "A Storm of Swords");
                values.put("pages", 1216);
                values.put("price", 24.05);
                getContentResolver().update(uri,values,null,null);
            }
        });


        Button deleteData=(Button)findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("hook_delete", ""+newId);
                Uri uri=Uri.parse("content://"+DatabaseProvider.AUTHORITY+"/book/"+newId);
                getContentResolver().delete(uri,null,null);
            }
        });
    }
}
