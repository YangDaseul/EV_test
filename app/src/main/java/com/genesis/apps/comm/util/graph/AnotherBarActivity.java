
package com.genesis.apps.comm.util.graph;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.genesis.apps.R;
import com.genesis.apps.comm.util.DeviceUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;

import java.util.ArrayList;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import static android.graphics.Paint.Style.FILL;
import static android.graphics.Paint.Style.STROKE;
import static com.github.mikephil.charting.components.LimitLine.LimitLabelPosition.RIGHT_TOP;

public class AnotherBarActivity extends DemoBase implements OnSeekBarChangeListener {

    private BarChart chart;
    private SeekBar seekBarX, seekBarY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_barchart);

        seekBarX = findViewById(R.id.seekBar1);
        seekBarX.setOnSeekBarChangeListener(this);
        seekBarY = findViewById(R.id.seekBar2);
        seekBarY.setOnSeekBarChangeListener(this);

        chart = findViewById(R.id.chart1);

//        chart.setMinimumHeight(150);
//
//        ValueFormatter xAxisFormatter = new DayAxisValueFormatter();
//        XAxis xAxis = chart.getXAxis();
//        xAxis.setTypeface(Typeface.createFromAsset(getAssets(), "fonts/HyundaiSansHeadKRRegular.ttf"));
//        xAxis.setTextSize(13);
//        xAxis.setValueFormatter(xAxisFormatter);
//        xAxis.setLabelCount(5);
//
//        xAxis.setDrawAxisLine(false);
//        xAxis.setTextColor(ContextCompat.getColor(this, R.color.color_2e323e));
//        chart.setExtraOffsets(0, 0, 0, 12);
//        chart.setXAxisRenderer(new GraphLabelXAxisRenderer(chart.getViewPortHandler(), chart.getXAxis(), chart.getTransformer(YAxis.AxisDependency.LEFT), chart, this));
//        chart.getAxisLeft().setEnabled(false);
//        chart.getAxisRight().setEnabled(false);

