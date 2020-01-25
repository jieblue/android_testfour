package com.example.test_four;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.UrlQuerySanitizer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;

public class ResultActivity extends AppCompatActivity {
   private  String ans;
   private Handler handler;
   private String iurl;
    private String res = null;
    private ImageButton rbutton;
    private RecyclerView recyclerView;
    private Useradapter adapter;
    private ArrayList<User> Data;
    private boolean done=false;
    private String purl;
    private Bitmap bitmap;
    public ResultActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        rbutton = findViewById(R.id.rback);
        recyclerView=findViewById(R.id.rview);
        Data=new ArrayList<User>();
        recyclerView.setAdapter(adapter);
        adapter=new Useradapter(this,Data);
       // recyclerView.setAdapter(adapter);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ResultActivity.this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter.setItemClickListener(new Useradapter.OnItemClickListener() {
            @Override
            public void onClick(int Position) {
                Intent tintent=new Intent(ResultActivity.this,DetailActivity.class);
                Bundle bundle=new Bundle();
                Bitmap bitmap=Data.get(Position).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[ ] bytes=baos.toByteArray();
                bundle.putString("url",Data.get(Position).getHomeurl());
                bundle.putByteArray("img",bytes);
                System.out.println(bytes);
                tintent.putExtras(bundle);
                startActivity(tintent);

            }

        });

        rbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        iurl = intent.getStringExtra("url");

        final  String turl=iurl;
      new Thread(new Runnable() {
          @Override
          public void run() {
              getinfo(iurl);
          }
      }).start();
        handler=new Handler()
        {
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                if (msg.what==1)
                {
                   User tt=(User)msg.obj;
                   Data.add(tt);
                    recyclerView.setAdapter(adapter);



                }
            }
        };
    }
    public void getinfo(final String url)
    {

        // String fstr="";


                //  System.out.println("here");
                HttpURLConnection connection=null;
                StringBuffer buffer=new StringBuffer();
                try {
                    URL myurl=new URL(url);

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
                    ans=fstr;
                    myanalyse(ans);

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
    public void myanalyse(String str)
    {

        try {
            JSONObject jsonObject=new JSONObject(str);
           // int num=jsonObject.getInt("total_count");

            JSONArray jsonArray=jsonObject.getJSONArray("items");
            for (int i=0;i<jsonArray.length();i++)
            {
                JSONObject tmp= (JSONObject) jsonArray.get(i);

                User user=new User();
                user.setName(tmp.getString("login"));
                user.setHomeurl(tmp.getString("url"));
                String string=tmp.getString("avatar_url");
                Bitmap bitmap=mytest(string);
                user.setBitmap(bitmap);
                Message message=new Message();
                message.what=1;
                message.obj=user;
                handler.sendMessage(message);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }
    public Bitmap mytest(String string)throws Exception
    {
        Bitmap bitmap=null;

            URL url=new URL(string);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream=connection.getInputStream();

            bitmap= BitmapFactory.decodeStream(inputStream);

            inputStream.close();



        return bitmap;
    }
}
