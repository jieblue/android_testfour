package com.example.test_four;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Useradapter extends RecyclerView.Adapter {

    private ArrayList<User> users;
    private Context context;
    private OnItemClickListener itemClickListener;
    public Useradapter(@NonNull Context context1, ArrayList<User> arrayList) {
        context=context1;
        users=arrayList;
    }

    public void setItemClickListener(OnItemClickListener onItemClickListener)//设置长按接口
    {
        this.itemClickListener=onItemClickListener;
    }
    public interface OnItemClickListener
    {
        void onClick(int Position);//按接口

    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.myitem,parent,false);
        return new UserHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        UserHolder userHolder=(UserHolder)holder;
        userHolder.textView.setText(users.get(position).getName());
        try {if (users.get(position).getBitmap()!=null)

            userHolder.imageView.setImageBitmap(users.get(position).getBitmap());
        }
       catch (Exception e)
       {
           e.printStackTrace();
       }
        if (itemClickListener!=null)
        {
            userHolder.textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(position);
                }

            });
            userHolder.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onClick(position);
                }

            });
        }
    }

    @Override
    public int getItemCount() {
        return users.size();
    }
    class UserHolder extends RecyclerView.ViewHolder
    {
        public ImageView imageView;
        public TextView textView;
        public UserHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iicon);
            textView=itemView.findViewById(R.id.iname);

        }
    }
}
