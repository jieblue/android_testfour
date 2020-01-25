package com.example.test_four;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class HubActivity extends AppCompatActivity {
    static private RepoDBHelper helper;
    private String rurl;
    private ImageButton button;
    private RecyclerView recyclerView;
    private boolean done=false;
    private Handler handler;
    private ArrayList<Repositories> repositories;
 //   private JSONObject jsonObject;
    private JSONArray jsonArray;
    private Repoadapter repoadapter;
    private  Repositories tt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        helper=new RepoDBHelper(HubActivity.this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hub);
        final Intent intent=getIntent();
        rurl=intent.getStringExtra("url");
        recyclerView=findViewById(R.id.review);
        button=findViewById(R.id.rback);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tt=new Repositories();
        repositories=new ArrayList<Repositories>();
        repoadapter=new Repoadapter(this,repositories);
        repoadapter.setItemClickListener(new Repoadapter.OnItemClickListener() {
            @Override
            public void OnClick(int Position) {

                helper.insert(repositories.get(Position));
            }
        });
        repoadapter.setWebClickListener(new Repoadapter.WebClickListener() {
            @Override
            public void WebClick(int position) {
                String address=repositories.get(position).getUrl();
                Uri uri=Uri.parse(address);
                Intent webintenent=new Intent(Intent.ACTION_VIEW,uri);
                startActivity(webintenent);
            }
        });

                // repoadapter.
        recyclerView.setLayoutManager(new LinearLayoutManager(HubActivity.this));

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection=null;
                StringBuffer buffer=new StringBuffer();
                try {
                    URL myurl=new URL(rurl);
                    System.out.println(rurl);
                    connection=(HttpURLConnection)myurl.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream is=connection.getInputStream();
                    BufferedReader br=new BufferedReader(new InputStreamReader(is,"utf-8"));
                    String line="";
                    while ((line=br.readLine())!=null)
                    {
                        buffer.append(line);
                    }
                    br.close();
                    String fstr=buffer.toString();
                    jsonArray=new JSONArray(fstr);
                    myadd();
                    // System.out.println(ans);

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
                finally{
                    connection.disconnect();
                }
            }
        }).start();
        handler=new Handler()
        {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what==1)
                {
                    Repositories tmp=(Repositories)msg.obj;
                    repositories.add(tmp);

                    recyclerView.setAdapter(repoadapter);//适配器扔进去

                }
            }
        };

    }

    @Override
    protected void onResume() {

        super.onResume();
        helper.openWriteLink();
    }
    @Override
    protected void onPause() {

        super.onPause();
        helper.closeLink();
    }
    public void myadd()
    {
        try {
            for (int i=0;i<jsonArray.length();i++)
            {
                Repositories tmp = new Repositories();
                JSONObject object=(JSONObject) jsonArray.get(i);
                tmp.setForks(object.getInt("forks"));
                tmp.setLanguage(object.getString("language"));
                tmp.setOwner(object.getJSONObject("owner").getString("login"));
                tmp.setStarts(object.getInt("stargazers_count"));
                tmp.setName(object.getString("name"));
                tmp.setUrl(object.getString("html_url"));
                Message message=new Message();
                message.what=1;
                message.obj=tmp;
                handler.sendMessage(message);

                //tt=tmp;

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
