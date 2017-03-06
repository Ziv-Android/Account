package com.ziv.accountdemo;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<CostBean> mCostBeanList;
    private DataBaseHelper mDataBaseHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDataBaseHelper = new DataBaseHelper(this);
        mCostBeanList = new ArrayList<>();
        ListView costList = (ListView) findViewById(R.id.lv_main);
        initCostData();
        costList.setAdapter(new CostListAdapter(this, mCostBeanList));
    }

    /**
     * 测试数据
     */
    private void initCostData() {
        mDataBaseHelper.deleteAllData();
        for (int i = 0; i < 6; i++) {
            CostBean costBean = new CostBean();
            costBean.costTitle = i + " Others";
            costBean.costDate = "11-11";
            costBean.costMoney = "100";
//            mCostBeanList.add(costBean);
            mDataBaseHelper.insertCost(costBean);
        }
        Cursor cursor = mDataBaseHelper.getAllCostData();
        if (cursor != null) {
            while (cursor.moveToNext()){
                CostBean costBean = new CostBean();
                costBean.costTitle = cursor.getString(cursor.getColumnIndex("cost_title"));
                costBean.costDate = cursor.getString(cursor.getColumnIndex("cost_data"));
                costBean.costMoney = cursor.getString(cursor.getColumnIndex("cost_money"));
                mCostBeanList.add(costBean);
            }
            cursor.close();
        }
    }
}
