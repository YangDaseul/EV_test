package com.genesis.apps.ui.fragment.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appeaser.sublimepickerlibrary.datepicker.SelectedDate;
import com.appeaser.sublimepickerlibrary.helpers.SublimeOptions;
import com.appeaser.sublimepickerlibrary.recurrencepicker.SublimeRecurrencePicker;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.RequestCodes;
import com.genesis.apps.comm.util.CalenderUtil;
import com.genesis.apps.comm.util.ScreenCaptureUtil;
import com.genesis.apps.comm.util.graph.AnotherBarActivity;
import com.genesis.apps.comm.util.graph.PieChartActivity;
import com.genesis.apps.comm.util.graph.StackedBarActivity;
import com.genesis.apps.databinding.Frame4pBinding;
import com.genesis.apps.ui.activity.GAWebActivity;
import com.genesis.apps.ui.activity.WebviewActivity;
import com.genesis.apps.ui.dialog.TestDialog;
import com.genesis.apps.ui.fragment.SubFragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import androidx.annotation.Nullable;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import static android.app.Activity.RESULT_OK;

@AndroidEntryPoint
public class FragFourth extends SubFragment<Frame4pBinding> {
    @Inject
    public ScreenCaptureUtil screenCaptureUtil;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.frame_4p);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        me.btnBottom.setOnClickListener(view -> {

            TestDialog testDialog = new TestDialog(getContext(), R.style.BottomSheetDialogTheme);
            testDialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    testDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
//                        new Handler().postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                testDialog.getBehavior().setState(BottomSheetBehavior.STATE_EXPANDED);
////                                BottomSheetBehavior.from(bottomSheet).setState(BottomSheetBehavior.STATE_EXPANDED);
//                            }
//                        },2000);
                }
            });
            testDialog.show();;
        });




        me.btnCalender.setOnClickListener(view -> {
            CalenderUtil pickerFrag = new CalenderUtil();
            pickerFrag.setCallback(mFragmentCallback);

            // Options
            Pair<Boolean, SublimeOptions> optionsPair = pickerFrag.getOptions(SublimeOptions.ACTIVATE_DATE_PICKER,true);
            // Valid options
            Bundle bundle = new Bundle();
            bundle.putParcelable("SUBLIME_OPTIONS", optionsPair.second);
            pickerFrag.setArguments(bundle);

            pickerFrag.setStyle(CalenderUtil.STYLE_NO_TITLE, 0);
            pickerFrag.show(getChildFragmentManager(), "SUBLIME_PICKER");
        });


        me.btnGraphBar.setOnClickListener(view -> baseActivity.startActivitySingleTop(new Intent(getActivity(), AnotherBarActivity.class), 0));
        me.btnGraphStackedbar.setOnClickListener(view -> baseActivity.startActivitySingleTop(new Intent(getActivity(), StackedBarActivity.class), 0));
        me.btnGraphPie.setOnClickListener(view -> baseActivity.startActivitySingleTop(new Intent(getActivity(), PieChartActivity.class), 0));

        me.btnGaPurchase.setOnClickListener(view -> baseActivity.startActivitySingleTop(new Intent(getActivity(), GAWebActivity.class).putExtra("url",GAWebActivity.URL_PURCHASE_CONSULTING), 0));
        me.btnGaDrive.setOnClickListener(view -> baseActivity.startActivitySingleTop(new Intent(getActivity(), GAWebActivity.class).putExtra("url",GAWebActivity.URL_TEST_DRIVE), 0));
        me.btnGaSimilar.setOnClickListener(view -> baseActivity.startActivitySingleTop(new Intent(getActivity(), GAWebActivity.class).putExtra("url",GAWebActivity.URL_SIMILAR_STOCKS), 0));
        me.btnGenesisMembership.setOnClickListener(view -> baseActivity.startActivitySingleTop(new Intent(getActivity(), GAWebActivity.class).putExtra("url",GAWebActivity.URL_MEMBERSHIP), 0));
        me.btnBtoMain.setOnClickListener(view -> baseActivity.startActivitySingleTop(new Intent(getActivity(), GAWebActivity.class).putExtra("url",GAWebActivity.URL_BTO_MAIN), 0));
        me.btnRecord.setOnClickListener(view -> screenCaptureUtil.toggleRecord(()->{me.btnRecord.setText("recoding.....");}, ()->{me.btnRecord.setText("start record");}));
    }

    @Override
    public void onPause() {
        super.onPause();
        screenCaptureUtil.stopRecord();
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RequestCodes.REQ_CODE_PERMISSIONS_MEDIAPROJECTION.getCode() && resultCode == RESULT_OK) {
            screenCaptureUtil.screenRecorder(resultCode, data);
            return;
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    @Override
    public void onRefresh() {

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }


    SelectedDate mSelectedDate;
    int mHour, mMinute;
    String mRecurrenceOption, mRecurrenceRule;
    CalenderUtil.Callback mFragmentCallback = new CalenderUtil.Callback() {
        @Override
        public void onCancelled() {

        }

        @Override
        public void onDateTimeRecurrenceSet(SelectedDate selectedDate,
                                            int hourOfDay, int minute,
                                            SublimeRecurrencePicker.RecurrenceOption recurrenceOption,
                                            String recurrenceRule) {

            mSelectedDate = selectedDate;
            mHour = hourOfDay;
            mMinute = minute;
            mRecurrenceOption = recurrenceOption != null ?
                    recurrenceOption.name() : "n/a";
            mRecurrenceRule = recurrenceRule != null ?
                    recurrenceRule : "n/a";
        }
    };

}