package com.example.maedin.sqlitetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.sql.SQLException;

public class MyDB extends SQLiteOpenHelper {

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTable(db);

    }

    public void createTable(SQLiteDatabase db)
    {
        String sql = "CREATE TABLE MYTABLE(content)";

        try{
            db.execSQL(sql);

        }catch (SQLiteException e)
        {
        }
    }


    public MyDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public void insert(SQLiteDatabase db, String content) {
        db.beginTransaction();
        try {
            String sql = "insert into MYTABLE" + "(content)" + " values('" + content + "')";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void update(SQLiteDatabase db, String updpate, String content) {
        db.beginTransaction();
        try {
            String sql = "update MYTABLE set content = '"+updpate+"' where content = " + "'" + content + "'";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public void delete(SQLiteDatabase db, String content) {
        db.beginTransaction();
        try {
            String sql = "delete from MYTABLE" + " where content = " + "'" + content + "'";
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

}
