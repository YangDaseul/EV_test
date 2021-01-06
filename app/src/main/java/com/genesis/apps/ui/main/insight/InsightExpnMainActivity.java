package com.genesis.apps.ui.main.insight;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.gra.CBK_1001;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CBK_1002;
import com.genesis.apps.comm.model.api.gra.CBK_1007;
import com.genesis.apps.comm.model.vo.ExpnVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.RecyclerViewDecoration;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.graph.AxisValueFormatter;
import com.genesis.apps.comm.util.graph.RoundedBarChartRenderer;
import com.genesis.apps.comm.viewmodel.CBKViewModel;
import com.genesis.apps.databinding.ActivityInsightExpnMainBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomListDialog;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

/**
 * @author hjpark
 * @brief 차계부 메인
 */
public class InsightExpnMainActivity extends SubActivity<ActivityInsightExpnMainBinding> {
    private CBKViewModel cbkViewModel;
    private InsightExpnAdapter adapter;
    private BottomListDialog bottomListDialog;
    private List<String> vehicleList = new ArrayList<>();
    private VehicleVO selectVehicle=null;
    private String basYymm;
    private final int PAGE_UNIT=11;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insight_expn_main);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        cbkViewModel.reqCBK1001(new CBK_1001.Request(APPIAInfo.TM_EXPS01_P03.getId()));
    }

    private void initView() {
        initGraph();
        adapter = new InsightExpnAdapter(onSingleClickListener);
        ui.rv.setLayoutManager(new LinearLayoutManager(this));
        ui.rv.addItemDecoration(new RecyclerViewDecoration((int) DeviceUtil.dip2Pixel(this, 4.0f)));
        ui.rv.setHasFixedSize(true);
        ui.rv.setAdapter(adapter);
        ui.lTitle.setBtnText(getString(R.string.tm_exps01_2));
        ui.lTitle.setTextBtnListener(onSingleClickListener);
    }

    private void initVehicleBtnStatus() {
        ui.btnVehicle.setText(getVehicleName(0));
        int size = vehicleList.size();
        if(size<2){
            ui.btnVehicle.setCompoundDrawables(null, null, null, null);
            ui.btnVehicle.setOnClickListener(null);
            reqCBKData();
        }else if(size==0){
            ui.btnMonth.setOnClickListener(null);
        }else{
            reqCBKData();
        }
    }

    private String getVehicleName(int position) {
        String vehicleName="--";
        try{
            vehicleName = vehicleList.get(position);
        }catch (Exception e){
            vehicleName="--";
        }finally{
            return vehicleName;
        }
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.btn_modify:
                ExpnVO item = (ExpnVO)v.getTag(R.id.insight_expn_vo);
                if(item!=null){
                    startActivitySingleTop(new Intent(this, InsightExpnModifyActivity.class).putExtra(KeyNames.KEY_NAME_INSIGHT_EXPN, item).putExtra(KeyNames.KEY_NAME_VEHICLE, selectVehicle), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }
                break;
            case R.id.btn_delete:
                ExpnVO expnVO = (ExpnVO)v.getTag(R.id.insight_expn_vo);
                if(expnVO!=null){
                    MiddleDialog.dialogInsightExpnDelete(this, () -> {
                        adapter.setDeleteExpnSeqNo(expnVO.getExpnSeqNo());
                        cbkViewModel.reqCBK1007(new CBK_1007.Request(APPIAInfo.TM_EXPS01_P03.getId(), expnVO.getExpnSeqNo()));
                    }, () -> {

                    }, expnVO);
                }
                break;
            case R.id.tv_titlebar_text_btn:
                if(selectVehicle!=null&&selectVehicle.getVin()!=null) {
                    startActivitySingleTop(new Intent(this, InsightExpnInputActivity.class).putExtra(KeyNames.KEY_NAME_VIN, selectVehicle.getVin()), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }
                break;
            case R.id.btn_month:
                final List<String> yearList = cbkViewModel.getYearRecently5Years();
                showMapDialog(yearList, R.string.tm_exps01_23, dialogInterface -> {
                    String year = bottomListDialog.getSelectItem();
                    if(!TextUtils.isEmpty(year)){
                        openDialogMonth(year);
                    }
                });

                break;
            case R.id.btn_vehicle:

                showMapDialog(vehicleList, R.string.tm_exps01_24, dialogInterface -> {
                    String vehicleName = bottomListDialog.getSelectItem();
                    if(!TextUtils.isEmpty(vehicleName)){
                        selectVehicle = cbkViewModel.getVehicleList().getValue().get(getVehiclePosition(vehicleName));
                        ui.btnVehicle.setText(vehicleName);
                        reqCBKData();
                    }
                });

                break;

            case R.id.btn_more:
                int totalCnt = 0;
                try{
                    totalCnt = Integer.parseInt(cbkViewModel.getRES_CBK_1002().getValue().data.getTotCnt());
                }catch (Exception e){
                    totalCnt = 0;
                }finally{
                    if(totalCnt>0){
                        if (adapter.getItemCount()>=totalCnt) {
                            adapter.setMore(false);
                            adapter.notifyItemChanged(adapter.getItemCount()-1);
                        } else {
                            cbkViewModel.reqCBK1002(new CBK_1002.Request(APPIAInfo.TM_EXPS01_P03.getId(), selectVehicle.getVin(), basYymm, adapter.getPageNo()+1+"", "11"));
                        }
                    }
                }
                break;
        }

    }

    private void openDialogMonth(String year) {
        final List<String> monthList = Arrays.asList(getResources().getStringArray(R.array.insight_month));
        showMapDialog(monthList, R.string.tm_exps01_22, dialogInterface -> {
            String month = bottomListDialog.getSelectItem();
            if(!TextUtils.isEmpty(month)){
                basYymm = year+month;
                ui.btnMonth.setText(Integer.parseInt(month)+"월");
                reqCBKData();
            }
        });
    }

    private void reqCBKData(){
        if(selectVehicle!=null) {
            adapter.clear();
            adapter.setPageNo(0);
            adapter.setMore(false);
            cbkViewModel.reqCBK1002(new CBK_1002.Request(APPIAInfo.TM_EXPS01_P03.getId(), selectVehicle.getVin(), basYymm, "1", PAGE_UNIT+""));
        }
    }


    private void showMapDialog(List<String> list, int title, DialogInterface.OnDismissListener dismissListener) {
        bottomListDialog = new BottomListDialog(this, R.style.BottomSheetDialogTheme);
        bottomListDialog.setOnDismissListener(dismissListener);
        bottomListDialog.setDatas(list);
        bottomListDialog.setTitle(getString(title));
        bottomListDialog.show();
    }

    private int getVehiclePosition(String selectVehicle){
        int pos=0;

        for(String vehicleName : vehicleList){
            if(vehicleName.equalsIgnoreCase(selectVehicle)){
                return pos;
            }
            pos++;
        }
        return 0;
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        cbkViewModel = new ViewModelProvider(this).get(CBKViewModel.class);
    }

    @Override
    public void setObserver() {

        cbkViewModel.getRES_CBK_1001().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if(result.data!=null
                            &&result.data.getRtCd().equalsIgnoreCase("0000")
                            &&result.data.getVhclList()!=null
                            &&result.data.getVhclList().size()>0){

                        try {
                            vehicleList.addAll(cbkViewModel.getInsightVehicleList(result.data.getVhclList()));
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally{
                            showProgressDialog(false);
                        }
                        break;
                    }

                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });

        //최초 진입 후 차량
        cbkViewModel.getVehicleList().observe(this, vehicleVOList -> {
            if(vehicleVOList!=null&&vehicleVOList.size()>0) {
                selectVehicle = vehicleVOList.get(0);
                basYymm = cbkViewModel.getCurrentDateyyyyMM();
                ui.btnMonth.setText(cbkViewModel.getCurrentMM());
                initVehicleBtnStatus();
            }
        });

        cbkViewModel.getRES_CBK_1002().observe(this, result -> {

            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if(result.data!=null&&result.data.getExpnList()!=null&&result.data.getExpnList().size()>0){
                        List<ExpnVO> list = new ArrayList<>();
                        try{
                            list.addAll(cbkViewModel.getExpnList(result.data.getExpnList()));
                            if (adapter.getPageNo() == 0) {
                                adapter.setRows(list);
                            } else {
                                adapter.addRows(list);
                            }
                            adapter.setPageNo(adapter.getPageNo() + 1);

                            //더보기 셋팅
                            int totalCnt=0;
                            try{
                                totalCnt = Integer.parseInt(result.data.getTotCnt());
                            }catch (Exception e){
                                totalCnt=0;
                            }finally{
                                adapter.setMore((adapter.getItemCount() < totalCnt) //현재 아이템 수가 총 카운트보다 적거나
                                        || (adapter.getItemCount() % PAGE_UNIT == 0));//11번째 아이템일 경우 더보기 활성화
                            }

                            //그래프 셋팅. 첫번째 페이지 로드시에만
                            if(adapter.getPageNo()==1) {
                                setGraph(result.data);
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }finally{
                            adapter.notifyDataSetChanged();
                            ui.tvEmpty.setVisibility(adapter.getItemCount()==0 ? View.VISIBLE : View.GONE);
                            showProgressDialog(false);
                        }
                        break;
                    }
                default:
                    ui.tvEmpty.setVisibility(adapter.getItemCount()==0 ? View.VISIBLE : View.GONE);
                    adapter.clear();
                    adapter.notifyDataSetChanged();
                    setGraph(new CBK_1002.Response());
                    showProgressDialog(false);
                    break;
            }

        });
        cbkViewModel.getRES_CBK_1007().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&!TextUtils.isEmpty(result.data.getRtCd())&&result.data.getRtCd().equalsIgnoreCase("0000")){
                        int deletePosition = adapter.getRemovePosition();
                        if(deletePosition>-1){
                            adapter.setDeleteExpnSeqNo("");
                            adapter.remove(deletePosition);
                            adapter.notifyItemRemoved(deletePosition);
                        }
                        ui.tvEmpty.setVisibility(adapter.getItemCount()==0 ? View.VISIBLE : View.GONE);
                        break;
                    }
                default:
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        if (TextUtils.isEmpty(serverMsg)){
                            serverMsg = getString(R.string.instability_network);
                        }
                        SnackBarUtil.show(this, serverMsg);
                        showProgressDialog(false);
                    }
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {

    }

    private void initGraph(){
        //최초 로드 시
        //차트 속성 정의
        ui.chart.getLegend().setEnabled(false);
        ui.chart.setDrawValueAboveBar(false);
        ui.chart.setScaleEnabled(false);
        ui.chart.getDescription().setEnabled(false);
        ui.chart.setPinchZoom(false);
        ui.chart.setDrawBarShadow(false); //charbar shadow
        ui.chart.setDrawGridBackground(false);
        ui.chart.getAxisLeft().setSpaceBottom(0f);
        ui.chart.getAxisRight().setSpaceBottom(0f);
        ui.chart.getAxisLeft().setSpaceTop(0f);
        ui.chart.getAxisRight().setSpaceTop(0f);
        ui.chart.setExtraOffsets(0, 0, 0, 12);
        ui.chart.setAutoScaleMinMaxEnabled(false);

        //좌측의 y축은 사용하지 않음
        ui.chart.getAxisLeft().setEnabled(false);

        //우측의 y축에 대한 정의
        ui.chart.getAxisRight().setZeroLineColor(ContextCompat.getColor(this,R.color.x_e2e2e2));
        ui.chart.getAxisRight().setGridColor(ContextCompat.getColor(this,R.color.x_e2e2e2));
        ui.chart.getAxisRight().setAxisLineColor(ContextCompat.getColor(this,R.color.x_00000000));
        ui.chart.getAxisRight().setTextColor(ContextCompat.getColor(this,R.color.x_4d525252));
        ui.chart.getAxisRight().setTextSize(8f);
        ui.chart.getAxisRight().setTypeface(ResourcesCompat.getFont(this, R.font.regular_genesissansheadglobal));
        ui.chart.getAxisRight().setLabelCount(5);
        ui.chart.getAxisRight().setAxisMinimum(0); //좌측과 우측에대한 최소 값을 반드시 0으로 설정해야 정상적인 그래프가 출력됨 1
        ui.chart.getAxisLeft().setAxisMinimum(0); //좌측과 우측에대한 최소 값을 반드시 0으로 설정해야 정상적인 그래프가 출력됨 2

        //x축에 대한 정의
        XAxis xAxis = ui.chart.getXAxis();
        xAxis.setValueFormatter(new AxisValueFormatter());
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelCount(4);
        xAxis.setTextColor(ContextCompat.getColor(this,R.color.x_bf000000));
        xAxis.setTextSize(11f);
        xAxis.setTypeface(ResourcesCompat.getFont(this, R.font.regular_genesissansheadglobal));
        //위 차트 속성에대한 정의는 최초1회만 진행
    }
    
    private void setGraph(CBK_1002.Response item){

        //데이터에 따른 max값 정의
        float maxValue = getMaxValue(item);
        if(maxValue==0){
            maxValue = 100000;
//            ui.tvEmptyChart.setVisibility(View.VISIBLE);
//            return;
        }
        ui.tvEmptyChart.setVisibility(View.GONE);

        ui.chart.getAxisRight().setAxisMaximum(maxValue);
        ui.chart.getAxisLeft().setAxisMaximum(maxValue);

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
//            ui.chart.getAxisRight().addLimitLine(limitLine);

        //데이터 추가
        ArrayList<BarEntry> values = new ArrayList<>();
        values.add(new BarEntry(0, Float.parseFloat(item.getRefulSumAmt())));
        values.add(new BarEntry(1, Float.parseFloat(item.getRparSumAmt())));
        values.add(new BarEntry(2, Float.parseFloat(item.getCarWshSumAmt())));
        values.add(new BarEntry(3, Float.parseFloat(item.getEtcSumAmt())));

        BarDataSet set1;
        //한번 데이터가 로드됬을 경우
        if (ui.chart.getData() != null &&
                ui.chart.getData().getDataSetCount() > 0) {
            set1 = (BarDataSet) ui.chart.getData().getDataSetByIndex(0);
            set1.setValues(values);
            ui.chart.getData().notifyDataChanged();
            ui.chart.notifyDataSetChanged();
        } else {

            //데이터 정의
            set1 = new BarDataSet(values, "Data Set");
            set1.setColor(ContextCompat.getColor(this,R.color.x_4ea39d));
            set1.setDrawValues(false);
            set1.setDrawIcons(false);
            set1.setHighlightEnabled(false);

            ArrayList<IBarDataSet> dataSets = new ArrayList<>();
            dataSets.add(set1);

            BarData data = new BarData(dataSets);
            data.setBarWidth(0.2f);

            RoundedBarChartRenderer roundedBarChartRenderer = new RoundedBarChartRenderer(ui.chart, ui.chart.getAnimator(), ui.chart.getViewPortHandler());
            roundedBarChartRenderer.setmRadius(20f);

            ui.chart.setRenderer(roundedBarChartRenderer);
            ui.chart.setData(data);
        }

        ui.chart.invalidate();
        ui.chart.animateY(1500);
        
        
    }


    private float getMaxValue(CBK_1002.Response data){
        float maxValue=0;
        ArrayList<Float> list = new ArrayList<>();
        try{
            list.add(TextUtils.isEmpty(data.getRefulSumAmt()) ? 0 : Float.parseFloat(data.getRefulSumAmt()));
            list.add(TextUtils.isEmpty(data.getRparSumAmt()) ? 0 : Float.parseFloat(data.getRparSumAmt()));
            list.add(TextUtils.isEmpty(data.getCarWshSumAmt()) ? 0 : Float.parseFloat(data.getCarWshSumAmt()));
            list.add(TextUtils.isEmpty(data.getEtcSumAmt()) ? 0 : Float.parseFloat(data.getEtcSumAmt()));
            maxValue = list.stream().max(Comparator.comparingDouble(o -> o)).get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return maxValue;
    }
    


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == ResultCodes.REQ_CODE_INSIGHT_EXPN_ADD.getCode()
                ||resultCode == ResultCodes.REQ_CODE_INSIGHT_EXPN_MODIFY.getCode()){
             String msg="";
             try {
                 msg = data.getStringExtra("msg");
             }catch (Exception e){
                 e.printStackTrace();
             }finally{
                 SnackBarUtil.show(this, msg);
                 reqCBKData();
             }
         }
    }

}
