package com.genesis.apps.comm.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.projection.MediaProjectionManager;
import android.net.Uri;
import android.os.Handler;
import android.view.View;

import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.common.service.ScreenRecorderService;

import java.lang.ref.WeakReference;

import static android.content.Context.MEDIA_PROJECTION_SERVICE;

public class RecordUtil {
    private MyBroadcastReceiver mReceiver;
    private SubFragment subFragment;
    private ActionView actionView;

    public interface ActionView{
        void setViewVisibility(int visibility);
    }

    public RecordUtil(SubFragment subFragment, ActionView actionView){
        this.subFragment = subFragment;
        this.actionView = actionView;
    }

    private static final class MyBroadcastReceiver extends BroadcastReceiver {
        private final WeakReference<SubFragment> mWeakParent;
        public MyBroadcastReceiver(final SubFragment parent) {
            mWeakParent = new WeakReference<SubFragment>(parent);
        }

        @Override
        public void onReceive(final Context context, final Intent intent) {
            final String action = intent.getAction();
            if (ScreenRecorderService.ACTION_QUERY_STATUS_RESULT.equals(action)) {
                final boolean isRecording = intent.getBooleanExtra(ScreenRecorderService.EXTRA_QUERY_RESULT_RECORDING, false);
                final SubFragment parent = mWeakParent.get();
                if (parent != null) {
                    parent.updateRecording(isRecording);
                }
            }
        }
    }


    public void requestShare(){
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        Uri screenshotUri = Uri.parse(ScreenCaptureUtil.videoFile);    // android image path
        sharingIntent.setType("video/*");
        sharingIntent.putExtra(Intent.EXTRA_STREAM, screenshotUri);
        subFragment.getActivity().startActivity(Intent.createChooser(sharingIntent, "Share image using")); // 변경가능
    }


    public void unRegReceiver() {
        try {
            if (mReceiver != null) {
                final IntentFilter intentFilter = new IntentFilter();
                intentFilter.addAction(ScreenRecorderService.ACTION_QUERY_STATUS_RESULT);
                subFragment.getActivity().unregisterReceiver(mReceiver);
            }
        }catch (Exception e){

        }
    }

    public void regReceiver(){
        mReceiver = new MyBroadcastReceiver(subFragment);
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ScreenRecorderService.ACTION_QUERY_STATUS_RESULT);
        subFragment.getActivity().registerReceiver(mReceiver, intentFilter);
    }

    private void setRejectClick(View view){
        if(view.getVisibility()==View.VISIBLE){
            view.setVisibility(View.GONE);
            setLayoutVisibility(View.VISIBLE);
        }else{
            view.setVisibility(View.VISIBLE);
            setLayoutVisibility(View.GONE);
        }
    }


    public void doRecordService(View vClickReject, final int resultCode, final Intent data) {
        setRejectClick(vClickReject);
        startRecordService(resultCode, data);
//        subFragment.doFullScreen();
        new Handler().postDelayed(() -> {
            stopRecordService();
            new Handler().postDelayed(() -> {
                setRejectClick(vClickReject);
            }, 1000);
        }, 3000);
    }

    private void setLayoutVisibility(int visibility) {
        if(actionView!=null){
            actionView.setViewVisibility(visibility);
        }
//        me.layout.setVisibility(visibility);
    }

    private void startRecordService(final int resultCode, final Intent data){
        final Intent intent = new Intent(subFragment.getActivity(), ScreenRecorderService.class);
        intent.setAction(ScreenRecorderService.ACTION_START);
        intent.putExtra(ScreenRecorderService.EXTRA_RESULT_CODE, resultCode);
        intent.putExtras(data);
        subFragment.startForegroundService(intent);
    }

    private void stopRecordService(){
        final Intent intent = new Intent(subFragment.getActivity(), ScreenRecorderService.class);
        intent.setAction(ScreenRecorderService.ACTION_STOP);
        subFragment.startForegroundService(intent);
    }

    public void checkRecordPermission() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) subFragment.getActivity().getSystemService(MEDIA_PROJECTION_SERVICE);
            subFragment.getActivity().startActivityForResult(mediaProjectionManager.createScreenCaptureIntent(), RequestCodes.REQ_CODE_PERMISSIONS_MEDIAPROJECTION.getCode());
        }
    }
}
