package com.ziv.accountdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.model.ValueShape;
import lecho.lib.hellocharts.util.ChartUtils;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;

/**
 * 显示消费图表
 * <p>
 * Created by Ziv_A on 2017/3/9.
 */

public class ChartsActivity extends AppCompatActivity {
    private LineChartView mChart;
    private Map<String, Integer> table = new TreeMap<>();
    private LineChartData mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chart_view);

        mChart = (LineChartView) findViewById(R.id.chart);
        mChart.setInteractive(true);

        List<CostBean> allData = (List<CostBean>) getIntent().getSerializableExtra("cost_list");
        generateValues(allData);
        generateData();
    }

    private void generateData() {
        List<PointValue> values = new ArrayList<>();
        int indexX = 0;
        for (Integer value : table.values()) {
//            values.add(new PointValue(indexX, value));
            values.add(new PointValue(0, 2));
            values.add(new PointValue(1, 4));
            values.add(new PointValue(2, 3));
            values.add(new PointValue(3, 4));
            indexX++;
        }

        List<Line> lines = new ArrayList<>();
        Line line = new Line(values);
        line.setColor(ChartUtils.COLORS[0]).setShape(ValueShape.CIRCLE).setPointColor(ChartUtils.COLORS[1]);
        lines.add(line);

        mData = new LineChartData(lines);
        mData.setLines(lines);

        mChart = new LineChartView(this);
        mChart.setLineChartData(mData);
    }

    /**
     * 数据处理函数——同一天消费数据累加
     *
     * @param allData
     */
    private void generateValues(List<CostBean> allData) {
        if (allData != null) {
            for (int i = 0; i < allData.size(); i++) {
                CostBean costBean = allData.get(i);
                String costDate = costBean.costDate;
                int costMoney = Integer.parseInt(costBean.costMoney);
                if (!table.containsKey(costDate)) {
                    table.put(costDate, costMoney);
                } else {
                    int originMoney = table.get(costDate);
                    table.put(costDate, originMoney + costMoney);
                }
            }
        }
    }
}
