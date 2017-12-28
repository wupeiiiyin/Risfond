package com.risfond.rnss.home.commonFuctions.reminding.activity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.risfond.rnss.home.commonFuctions.reminding.utils.Data;

/**
 * Created by lenovo on 2017/7/22.
 */

public class TransactiondatabaseSQL extends SQLiteOpenHelper{
    private static final String DB_NAME="cool.db";
    private static final String TAB_NAME = "collTab";
    private static final String Create_TAB = "create table collTab(_id integer primary key autoincrement,name text,time text)";
    private SQLiteDatabase db;
    public TransactiondatabaseSQL(Context context) {//time  时间   name 内容
        super(context,DB_NAME,null,2);
        this.db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Create_TAB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
//    public Cursor checkTime(){
//        String status = "available";
//        Cursor c = db.query(TAB_NAME, columns, KEY_STATUS +"=?",
//                new String[] {status}, null, null, KEY_NAME);
//        return 0;
//    }

    //查
    public Cursor checktransaction(){
        Cursor query = db.query(TAB_NAME, null, null, null, null, null, null);
        return query;
    }

    //增
    public void Addtransaction(ContentValues contentValues){
        db.insert(TAB_NAME,null,contentValues);
        Log.e("sss","增加111");

    }


    //删除
    public void deletetransaction(int i){
//        SQLiteDatabase db1 = getWritableDatabase();.
//        db1.close();
        db.delete(TAB_NAME,"_id=?",new String[]{String.valueOf(i)});

    }
    public void updatetransaction(ContentValues c,int i ){
//        SQLiteDatabase db1 = getWritableDatabase();
        db.update(TAB_NAME,c,"_id=?",new String[]{String.valueOf(i)});
//        db1.close();
    }

    public synchronized void close(){
        if(db!=null){
            db.close();
        }
        super.close();
    }

}
