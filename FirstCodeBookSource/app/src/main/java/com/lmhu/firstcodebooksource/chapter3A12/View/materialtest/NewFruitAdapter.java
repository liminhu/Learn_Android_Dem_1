package com.lmhu.firstcodebooksource.chapter3A12.View.materialtest;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lmhu.firstcodebooksource.R;

import java.util.List;

/**
 * Created by hulimin on 2017/4/4.
 */

public class NewFruitAdapter extends RecyclerView.Adapter<NewFruitAdapter.ViewHolder> {
    private static final String TAG="hook_fruitAdapter";
    private Context mContext;
    private List<Fruit> mFruitList;

    public NewFruitAdapter(List<Fruit> mFruitList) {
        this.mFruitList = mFruitList;
    }

    public List<Fruit> getmFruitList() {
        return mFruitList;
    }

    public void setmFruitList(List<Fruit> mFruitList) {
        this.mFruitList = mFruitList;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
        ImageView fruitImage;
        TextView fruitName;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView;
            fruitImage=(ImageView)itemView.findViewById(R.id.chapter12_fruit_image);
            fruitName=(TextView)itemView.findViewById(R.id.chapter12_fruit_name);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(mContext==null){
            mContext=parent.getContext();
        }
        View view= LayoutInflater.from(mContext).inflate(R.layout.chapter12_fruit_item,parent,false);
        final ViewHolder holder=new ViewHolder(view);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=holder.getAdapterPosition();
                Fruit fruit=mFruitList.get(position);
                Intent intent=new Intent(mContext, NewFruitActivity.class);
                intent.putExtra(NewFruitActivity.FRUIT_NAME,fruit.getName());
                intent.putExtra(NewFruitActivity.FRUIT_IMAGE_ID, fruit.getImageId());
                mContext.startActivity(intent);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Fruit fruit=mFruitList.get(position);
        holder.fruitName.setText(fruit.getName());
        Glide.with(mContext).load(fruit.getImageId()).into(holder.fruitImage);
    }

    @Override
    public int getItemCount() {
        return mFruitList.size();
    }
}
