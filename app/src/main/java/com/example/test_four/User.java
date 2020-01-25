package com.example.test_four;

import android.graphics.Bitmap;
import android.media.Image;

import java.sql.Date;

public class User {
    private String name;
   Bitmap bitmap;
    private String homeurl;

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public String getName() {
        return name;
    }

    public void setHomeurl(String homeurl) {
        this.homeurl = homeurl;
    }

    public String getHomeurl() {
        return homeurl;
    }

    public void setName(String name) {
        this.name = name;
    }


}
