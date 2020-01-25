package com.example.test_four;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;

import java.sql.ResultSet;
import java.util.ArrayList;

import javax.xml.transform.Result;

public class RepoDBHelper extends SQLiteOpenHelper {
    private static final String db_name="mydata";
   // private static final int db_version = 1;
   // private static RepoDBHelper helper=null;
    private SQLiteDatabase rdb = null;
    public static final String table_name="repo_table";
    public RepoDBHelper(Context context)
    {
        super(context,db_name,null,1);
    }

    public SQLiteDatabase openReadLink()
    {
        if (rdb==null||!rdb.isOpen())
            rdb=this.getReadableDatabase();
        return rdb;
    }
    public SQLiteDatabase openWriteLink()
    {
        if (rdb==null||!rdb.isOpen())
            rdb=this.getWritableDatabase();
        return rdb;
    }
    public void closeLink()
    {
        if (rdb!=null||rdb.isOpen())
        {
            rdb.close();
            rdb=null;
        }
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String create_sql="create table if not exists "+table_name+" ( _id integer primary key autoincrement not null, reponame varchar, owner varchar, language varchar, forks integer, stars integer,url varchar)";
        db.execSQL(create_sql);
    }
    public int delete(String string)
    {
        return rdb.delete(table_name,string,null);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    public long insert(Repositories repositories)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put("reponame",repositories.getName());
        contentValues.put("owner",repositories.getOwner());
        contentValues.put("language",repositories.getLanguage());
        contentValues.put("forks",repositories.getForks());
        contentValues.put("stars",repositories.getStarts());
        contentValues.put("url",repositories.getUrl());
       long it= rdb.insert(table_name,"",contentValues);
      // System.out.println(it);
       return it;
    }
    public void remove(Repositories repositories)
    {
        String string="delete from "+table_name+" where reponame = '"+repositories.getName()+"' and owner = '"+repositories.getOwner()+"'";
        try {
            rdb.execSQL(string);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
    public void clear()
    {
        String string="delete from "+table_name+";";
        rdb.execSQL(string);
    }
    public ArrayList<Repositories> getall()
    {
        //System.out.println("get all here");
        String string="select * from "+table_name+";";
        ArrayList<Repositories> arrayList=new ArrayList<Repositories>();
        try {
            Cursor cursor=rdb.rawQuery(string,null);
        while (cursor.moveToNext())
        {
            Repositories tmp=new Repositories();
            int id=cursor.getInt(0);
            tmp.setName(cursor.getString(1));
            tmp.setOwner(cursor.getString(2));
            tmp.setLanguage(cursor.getString(3));
            tmp.setForks(cursor.getInt(4));
            tmp.setStarts(cursor.getInt(5));
            tmp.setUrl(cursor.getString(6));
            arrayList.add(tmp);

        }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return arrayList;
    }
}
