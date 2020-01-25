package com.example.test_four;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DetailActivity extends AppCompatActivity {
    private String rurl;
    private ImageButton button;
    private Button brepo;
    private TextView tname;
    private TextView tword;
    private  TextView ttime;
    private TextView tfollowing;
    private TextView tfollowed;
    private JSONObject jsonObject;
    private Handler handler;
    private ImageView imageView;
    private String hurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        tname=findViewById(R.id.uname);
        tfollowed=findViewById(R.id.ufd);
        imageView=findViewById(R.id.uimg);
        tfollowing=findViewById(R.id.ufi);
        button=findViewById(R.id.uback);
        ttime=findViewById(R.id.utime);
        tword=findViewById(R.id.uword);
        brepo=findViewById(R.id.uhub);
        Bundle bundle=intent.getExtras();
        hurl=bundle.getString("url");
        byte[ ] bytes=bundle.getByteArray("img");
        Bitmap bitmap=null;
        bitmap= BitmapFactory.decodeByteArray(bytes,0,bytes.length);
        imageView.setImageBitmap(bitmap);
        button.setOnClickListener(new dclicl());
        brepo.setOnClickListener(new dclicl());
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                StringBuffer buffer = new StringBuffer();
                try {
                    URL myurl = new URL(hurl);
                    connection = (HttpURLConnection) myurl.openConnection();
                    connection.setRequestMethod("GET");
                    InputStream is = connection.getInputStream();
                    BufferedReader br = new BufferedReader(new InputStreamReader(is, "utf-8"));
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        buffer.append(line);
                    }
                    br.close();
                    String fstr = buffer.toString();
                    jsonObject = new JSONObject(fstr);
                    rurl=jsonObject.getString("repos_url");
                    tword.post (new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String text=jsonObject.getString("bio");
                                if (text.equals("null"))
                                    tword.setText("bio : 该用户暂无签名信息");
                                else {
                                    tword.setText("bio : " + text);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                     tfollowed.post (new Runnable() {
                         @Override
                         public void run() {
                             try {
                                 tfollowed.setText("followers : "+jsonObject.getString("followers"));
                             } catch (JSONException e) {
                                 e.printStackTrace();
                             }

                         }
                     });
                    tfollowing.post (new Runnable() {
                        @Override
                        public void run() {
                            try {
                                tfollowing.setText("following : "+jsonObject.getString("following"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    ttime.post (new Runnable() {
                        @Override
                        public void run() {
                            try {
                                ttime.setText("created_at : "+jsonObject.getString("created_at"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });
                    tname.post (new Runnable() {
                        @Override
                        public void run() {
                            try {
                                tname.setText("name : "+jsonObject.getString("login"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    });


//
//
//

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    connection.disconnect();
                }
            }
        }).start();




    }
    public class dclicl implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.uback)
            {
                finish();
            }
            else if (v.getId()==R.id.uhub)
            {
                Intent tintent=new Intent(DetailActivity.this,HubActivity.class);
                tintent.putExtra("url",rurl);
                startActivity(tintent);
            }
        }
    }
}
