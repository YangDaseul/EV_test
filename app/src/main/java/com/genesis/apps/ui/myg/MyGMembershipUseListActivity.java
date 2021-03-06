package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.MYP_2002;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.vo.MembershipPointVO;
import com.genesis.apps.comm.util.CalenderUtil;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.databinding.ActivityMygMembershipUseListBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.myg.view.PointUseListAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MyGMembershipUseListActivity extends SubActivity<ActivityMygMembershipUseListBinding> {
    private static final int PAGE_SIZE = 20;
    private final String TRANS_TYPE_CODE_SAVE="20";
    private final String TRANS_TYPE_CODE_USE="40";
    private final String TRANS_TYPE_CODE_CANCEL="80";
    private MYPViewModel mypViewModel;
    private PointUseListAdapter adapter;
    private String mbrshMbrMgmtNo;
    private Calendar startDate = Calendar.getInstance(Locale.getDefault());
    private Calendar endDate = Calendar.getInstance(Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_membership_use_list);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        reqMYP2002(DateUtil.getDate(startDate.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd), DateUtil.getDate(endDate.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd),1);
    }

    private void reqMYP2002(String transStrDt, String transEndDt, int pageNo){
        mypViewModel.reqMYP2002(
                new MYP_2002.Request(
                        APPIAInfo.MG_MEMBER04.getId(),
                        mbrshMbrMgmtNo,
                        transStrDt,
                        transEndDt,
                        ""+ pageNo,
                        "" + PAGE_SIZE,
                        getTransTypCd()));
    }

    private void initView() {
        adapter = new PointUseListAdapter();
//        ui.rv.setHasFixedSize(true);
        ui.rv.setAdapter(adapter);
        ui.rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                //2021-03-15 park ?????? ???????????? ??????????????? ????????????????????? ???????????? ????????? ????????????????????? canScrollVertically ?????? ???????????????
                //??????????????? ?????? ?????? ??? ?????? ??????????????? ?????? ??????
                if ((ui.sc.getChildAt(0).getBottom() - ui.sc.getHeight())<=ui.sc.getScrollY()&&ui.rv.getScrollState()==RecyclerView.SCROLL_STATE_IDLE) {//scroll end
                    if(adapter.getItemCount()>0&&adapter.getItemCount() >= adapter.getPageNo() * PAGE_SIZE)
                        reqMYP2002(DateUtil.getDate(startDate.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd), DateUtil.getDate(endDate.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd), adapter.getPageNo() + 1);
                }
            }
        });
        ui.r1.performClick();
        setDateAuto(ui.r1);
    }


    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.btn_query:
                adapter.setPageNo(0);
                reqMYP2002(DateUtil.getDate(startDate.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd), DateUtil.getDate(endDate.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd),1 );
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
        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
    }

    @Override
    public void setObserver() {
        mypViewModel.getRES_MYP_2002().observe(this, result -> {
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
            mbrshMbrMgmtNo = getIntent().getStringExtra(KeyNames.KEY_NAME_MEMBERSHIP_MBR_MGMT_NO);
//            if (TextUtils.isEmpty(mbrshMbrMgmtNo)) {
//                exitPage("??????????????? ??????????????? ???????????? ????????????.\n????????? ?????? ????????? ????????????.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
//            }
        } catch (Exception e) {
            e.printStackTrace();
//            exitPage("??????????????? ??????????????? ???????????? ????????????.\n????????? ?????? ????????? ????????????.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
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
                SnackBarUtil.show(MyGMembershipUseListActivity.this, "???????????? ??????????????? ??? ??? ????????????.\n?????? ??? ?????? ????????? ????????????.");
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
                SnackBarUtil.show(MyGMembershipUseListActivity.this, "???????????? ??????????????? ?????? ??? ????????????.\n?????? ??? ?????? ????????? ????????????.");
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
        List<MembershipPointVO> list = new ArrayList<>();
        if (mypViewModel.getRES_MYP_2002().getValue() != null
                && mypViewModel.getRES_MYP_2002().getValue().data != null
                && mypViewModel.getRES_MYP_2002().getValue().data.getTransList() != null
                && mypViewModel.getRES_MYP_2002().getValue().data.getTransList().size() > 0) {

            list.addAll(mypViewModel.getRES_MYP_2002().getValue().data.getTransList());
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
        setViewSavePoint();
        setViewUsePoint();
    }

    private void setViewUsePoint() {
        String usePoint = "0";
        try{
            usePoint = mypViewModel.getRES_MYP_2002().getValue().data.getUsedBlueMbrPoint();
            if(TextUtils.isEmpty(usePoint)){
                usePoint = "0";
            }
        }catch (Exception e){
            usePoint = "0";
        }

        ui.tvPointUse.setText(StringUtil.getDigitGroupingString(usePoint));
    }

    private void setViewSavePoint() {
        String savedPoint = "0";
        try{
            savedPoint = mypViewModel.getRES_MYP_2002().getValue().data.getSavedBlueMbrPoint();
            if(TextUtils.isEmpty(savedPoint)){
                savedPoint = "0";
            }
        }catch (Exception e){
            savedPoint = "0";
        }

        ui.tvPointSave.setText(StringUtil.getDigitGroupingString(savedPoint));
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
