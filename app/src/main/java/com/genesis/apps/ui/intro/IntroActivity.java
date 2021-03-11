package com.genesis.apps.ui.intro;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.etc.AbnormalCheck;
import com.genesis.apps.comm.model.api.gra.CMN_0001;
import com.genesis.apps.comm.model.api.gra.CMN_0002;
import com.genesis.apps.comm.model.api.gra.LGN_0001;
import com.genesis.apps.comm.model.api.gra.LGN_0004;
import com.genesis.apps.comm.model.api.gra.LGN_0007;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.NotiVO;
import com.genesis.apps.comm.model.vo.UserVO;
import com.genesis.apps.comm.net.ga.LoginInfoDTO;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.comm.viewmodel.DevelopersViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivityIntroBinding;
import com.genesis.apps.room.ResultCallback;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.main.MainActivity;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;

@AndroidEntryPoint
public class IntroActivity extends SubActivity<ActivityIntroBinding> {

    @Inject
    public LoginInfoDTO loginInfoDTO;

    private CMNViewModel cmnViewModel;
    private LGNViewModel lgnViewModel;
    private DevelopersViewModel developersViewModel;

    private Runnable reqDownloadCarInfo;
    private Runnable reqContentsDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        setStatusBarColor(this, R.color.x_000000);
        startProgressTask();
        updateProgressBar(PROGRESS.PERMISSION.getProgress());


        if(isPermissions()){
            init();
        }else{
            //권한 확인 페이지로 이동
            startActivitySingleTop(new Intent(this, PermissionsActivity.class), RequestCodes.REQ_CODE_PERMISSIONS.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        deviceDTO.initData();
        cmnViewModel = new ViewModelProvider(this).get(CMNViewModel.class);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
        developersViewModel = new ViewModelProvider(this).get(DevelopersViewModel.class);
    }

    @Override
    public void setObserver() {
        cmnViewModel.getRES_ABNORMAL_CHECK().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    break;
                case SUCCESS:
                    if(result.data!=null){
                        if(result.data.isAbnormal()){
                            MiddleDialog.dialogAbnormal(this, StringUtil.isValidString(result.data.getTitle()), StringUtil.isValidString(result.data.getMessage()), () -> finish());
                            break;
                        }
                    }
                default:
                    cmnViewModel.reqCMN0001(new CMN_0001.Request(APPIAInfo.INT01.getId(), PackageUtil.getApplicationVersionName(this, getPackageName())));
                    break;
            }
        });

        cmnViewModel.getRES_CMN_0001().observe(this, result -> {

            switch (result.status){
                case LOADING:

                    break;
                case SUCCESS:
                    if (!TextUtils.isEmpty(result.data.getNotiDt()))
                        cmnViewModel.updateNotiDt(result.data.getNotiDt());

                    if(result.data.getBtoVhclList()!=null)
                        cmnViewModel.updateBto(result.data.getBtoVhclList());

                    if(result.data.getFmlyAppList()!=null)
                        cmnViewModel.updateFamilyApp(result.data.getFmlyAppList());

                    reqDownloadCarInfo = () -> {
                        updateProgressBar(PROGRESS.VEHICLE.getProgress());
                        lgnViewModel.reqLGN0001(new LGN_0001.Request(APPIAInfo.INT01.getId(), PackageUtil.getApplicationVersionName(IntroActivity.this, getPackageName()),""));
                    };

                    reqContentsDownload = () -> {
                        updateProgressBar(PROGRESS.CONTENTS.getProgress());
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
                    MiddleDialog.dialogNetworkError(this, () -> finish());
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
                                MiddleDialog.dialogNetworkError(IntroActivity.this, () -> finish());
                            }
                        }

                        @Override
                        public void onError(Object e) {
                            MiddleDialog.dialogNetworkError(IntroActivity.this, () -> finish());
                        }
                    });
                    break;
                case LOADING:

                    break;
                default:
                    MiddleDialog.dialogNetworkError(IntroActivity.this, () -> finish());
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
                                String vin="";
                                String userId="";
                                String accessToken="";
                                try{
                                    vin = lgnViewModel.getDbVehicleRepository().getMainVehicleFromDB().getVin();
                                    userId = loginInfoDTO.getProfile().getId();
                                    accessToken = loginInfoDTO.getAccessToken();
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                developersViewModel.checkVehicleCarId(vin, userId, accessToken, new ResultCallback() {
                                    @Override
                                    public void onSuccess(Object object) {
                                        if(!TextUtils.isEmpty(result.data.getPushIdChgYn())&&result.data.getPushIdChgYn().equalsIgnoreCase(VariableType.COMMON_MEANS_YES)){
                                            MiddleDialog.dialogDuplicateLogin(IntroActivity.this, () -> {
                                                lgnViewModel.reqLGN0004(new LGN_0004.Request(APPIAInfo.INT01.getId()));
                                            }, goToMain);

                                        }else{
                                            goToMain.run();
                                        }
                                    }
                                    @Override
                                    public void onError(Object e) {
                                        //사용하지 않음
                                    }
                                });
                            } else {
                                MiddleDialog.dialogNetworkError(IntroActivity.this, () -> finish());
                            }
                        }
                        @Override
                        public void onError(Object e) {
                            MiddleDialog.dialogNetworkError(IntroActivity.this, () -> finish());
                        }
                    }, false);
                    break;
                case LOADING:

                    break;
                default:
                    MiddleDialog.dialogNetworkError(IntroActivity.this, () -> finish());
                    break;
            }
        });

        lgnViewModel.getRES_LGN_0004().observe(this, result -> {

            switch (result.status){
                case LOADING:
                    break;
                case SUCCESS:
                    if(result.data.getRtCd().equalsIgnoreCase(RETURN_CODE_SUCC)){
                        lgnViewModel.reqLGN0007(new LGN_0007.Request(APPIAInfo.INT01.getId()));
                        break;
                    }
                default:
                    //결과에 상관없이 메인화면으로 이동
                    goToMain.run();
                    break;
            }
        });

        lgnViewModel.getRES_LGN_0007().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    break;
                case SUCCESS:
                    if(result.data!=null&&result.data.getTopicList()!=null) {
                        subscribeTopic(lgnViewModel, result.data.getTopicList());
                    }
                    goToMain.run();
                    break;
                default:
                    goToMain.run();
                    break;
            }
        });

    }