//        chart.getAxisLeft().setSpaceBottom(0f);
//        chart.getAxisRight().setSpaceBottom(0f);
//        chart.getAxisLeft().setLabelCount(25);
//        chart.getAxisRight().setLabelCount(25);
//        chart.getAxisLeft().setSpaceTop(0f);
//        chart.getAxisRight().setSpaceTop(0f);

        chart.setAutoScaleMinMaxEnabled(false);
        chart.setDrawValueAboveBar(false);
        chart.setScaleEnabled(false);
        chart.getDescription().setEnabled(false);
        chart.setPinchZoom(false);
        chart.setDrawBarShadow(false);
        chart.setDrawGridBackground(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setValueFormatter(new AxisValueFormatter());
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(4);
//
        xAxis.setTextColor(ContextCompat.getColor(this,R.color.x_bf000000));
        xAxis.setTextSize(8f);
        xAxis.setTypeface(ResourcesCompat.getFont(this, R.font.regular_genesissansheadglobal));
//        xAxis.setAxisMinimum(0f);
        chart.setExtraOffsets(0, 0, 0, 12);
        chart.getAxisLeft().setEnabled(false);
        chart.getAxisRight().setZeroLineColor(ContextCompat.getColor(this,R.color.x_e2e2e2));
        chart.getAxisRight().setGridColor(ContextCompat.getColor(this,R.color.x_e2e2e2));
        chart.getAxisRight().setAxisLineColor(ContextCompat.getColor(this,R.color.x_00000000));
        chart.getAxisRight().setTextColor(ContextCompat.getColor(this,R.color.x_4d525252));
        chart.getAxisRight().setTextSize(DeviceUtil.dip2Pixel(this, 1));
        chart.getAxisRight().setTypeface(ResourcesCompat.getFont(this, R.font.regular_genesissansheadglobal));
        chart.getAxisRight().setAxisMinimum(0);
        chart.getAxisLeft().setAxisMinimum(0);

        LimitLine limitLine = new LimitLine(70000f);
        limitLine.setLineColor(ContextCompat.getColor(this,R.color.x_cd9a81));
        limitLine.setTextColor(ContextCompat.getColor(this,R.color.x_cd9a81));
        limitLine.setLabel("이번달 사용 금액");
        limitLine.setLabelPosition(RIGHT_TOP);
        limitLine.enableDashedLine(10f,10f,0f);
        limitLine.setTextStyle(FILL);
        limitLine.setTypeface(ResourcesCompat.getFont(this, R.font.regular_genesissansheadglobal));
        limitLine.setTextSize(DeviceUtil.dip2Pixel(this, 8));
        limitLine.setYOffset(5f);
        chart.getAxisRight().addLimitLine(limitLine);
        chart.getAxisRight().setAxisMaximum(1500000f);
        chart.getAxisLeft().setAxisMaximum(1500000f);

        // setting data
        seekBarX.setProgress(3);
        seekBarY.setProgress(100);

        // add a nice and smooth animation
        chart.animateY(1500);
        chart.getLegend().setEnabled(false);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        ArrayList<BarEntry> values = new ArrayList<>();

//        for (int i = 0; i < 3; i++) {
//            float multi = (seekBarY.getProgress() + 100000);
//            float val = (float) (Math.random() * multi) + multi / 3;
//            values.add(new BarEntry(i, val));
//        }
        values.add(new BarEntry(0, 500000));
        values.add(new BarEntry(1, 500000));
        values.add(new BarEntry(2, 500000));
        values.add(new BarEntry(3, 500000));


        BarDataSet set1;

        if (chart.getData() != null &&
                chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            chart.getData().notifyDataChanged();
            chart.notifyDataSetChanged();
        } else {
            set1 = new BarDataSet(values, "Data Set");
            set1.setColor(ContextCompat.getColor(this,R.color.x_4ea39d));
            set1.setDrawValues(false);
            set1.setDrawIcons(false);
            set1.setHighlightEnabled(false);


            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setBarWidth(0.2f);

            RoundedBarChartRenderer roundedBarChartRenderer = new RoundedBarChartRenderer(chart, chart.getAnimator(), chart.getViewPortHandler());
            roundedBarChartRenderer.setmRadius(20f);



            chart.setRenderer(roundedBarChartRenderer);
            chart.setData(data);
//            chart.setFitBars(true);
        }

        chart.invalidate();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.bar, menu);
        menu.removeItem(R.id.actionToggleIcons);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.viewGithub: {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("https://github.com/PhilJay/MPAndroidChart/blob/master/MPChartExample/src/com/xxmassdeveloper/mpchartexample/AnotherBarActivity.java"));
                startActivity(i);
                break;
            }
            case R.id.actionToggleValues: {

                for (IDataSet set : chart.getData().getDataSets())
                    set.setDrawValues(!set.isDrawValuesEnabled());

                chart.invalidate();
                break;
            }
            /*
            case R.id.actionToggleIcons: { break; }
             */
            case R.id.actionToggleHighlight: {

                if(chart.getData() != null) {
                    chart.getData().setHighlightEnabled(!chart.getData().isHighlightEnabled());
                    chart.invalidate();
                }
                break;
            }
            case R.id.actionTogglePinch: {
                if (chart.isPinchZoomEnabled())
                    chart.setPinchZoom(false);
                else
                    chart.setPinchZoom(true);

                chart.invalidate();
                break;
            }
            case R.id.actionToggleAutoScaleMinMax: {
                chart.setAutoScaleMinMaxEnabled(!chart.isAutoScaleMinMaxEnabled());
                chart.notifyDataSetChanged();
                break;
            }
            case R.id.actionToggleBarBorders: {
                for (IBarDataSet set : chart.getData().getDataSets())
                    ((BarDataSet)set).setBarBorderWidth(set.getBarBorderWidth() == 1.f ? 0.f : 1.f);

                chart.invalidate();
                break;
            }
            case R.id.animateX: {
                chart.animateX(2000);
                break;
            }
            case R.id.animateY: {
                chart.animateY(2000);
                break;
            }
            case R.id.animateXY: {

                chart.animateXY(2000, 2000);
                break;
            }
            case R.id.actionSave: {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    saveToGallery();
                } else {
                    requestStoragePermission(chart);
                }
                break;
            }
        }
        return true;
    }

    @Override
    protected void saveToGallery() {
        saveToGallery(chart, "AnotherBarActivity");
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
}
