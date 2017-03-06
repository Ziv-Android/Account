package com.ziv.accountdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ziv_A on 2017/3/7.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context) {
        super(context, "daily", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Sql语句之间使用“,”分割开
        sqLiteDatabase.execSQL("create table if not exists cost(" +
                "id integer primary key," +
                "cost__title vachar," +
                "cost_date vachar," +
                "cost_money vachar)");
    }

    public void insertCost(CostBean costBean){
        SQLiteDatabase database = getWritableDatabase();// 获取数据库对象
        ContentValues values = new ContentValues();// 键值对
        values.put("cost_title", costBean.costTitle);
        values.put("cost_date", costBean.costDate);
        values.put("cost_money", costBean.costMoney);
        database.insert("cost", null, values);
    }

    /**
     * 获取数据库中所有的数据
     * @return 查询得到的游标cursor
     */
    public Cursor getAllCostData(){
        SQLiteDatabase database = getWritableDatabase();
        //String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy
        return database.query("cost", null, null, null, null, null, "cost_date " + "ASC");//ASC代表顺序排列
    }

    /**
     * 删除数据库中的内容
     */
    public void deleteAllData(){
        SQLiteDatabase database = getWritableDatabase();
        database.delete("cost", null, null);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
