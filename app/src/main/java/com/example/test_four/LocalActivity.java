package com.example.test_four;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class LocalActivity extends AppCompatActivity {
    private RepoDBHelper helper;
    private Repoadapter repoadapter;
    private ArrayList<Repositories> arrayList;
    private RecyclerView recyclerView;
    private ImageButton button;
    private ImageButton bclear;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);
        recyclerView=findViewById(R.id.lview);
        button=findViewById(R.id.lback);
        bclear=findViewById(R.id.lclear);
        bclear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.clear();
                arrayList.clear();
                repoadapter.notifyDataSetChanged();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        helper=new RepoDBHelper(LocalActivity.this);
        helper.openReadLink();
        arrayList=helper.getall();
        repoadapter=new Repoadapter(this,arrayList,"取消收藏");

        recyclerView.setAdapter(repoadapter);//适配器扔进去
        repoadapter.setItemClickListener(new Repoadapter.OnItemClickListener() {
            @Override
            public void OnClick(int Position) {
                 helper.remove(arrayList.get(Position));
                 arrayList.remove(Position);
                 repoadapter.notifyDataSetChanged();
            }
        });
        repoadapter.setWebClickListener(new Repoadapter.WebClickListener() {
            @Override
            public void WebClick(int position) {
                String address=arrayList.get(position).getUrl();
                Uri uri=Uri.parse(address);
                Intent webintenent=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(webintenent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(LocalActivity.this));

        recyclerView.setItemAnimator(new DefaultItemAnimator());


    }
    @Override
    protected void onResume() {

        super.onResume();
        helper.openReadLink();
    }
    @Override
    protected void onPause() {

        super.onPause();
        helper.closeLink();
    }
}
