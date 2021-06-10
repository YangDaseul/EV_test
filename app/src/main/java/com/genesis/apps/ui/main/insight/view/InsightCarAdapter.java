package com.genesis.apps.ui.main.insight.view;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.gra.CBK_1002;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.ISTAmtVO;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.util.graph.AxisValueFormatter;
import com.genesis.apps.comm.util.graph.CustomXAxisRenderer;
import com.genesis.apps.comm.util.graph.EvAxisValueFormatter;
import com.genesis.apps.comm.util.graph.RoundedBarChartRenderer;
import com.genesis.apps.databinding.ItemInsightCarBinding;
import com.genesis.apps.databinding.ItemInsightCarEmpty2Binding;
import com.genesis.apps.databinding.ItemInsightCarEmptyBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Locale;

import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;


public class InsightCarAdapter extends BaseRecyclerViewAdapter2<ISTAmtVO> {

    private static String prvsToUseAmt="0";
    public static final int TYPE_CAR = 0;
    public static final int TYPE_EMPTY = 1;
    public static final int TYPE_EMPTY2 = 2;
    private int VIEW_TYPE = TYPE_CAR;
    private static boolean isEv=false;
    private static String stMbrYn;

    public void setStMbrYn(String stMbrYn) {
        InsightCarAdapter.stMbrYn = stMbrYn;
    }

    public int getViewType() {
        return VIEW_TYPE;
    }

    public void setViewType(int VIEW_TYPE) {
        this.VIEW_TYPE = VIEW_TYPE;
    }

    private static OnSingleClickListener onSingleClickListener;

