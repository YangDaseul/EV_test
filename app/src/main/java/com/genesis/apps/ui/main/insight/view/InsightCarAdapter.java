package com.genesis.apps.ui.main.insight.view;

import android.graphics.Typeface;
import android.text.Spannable;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.LGN_0005;
import com.genesis.apps.comm.model.vo.ISTAmtVO;
import com.genesis.apps.comm.util.CustomTypefaceSpan;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.util.graph.AxisValueFormatter;
import com.genesis.apps.comm.util.graph.RoundedBarChartRenderer;
import com.genesis.apps.databinding.ItemInsightCarBinding;
import com.genesis.apps.databinding.ItemInsightCarEmptyBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;
import com.genesis.apps.ui.main.MainActivity;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Comparator;

import static android.graphics.Paint.Style.FILL;
import static com.github.mikephil.charting.components.LimitLine.LimitLabelPosition.RIGHT_BOTTOM;
import static com.github.mikephil.charting.components.LimitLine.LimitLabelPosition.RIGHT_TOP;


public class InsightCarAdapter extends BaseRecyclerViewAdapter2<ISTAmtVO> {

    private static String prvsToUseAmt="0";
    public static final int TYPE_CAR = 0;
    public static final int TYPE_EMPTY = 1;
    private int VIEW_TYPE = TYPE_CAR;

    public int getViewType() {
        return VIEW_TYPE;
    }

    public void setViewType(int VIEW_TYPE) {
        this.VIEW_TYPE = VIEW_TYPE;
    }

    private static OnSingleClickListener onSingleClickListener;

