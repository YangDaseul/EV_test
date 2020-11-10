package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Pair;
import android.view.View;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.MYP_2002;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.comm.util.CalenderUtil;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ActivityMygMembershipUseListBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.myg.view.PointUseListAdapter;
import com.google.gson.Gson;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;
import java.util.Locale;

public class MyGMembershipUseListActivity extends SubActivity<ActivityMygMembershipUseListBinding> {
    private static final int PAGE_SIZE = 20;

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

        reqMYP2002(DateUtil.getDate(startDate.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd), DateUtil.getDate(endDate.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd),1 );
    }

    private void reqMYP2002(String transStrDt, String transEndDt, int pageNo){
        mypViewModel.reqMYP2002(
                new MYP_2002.Request(
                        APPIAInfo.MG_MEMBER04.getId(),
                        mbrshMbrMgmtNo,
                        transStrDt,
                        transEndDt,
                        ""+ pageNo,
                        "" + PAGE_SIZE));
    }

    private void initView() {
        adapter = new PointUseListAdapter();
        ui.rv.setHasFixedSize(true);
        ui.rv.setAdapter(adapter);
        ui.rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!ui.rv.canScrollVertically(1)) {//scroll end
                    if(adapter.getItemCount() >= adapter.getPageNo() * PAGE_SIZE)
                        reqMYP2002("", "", adapter.getPageNo() + 1);
                }
            }
        });
    }


    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.btn_query:
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

            String test = "{\n" +
                    "  \"rsltCd\": \"0000\",\n" +
                    "  \"rsltMsg\": \"성공\",\n" +
                    "  \"blueMbrYn\": \"Y\",\n" +
                    "  \"mbrshMbrMgmtNo\": \"1000000\",\n" +
                    "  \"transTotCnt\": \"3\",\n" +
                    "  \"transList\": [\n" +
                    "    {\n" +
                    "      \"seqNo\": \"1\",\n" +
                    "      \"transDtm\": \"20200901111111\",\n" +
                    "      \"frchsNm\": \"가맹점1\",\n" +
                    "      \"transTypNm\": \"사용\",\n" +
                    "      \"useMlg\": \"124574\",\n" +
                    "      \"rmndPont\": \"1111111\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"seqNo\": \"2\",\n" +
                    "      \"transDtm\": \"20200902222222\",\n" +
                    "      \"frchsNm\": \"가맹점2\",\n" +
                    "      \"transTypNm\": \"사용\",\n" +
                    "      \"useMlg\": \"222222\",\n" +
                    "      \"rmndPont\": \"333333\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"seqNo\": \"3\",\n" +
                    "      \"transDtm\": \"20200920000000\",\n" +
                    "      \"frchsNm\": \"가맹점3\",\n" +
                    "      \"transTypNm\": \"적립\",\n" +
                    "      \"useMlg\": \"222222\",\n" +
                    "      \"rmndPont\": \"333333\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"seqNo\": \"4\",\n" +
                    "      \"transDtm\": \"20200930000000\",\n" +
                    "      \"frchsNm\": \"가맹점4\",\n" +
                    "      \"transTypNm\": \"취소\",\n" +
                    "      \"useMlg\": \"2222221\",\n" +
                    "      \"rmndPont\": \"3333331\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";
            MYP_2002.Response sample = new Gson().fromJson(test, MYP_2002.Response.class);

            if (sample != null && sample.getTransList() != null && sample.getTransList().size() > 0) {
                int itemSizeBefore = adapter.getItemCount();
                if (adapter.getPageNo() == 0) {
                    adapter.setRows(sample.getTransList());
                } else {
                    adapter.addRows(sample.getTransList());
                }
                adapter.setPageNo(adapter.getPageNo() + 1);
//                      adapter.notifyDataSetChanged();
                adapter.notifyItemRangeInserted(itemSizeBefore, adapter.getItemCount());

                ui.tvPointSave.setText(StringUtil.getDigitGrouping(adapter.getTotalSavePoint()));
                ui.tvPointUse.setText(StringUtil.getDigitGrouping(adapter.getTotalUsePoint()));
            }

            if (adapter != null && adapter.getItemCount() < 1) {
                ui.tvEmpty.setVisibility(View.VISIBLE);
            } else {
                ui.tvEmpty.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        try {
            mbrshMbrMgmtNo = getIntent().getStringExtra("mbrshMbrMgmtNo");
            if (TextUtils.isEmpty(mbrshMbrMgmtNo)) {
                exitPage("블루멤버스 회원번호가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            exitPage("블루멤버스 회원번호가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
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

            if (endDate != null && selectedDate.getFirstDate().compareTo(endDate) == 1) {
                SnackBarUtil.show(MyGMembershipUseListActivity.this, "시작일은 종료일보다 클 수 없습니다.\n확인 후 다시 시도해 주십시오.");
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

            if (startDate != null && startDate.compareTo(selectedDate.getFirstDate()) == 1) {
                SnackBarUtil.show(MyGMembershipUseListActivity.this, "종료일은 시작일보다 작을 수 없습니다.\n확인 후 다시 시도해 주십시오.");
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
                break;
            case R.id.r_30:
                startDate.add(Calendar.MONTH, -1);
                break;
            case R.id.r_180:
                startDate.add(Calendar.MONTH, -6);
                break;
        }

        setDate();
    }
}