//    /**
//     * @brief Developers에 소유 차량에 대한 carId 확인 요청
//     */
//    private void checkVehicleCarId() {
//        try {
//            String vin = lgnViewModel.getDbVehicleRepository().getMainVehicleFromDB().getVin();
//            String userId = loginInfoDTO.getProfile().getId();
//            String accessToken = loginInfoDTO.getAccessToken();
//            if (!TextUtils.isEmpty(accessToken)&&!TextUtils.isEmpty(vin)&&!TextUtils.isEmpty(userId)&&developersViewModel.checkJoinCCS(new CheckJoinCCS.Request(userId, vin))) {
//                developersViewModel.checkCarId(userId, accessToken);
//            }
//        }catch (Exception ignore){
//
//        }
//    }

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
        startLogo();
        getDataFromIntent();
        setViewModel();
        setObserver();
        updateProgressBar(PROGRESS.INTRO.getProgress());
        initData();
        cmnViewModel.reqAbnormalCheck(new AbnormalCheck.Request());
    }

    private void startLogo() {
        AnimationDrawable animationDrawable = ((AnimationDrawable) ui.ivLogo.getDrawable());
        animationDrawable.setOneShot(true);
        animationDrawable.start();
    }

    /**
     * @brief 기 로그인된 정보 초기화
     * 1) CCSP 로그인 정보는 있으나 DB에 유저 정보가 없을 경우
     * CCSP 로그인 정보 초기화
     *
     * 2) 액세스 트콘 정보가 없고 DB에 유저 정보가 있을 경우
     * DB 정보 초기화
     */
    private void initData() {
        if(loginInfoDTO.loadLoginInfo()!=null){
            UserVO userVO = null;
            try {
                userVO = lgnViewModel.getUserInfoFromDB();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(userVO==null||TextUtils.isEmpty(userVO.getCustNo())||userVO.getCustNo().equalsIgnoreCase("0000")){
                    //GRA정보는 DB에 없는데 CCSP 로그인 정보가 살아있을 경우 CCSP 로그인 정보 제거
                    loginInfoDTO.clearLoginInfo();
                }else if (TextUtils.isEmpty(loginInfoDTO.getAccessToken())&&userVO!=null&&!TextUtils.isEmpty(userVO.getCustGbCd())&&!userVO.getCustGbCd().equalsIgnoreCase("0000")){
                    //엑세스토큰이 없는데 DB에 GRA 커스터머 정보가 살아있을 경우 DB 정보 클리어
                    lgnViewModel.removeDBTable();
                }
            }
        }
    }

    private boolean checkVersion(String newVersion, String versionType, Runnable runnable) {
        updateProgressBar(PROGRESS.VERSION.getProgress());
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
        updateProgressBar(PROGRESS.NOTI.getProgress());
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

        return notiVO != null;
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

            //로고 로딩 시간 3초를 기다리기 위한 변수
            int time=0;

            try {
                while (!isCancelled() && progressC < 100 && time < 3000) {
                    time++;
                    Thread.sleep(100);
                    if(progressC<progressV) {
                        progressC += 5;
                        publishProgress(progressC);
                    }
                }
            } catch (InterruptedException e) {
                Log.d("IntroActivity", "InterruptedException");
                Thread.currentThread().interrupt();
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
            updateProgressBar(PROGRESS.START.getProgress());
        }

        @Override
        protected Integer doInBackground(Void... voids) {

            if(progressbarTask.getStatus() != Status.FINISHED){
                waitNotify.mWait();
            }

            return 0;
        }


        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);

            if(!isExit) {
                if (isPushData()) {
                    startActivitySingleTop(getPushIntent(MainActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
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
