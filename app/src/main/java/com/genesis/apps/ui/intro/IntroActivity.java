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
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.databinding.ActivityIntroBinding;
import com.genesis.apps.ui.common.activity.MainActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ui.setLifecycleOwner(this);
        init();


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

    private void init(){
        if(isPermissions()){
            deviceDTO.initData();

            cmnViewModel= new ViewModelProvider(this).get(CMNViewModel.class);
            cmnViewModel.getRES_CMN_0001().observe(this, new Observer<NetUIResponse<CMN_0001.Response>>() {
                @Override
                public void onChanged(NetUIResponse<CMN_0001.Response> result) {


                    switch (result.status){
                        case SUCCESS:
                            if(!TextUtils.isEmpty(result.data.getNotiDt()))
                                cmnViewModel.updateNotiDt(result.data.getNotiDt());

                            Runnable reqContentsDownload = () -> {
                                cmnViewModel.reqCMN0002(new CMN_0002.Request(APPIAInfo.INT01.getId()));
                            };
                            Runnable notiCheck = () -> {
                                if(!checkNoti(result.data.getNotiList(), reqContentsDownload)){
                                    reqContentsDownload.run();
                                }
                            };
                            Runnable versionCheck = () -> {
                                if(!checkVersion(result.data.getAppVer(), result.data.getAppUpdType(), notiCheck)){
                                    notiCheck.run();
                                }
                            };
                            versionCheck.run();
                            break;
                        case LOADING:

                            break;
                        case ERROR:
                        default:

                            break;
                    }


                }
            });

            cmnViewModel.getRES_CMN_0002().observe(this, new Observer<NetUIResponse<CMN_0002.Response>>() {
                @Override
                public void onChanged(NetUIResponse<CMN_0002.Response> result) {

                    new Handler().postDelayed(() -> {
                        if(isPushData()){
                            startActivity(moveToPush(MainActivity.class));
                        }else{
                            startActivitySingleTop(new Intent(IntroActivity.this, MainActivity.class),0);
                        }
                        finish();
                    }, 2000);

                }
            });

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


    private boolean checkVersion(String newVersion, String versionType, Runnable runnable){
        boolean needUpdate =false;

        if(PackageUtil.versionCompare(PackageUtil.getApplicationVersionName(this, getPackageName()), newVersion)<0){
            needUpdate=true;

            MiddleDialog.updatePopUp(this, new Runnable() {
                @Override
                public void run() {

                }
            }, runnable, newVersion, versionType);



        }

        return needUpdate;
    }

    private boolean checkNoti (List<NotiVO> list, Runnable runnable){
        NotiVO notiVO=null;

        try{
            //NOTI 우선순위 결정
            for(int i=0; i<list.size(); i++){
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
                        if (notiVO == null
                                || !notiVO.getNotiCd().equalsIgnoreCase(VariableType.NOTI_CODE_EMGR)
                                || !notiVO.getNotiCd().equalsIgnoreCase(VariableType.NOTI_CODE_ANNC)) {
                            notiVO = list.get(i);
                        }
                        break;
                }
            }
        }catch (Exception ignore){
            ignore.printStackTrace();
        }finally {
            //노티팝업활성화
            if (notiVO != null) {
                switch (notiVO.hashCode()) {


                }
            }
        }

        return notiVO==null ? false : true;
    }

}
