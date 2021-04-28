package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.STC_2001;
import com.genesis.apps.comm.model.vo.CreditPointVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.CalenderUtil;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.STCViewModel;
import com.genesis.apps.databinding.ActivityMygCreditUseListBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.myg.view.CreditUseListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MyGCreditUseListActivity extends SubActivity<ActivityMygCreditUseListBinding> {
//    private static final int PAGE_SIZE = 20;
    public static final String TRANS_TYPE_CODE_SAVE="01";
    public static final String TRANS_TYPE_CODE_USE="02";
    public static final String TRANS_TYPE_CODE_CANCEL="09";
    private STCViewModel stcViewModel;
    private CreditUseListAdapter adapter;
    private VehicleVO mainVehicleVO;
    private Calendar startDate = Calendar.getInstance(Locale.getDefault());
    private Calendar endDate = Calendar.getInstance(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_credit_use_list);
        setViewModel();
        getDataFromIntent();
        setObserver();
        initView();
        reqSTC2001(DateUtil.getDate(startDate.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd), DateUtil.getDate(endDate.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd),1);
    }

    private void reqSTC2001(String transStrDt, String transEndDt, int pageNo){
        stcViewModel.reqSTC2001(
                new STC_2001.Request(
                        APPIAInfo.MG_CP01.getId(),
                        mainVehicleVO.getVin(),
                        mainVehicleVO.getMdlCd(),
                        mainVehicleVO.getMdlNm(),
                        transStrDt,
                        transEndDt,
                        getTransTypCd()));
    }

    private void initView() {
        adapter = new CreditUseListAdapter();
//        ui.rv.setHasFixedSize(true);
        ui.rv.setAdapter(adapter);
//        ui.rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                //2021-03-15 park 해당 페이지는 스크롤뷰와 리사이클러뷰가 묶여있기 때문에 리사이클러뷰의 canScrollVertically 사용 불가함으로
//                //스크롤뷰의 전체 크기 중 현재 포지션으로 위치 확인
//                if ((ui.sc.getChildAt(0).getBottom() - ui.sc.getHeight())<=ui.sc.getScrollY()&&ui.rv.getScrollState()==RecyclerView.SCROLL_STATE_IDLE) {//scroll end
//                    if(adapter.getItemCount()>0&&adapter.getItemCount() >= adapter.getPageNo() * PAGE_SIZE)
//                        reqSTC2001(DateUtil.getDate(startDate.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd), DateUtil.getDate(endDate.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd), adapter.getPageNo() + 1);
//                }
//            }
//        });
        ui.r1.performClick();
        setDateAuto(ui.r1);
    }


    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.btn_query:
                adapter.setPageNo(0);
                reqSTC2001(DateUtil.getDate(startDate.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd), DateUtil.getDate(endDate.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd),1 );
                break;
            case R.id.btn_start_date:
                openDatePicker(startDateCallback, -1L, endDate == null ? CalenderUtil.getDateMils(0) : endDate.getTimeInMillis(), startDate);
                break;
            case R.id.btn_end_date:
                openDatePicker(endDateCallback, startDate != null ? startDate.getTimeInMillis() : -1L, CalenderUtil.getDateMils(0), endDate != null ? endDate : startDate);
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        stcViewModel = new ViewModelProvider(this).get(STCViewModel.class);
    }

    @Override
    public void setObserver() {
        stcViewModel.getRES_STC_2001().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    setFilter();
                    break;
                default:
                    showProgressDialog(false);
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        if(TextUtils.isEmpty(serverMsg)) serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        SnackBarUtil.show(this, serverMsg);
                        ui.tvEmpty.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        try {
            mainVehicleVO = stcViewModel.getMainVehicle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    CalenderUtil.Callback startDateCallback = new CalenderUtil.Callback() {
        @Override
        public void onCancelled() {

        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {

            selectedDate.getFirstDate().set(Calendar.HOUR_OF_DAY, 0);
            selectedDate.getFirstDate().set(Calendar.MINUTE, 0);
            selectedDate.getFirstDate().set(Calendar.SECOND, 0);
            selectedDate.getFirstDate().set(Calendar.MILLISECOND, 0);

            if (endDate != null && selectedDate.getFirstDate().compareTo(endDate) > 0) { // ==1
                SnackBarUtil.show(MyGCreditUseListActivity.this, "시작일은 종료일보다 클 수 없습니다.\n확인 후 다시 시도해 주십시오.");
            } else {
                startDate = selectedDate.getFirstDate();
                setDate();
            }


        }
    };

    CalenderUtil.Callback endDateCallback = new CalenderUtil.Callback() {
        @Override
        public void onCancelled() {

        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {

            selectedDate.getFirstDate().set(Calendar.HOUR_OF_DAY, 0);
            selectedDate.getFirstDate().set(Calendar.MINUTE, 0);
            selectedDate.getFirstDate().set(Calendar.SECOND, 0);
            selectedDate.getFirstDate().set(Calendar.MILLISECOND, 0);

            if (startDate != null && startDate.compareTo(selectedDate.getFirstDate()) > 0) { // ==1
                SnackBarUtil.show(MyGCreditUseListActivity.this, "종료일은 시작일보다 작을 수 없습니다.\n확인 후 다시 시도해 주십시오.");
            } else {
                endDate = selectedDate.getStartDate();
                setDate();
            }
        }
    };

    private void openDatePicker(CalenderUtil.Callback callback, long startLimit, long endLimit, Calendar baseDate) {
        CalenderUtil pickerFrag = new CalenderUtil();
        pickerFrag.setCallback(callback);
        // Options
        Pair<Boolean, SublimeOptions> optionsPair = pickerFrag.getOptions(SublimeOptions.ACTIVATE_DATE_PICKER, false, startLimit, endLimit, baseDate);
        // Valid options
        Bundle bundle = new Bundle();
        bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
        pickerFrag.setArguments(bundle);
        pickerFrag.setStyle(CalenderUtil.STYLE_NO_TITLE, 0);
        pickerFrag.show(getSupportFragmentManager(), "SUBLIME_PICKER");
    }

    private void setDate() {
        if (startDate != null)
            ui.btnStartDate.setText(DateUtil.getDate(startDate.getTime(), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));

        if (endDate != null)
            ui.btnEndDate.setText(DateUtil.getDate(endDate.getTime(), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));
    }

    public void setDateAuto(View view) {
        startDate = Calendar.getInstance(Locale.getDefault());
        startDate.set(Calendar.HOUR_OF_DAY, 0);
        startDate.set(Calendar.MINUTE, 0);
        startDate.set(Calendar.SECOND, 0);
        startDate.set(Calendar.MILLISECOND, 0);

        endDate = Calendar.getInstance(Locale.getDefault());
        endDate.set(Calendar.HOUR_OF_DAY, 0);
        endDate.set(Calendar.MINUTE, 0);
        endDate.set(Calendar.SECOND, 0);
        endDate.set(Calendar.MILLISECOND, 0);

        switch (view.getId()) {
            case R.id.r_1:
                break;
            case R.id.r_7:
                startDate.add(Calendar.WEEK_OF_MONTH, -1);
                startDate.add(Calendar.DAY_OF_WEEK, 1);
                break;
            case R.id.r_30:
                startDate.add(Calendar.MONTH, -1);
                startDate.add(Calendar.DAY_OF_WEEK, 1);
                break;
            case R.id.r_180:
                startDate.add(Calendar.MONTH, -6);
                startDate.add(Calendar.DAY_OF_WEEK, 1);
                break;
        }

        setDate();
    }


    private void setFilter(){
        List<CreditPointVO> list = new ArrayList<>();
        if (stcViewModel.getRES_STC_2001().getValue() != null
                && stcViewModel.getRES_STC_2001().getValue().data != null
                && stcViewModel.getRES_STC_2001().getValue().data.getCreditList() != null
                && stcViewModel.getRES_STC_2001().getValue().data.getCreditList().size() > 0) {

            list.addAll(stcViewModel.getRES_STC_2001().getValue().data.getCreditList());
        }

        if (list.size() > 0) {
            if (adapter.getPageNo() == 0) {
                adapter.setRows(list);
            } else {
                adapter.addRows(list);
            }
            adapter.notifyDataSetChanged();
            adapter.setPageNo(adapter.getPageNo() + 1);
            ui.tvEmpty.setVisibility(View.GONE);
        }else{
            if(adapter.getItemCount()<1||adapter.getPageNo()==0){
                adapter.clear();
                adapter.notifyDataSetChanged();
                ui.tvEmpty.setVisibility(View.VISIBLE);
            }
        }
        setViewCreditPoint();
    }

    private void setViewCreditPoint() {
        String chgCreditAmout = "0";
        try{
            chgCreditAmout = stcViewModel.getRES_STC_2001().getValue().data.getCretTotAmount();
            if(TextUtils.isEmpty(chgCreditAmout)){
                chgCreditAmout = "0";
            }
        }catch (Exception e){
            chgCreditAmout = "0";
        }

        ui.tvPointSave.setText(StringUtil.getDigitGroupingString(chgCreditAmout));
    }

    private String getTransTypCd(){
        String transTypCd;
        switch (ui.rGroup2.getCheckedRadioButtonId()){
            case R.id.r_use:
                transTypCd = TRANS_TYPE_CODE_USE;
                break;
            case R.id.r_save:
                transTypCd = TRANS_TYPE_CODE_SAVE;
                break;
            case R.id.r_cancel:
                transTypCd = TRANS_TYPE_CODE_CANCEL;
                break;
            case R.id.r_all:
            default:
                transTypCd = "";
                break;
        }
        return transTypCd;
    }
}
