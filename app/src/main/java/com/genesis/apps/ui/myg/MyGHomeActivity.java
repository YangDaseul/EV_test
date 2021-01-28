package com.genesis.apps.ui.myg;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.IST_1002;
import com.genesis.apps.comm.model.api.gra.MYP_0001;
import com.genesis.apps.comm.model.api.gra.MYP_1003;
import com.genesis.apps.comm.model.api.gra.MYP_1005;
import com.genesis.apps.comm.model.api.gra.MYP_1006;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.OilCodes;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.StoreInfo;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.ISTAmtVO;
import com.genesis.apps.comm.model.vo.OilPointVO;
import com.genesis.apps.comm.model.vo.PrivilegeVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.ISTViewModel;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.comm.viewmodel.OILViewModel;
import com.genesis.apps.databinding.ActivityMygHomeBinding;
import com.genesis.apps.ui.common.activity.GAWebActivity;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.view.listener.ViewPressEffectHelper;
import com.genesis.apps.ui.main.insight.InsightExpnMainActivity;
import com.genesis.apps.ui.main.store.StoreWebActivity;
import com.genesis.apps.ui.myg.view.FamilyAppHorizontalAdapter;
import com.genesis.apps.ui.myg.view.OilView;

import java.util.Locale;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;
import static com.genesis.apps.comm.model.constants.VariableType.TERM_SERVICE_JOIN_GRA0001;
import static com.genesis.apps.comm.model.constants.VariableType.TERM_SERVICE_JOIN_GRA0002;

public class MyGHomeActivity extends SubActivity<ActivityMygHomeBinding> {

