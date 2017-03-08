package com.ziv.accountdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * 数据库辅助
 *
 * Created by Ziv_A on 2017/3/7.
 */

public class DataBaseHelper extends SQLiteOpenHelper {

    private static final String COST_TITLE = "cost_title";
    private static final String COST_DATE = "cost_date";
    private static final String COST_MONEY = "cost_money";
    private static final String TABLE_COST = "table_cost";

    public DataBaseHelper(Context context) {
        super(context, "db_cost", null, 1);
        Log.e("ziv", "DataBaseHelper constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Sql语句之间使用“,”分割开
        Log.e("ziv", "create dataBase");
        sqLiteDatabase.execSQL("create table if not exists table_cost (" +
                "_id integer primary key, " +
                "cost_title varchar, " +
                "cost_date varchar, " +
                "cost_money varchar)");
    }

    public void insertCost(CostBean costBean){
        SQLiteDatabase database = getWritableDatabase();// 获取数据库对象
        ContentValues values = new ContentValues();// 键值对
        values.put(COST_TITLE, costBean.costTitle);
        values.put(COST_DATE, costBean.costDate);
        values.put(COST_MONEY, costBean.costMoney);
        database.insert(TABLE_COST, null, values);
    }

    /**
     * 获取数据库中所有的数据
     * @return 查询得到的游标cursor
     */
    public Cursor getAllCostData(){
        SQLiteDatabase database = getWritableDatabase();
        //String table, String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy
        return database.query(TABLE_COST, null, null, null, null, null, COST_DATE + " ASC");//ASC代表顺序排列
    }

    /**
     * 删除数据库中的内容
     */
    public void deleteAllData(){
        SQLiteDatabase database = getWritableDatabase();
        database.delete(TABLE_COST, null, null);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

    }
}
