package com.genesis.apps.ui.intro;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.CMN_0001;
import com.genesis.apps.comm.model.gra.api.CMN_0002;
import com.genesis.apps.comm.model.vo.DeviceDTO;
import com.genesis.apps.comm.model.vo.NotiVO;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.databinding.ActivityIntroBinding;
import com.genesis.apps.room.ResultCallback;
import com.genesis.apps.ui.main.MainActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.google.gson.Gson;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.ViewModelProvider;
import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class IntroActivity extends SubActivity<ActivityIntroBinding> {

    @Inject
    public DeviceDTO deviceDTO;

    private CMNViewModel cmnViewModel;

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
    }

    @Override
    public void setObserver() {
        cmnViewModel.getRES_CMN_0001().observe(this, result -> {


            String test = "{\n" +
                    "  \"rtCd\": \"0000\",\n" +
                    "  \"rtMsg\": \"Success\",\n" +
                    "  \"appVer\": \"01.00.00\",\n" +
                    "  \"appUpdType\": \"X\",\n" +
                    "  \"notiDt\": \"20200910\",\n" +
                    "  \"notiList\": [\n" +
                    "    {\n" +
                    "      \"notiCd\": \"ANNC\",\n" +
                    "      \"seqNo\": \"2020091000000015\",\n" +
                    "      \"notiTtl\": \"필독공지1\",\n" +
                    "      \"notiCont\": \"필독공지내용1\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"notiCd\": \"NOTI\",\n" +
                    "      \"seqNo\": \"2020091000000014\",\n" +
                    "      \"notiTtl\": \"일반공지1\",\n" +
                    "      \"notiCont\": \"일반공지내용1\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"notiCd\": \"ANNC\",\n" +
                    "      \"seqNo\": \"2020091000000013\",\n" +
                    "      \"notiTtl\": \"긴급공지1\",\n" +
                    "      \"notiCont\": \"긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용2\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            CMN_0001.Response sample = new Gson().fromJson(test, CMN_0001.Response.class);

            if (!TextUtils.isEmpty(sample.getNotiDt()))
                cmnViewModel.updateNotiDt(sample.getNotiDt());

            Runnable reqContentsDownload = () -> cmnViewModel.reqCMN0002(new CMN_0002.Request(APPIAInfo.INT01.getId()));

            Runnable notiCheck = () -> {
                if (!checkNoti(sample.getNotiList(), reqContentsDownload)) {
                    reqContentsDownload.run();
                }
            };
            Runnable versionCheck = () -> {
                if (!checkVersion(sample.getAppVer(), sample.getAppUpdType(), notiCheck)) {
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

            String test = "{\n" +
                    "  \"rtCd\": \"0000\",\n" +
                    "  \"rtMsg\": \"Success\",\n" +
                    "  \"menu0000\": {\n" +
                    "    \"menuList\": [\n" +
                    "      {\n" +
                    "        \"menuId\": \"GM04\",\n" +
                    "        \"upMenuId\": \"GM04\",\n" +
                    "        \"menuNm\": \"HOME (비로그인)\",\n" +
                    "        \"menuTypCd\": \"NA\",\n" +
                    "        \"scrnTypCd\": \"PG\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"qckMenuList\": []\n" +
                    "  },\n" +
                    "  \"menuOV\": {\n" +
                    "    \"menuList\": [\n" +
                    "      {\n" +
                    "        \"menuId\": \"GM01\",\n" +
                    "        \"upMenuId\": \"GM01\",\n" +
                    "        \"menuNm\": \"HOME (로그인/차량보유)\",\n" +
                    "        \"menuTypCd\": \"NA\",\n" +
                    "        \"scrnTypCd\": \"PG\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"menuId\": \"INT01\",\n" +
                    "        \"upMenuId\": \"INT01\",\n" +
                    "        \"menuNm\": \"스플래시\",\n" +
                    "        \"menuTypCd\": \"NA\",\n" +
                    "        \"scrnTypCd\": \"PG\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"qckMenuList\": [\n" +
                    "      {\n" +
                    "        \"menuId\": \"MENU0001\",\n" +
                    "        \"menuNm\": \"메뉴0001\",\n" +
                    "        \"qckMenuDivCd\": \"IM\",\n" +
                    "        \"wvYn\": \"N\",\n" +
                    "        \"nttOrd\": \"1\"\n" +
                    "      },\n" +
                    "      {\n" +
                    "        \"menuId\": \"MENU0002\",\n" +
                    "        \"menuNm\": \"메뉴0002\",\n" +
                    "        \"qckMenuDivCd\": \"OM\",\n" +
                    "        \"wvYn\": \"Y\",\n" +
                    "        \"lnkUri\": \"http://www.genesis.com/priv?id=G000001\",\n" +
                    "        \"nttOrd\": \"2\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  \"menuCV\": {\n" +
                    "    \"menuList\": [\n" +
                    "      {\n" +
                    "        \"menuId\": \"GM02\",\n" +
                    "        \"upMenuId\": \"GM02\",\n" +
                    "        \"menuNm\": \"HOME (로그인/예약대기)\",\n" +
                    "        \"menuTypCd\": \"NA\",\n" +
                    "        \"scrnTypCd\": \"PG\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"qckMenuList\": [\n" +
                    "      {\n" +
                    "        \"menuId\": \"MENU0001\",\n" +
                    "        \"menuNm\": \"메뉴0001\",\n" +
                    "        \"qckMenuDivCd\": \"IM\",\n" +
                    "        \"wvYn\": \"N\",\n" +
                    "        \"nttOrd\": \"1\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  \"menuNV\": {\n" +
                    "    \"menuList\": [\n" +
                    "      {\n" +
                    "        \"menuId\": \"GM03\",\n" +
                    "        \"upMenuId\": \"GM03\",\n" +
                    "        \"menuNm\": \"HOME (로그인/차량미보유)\",\n" +
                    "        \"menuTypCd\": \"NA\",\n" +
                    "        \"scrnTypCd\": \"PG\"\n" +
                    "      }\n" +
                    "    ],\n" +
                    "    \"qckMenuList\": [\n" +
                    "      {\n" +
                    "        \"menuId\": \"MENU0001\",\n" +
                    "        \"menuNm\": \"메뉴0001\",\n" +
                    "        \"qckMenuDivCd\": \"IM\",\n" +
                    "        \"wvYn\": \"N\",\n" +
                    "        \"nttOrd\": \"1\"\n" +
                    "      }\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  \"wthrInsgtList\": [\n" +
                    "    {\n" +
                    "      \"wthrCd\": \"SKY_1\",\n" +
                    "      \"msgTypCd\": \"TXT\",\n" +
                    "      \"lnkUseYn\": \"N\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"wthrCd\": \"SKY_1\",\n" +
                    "      \"msgTypCd\": \"TXT\",\n" +
                    "      \"lnkUseYn\": \"N\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";
            CMN_0002.Response sample = new Gson().fromJson(test, CMN_0002.Response.class);

            cmnViewModel.setContents(sample, new ResultCallback() {
                @Override
                public void onSuccess(Object result) {
                    if (((Boolean) result)) {
                        new Handler().postDelayed(() -> {
                            if (isPushData()) {
                                startActivity(moveToPush(MainActivity.class));
                            } else {
                                startActivitySingleTop(new Intent(IntroActivity.this, MainActivity.class), 0);
                            }
                            finish();
                        }, 2000);

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
//                                new Handler().postDelayed(() -> {
//                                    if (isPushData()) {
//                                        startActivity(moveToPush(MainActivity.class));
//                                    } else {
//                                        startActivitySingleTop(new Intent(IntroActivity.this, MainActivity.class), 0);
//                                    }
//                                    finish();
//                                }, 2000);
//
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


//            String test = "{\n" +
//                    "  \"rsltCd\": \"0000\",\n" +
//                    "  \"rsltMsg\": \"성공\",\n" +
//                    "  \"pushIdChgYn\": \"0000\",\n" +
//                    "  \"carGbCd\": \"0000\",\n" +
//                    "  \"custMgmtNo\": \"0000\",\n" +
//                    "  \"custNm\": \"0000\",\n" +
//                    "  \"celphNo\": \"0000\",\n" +
//                    "  \"vin\": \"JK1234~\",\n" +
//                    "  \"vrn\": \"0000\",\n" +
//                    "  \"carMdelNm\": \"G70\",\n" +
//                    "  \"carCdNm\": \"2WD 엘리트\",\n" +
//                    "  \"exteriaColr\": \"블레이징 레드\",\n" +
//                    "  \"interiaColor\": \"브라운 투톤\",\n" +
//                    "  \"contractNo\": \"CJK1234~\"\n" +
//                    "}";
//
//            UserVO user = new Gson().fromJson(test, UserVO.class);
//
//            databaseHolder.getDatabase().userDao().insert(user);
//            UserVO user2 = databaseHolder.getDatabase().userDao().select();
//
//
//
//
//
//            String test2 = "{\n" +
//                    "  \"rsltCd\": \"0000\",\n" +
//                    "  \"rsltMsg\": \"성공\",\n" +
//                    "  \"pushIdChgYn\": \"0000\",\n" +
//                    "  \"carGbCd\": \"park\",\n" +
//                    "  \"custMgmtNo\": \"park\",\n" +
//                    "  \"custNm\": \"park\",\n" +
//                    "  \"celphNo\": \"0000\",\n" +
//                    "  \"vin\": \"JK1234~\",\n" +
//                    "  \"vrn\": \"0000\",\n" +
//                    "  \"carMdelNm\": \"G70\",\n" +
//                    "  \"carCdNm\": \"2WD 엘리트\",\n" +
//                    "  \"exteriaColr\": \"블레이징 레드\",\n" +
//                    "  \"interiaColor\": \"브라운 투톤\",\n" +
//                    "  \"contractNo\": \"CJK1234~\"\n" +
//                    "}";
//
//            UserVO user3 = new Gson().fromJson(test2, UserVO.class);
//            databaseHolder.getDatabase().userDao().insert(user3);
//            UserVO user4 = databaseHolder.getDatabase().userDao().select();

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