    private MYPViewModel mypViewModel;
    private OILViewModel oilViewModel;
    private ISTViewModel istViewModel;
    private OilView oilView;
    private VehicleVO mainVehicle;
    private FamilyAppHorizontalAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_home);
        setViewModel();
        getDataFromIntent();
        setObserver();
        initView();
        reqData();
        setIvEffect();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mypViewModel.reqMYP1005(new MYP_1005.Request(APPIAInfo.MG01.getId(), ""));
        reqInsightExpn();
    }

    private void reqInsightExpn(){
        if(mainVehicle!=null&&!TextUtils.isEmpty(mainVehicle.getVin())){
            setInsightExpnLayout(View.VISIBLE);
            istViewModel.reqIST1002(new IST_1002.Request(APPIAInfo.MG01.getId(), "INSGT", "CBK", mainVehicle.getVin()));
        }else{
            setInsightExpnLayout(View.GONE);
        }
    }

    private void setInsightExpnLayout(int visibility){
        ui.btnInsightExpn.lWhole.setVisibility(visibility);
        ui.lInsightExpnInfo.setVisibility(visibility);
        ui.tvTitleInsightExpn.setVisibility(visibility);
        ui.vLine01.setVisibility(visibility);
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
        oilViewModel = new ViewModelProvider(this).get(OILViewModel.class);
        istViewModel = new ViewModelProvider(this).get(ISTViewModel.class);
    }

    @Override
    public void setObserver() {

        istViewModel.getRES_IST_1002().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    ui.tvInsightExpnUnit.setVisibility(View.INVISIBLE);
                    ui.tvInsightExpn.setVisibility(View.INVISIBLE);
                    ui.tvExpnConnectError.setVisibility(View.GONE);
                    ui.tvExpnRetry.setVisibility(View.GONE);
                    ui.pInsightExpn.show();
                    break;
                case SUCCESS:
                    ISTAmtVO current = null;
                    if (result.data != null
                            &&result.data.getRtCd().equalsIgnoreCase("0000")
                            &&result.data.getCurrMthAmt()!=null
                            &&result.data.getCurrMthAmt().size()>0
                            &&!TextUtils.isEmpty(result.data.getCurrMthAmt().get(0).getTotUseAmt())) {
                        try {
                            current = ((ISTAmtVO)result.data.getCurrMthAmt().get(0).clone());
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        ui.tvInsightExpn.setText(StringUtil.getDigitGroupingString(current.getTotUseAmt()));
                        ui.tvInsightExpn.setVisibility(View.VISIBLE);
                        ui.tvInsightExpnUnit.setVisibility(View.VISIBLE);
                        ui.tvExpnConnectError.setVisibility(View.GONE);
                        ui.tvExpnRetry.setVisibility(View.GONE);
                        ui.pInsightExpn.hide();
                        break;
                    }
                default:
                    ui.tvInsightExpnUnit.setVisibility(View.INVISIBLE);
                    ui.tvInsightExpn.setVisibility(View.INVISIBLE);
                    ui.tvExpnConnectError.setVisibility(View.VISIBLE);
                    ui.tvExpnRetry.setVisibility(View.VISIBLE);
                    ui.pInsightExpn.hide();
                    break;
            }
        });


        mypViewModel.getRES_MYP_0001().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    ui.tvName.setVisibility(View.INVISIBLE);
                    ui.tvMail.setVisibility(View.INVISIBLE);
                    ui.btnMyInfo.setVisibility(View.INVISIBLE);
                    ui.tvUserRetry.setVisibility(View.GONE);
                    ui.tvUserConnectError.setVisibility(View.GONE);
                    ui.pUser.show();
                    break;
                case SUCCESS:
                    ui.tvName.setText((result.data==null||TextUtils.isEmpty(result.data.getMbrNm()))
                            ? "--" : String.format(Locale.getDefault(), getString(R.string.word_home_23), result.data.getMbrNm()));
                    ui.tvMail.setText((result.data==null||TextUtils.isEmpty(result.data.getCcspEmail()))
                            ? "--" : result.data.getCcspEmail());

                    ui.tvName.setVisibility(View.VISIBLE);
                    ui.tvMail.setVisibility(View.VISIBLE);
                    ui.btnMyInfo.setVisibility(View.VISIBLE);
                    ui.tvUserRetry.setVisibility(View.GONE);
                    ui.tvUserConnectError.setVisibility(View.GONE);
                    ui.pUser.hide();
                    break;
                default:
                    ui.tvName.setVisibility(View.INVISIBLE);
                    ui.tvMail.setVisibility(View.INVISIBLE);
                    ui.btnMyInfo.setVisibility(View.INVISIBLE);
                    ui.tvUserConnectError.setVisibility(View.VISIBLE);
                    ui.tvUserRetry.setVisibility(View.VISIBLE);
                    ui.pUser.hide();
                    break;
            }
        });

        mypViewModel.getRES_MYP_1003().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    ui.tvPointUnit.setVisibility(View.INVISIBLE);
                    ui.tvPoint.setVisibility(View.INVISIBLE);
                    ui.lPointDetail.lWhole.setVisibility(View.VISIBLE);
                    ui.tvPointConnectError.setVisibility(View.GONE);
                    ui.tvPointRetry.setVisibility(View.GONE);
                    ui.pPoint.show();
                    break;
                case SUCCESS:
                    ui.tvPoint.setText((result.data==null||TextUtils.isEmpty(result.data.getBludMbrPoint()))
                            ? "0" : StringUtil.getDigitGroupingString(result.data.getBludMbrPoint()));
                    ui.tvPointUnit.setVisibility(View.VISIBLE);
                    ui.tvPoint.setVisibility(View.VISIBLE);
                    ui.lPointDetail.lWhole.setVisibility(View.VISIBLE);
                    ui.tvPointConnectError.setVisibility(View.GONE);
                    ui.tvPointRetry.setVisibility(View.GONE);
                    ui.pPoint.hide();
                    break;
                default:
                    ui.tvPointUnit.setVisibility(View.INVISIBLE);
                    ui.tvPoint.setVisibility(View.INVISIBLE);
                    ui.lPointDetail.lWhole.setVisibility(View.INVISIBLE);
                    ui.tvPointConnectError.setVisibility(View.VISIBLE);
                    ui.tvPointRetry.setVisibility(View.VISIBLE);
                    ui.pPoint.hide();
                    break;
            }
        });

        mypViewModel.getRES_MYP_1005().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    ui.btnStatus.setVisibility(View.GONE);
                    ui.btnCarList.setVisibility(View.GONE);
                    ui.btnApply.setVisibility(View.GONE);
                    ui.pPrivilege.show();
                    break;
                case SUCCESS:
                    setPrivilegeLayout(result.data);
                    ui.pPrivilege.hide();
                    break;
                default:
                    ui.pPrivilege.hide();
                    break;
            }
        });

        mypViewModel.getRES_MYP_1006().observe(this, result -> {

            switch (result.status){
                case LOADING:
                    oilView.showErrorLayout(View.GONE);
                    oilView.showPorgessBar(true);
                    break;
                case SUCCESS:
                    oilView.setOilLayout(result.data);
                    oilView.showErrorLayout(View.GONE);
                    oilView.showPorgessBar(false);
                    break;
                default:
                    oilView.showErrorLayout(View.VISIBLE);
                    oilView.showPorgessBar(false);
                    break;
            }
        });

        oilViewModel.getRES_OIL_0005().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null&&result.data.getRtCd().equalsIgnoreCase(RETURN_CODE_SUCC)){
                        mypViewModel.reqMYP1006(new MYP_1006.Request(APPIAInfo.MG01.getId()));
                        SnackBarUtil.show(this, "연동이 완료되었습니다.");
                        break;
                    }
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
                    }
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        try {
            mainVehicle = mypViewModel.getMainVehicleSimplyFromDB();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void initView() {
        oilView = new OilView(ui.lOil, v -> onSingleClickListener.onClick(v), oilViewModel);
        ui.setActivity(this);
        ui.setOilView(oilView);
        ui.lTitle.ivTitlebarImgBtn.setOnClickListener(onSingleClickListener);
        setCallCenter();
        initFamilyApp();
        ui.tvVersion.setText("V"+PackageUtil.changeVersionToAppFormat(PackageUtil.getApplicationVersionName(this, getPackageName())));
    }

    private void initFamilyApp(){
        adapter = new FamilyAppHorizontalAdapter(onSingleClickListener);
        adapter.setRows(mypViewModel.getFamilyAppList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        ui.rcFamilyApp.setLayoutManager(layoutManager);
        ui.rcFamilyApp.setHasFixedSize(true);
        ui.rcFamilyApp.setAdapter(adapter);
    }

    private void reqData() {
        mypViewModel.reqMYP0001(new MYP_0001.Request(APPIAInfo.MG01.getId()));
        mypViewModel.reqMYP1003(new MYP_1003.Request(APPIAInfo.MG01.getId()));
        mypViewModel.reqMYP1006(new MYP_1006.Request(APPIAInfo.MG01.getId()));
    }


    private void setPrivilegeLayout(MYP_1005.Response data) {

        if (data==null
                || TextUtils.isEmpty(data.getMbrshJoinYn())
                || data.getMbrshJoinYn().equalsIgnoreCase("N")
                || data.getPvilList()==null
                || data.getPvilList().size() < 1) {
            ui.lPrivilege.setVisibility(View.GONE);
        } else {
            ui.lPrivilege.setVisibility(View.VISIBLE);

            int privilegeCarCnt = 1;
            try {
                privilegeCarCnt = mypViewModel.getPrivilegeCarCnt(data.getPvilList());
            }catch (Exception e){
                privilegeCarCnt = 1;
            }
            if (privilegeCarCnt == 1) {
                PrivilegeVO privilegeVO = null;
                try {
                    privilegeVO = mypViewModel.getPrivilegeCar(data.getPvilList());
                } catch (Exception e) {
                    privilegeVO = null;
                }
                ui.btnCarList.setVisibility(View.GONE);
                if(privilegeVO!=null) {
                    switch (StringUtil.isValidString(privilegeVO.getJoinPsblCd())) {
                        case PrivilegeVO.JOIN_CODE_APPLY_POSSIBLE:
                            ui.btnStatus.setVisibility(View.GONE);
//                            ui.btnBenefit.setVisibility(View.GONE);
                            ui.btnApply.setVisibility(View.VISIBLE);
                            ui.btnApply.setTag(R.id.url, privilegeVO.getServiceUrl());
                            break;
                        case PrivilegeVO.JOIN_CODE_APPLYED:
                            ui.btnStatus.setVisibility(View.VISIBLE);
//                            ui.btnBenefit.setVisibility(View.VISIBLE);
                            ui.btnApply.setVisibility(View.GONE);
                            ui.btnStatus.setTag(R.id.item, privilegeVO);
//                            ui.btnBenefit.setTag(R.id.url, privilegeVO.getServiceDetailUrl());
                            break;
                        default:
                            ui.lPrivilege.setVisibility(View.GONE);
                            break;
                    }
                }else{
                    ui.lPrivilege.setVisibility(View.GONE);
                }
            } else {
                ui.btnCarList.setVisibility(View.VISIBLE);
                ui.btnStatus.setVisibility(View.GONE);
//                ui.btnBenefit.setVisibility(View.GONE);
                ui.btnApply.setVisibility(View.GONE);
            }

        }


    }

    private void setIvEffect() {
        ViewPressEffectHelper.attach(ui.lAppConnected);
        ViewPressEffectHelper.attach(ui.lAppDigitalkey);
        ViewPressEffectHelper.attach(ui.lAppCarpay);
        ViewPressEffectHelper.attach(ui.lAppCam);
    }

    private void setCallCenter(){
        if(mainVehicle!=null&&!TextUtils.isEmpty(mainVehicle.getMdlNm())&& (mainVehicle.getMdlNm().equalsIgnoreCase("G90")||mainVehicle.getMdlNm().equalsIgnoreCase("EQ900"))){
            ui.tvCenterMsg4.setText(R.string.word_home_25);
        }else{
            ui.tvCenterMsg4.setText(R.string.word_home_14);
        }
    }

    @Override
    public void onClickCommon(final View v) {
        Log.v("test duplicate", "id:" + v.getId());
        if (v != null) {
            switch (v.getId()) {
                case R.id.btn_insight_expn://인사이트 자세히 보기
                    startActivitySingleTop(new Intent(this, InsightExpnMainActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.btn_my_info: //내정보보기
                    startActivitySingleTop(new Intent(this, MyGGAActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.l_store:
                    startActivitySingleTop(new Intent(this, StoreWebActivity.class).putExtra(KeyNames.KEY_NAME_URL, StoreInfo.STORE_PURCHASE_URL), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.l_point_detail: //블루멤버스 사용 가능 포인트
                    startActivitySingleTop(new Intent(this, MyGMembershipActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.l_mobility_care: //혜택 쿠폰
                    startActivitySingleTop(new Intent(this, MyGCouponActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.btn_status:
                    PrivilegeVO data = (PrivilegeVO) v.getTag(R.id.item);

                    if(data != null) {
                        if("EQ900".equals(data.getMdlNm()) || "G90".equals(data.getMdlNm()) || "G80".equals(data.getMdlNm())) {
                            startActivitySingleTop(new Intent(this, MyGPrivilegeStateActivity.class).putExtra(KeyNames.KEY_NAME_PRIVILEGE_VO, data), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                        } else {
                            String url = data.getServiceUrl();
                            goPrivilege(v.getId(), url);
                        }
                    }

                    break;
                case R.id.btn_benefit:
                case R.id.btn_apply:
                    String url = v.getTag(R.id.url).toString();
                    goPrivilege(v.getId(), url);
                    break;

                case R.id.btn_car_list: //프리빌리지 차량목록
                    startActivitySingleTop(new Intent(this, MyGPrivilegeApplyActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.tv_integration_gs: //gs칼텍스 연동하기
                    oilView.reqIntegrateOil(OilPointVO.OIL_CODE_GSCT);
                    break;
                case R.id.tv_integration_ho: //hyundai oilbank 연동하기
                    oilView.reqIntegrateOil(OilPointVO.OIL_CODE_HDOL);
                    break;
                case R.id.tv_integration_sk: //sk에너지 연동하기
                    oilView.reqIntegrateOil(OilPointVO.OIL_CODE_SKNO);
                    break;
                case R.id.tv_integration_soil: //S-OIL 연동하기
                    oilView.reqIntegrateOil(OilPointVO.OIL_CODE_SOIL);
                    break;
                case R.id.btn_barcode_gs: //GS 바코드, 포인트 화면 이동
                    startActivitySingleTop(new Intent(this, MyGOilPointActivity.class).putExtra(OilCodes.KEY_OIL_CODE, OilPointVO.OIL_CODE_GSCT), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.btn_barcode_ho: //hyundai oilbank 바코드, 포인트 화면 이동
                    startActivitySingleTop(new Intent(this, MyGOilPointActivity.class).putExtra(OilCodes.KEY_OIL_CODE, OilPointVO.OIL_CODE_HDOL), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.btn_barcode_sk: //sk 에너지 바코드, 포인트 화면 이동
                    startActivitySingleTop(new Intent(this, MyGOilPointActivity.class).putExtra(OilCodes.KEY_OIL_CODE, OilPointVO.OIL_CODE_SKNO), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.btn_barcode_soil: //S-OIL 바코드, 포인트 화면 이동
                    startActivitySingleTop(new Intent(this, MyGOilPointActivity.class).putExtra(OilCodes.KEY_OIL_CODE, OilPointVO.OIL_CODE_SOIL), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.btn_integration_michael: //마이클 연동하기
                    //TODO 정책 결정 안됨
                    break;
                case R.id.btn_integration_owon: //오원 연동하기
                    //TODO 정책 결정 안됨
                    break;
                case R.id.btn_integration_wiper: //와이퍼 연동하기
                    //TODO 정책 결정 안됨
                    break;
                case R.id.btn_release_michael: //마이클 해제하기
                    //TODO 정책 결정 안됨
                    break;
                case R.id.btn_release_owon: //오원 해제하기
                    //TODO 정책 결정 안됨
                    break;
                case R.id.btn_release_wiper: //와이퍼 해제하기
                    //TODO 정책 결정 안됨
                    break;
                case R.id.btn_call: //전화 상담
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL + ui.tvCenterMsg4.getText().toString())));
                    break;
                case R.id.l_terms_1://공지사항
                    startActivitySingleTop(new Intent(this, MyGNotiActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.l_terms_2://이용약관
//                    mypViewModel.reqMYP8001(new MYP_8001.Request(APPIAInfo.MG01.getId(), TERM_SERVICE_JOIN_GRA0001));
                    startActivitySingleTop(new Intent(this, MyGTermsActivity.class).putExtra(MyGTermsActivity.TERMS_CODE, TERM_SERVICE_JOIN_GRA0001), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.l_terms_3://개인정보처리방침
//                    mypViewModel.reqMYP8001(new MYP_8001.Request(APPIAInfo.MG01.getId(), TERM_SERVICE_JOIN_GRA0002));
                    startActivitySingleTop(new Intent(this, MyGTermsActivity.class).putExtra(MyGTermsActivity.TERMS_CODE, TERM_SERVICE_JOIN_GRA0002), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.l_terms_4://오픈소스 라이선스
                    startActivitySingleTop(new Intent(this, MyGTermsActivity.class).putExtra(MyGTermsActivity.TERMS_CODE, MyGTermsActivity.TERMS_6000), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.l_terms_5://버전 정보
                    startActivitySingleTop(new Intent(this, MyGVersioniActivity.class), 0,VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
//                case R.id.l_app_connected://커넥티트 아이콘
//                case R.id.l_app_digitalkey://디지털 키 아이콘
//                case R.id.l_app_carpay://카페이 아이콘
//                case R.id.l_app_cam://빌트인캠 아이콘
//                    PackageUtil.runApp(this, v.getTag().toString());
//                    break;

                case R.id.iv_app:
                    PackageUtil.runApp(this, v.getTag(R.id.url).toString());
                    break;

                case R.id.tv_user_retry://사용자 정보 다시 시도
                    mypViewModel.reqMYP0001(new MYP_0001.Request(APPIAInfo.MG01.getId()));
                    break;
                case R.id.tv_point_retry:
                    mypViewModel.reqMYP1003(new MYP_1003.Request(APPIAInfo.MG01.getId()));
                    break;
                case R.id.tv_expn_retry:
                    reqInsightExpn();
                    break;
                case R.id.tv_oil_retry:
                    mypViewModel.reqMYP1006(new MYP_1006.Request(APPIAInfo.MG01.getId()));
                    break;
                case R.id.iv_titlebar_img_btn:
                    try {
                        loginChk(StoreInfo.STORE_CART_URL, mypViewModel.getUserInfoFromDB().getCustGbCd());
                    }catch (Exception ignore){

                    }
                    break;
            }
        }

    }


    private void goPrivilege(int id, String url) {
        if(!TextUtils.isEmpty(url.trim())){
            int titleId=0;
            switch (id){
                case R.id.btn_status:
                    titleId = R.string.mg_prvi01_word_1_2;
                    break;
                case R.id.btn_benefit:
                    titleId = R.string.mg_prvi01_word_1_3;
                    break;
                case R.id.btn_apply:
                    titleId = R.string.mg_prvi01_word_1;
                    break;
            }
            startActivitySingleTop(new Intent(this, GAWebActivity.class).putExtra(KeyNames.KEY_NAME_URL, url).putExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID, titleId), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
        }else{
            SnackBarUtil.show(this, "페이지 정보가 존재하지 않습니다.\n잠시 후 다시 시도해 주십시오.");
        }
    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if(resultCode == ResultCodes.REQ_CODE_OIL_INTEGRATION_SUCCESS.getCode()){
//            exitPage(data, resultCode);
//        }
//    }
}
