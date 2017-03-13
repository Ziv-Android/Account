package com.ziv.accountdemo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 记账Demo实现，主要功能：
 * 1 数据List显示
 * 2 数据添加
 * 3 数据保存数据库
 * 4 图表统计显示(第三方库)
 */
public class MainActivity extends AppCompatActivity {
    private List<CostBean> mCostBeanList;
    private DataBaseHelper mDataBaseHelper;
    private CostListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        View layout = findViewById(R.id.layout_list);
        ListView costList = (ListView) layout.findViewById(R.id.lv_main);
        setSupportActionBar(toolbar);

        mDataBaseHelper = new DataBaseHelper(this);
        mCostBeanList = new ArrayList<>();

        initCostData();
        mAdapter = new CostListAdapter(this, mCostBeanList);
        costList.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View inflate = inflater.inflate(R.layout.new_cost_data, null);
                final EditText title = (EditText) inflate.findViewById(R.id.et_cost_title);
                final EditText money = (EditText) inflate.findViewById(R.id.et_cost_money);
                final DatePicker date = (DatePicker) inflate.findViewById(R.id.dp_cost_data);
                builder.setView(inflate);
                builder.setTitle("New Cost");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        // 保存数据
                        CostBean costBean = new CostBean();
                        costBean.costTitle = title.getText().toString().trim();
                        costBean.costMoney = money.getText().toString().trim();
                        costBean.costDate = date.getYear() + "-" + (date.getMonth() - 1) + "-" + date.getDayOfMonth();
                        mDataBaseHelper.insertCost(costBean);
                        mCostBeanList.add(costBean);
                        mAdapter.notifyDataSetChanged();
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.create().show();
            }
        });
    }

    /**
     * 测试数据
     */
    private void initCostData() {
        Cursor cursor = mDataBaseHelper.getAllCostData();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                CostBean costBean = new CostBean();
                costBean.costTitle = cursor.getString(cursor.getColumnIndex("cost_title"));
                costBean.costDate = cursor.getString(cursor.getColumnIndex("cost_date"));
                costBean.costMoney = cursor.getString(cursor.getColumnIndex("cost_money"));
                mCostBeanList.add(costBean);
            }
            cursor.close();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_delete) {
            // 清空所有数据
            mCostBeanList.clear();
            mAdapter.notifyDataSetChanged();
            mDataBaseHelper.deleteAllData();
            return true;
        }

        if (id == R.id.action_charts) {
            // 跳转至图表页
            Intent intent = new Intent(MainActivity.this, ChartsActivity.class);
            intent.putExtra("cost_list", (Serializable) mCostBeanList);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