    public InsightCarAdapter(OnSingleClickListener onSingleClickListener) {
        InsightCarAdapter.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("recyclerview test2", "create :");
        switch (viewType){
            case TYPE_EMPTY:
                return new ItemInsightCarEmpty(getView(parent, R.layout.item_insight_car_empty));
            case TYPE_EMPTY2:
                return new ItemInsightCarEmpty2(getView(parent, R.layout.item_insight_car_empty2));
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

    @Override
    public int getItemViewType(int position) {
        return VIEW_TYPE;
    }

    public void setPrvsToUseAmt(String prvsToUseAmt) {
        if(TextUtils.isEmpty(prvsToUseAmt))
            prvsToUseAmt="0";

        InsightCarAdapter.prvsToUseAmt = prvsToUseAmt;
    }

    public boolean isEv() {
        return isEv;
    }

    public void setEv(boolean ev) {
        isEv = ev;
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

            setViewCreditInfo(item);
            getBinding().lCarExpnGraph.setOnClickListener(onSingleClickListener);
            getBinding().chart.setOnClickListener(onSingleClickListener);

            //데이터에 따른 max값 정의
            float maxValue = getMaxValue(item);

            if(maxValue==0){//데이터가 없어도 표시되도록 하기 위해서 임의로 최대값 지정
                maxValue=100000;
            }

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
            XAxis xAxis = getBinding().chart.getXAxis();
            if(isEv) {
                xAxis.setLabelCount(EvAxisValueFormatter.xNames.length);
                xAxis.setValueFormatter(new EvAxisValueFormatter());
                values.add(new BarEntry(0, getValue(item.getChgAmt())));
                values.add(new BarEntry(1, getValue(item.getCretAmt())));
                values.add(new BarEntry(2, getValue(item.getRparAmt())));
                values.add(new BarEntry(3, getValue(item.getCarWshAmt())));
                values.add(new BarEntry(4, getValue(item.getEtcAmt())));
            }else{
                xAxis.setLabelCount(AxisValueFormatter.xNames.length);
                xAxis.setValueFormatter(new AxisValueFormatter());
                values.add(new BarEntry(0, getValue(item.getOilAmt())));
                values.add(new BarEntry(1, getValue(item.getRparAmt())));
                values.add(new BarEntry(2, getValue(item.getCarWshAmt())));
                values.add(new BarEntry(3, getValue(item.getEtcAmt())));
            }

            BarDataSet set1;
            //한번 데이터가 로드됬을 경우
            if (getBinding().chart.getData() != null &&
                    getBinding().chart.getData().getDataSetCount() > 0) {
                set1 = (BarDataSet) getBinding().chart.getData().getDataSetByIndex(0);
                set1.setValues(values);
                getBinding().chart.getData().notifyDataChanged();
                getBinding().chart.notifyDataSetChanged();
                getBinding().chart.invalidate();
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
                getBinding().chart.setExtraOffsets(0, 20, 0, 12);
                getBinding().chart.setAutoScaleMinMaxEnabled(false);
                //차트의 기본 패딩을 초기화
                getBinding().chart.setMinOffset(0f);

                //좌측의 y축은 사용하지 않음
                getBinding().chart.getAxisLeft().setEnabled(false);

                //우측의 y축에 대한 정의
                getBinding().chart.getAxisRight().setDrawZeroLine(true); //제로 라인 두께 설정
                getBinding().chart.getAxisRight().setZeroLineColor(ContextCompat.getColor(getContext(),R.color.x_4d4d4d));
                getBinding().chart.getAxisRight().setGridColor(ContextCompat.getColor(getContext(),R.color.x_e5e5e5));
                getBinding().chart.getAxisRight().setAxisLineColor(ContextCompat.getColor(getContext(),R.color.x_00000000));
                getBinding().chart.getAxisRight().setTextColor(ContextCompat.getColor(getContext(),R.color.x_757575));
                getBinding().chart.getAxisRight().setTextSize(8f);
                getBinding().chart.getAxisRight().setTypeface(ResourcesCompat.getFont(getContext(), R.font.regular_genesissansheadglobal));
                getBinding().chart.getAxisRight().setLabelCount(5);
                getBinding().chart.getAxisRight().setAxisMinimum(0); //좌측과 우측에대한 최소 값을 반드시 0으로 설정해야 정상적인 그래프가 출력됨 1
                getBinding().chart.getAxisLeft().setAxisMinimum(0); //좌측과 우측에대한 최소 값을 반드시 0으로 설정해야 정상적인 그래프가 출력됨 2

                //x축에 대한 정의
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
                xAxis.setDrawGridLines(false);
                xAxis.setTextColor(ContextCompat.getColor(getContext(),R.color.x_bf000000));
                xAxis.setTextSize(12f);
                xAxis.setTypeface(ResourcesCompat.getFont(getContext(), R.font.regular_genesissanstextglobal));
                //위 차트 속성에대한 정의는 최초1회만 진행
                //x축 라벨이 2줄일 경우 2줄처리 진행
                getBinding().chart.setExtraBottomOffset(12f+(12f*1.5f));
                getBinding().chart.setXAxisRenderer(new CustomXAxisRenderer(getBinding().chart.getViewPortHandler(), getBinding().chart.getXAxis(), getBinding().chart.getTransformer(YAxis.AxisDependency.LEFT)));


                //데이터 정의
                set1 = new BarDataSet(values, "Data Set");
                set1.setColor(ContextCompat.getColor(getContext(),R.color.x_996449));
                set1.setDrawValues(false);
                set1.setDrawIcons(false);
                set1.setHighlightEnabled(false);

                ArrayList<IBarDataSet> dataSets = new ArrayList<>();
                dataSets.add(set1);

                BarData data = new BarData(dataSets);
                data.setBarWidth(0.4f);

                RoundedBarChartRenderer roundedBarChartRenderer = new RoundedBarChartRenderer(getBinding().chart, getBinding().chart.getAnimator(), getBinding().chart.getViewPortHandler());
                roundedBarChartRenderer.setmRadius(0f);

                getBinding().chart.setRenderer(roundedBarChartRenderer);
                getBinding().chart.setData(data);

                getBinding().chart.invalidate();
                getBinding().chart.animateY(1500);
//                getBinding().chart.setViewPortOffsets(0f, 10f, 10f, 10f);
            }


            //이전달 금액표시
            String prvsMthAmt = "0";
            try {
                prvsMthAmt = StringUtil.getDigitGroupingString(prvsToUseAmt);
            } catch (Exception e) {
                prvsMthAmt = "0";
            } finally {
                if (TextUtils.isEmpty(prvsMthAmt)||"0".equalsIgnoreCase(prvsMthAmt)){
                    getBinding().tvPrvsMthAmt.setVisibility(View.GONE);
                    getBinding().tvPrvsMthAmt1.setVisibility(View.GONE);
                    getBinding().tvPrvsMthAmt2.setVisibility(View.GONE);
                }else{
                    getBinding().tvPrvsMthAmt.setText(prvsMthAmt);
                    getBinding().tvPrvsMthAmt.setVisibility(View.VISIBLE);
                    getBinding().tvPrvsMthAmt1.setVisibility(View.VISIBLE);
                    getBinding().tvPrvsMthAmt2.setVisibility(View.VISIBLE);
                }
            }

//            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.regular_genesissansheadglobal);
//            if(TextUtils.isEmpty(prvsToUseAmt)){
//                getBinding().tvPrvsMthAmt.setVisibility(View.GONE);
//            }else{
//                String prvsMthAmt = StringUtil.getDigitGrouping(Integer.parseInt(prvsToUseAmt));
//                getBinding().tvPrvsMthAmt.setVisibility(View.VISIBLE);
//                getBinding().tvPrvsMthAmt.setText(String.format(getContext().getString(R.string.tm01_2), prvsMthAmt));
//                String total = getBinding().tvPrvsMthAmt.getText().toString();
//                int start = total.indexOf(prvsMthAmt.charAt(0));
//                int end = start + prvsMthAmt.length();
//                Spannable span = (Spannable)getBinding().tvPrvsMthAmt.getText();
//                span.setSpan(new AbsoluteSizeSpan((int)DeviceUtil.dip2Pixel(getContext(),14)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//                span.setSpan(new CustomTypefaceSpan(typeface), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            }

            //이번달 금액 표시
            String currentAmt = "0";
            try {
                currentAmt = StringUtil.getDigitGroupingString((item.getTotUseAmt()));
            } catch (Exception e) {
                currentAmt = "0";
            } finally {
                if (TextUtils.isEmpty(currentAmt)) currentAmt = "0";
                getBinding().tvCurrMthAmt.setText(currentAmt);
            }

//            getBinding().tvCurrMthAmt.setText(String.format(getContext().getString(R.string.tm01_1), currentAmt));
//            String total = getBinding().tvCurrMthAmt.getText().toString();
//            int start = total.indexOf(currentAmt.charAt(0));
//            int end = start + currentAmt.length();
//            Spannable span = (Spannable) getBinding().tvCurrMthAmt.getText();
//            span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.x_cd9a81)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            span.setSpan(new AbsoluteSizeSpan((int) DeviceUtil.dip2Pixel(getContext(), 24)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//            span.setSpan(new CustomTypefaceSpan(typeface), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        private void setViewCreditInfo(ISTAmtVO item) {
            try {
                if (isEv&&VariableType.COMMON_MEANS_YES.equalsIgnoreCase(StringUtil.isValidString(stMbrYn))) {
                    String cardAmt = StringUtil.getPriceString(parsingStringToInt(item.getEtcAmt()) + parsingStringToInt(item.getChgAmt()) + parsingStringToInt(item.getRparAmt()) + parsingStringToInt(item.getCarWshAmt()));
                    String chgCretSum = StringUtil.getPriceString(parsingStringToInt(item.getCretAmt()));
                    getBinding().tvChargeCreditInfo1.setVisibility(View.VISIBLE);
                    getBinding().tvChargeCreditInfo2.setVisibility(View.VISIBLE);
                    getBinding().tvChargeCreditInfo1.setText(String.format(Locale.getDefault(), getContext().getString(R.string.tm01_13), cardAmt));
                    getBinding().tvChargeCreditInfo2.setText(String.format(Locale.getDefault(), getContext().getString(R.string.tm01_14), chgCretSum));
                } else {
                    getBinding().tvChargeCreditInfo1.setVisibility(View.GONE);
                    getBinding().tvChargeCreditInfo1.setText("");
                    getBinding().tvChargeCreditInfo2.setVisibility(View.GONE);
                    getBinding().tvChargeCreditInfo2.setText("");
                }
            }catch (Exception e){
                getBinding().tvChargeCreditInfo1.setText(String.format(Locale.getDefault(), getContext().getString(R.string.tm01_13), "0원"));
                getBinding().tvChargeCreditInfo2.setText(String.format(Locale.getDefault(), getContext().getString(R.string.tm01_14), "0원"));
            }
        }

        private int parsingStringToInt(String value) {
            int retv = 0;
            try {
                retv = Integer.parseInt(StringUtil.isValidString(value));
            } catch (Exception e) {
                retv = 0;
            }
            return retv;
        }

        @Override
        public void onBindView(ISTAmtVO item, int pos, SparseBooleanArray selectedItems) {

        }

        private float getMaxValue(ISTAmtVO istAmtVO){
            float maxValue=0;
            ArrayList<Float> list = new ArrayList<>();
            try{
                list.add(TextUtils.isEmpty(istAmtVO.getRparAmt()) ? 0 : Float.parseFloat(istAmtVO.getRparAmt()));
                list.add(TextUtils.isEmpty(istAmtVO.getEtcAmt()) ? 0 : Float.parseFloat(istAmtVO.getEtcAmt()));
                list.add(TextUtils.isEmpty(istAmtVO.getCarWshAmt()) ? 0 : Float.parseFloat(istAmtVO.getCarWshAmt()));
                if(isEv) {
                    list.add(TextUtils.isEmpty(istAmtVO.getCretAmt()) ? 0 : Float.parseFloat(istAmtVO.getCretAmt()));
                    list.add(TextUtils.isEmpty(istAmtVO.getChgAmt()) ? 0 : Float.parseFloat(istAmtVO.getChgAmt()));
                }else{
                    list.add(TextUtils.isEmpty(istAmtVO.getOilAmt()) ? 0 : Float.parseFloat(istAmtVO.getOilAmt()));
                }
                maxValue = list.stream().max(Comparator.comparingDouble(o -> o)).orElse(0f);
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
        }

        @Override
        public void onBindView(ISTAmtVO item, int pos, SparseBooleanArray selectedItems) {

        }


    }


    private static class ItemInsightCarEmpty2 extends BaseViewHolder<ISTAmtVO, ItemInsightCarEmpty2Binding> {
        public ItemInsightCarEmpty2(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(ISTAmtVO item) {

        }

        @Override
        public void onBindView(ISTAmtVO item, final int pos) {
        }

        @Override
        public void onBindView(ISTAmtVO item, int pos, SparseBooleanArray selectedItems) {

        }


    }

    private static Float getValue(String data){
        if(data!=null){
            return Float.parseFloat(data);
        }else{
            return 0f;
        }
    }
}