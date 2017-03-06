package com.ziv.accountdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<CostBean> mCostBeanList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mCostBeanList = new ArrayList<>();
        ListView costList = (ListView) findViewById(R.id.lv_main);
        initCostData();
        costList.setAdapter(new CostListAdapter(this, mCostBeanList));
    }

    /**
     * 测试数据
     */
    private void initCostData() {
        for (int i = 0; i < 6; i++) {
            CostBean costBean = new CostBean();
            costBean.costTitle = "Others";
            costBean.costDate = "11-11";
            costBean.costMoney = "100";
            mCostBeanList.add(costBean);
        }
    }
}
