package com.genesis.apps.ui.intro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.TestCode;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.CMN_0001;
import com.genesis.apps.comm.model.gra.api.CMN_0002;
import com.genesis.apps.comm.model.gra.api.LGN_0001;
import com.genesis.apps.comm.model.vo.DeviceDTO;
import com.genesis.apps.comm.model.vo.NotiVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivityIntroBinding;
import com.genesis.apps.room.ResultCallback;
import com.genesis.apps.ui.main.MainActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.google.gson.Gson;

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

            if (!TextUtils.isEmpty(TestCode.CMN_0001.getNotiDt()))
                cmnViewModel.updateNotiDt(TestCode.CMN_0001.getNotiDt());

            reqDownloadCarInfo = () -> lgnViewModel.reqLGN0001(new LGN_0001.Request(APPIAInfo.INT01.getId(), PackageUtil.getApplicationVersionName(this, getPackageName()),""));

            reqContentsDownload = () -> cmnViewModel.reqCMN0002(new CMN_0002.Request(APPIAInfo.INT01.getId()));

            Runnable notiCheck = () -> {
                if (!checkNoti(TestCode.CMN_0001.getNotiList(), reqContentsDownload)) {
                    reqContentsDownload.run();
                }
            };
            Runnable versionCheck = () -> {
                if (!checkVersion(TestCode.CMN_0001.getAppVer(), TestCode.CMN_0001.getAppUpdType(), notiCheck)) {
                    notiCheck.run();
                }
            };
            versionCheck.run();


//            switch (result.status){
//                case SUCCESS:
//                    if(!TextUtils.isEmpty(result.data.getNotiDt()))
//                        cmnViewModel.updateNotiDt(result.data.getNotiDt());
//
//                    Runnable reqContentsDownload = () -> {
//                        cmnViewModel.reqCMN0002(new CMN_0002.Request(APPIAInfo.INT01.getId()));
//                    };
//                    Runnable notiCheck = () -> {
//                        if(!checkNoti(result.data.getNotiList(), reqContentsDownload)){
//                            reqContentsDownload.run();
//                        }
//                    };
//                    Runnable versionCheck = () -> {
//                        if(!checkVersion(result.data.getAppVer(), result.data.getAppUpdType(), notiCheck)){
//                            notiCheck.run();
//                        }
//                    };
//                    versionCheck.run();
//                    break;
//                case LOADING:
//
//                    break;
//                case ERROR:
//                default:
//
//                    break;
//            }


        });
        cmnViewModel.getRES_CMN_0002().observe(this, result -> {


            cmnViewModel.setContents(TestCode.CMN_0002, new ResultCallback() {
                @Override
                public void onSuccess(Object result) {
                    if (((Boolean) result)) {
                        reqDownloadCarInfo.run();
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

//            switch (result.status){
//                case SUCCESS:
//                    cmnViewModel.setContents(result.data, new ResultCallback() {
//                        @Override
//                        public void onSuccess(Object result) {
//                            if(((Boolean)result)){
//                                reqDownloadCarInfo.run();
//                            }else{
//                                //TODO ERROR팝업 추가 필요
//                                finish();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Object e) {
//                            //TODO ERROR팝업 추가 필요
//                            finish();
//                        }
//                    });
//                    break;
//                case LOADING:
//
//                    break;
//                default:
//                    //TODO ERROR팝업 추가 필요
//                    finish();
//                    break;
//            }
        });


        lgnViewModel.getRES_LGN_0001().observe(this, result -> {

            lgnViewModel.setLGN0001ToDB(TestCode.LGN_0001, new ResultCallback() {
                @Override
                public void onSuccess(Object result) {
                    if (((Boolean) result)) {
                        moveToMain();
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

//            switch (result.status) {
//                case SUCCESS:
//                    lgnViewModel.setLGN0001ToDB(result.data, new ResultCallback() {
//                        @Override
//                        public void onSuccess(Object result) {
//                            if (((Boolean) result)) {
//                                moveToMain();
//                            } else {
//                                //TODO ERROR팝업 추가 필요
//                                finish();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Object e) {
//                            //TODO ERROR팝업 추가 필요
//                            finish();
//                        }
//                    });
//                    break;
//                case LOADING:
//
//                    break;
//                default:
//                    //TODO ERROR팝업 추가 필요
//                    finish();
//                    break;
//            }
        });


    }

    private void moveToMain() {
        new Handler().postDelayed(() -> {
            if (isPushData()) {
                startActivity(moveToPush(MainActivity.class));
            } else {
                startActivitySingleTop(new Intent(IntroActivity.this, MainActivity.class), 0);
            }
            finish();
        }, 2000);
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void onClickCommon(View v) {

    }


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
        cmnViewModel.reqCMN0001(new CMN_0001.Request(APPIAInfo.INT01.getId(), PackageUtil.getApplicationVersionName(this, getPackageName())));
    }


    private boolean isPermissions() {
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
                startActivitySingleTop(new Intent(this, PermissionsActivity.class), RequestCodes.REQ_CODE_PERMISSIONS.getCode());
                return false;
            }
        }

        return true;
    }


    private boolean checkVersion(String newVersion, String versionType, Runnable runnable) {
        boolean needUpdate = false;

        if (PackageUtil.versionCompare(PackageUtil.getApplicationVersionName(this, getPackageName()), newVersion) < 0) {
            needUpdate = true;

            MiddleDialog.dialogUpdate(this, new Runnable() {
                @Override
                public void run() {

                }
            }, runnable, newVersion, versionType);


        }

        return needUpdate;
    }

    private boolean checkNoti(List<NotiVO> list, Runnable runnable) {
        NotiVO notiVO = null;

        try {
            //NOTI 우선순위 결정
            for (int i = 0; i < list.size(); i++) {
                switch (list.get(i).getNotiCd()) {
                    case VariableType.NOTI_CODE_EMGR:
                        notiVO = list.get(i);
                        break;
                    case VariableType.NOTI_CODE_ANNC:
                        if (notiVO == null
                                || !notiVO.getNotiCd().equalsIgnoreCase(VariableType.NOTI_CODE_EMGR)) {
                            notiVO = list.get(i);
                        }
                        break;
                    default:
//                        if (notiVO == null
//                                || !notiVO.getNotiCd().equalsIgnoreCase(VariableType.NOTI_CODE_EMGR)
//                                || !notiVO.getNotiCd().equalsIgnoreCase(VariableType.NOTI_CODE_ANNC)) {
//                            notiVO = list.get(i);
//                        }
                        break;
                }
            }
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
    }

}
