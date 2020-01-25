package com.example.test_four;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Repoadapter extends RecyclerView.Adapter {
    private ArrayList<Repositories> repositories;
    private Context context;
    private OnItemClickListener itemClickListener;
    private String btype;
    public WebClickListener webClickListener;
    public Repoadapter(@NonNull Context context1, ArrayList<Repositories> arrayList) {
        context=context1;
        repositories=arrayList;
        btype="收藏";
    }
    public Repoadapter(@NonNull Context context1, ArrayList<Repositories> arrayList,String string) {
        context=context1;
        repositories=arrayList;
        btype="取消收藏";
    }

    public ArrayList<Repositories> getRepositories() {
        return repositories;
    }

    public interface WebClickListener
    {
        void WebClick(int position);
    }
    public interface OnItemClickListener
    {
        void OnClick(int Position);//按接口
    }

    public void setWebClickListener(WebClickListener webClickListener) {
        this.webClickListener = webClickListener;
    }

    public void setItemClickListener(OnItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.repolayout,parent,false);
        return new Repohoder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final Repohoder repohoder=(Repohoder) holder;
        repohoder.tname.setText(repositories.get(position).getName());
        repohoder.tonwer.setText(repositories.get(position).getOwner());
        repohoder.tfork.setText(String.valueOf(repositories.get(position).getForks()));
        repohoder.tstar.setText(String.valueOf(repositories.get(position).getStarts()));
        repohoder.tlanguage.setText(repositories.get(position).getLanguage());
        repohoder.button.setText(btype);
        if (itemClickListener != null)
        {
            repohoder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    itemClickListener.OnClick(position);
                }
            });
        }
        if (webClickListener!=null)
        {
            repohoder.tie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    webClickListener.WebClick(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return repositories.size();
    }
    class Repohoder extends RecyclerView.ViewHolder
    {
        public  TextView tname;
        public  TextView tonwer;
        public TextView tlanguage;
        public  TextView tfork;
        public TextView tstar;
        public Button button;
        public Button tie;
        public Repohoder(@NonNull View itemView) {
            super(itemView);
            tname=itemView.findViewById(R.id.hname);
            tfork=itemView.findViewById(R.id.hfork);
            tlanguage=itemView.findViewById(R.id.hlanguage);
            tstar=itemView.findViewById(R.id.hstart);
            tonwer=itemView.findViewById(R.id.hower);
            button=itemView.findViewById(R.id.hbutton);
            tie=itemView.findViewById(R.id.hie);
        }
    }
}
