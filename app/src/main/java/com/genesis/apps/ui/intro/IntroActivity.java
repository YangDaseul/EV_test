package com.genesis.apps.ui.intro;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.gra.LGN_0004;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CMN_0001;
import com.genesis.apps.comm.model.api.gra.CMN_0002;
import com.genesis.apps.comm.model.api.gra.LGN_0001;
import com.genesis.apps.comm.model.vo.DeviceDTO;
import com.genesis.apps.comm.model.vo.NotiVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivityIntroBinding;
import com.genesis.apps.room.ResultCallback;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.main.MainActivity;
import com.genesis.apps.ui.myg.MyGVersioniActivity;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class IntroActivity extends SubActivity<ActivityIntroBinding> {

    @Inject
    public DeviceDTO deviceDTO;

    private CMNViewModel cmnViewModel;
    private LGNViewModel lgnViewModel;

    private Runnable reqDownloadCarInfo;
    private Runnable reqContentsDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        startProgressTask();
        if(isPermissions()){
            init();
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        deviceDTO.initData();
        cmnViewModel = new ViewModelProvider(this).get(CMNViewModel.class);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
    }

    @Override
    public void setObserver() {
        cmnViewModel.getRES_CMN_0001().observe(this, result -> {

            switch (result.status){
                case LOADING:

                    break;
                case SUCCESS:
                    if (!TextUtils.isEmpty(result.data.getNotiDt()))
                        cmnViewModel.updateNotiDt(result.data.getNotiDt());


                    reqDownloadCarInfo = () -> {
                        updateProgressBar(progressValue.VEHICLE.getProgress());
                        lgnViewModel.reqLGN0001(new LGN_0001.Request(APPIAInfo.INT01.getId(), PackageUtil.getApplicationVersionName(IntroActivity.this, getPackageName()),""));
                    };

                    reqContentsDownload = () -> {
                        updateProgressBar(progressValue.CONTENTS.getProgress());
                        cmnViewModel.reqCMN0002(new CMN_0002.Request(APPIAInfo.INT01.getId()));
                    };


                    Runnable notiCheck = () -> {
                        if (!checkNoti(result.data.getNotiList(), reqContentsDownload)) {
                            reqContentsDownload.run();
                        }
                    };
                    Runnable versionCheck = () -> {
                        if (!checkVersion(result.data.getAppVer(), result.data.getAppUpdType(), notiCheck)) {
                            notiCheck.run();
                        }
                    };
                    versionCheck.run();
                    break;
                case ERROR:
                default:
                    //TODO ERROR팝업 추가 필요
                    finish();
                    break;
            }
        });

        cmnViewModel.getRES_CMN_0002().observe(this, result -> {
            switch (result.status){
                case SUCCESS:
                    cmnViewModel.setContents(result.data, new ResultCallback() {
                        @Override
                        public void onSuccess(Object result) {
                            if(((Boolean)result)){
                                reqDownloadCarInfo.run();
                            }else{
                                //TODO ERROR팝업 추가 필요
                                finish();
                            }
                        }

                        @Override
                        public void onError(Object e) {
                            //TODO ERROR팝업 추가 필요
                            finish();
                        }
                    });
                    break;
                case LOADING:

                    break;
                default:
                    //TODO ERROR팝업 추가 필요
                    finish();
                    break;
            }
        });

        lgnViewModel.getRES_LGN_0001().observe(this, result -> {
            switch (result.status) {
                case SUCCESS:
                    lgnViewModel.setLGN0001ToDB(result.data, new ResultCallback() {
                        @Override
                        public void onSuccess(Object retv) {
                            if (((Boolean) retv)) {
                                if(!TextUtils.isEmpty(result.data.getPushIdChgYn())&&result.data.getPushIdChgYn().equalsIgnoreCase(VariableType.COMMON_MEANS_YES)){
                                    MiddleDialog.dialogDuplicateLogin(IntroActivity.this, () -> {
                                        lgnViewModel.reqLGN0004(new LGN_0004.Request(APPIAInfo.INT01.getId()));
                                    }, goToMain);

                                }else{
                                    goToMain.run();
                                }
                            } else {
                                //TODO ERROR팝업 추가 필요
                                finish();
                            }
                        }
                        @Override
                        public void onError(Object e) {
                            //TODO ERROR팝업 추가 필요
                            finish();
                        }
                    });
                    break;
                case LOADING:

                    break;
                default:
                    //TODO ERROR팝업 추가 필요
                    finish();
                    break;
            }
        });

        lgnViewModel.getRES_LGN_0004().observe(this, result -> {
            //결과에 상관없이 메인화면으로 이동
            goToMain.run();
        });
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void onClickCommon(View v) {

    }


    private Runnable goToMain = () -> new moveToMainTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RequestCodes.REQ_CODE_PERMISSIONS.getCode()) {
            if (resultCode == RESULT_OK) {
                init();
            } else {
                finish();
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void init() {
        getDataFromIntent();
        setViewModel();
        setObserver();
        updateProgressBar(progressValue.INTRO.getProgress());
        cmnViewModel.reqCMN0001(new CMN_0001.Request(APPIAInfo.INT01.getId(), PackageUtil.getApplicationVersionName(this, getPackageName())));
    }


    private boolean isPermissions() {
        updateProgressBar(progressValue.PERMISSION.getProgress());
        // 최초에 실행해서 권한 팝업으로 이동
        // 한번 권한 팝업을 받는다.
        // 필수 권한이 없는 경우 다시 권한 동의 팝업으로 이동한다.
        for (String p : PermissionsActivity.requiredPermissions) {
            boolean check = PackageUtil.checkPermission(this, p);
            for (String permission : PermissionsActivity.permissions) {
                if (permission.equals(p)) {
                    check = true;
                }
            }
            if (!check) {
                // 권한동의로 이동
                startActivitySingleTop(new Intent(this, PermissionsActivity.class), RequestCodes.REQ_CODE_PERMISSIONS.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                return false;
            }
        }

        return true;
    }


    private boolean checkVersion(String newVersion, String versionType, Runnable runnable) {
        updateProgressBar(progressValue.VERSION.getProgress());
        boolean needUpdate = false;

        if (PackageUtil.versionCompare(PackageUtil.getApplicationVersionName(this, getPackageName()), newVersion) < 0) {
            needUpdate = true;

            MiddleDialog.dialogUpdate(this, () -> {
                PackageUtil.goMarket(IntroActivity.this, getPackageName());
                finish();
            }, runnable, newVersion, versionType);
        }

        return needUpdate;
    }

    private boolean checkNoti(List<NotiVO> list, Runnable runnable) {
        updateProgressBar(progressValue.NOTI.getProgress());
        NotiVO notiVO = null;

        try {
            notiVO = cmnViewModel.getNoti(list);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        } finally {
            //노티팝업활성화
            if (notiVO != null) {
                switch (notiVO.getNotiCd()) {
                    case VariableType.NOTI_CODE_EMGR:
                        MiddleDialog.dialogNoti(this, new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, notiVO.getNotiTtl(), notiVO.getNotiCont());

                        break;
                    case VariableType.NOTI_CODE_ANNC:
                        MiddleDialog.dialogNoti(this, runnable, notiVO.getNotiTtl(), notiVO.getNotiCont());
                        break;
                    default:

                        break;

                }
            }
        }

        return notiVO == null ? false : true;

//        NotiVO notiVO = null;
//
//        try {
//            //NOTI 우선순위 결정
//            for (int i = 0; i < list.size(); i++) {
//                switch (list.get(i).getNotiCd()) {
//                    case VariableType.NOTI_CODE_EMGR:
//                        notiVO = list.get(i);
//                        break;
//                    case VariableType.NOTI_CODE_ANNC:
//                        if (notiVO == null
//                                || !notiVO.getNotiCd().equalsIgnoreCase(VariableType.NOTI_CODE_EMGR)) {
//                            notiVO = list.get(i);
//                        }
//                        break;
//                    default:
////                        if (notiVO == null
////                                || !notiVO.getNotiCd().equalsIgnoreCase(VariableType.NOTI_CODE_EMGR)
////                                || !notiVO.getNotiCd().equalsIgnoreCase(VariableType.NOTI_CODE_ANNC)) {
////                            notiVO = list.get(i);
////                        }
//                        break;
//                }
//            }
//        } catch (Exception ignore) {
//            ignore.printStackTrace();
//        } finally {
//            //노티팝업활성화
//            if (notiVO != null) {
//                switch (notiVO.getNotiCd()) {
//                    case VariableType.NOTI_CODE_EMGR:
//                        MiddleDialog.dialogNoti(this, new Runnable() {
//                            @Override
//                            public void run() {
//                                finish();
//                            }
//                        }, notiVO.getNotiTtl(), notiVO.getNotiCont());
//
//                        break;
//                    case VariableType.NOTI_CODE_ANNC:
//                        MiddleDialog.dialogNoti(this, runnable, notiVO.getNotiTtl(), notiVO.getNotiCont());
//                        break;
//                    default:
//
//                        break;
//
//                }
//            }
//        }
//
//        return notiVO == null ? false : true;
    }

    private ProgressbarTask progressbarTask = null;
    private WaitNotify waitNotify;
    private int progressC = 0;
    private int progressV =0;
    private PROGRESS progressValue = PROGRESS.START;
    private boolean isExit=false;

    private class ProgressbarTask extends AsyncTask<Void, Integer, Integer> {
        @Override
        protected Integer doInBackground(Void... voids) {

            try {
                while (!isCancelled() && progressC < 100) {
                    Thread.sleep(100);
                    if(progressC<progressV) {
                        progressC += 5;
                        publishProgress(progressC);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return progressC;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            waitNotify.mNotify();
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            ui.progress.setProgress(values[0],true);
            ui.tvProgress.setText(values[0]+"%");
        }

        @Override
        protected void onCancelled(Integer integer) {
            super.onCancelled(integer);
            waitNotify.mNotify();
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }


    private class moveToMainTask extends AsyncTask<Void, Integer, Integer> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            updateProgressBar(progressValue.START.getProgress());
        }

        @Override
        protected Integer doInBackground(Void... voids) {

            if(progressbarTask.getStatus()!= Status.FINISHED){
                waitNotify.mWait();
            }

            return 0;
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            if(!isExit) {
                if (isPushData()) {
                    startActivity(moveToPush(MainActivity.class));
                } else {
                    startActivitySingleTop(new Intent(IntroActivity.this, MainActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                }
                finish();
            }

        }
    }

    private class WaitNotify {
        synchronized public void mWait() {
            try {
                wait();
            } catch (Exception e) {
            }

        }
        synchronized public void mNotify() {
            try {
                notifyAll();
            } catch (Exception e) {
            }
        }
    }

    @Override
    protected void onDestroy() {
        isExit=true;
        stopProgressTask();
        super.onDestroy();
    }

    public enum PROGRESS{
        PERMISSION(10,R.string.intro_word_1),
        INTRO(20, R.string.intro_word_2),
        VERSION(50,R.string.intro_word_3),
        NOTI(65,R.string.intro_word_4),
        CONTENTS(70,R.string.intro_word_5),
        VEHICLE(85,R.string.intro_word_6),
        START(100,R.string.intro_word_7);

        private int progress_i;
        private int progress_message;
        PROGRESS(final int progress_i, int progress_message) {
            this.progress_i = progress_i;
            this.progress_message = progress_message;
        }

        public int getProgress() {
            return progress_i;
        }

        public int getProgress_message(int progress_i){
            for (PROGRESS progress : PROGRESS.values()) {
                if (progress_i<=progress.progress_i) {
                    return progress.progress_message;
                }
            }

            return 0;
        }

        public static PROGRESS find(int progress_i) {
            for (PROGRESS progress : PROGRESS.values()) {
                if (progress_i==progress.progress_i) {
                    return progress;
                }
            }
            return null;
        }
    }

    private void startProgressTask() {
        waitNotify = new WaitNotify();
        progressbarTask = new ProgressbarTask();
        progressbarTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void stopProgressTask(){
        if (progressbarTask != null && progressbarTask.getStatus() != AsyncTask.Status.FINISHED) {
            progressbarTask.cancel(true);
        }
    }

    private void updateProgressBar(int i) {
        progressV = i;
        progressValue = PROGRESS.find(i);
    }


}
