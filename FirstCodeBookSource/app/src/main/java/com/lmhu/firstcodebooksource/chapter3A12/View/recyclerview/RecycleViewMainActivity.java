package com.lmhu.firstcodebooksource.chapter3A12.View.recyclerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

import com.lmhu.firstcodebooksource.R;
import com.lmhu.firstcodebooksource.chapter3A12.View.materialtest.Fruit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RecycleViewMainActivity extends AppCompatActivity {
    private List<Fruit> fruitList=new ArrayList<Fruit>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chapter3_main);
        initFruits();
        Log.e("hook_data", "111");
        RecyclerView recycFruitView=(RecyclerView)findViewById(R.id.chapter3_recycler_view);

        //交错网格布局，类似于网格布局，但每个格子的高度或者长度可以不一样,俗称的瀑布流效果
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recycFruitView.setLayoutManager(layoutManager);
        RecycFruitAdapter adapter=new RecycFruitAdapter(fruitList);
        Log.e("hook_data", "222");
        recycFruitView.setAdapter(adapter);
    }

    private void initFruits(){
        for(int i=0; i<2; i++){
            Fruit apple=new Fruit(getRandomLengthName("Apple"), R.drawable.apple_pic);
            fruitList.add(apple);
            Fruit banana = new Fruit(getRandomLengthName("Banana"), R.drawable.banana_pic);
            fruitList.add(banana);
            Fruit orange = new Fruit(getRandomLengthName("Orange"), R.drawable.orange_pic);
            fruitList.add(orange);
            Fruit watermelon = new Fruit(getRandomLengthName("Watermelon"), R.drawable.watermelon_pic);
            fruitList.add(watermelon);
            Fruit pear = new Fruit(getRandomLengthName("Pear"), R.drawable.pear_pic);
            fruitList.add(pear);
            Fruit grape = new Fruit(getRandomLengthName("Grape"), R.drawable.grape_pic);
            fruitList.add(grape);
            Fruit pineapple = new Fruit(getRandomLengthName("Pineapple"), R.drawable.pineapple_pic);
            fruitList.add(pineapple);
            Fruit strawberry = new Fruit(getRandomLengthName("Strawberry"), R.drawable.strawberry_pic);
            fruitList.add(strawberry);
            Fruit cherry = new Fruit(getRandomLengthName("Cherry"), R.drawable.cherry_pic);
            fruitList.add(cherry);
            Fruit mango = new Fruit(getRandomLengthName("Mango"), R.drawable.mango_pic);
            fruitList.add(mango);
        }
    }


    private String getRandomLengthName(String name){
        Random random=new Random();
        int length=random.nextInt(20)+1;
        StringBuilder builder=new StringBuilder();
        for(int i=0; i<length; i++){
            builder.append(name+":i"+i);
        }
        Log.d("hook_name", builder.toString());
        return builder.toString();
    }

}
