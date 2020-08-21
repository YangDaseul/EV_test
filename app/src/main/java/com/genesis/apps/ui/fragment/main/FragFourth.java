package com.genesis.apps.ui.fragment.main;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

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
import com.genesis.apps.ui.activity.IntroActivity;
import com.genesis.apps.ui.activity.MainActivity;
import com.genesis.apps.ui.dialog.TestDialog;
import com.genesis.apps.ui.fragment.SubFragment;
import com.genesis.apps.ui.service.ScreenRecorderService;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.lang.ref.WeakReference;

import dagger.hilt.android.AndroidEntryPoint;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MEDIA_PROJECTION_SERVICE;

@AndroidEntryPoint
public class FragFourth extends SubFragment<Frame4pBinding> {
//    @Inject
//    public ScreenCaptureUtil screenCaptureUtil;

    private MyBroadcastReceiver mReceiver;

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
//        me.btnRecord.setOnClickListener(view -> screenCaptureUtil.toggleRecord(()->{me.btnRecord.setText("recoding.....");}, ()->{me.btnRecord.setText("start record");}));

        me.btnRecord.setOnClickListener(view -> checkRecordPermission());
    }

    @Override
    public void onPause() {
        super.onPause();
//        screenCaptureUtil.stopRecord();
        unRegReceiver();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == RequestCodes.REQ_CODE_PERMISSIONS_MEDIAPROJECTION.getCode() && resultCode == RESULT_OK) {
            doRecordService(resultCode, data);
            return;
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }


    @Override
    public void onRefresh() {
        regReceiver();
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




    private static final class MyBroadcastReceiver extends BroadcastReceiver {
        private final WeakReference<FragFourth> mWeakParent;
        public MyBroadcastReceiver(final FragFourth parent) {
            mWeakParent = new WeakReference<FragFourth>(parent);
        }

        @Override
        public void onReceive(final Context context, final Intent intent) {
            final String action = intent.getAction();
            if (ScreenRecorderService.ACTION_QUERY_STATUS_RESULT.equals(action)) {
                final boolean isRecording = intent.getBooleanExtra(ScreenRecorderService.EXTRA_QUERY_RESULT_RECORDING, false);
                final FragFourth parent = mWeakParent.get();
                if (parent != null) {
                    parent.updateRecording(isRecording);
                }
            }
        }
    }

    private void updateRecording(final boolean isRecording) {
        if(!isRecording){
            requestShare();
        }
    }

    private void requestShare(){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Uri screenshotUri = Uri.parse(ScreenCaptureUtil.videoFile);    // android image path
        sharingIntent.setType("video/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        startActivity(Intent.createChooser(sharingIntent, "Share image using")); // 변경가능
    }

    private void unRegReceiver() {
        if(mReceiver!=null) {
            final IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(ScreenRecorderService.ACTION_QUERY_STATUS_RESULT);
            getActivity().unregisterReceiver(mReceiver);
        }
    }

    private void regReceiver(){
        mReceiver = new MyBroadcastReceiver(this);
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ScreenRecorderService.ACTION_QUERY_STATUS_RESULT);
        getActivity().registerReceiver(mReceiver, intentFilter);
    }

    private void setRejectClick(View view){
        if(view.getVisibility()==View.VISIBLE){
            ((MainActivity)getActivity()).getViewPager().setUserInputEnabled(true);
            view.setVisibility(View.GONE);
        }else{
            ((MainActivity)getActivity()).getViewPager().setUserInputEnabled(false);
            view.setVisibility(View.VISIBLE);
        }
    }


    private void doRecordService(final int resultCode, final Intent data) {
        setRejectClick(me.vClickReject);
        startRecordService(resultCode, data);
        doFullScreen();
        new Handler().postDelayed(() -> {
            stopRecordService();
            setRejectClick(me.vClickReject);
        }, 3000);
    }

    private void startRecordService(final int resultCode, final Intent data){
        final Intent intent = new Intent(getActivity(), ScreenRecorderService.class);
        intent.setAction(ScreenRecorderService.ACTION_START);
        intent.putExtra(ScreenRecorderService.EXTRA_RESULT_CODE, resultCode);
        intent.putExtras(data);
        startForegroundService(intent);
    }

    private void stopRecordService(){
        final Intent intent = new Intent(getActivity(), ScreenRecorderService.class);
        intent.setAction(ScreenRecorderService.ACTION_STOP);
        startForegroundService(intent);
    }

    private void checkRecordPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getActivity().getSystemService(MEDIA_PROJECTION_SERVICE);
            getActivity().startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), RequestCodes.REQ_CODE_PERMISSIONS_MEDIAPROJECTION.getCode());
        }
    }
}