    public InsightCarAdapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("recyclerview test2", "create :");
        switch (getViewType()){
            case TYPE_EMPTY:
                return new ItemInsightCarEmpty(getView(parent, R.layout.item_insight_car_empty));
            default:
                return new ItemInsightCar(getView(parent, R.layout.item_insight_car));
        }

    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);
        holder.onBindView(getItem(position), position);

    }


    public void setPrvsToUseAmt(String prvsToUseAmt) {
        if(TextUtils.isEmpty(prvsToUseAmt))
            prvsToUseAmt="0";

        this.prvsToUseAmt = prvsToUseAmt;
    }

    private static class ItemInsightCar extends BaseViewHolder<ISTAmtVO, ItemInsightCarBinding> {
        public ItemInsightCar(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(ISTAmtVO item) {

        }

        @Override
        public void onBindView(ISTAmtVO item, final int pos) {

//            getBinding().chart.getAxisRight().setAxisMaximum(Float.parseFloat(item.getTotUseAmt())*120/100);
//            getBinding().chart.getAxisLeft().setAxisMaximum(Float.parseFloat(item.getTotUseAmt())*120/100);


            getBinding().lCarExpnGraph.setOnClickListener(onSingleClickListener);
            getBinding().chart.setOnClickListener(onSingleClickListener);

            //데이터에 따른 max값 정의
            float maxValue = getMaxValue(item);
            getBinding().chart.getAxisRight().setAxisMaximum(maxValue);
            getBinding().chart.getAxisLeft().setAxisMaximum(maxValue);

            //리밋라인은 정책상 제거됨
//            LimitLine limitLine = new LimitLine(Float.parseFloat(item.getTotUseAmt()));
//            limitLine.setLineColor(ContextCompat.getColor(getContext(),R.color.x_cd9a81));
//            limitLine.setTextColor(ContextCompat.getColor(getContext(),R.color.x_cd9a81));
//            limitLine.setLabel("이번달 사용 금액");
//            limitLine.setLabelPosition(RIGHT_BOTTOM);
//            limitLine.enableDashedLine(10f,10f,0f);
//            limitLine.setTextStyle(FILL);
//            limitLine.setTypeface(ResourcesCompat.getFont(getContext(), R.font.regular_genesissansheadglobal));
//            limitLine.setTextSize(10f);
//            limitLine.setYOffset(5f);
//            getBinding().chart.getAxisRight().addLimitLine(limitLine);

            //데이터 추가
            ArrayList<BarEntry> values = new ArrayList<>();
            values.add(new BarEntry(0, Float.parseFloat(item.getOilAmt())));
            values.add(new BarEntry(1, Float.parseFloat(item.getRparAmt())));
            values.add(new BarEntry(2, Float.parseFloat(item.getCarWshAmt())));
            values.add(new BarEntry(3, Float.parseFloat(item.getEtcAmt())));

            BarDataSet set1;
            //한번 데이터가 로드됬을 경우
            if (getBinding().chart.getData() != null &&
                    getBinding().chart.getData().getDataSetCount() > 0) {
                set1 = (BarDataSet) getBinding().chart.getData().getDataSetByIndex(0);
                set1.setValues(values);
                getBinding().chart.getData().notifyDataChanged();
                getBinding().chart.notifyDataSetChanged();
            } else {
            //최초 로드 시
                //차트 속성 정의
                getBinding().chart.getLegend().setEnabled(false);
                getBinding().chart.setDrawValueAboveBar(false);
                getBinding().chart.setScaleEnabled(false);
                getBinding().chart.getDescription().setEnabled(false);
                getBinding().chart.setPinchZoom(false);
                getBinding().chart.setDrawBarShadow(false); //charbar shadow
                getBinding().chart.setDrawGridBackground(false);
                getBinding().chart.getAxisLeft().setSpaceBottom(0f);
                getBinding().chart.getAxisRight().setSpaceBottom(0f);
                getBinding().chart.getAxisLeft().setSpaceTop(0f);
                getBinding().chart.getAxisRight().setSpaceTop(0f);
                getBinding().chart.setExtraOffsets(0, 0, 0, 12);
                getBinding().chart.setAutoScaleMinMaxEnabled(false);

                //좌측의 y축은 사용하지 않음
                getBinding().chart.getAxisLeft().setEnabled(false);

                //우측의 y축에 대한 정의
                getBinding().chart.getAxisRight().setZeroLineColor(ContextCompat.getColor(getContext(),R.color.x_e2e2e2));
                getBinding().chart.getAxisRight().setGridColor(ContextCompat.getColor(getContext(),R.color.x_e2e2e2));
                getBinding().chart.getAxisRight().setAxisLineColor(ContextCompat.getColor(getContext(),R.color.x_00000000));
                getBinding().chart.getAxisRight().setTextColor(ContextCompat.getColor(getContext(),R.color.x_4d525252));
                getBinding().chart.getAxisRight().setTextSize(8f);
                getBinding().chart.getAxisRight().setTypeface(ResourcesCompat.getFont(getContext(), R.font.regular_genesissansheadglobal));
                getBinding().chart.getAxisRight().setLabelCount(5);
                getBinding().chart.getAxisRight().setAxisMinimum(0); //좌측과 우측에대한 최소 값을 반드시 0으로 설정해야 정상적인 그래프가 출력됨 1
                getBinding().chart.getAxisLeft().setAxisMinimum(0); //좌측과 우측에대한 최소 값을 반드시 0으로 설정해야 정상적인 그래프가 출력됨 2

                //x축에 대한 정의
                XAxis xAxis = getBinding().chart.getXAxis();
                xAxis.setValueFormatter(new AxisValueFormatter());
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);
                xAxis.setLabelCount(4);
                xAxis.setTextColor(ContextCompat.getColor(getContext(),R.color.x_bf000000));
                xAxis.setTextSize(11f);
                xAxis.setTypeface(ResourcesCompat.getFont(getContext(), R.font.regular_genesissansheadglobal));
                //위 차트 속성에대한 정의는 최초1회만 진행



                //데이터 정의
                set1 = new BarDataSet(values, "Data Set");
                set1.setColor(ContextCompat.getColor(getContext(),R.color.x_4ea39d));
                set1.setDrawValues(false);
                set1.setDrawIcons(false);
                set1.setHighlightEnabled(false);

                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);

                BarData data = new BarData(dataSets);
                data.setBarWidth(0.2f);

                RoundedBarChartRenderer roundedBarChartRenderer = new RoundedBarChartRenderer(getBinding().chart, getBinding().chart.getAnimator(), getBinding().chart.getViewPortHandler());
                roundedBarChartRenderer.setmRadius(20f);

                getBinding().chart.setRenderer(roundedBarChartRenderer);
                getBinding().chart.setData(data);
            }

            getBinding().chart.invalidate();
            getBinding().chart.animateY(1500);







            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.regular_genesissansheadglobal);
            if(TextUtils.isEmpty(prvsToUseAmt)||prvsToUseAmt.equalsIgnoreCase("0")){
                getBinding().tvPrvsMthAmt.setVisibility(View.GONE);
            }else{
                String prvsMthAmt = StringUtil.getDigitGrouping(Integer.parseInt(prvsToUseAmt));
                getBinding().tvPrvsMthAmt.setVisibility(View.VISIBLE);
                getBinding().tvPrvsMthAmt.setText(String.format(getContext().getString(R.string.tm01_2), prvsMthAmt));
                String total = getBinding().tvPrvsMthAmt.getText().toString();
                int start = total.indexOf(prvsMthAmt.charAt(0));
                int end = start + prvsMthAmt.length();
                Spannable span = (Spannable)getBinding().tvPrvsMthAmt.getText();
//                span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.x_ffffff)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                span.setSpan(new AbsoluteSizeSpan((int)DeviceUtil.dip2Pixel(getContext(),14)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                span.setSpan(new CustomTypefaceSpan(typeface), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            if(!TextUtils.isEmpty(item.getTotUseAmt())){
                String currentAmt = StringUtil.getDigitGrouping(Integer.parseInt(item.getTotUseAmt()));
                getBinding().tvCurrMthAmt.setText(String.format(getContext().getString(R.string.tm01_1), currentAmt));
                String total = getBinding().tvCurrMthAmt.getText().toString();
                int start = total.indexOf(currentAmt.charAt(0));
                int end = start + currentAmt.length();
                Spannable span = (Spannable)getBinding().tvCurrMthAmt.getText();
                span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.x_cd9a81)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                span.setSpan(new AbsoluteSizeSpan((int)DeviceUtil.dip2Pixel(getContext(),24)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                span.setSpan(new CustomTypefaceSpan(typeface), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }

        @Override
        public void onBindView(ISTAmtVO item, int pos, SparseBooleanArray selectedItems) {

        }

        private float getMaxValue(ISTAmtVO istAmtVO){
            float maxValue=0;
            ArrayList<Float> list = new ArrayList<>();
            try{
                list.add(TextUtils.isEmpty(istAmtVO.getRparAmt()) ? 0 : Float.parseFloat(istAmtVO.getRparAmt()));
                list.add(TextUtils.isEmpty(istAmtVO.getOilAmt()) ? 0 : Float.parseFloat(istAmtVO.getOilAmt()));
                list.add(TextUtils.isEmpty(istAmtVO.getEtcAmt()) ? 0 : Float.parseFloat(istAmtVO.getEtcAmt()));
                list.add(TextUtils.isEmpty(istAmtVO.getCarWshAmt()) ? 0 : Float.parseFloat(istAmtVO.getCarWshAmt()));
                maxValue = list.stream().max(Comparator.comparingDouble(o -> o)).get();
            }catch (Exception e){
                e.printStackTrace();
            }
            return maxValue;
        }

    }










    private static class ItemInsightCarEmpty extends BaseViewHolder<ISTAmtVO, ItemInsightCarEmptyBinding> {
        public ItemInsightCarEmpty(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(ISTAmtVO item) {

        }

        @Override
        public void onBindView(ISTAmtVO item, final int pos) {

            getBinding().lCarExpnGraph.setOnClickListener(onSingleClickListener);
            if(pos==0){
                getBinding().ivEmpty.setImageResource(R.drawable.item_insight_car_empty_1);
            }else{
                getBinding().ivEmpty.setImageResource(R.drawable.item_insight_car_empty_2);
            }
        }

        @Override
        public void onBindView(ISTAmtVO item, int pos, SparseBooleanArray selectedItems) {

        }


    }

}