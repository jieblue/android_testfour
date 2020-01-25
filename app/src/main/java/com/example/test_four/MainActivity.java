package com.example.test_four;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    private String string=null;
    private ImageButton sbutton;
    private EditText editText;
    private Bitmap bitmap;
    private  String ans;
    private ImageButton bhub;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sbutton=findViewById(R.id.mbutton);

        editText=findViewById(R.id.medittext);

        sbutton.setOnClickListener(new mainclick());
        bhub=findViewById(R.id.mhub);
        bhub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, LocalActivity.class);
                startActivity(intent);
            }
        });

    }
    public class mainclick implements View.OnClickListener
    {

        @Override
        public void onClick(View v) {
            if (v.getId()==R.id.mbutton)
            {   String next="";
                string=editText.getText().toString();

                if (!string.equals("")) {

                  final String  ustr="https://api.github.com/search/users?q="+string;
                    Intent intent=new Intent(MainActivity.this, ResultActivity.class);
                    intent.putExtra("url",ustr);

                    startActivity(intent);
                }

            }
        }
    }

}
