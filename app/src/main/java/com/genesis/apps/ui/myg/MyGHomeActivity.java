package com.genesis.apps.ui.myg;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.OilCodes;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.MYP_0001;
import com.genesis.apps.comm.model.gra.api.MYP_1003;
import com.genesis.apps.comm.model.gra.api.MYP_1005;
import com.genesis.apps.comm.model.gra.api.MYP_1006;
import com.genesis.apps.comm.model.vo.OilPointVO;
import com.genesis.apps.comm.model.vo.PrivilegeVO;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.databinding.ActivityMygHomeBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.view.listener.ViewPressEffectHelper;
import com.genesis.apps.ui.myg.view.OilView;
import com.google.gson.Gson;

import java.util.Locale;

import androidx.lifecycle.ViewModelProvider;

public class MyGHomeActivity extends SubActivity<ActivityMygHomeBinding> {

    private MYPViewModel mypViewModel;
    private OilView oilView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_home);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        reqData();
        setIvEffect();
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
    }

    @Override
    public void setObserver() {

        mypViewModel.getRES_MYP_0001().observe(this, result -> {

            switch (result.status) {
                case SUCCESS:
                    ui.tvName.setText(String.format(Locale.getDefault(), getString(R.string.word_home_23), result.data.getMbrNm()));
                    ui.tvMail.setText(result.data.getCcspEmail());
                    break;
                case LOADING:
                    break;
                case ERROR:
                    break;
            }
        });

        mypViewModel.getRES_MYP_1003().observe(this, result -> {
            switch (result.status) {
                case SUCCESS:
                    ui.tvPoint.setText(String.format(Locale.getDefault(), getString(R.string.word_home_24), StringUtil.getDigitGrouping(Integer.parseInt(result.data.getBludMbrPoint()))));
                    break;
                case LOADING:
                    break;
                case ERROR:
                    break;
            }
        });

        mypViewModel.getRES_MYP_1005().observe(this, result -> {
            switch (result.status) {
                case SUCCESS:
                    setPrivilegeLayout(result.data);
                    break;
                case LOADING:
                    break;
                case ERROR:
                    break;
            }
        });

        mypViewModel.getRES_MYP_1006().observe(this, result -> {

            String test = "{\n" +
                    "  \"rsltCd\": \"0000\",\n" +
                    "  \"rsltMsg\": \"성공\",\n" +
                    "  \"oilRfnPontList\": [\n" +
                    "    {\n" +
                    "      \"oilRfnCd\": \"HDOL\",\n" +
                    "      \"oilRfnNm\": \"현대오일뱅크\",\n" +
                    "      \"rgstYn\": \"N\",\n" +
                    "      \"cardNo\": \"1111111111111111\",\n" +
                    "      \"pont\": \"1000000\",\n" +
                    "      \"errMsg\": \"\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"oilRfnCd\": \"GSCT\",\n" +
                    "      \"oilRfnNm\": \"GS칼텍스\",\n" +
                    "      \"rgstYn\": \"N\",\n" +
                    "      \"cardNo\": \"2222222222222222\",\n" +
                    "      \"pont\": \"2000000\",\n" +
                    "      \"errMsg\": \"\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"oilRfnCd\": \"SOIL\",\n" +
                    "      \"oilRfnNm\": \"S-OIL\",\n" +
                    "      \"rgstYn\": \"N\",\n" +
                    "      \"cardNo\": \"3333333333333333\",\n" +
                    "      \"pont\": \"3000000\",\n" +
                    "      \"errMsg\": \"\"\n" +
                    "    },\n" +
                    "    {\n" +
                    "      \"oilRfnCd\": \"SKNO\",\n" +
                    "      \"oilRfnNm\": \"SK 이노베이션\",\n" +
                    "      \"rgstYn\": \"N\",\n" +
                    "      \"cardNo\": \"4444444444444444\",\n" +
                    "      \"pont\": \"4000000\",\n" +
                    "      \"errMsg\": \"\"\n" +
                    "    }\n" +
                    "  ]\n" +
                    "}";

            MYP_1006.Response sample = new Gson().fromJson(test, MYP_1006.Response.class);

            oilView.setOilLayout(sample);


//            switch (result.status){
//                case SUCCESS:
//                    oilView.setOilLayout(result.data);
//                    break;
//                case LOADING:
//                    break;
//                case ERROR:
//                    break;
//            }
        });


    }

    @Override
    public void getDataFromIntent() {

    }

    private void initView() {
        oilView = new OilView(ui.lOil, v -> onSingleClickListener.onClick(v));
        ui.setActivity(this);
        ui.setView(oilView);
    }

    //TODO 로딩 처리 필요..
    private void reqData() {
        mypViewModel.reqMYP0001(new MYP_0001.Request(APPIAInfo.MG01.getId()));
        mypViewModel.reqMYP1003(new MYP_1003.Request(APPIAInfo.MG01.getId()));
        //TODO 차대번호 확인해서 넣어줘야함 
        mypViewModel.reqMYP1005(new MYP_1005.Request(APPIAInfo.MG01.getId(), ""));
        mypViewModel.reqMYP1006(new MYP_1006.Request(APPIAInfo.MG01.getId()));
    }


    private void setPrivilegeLayout(MYP_1005.Response data) {

        if (data.getMbrshJoinYn().equalsIgnoreCase("N") || data.getPvilList().size() < 1) {
            ui.lPrivilege.setVisibility(View.GONE);
        } else {
            ui.lPrivilege.setVisibility(View.VISIBLE);

            if (data.getPvilList().size() == 1) {
                ui.btnCarList.setVisibility(View.GONE);

                switch (data.getPvilList().get(0).getJoinPsblCd()) {
                    case PrivilegeVO.JOIN_CODE_APPLY_POSSIBLE:
                        ui.btnStatus.setVisibility(View.GONE);
                        ui.btnBenefit.setVisibility(View.GONE);
                        ui.btnApply.setVisibility(View.VISIBLE);
                        break;
                    case PrivilegeVO.JOIN_CODE_APPLYED:
                        ui.btnStatus.setVisibility(View.VISIBLE);
                        ui.btnBenefit.setVisibility(View.VISIBLE);
                        ui.btnApply.setVisibility(View.GONE);
                        break;
                    default:
                        //TODO 정의 필요 임시로 프리빌리지 레이아웃이 안보이도록 처리;
                        ui.lPrivilege.setVisibility(View.GONE);
                        break;
                }
            } else {
                ui.btnCarList.setVisibility(View.VISIBLE);
                ui.btnStatus.setVisibility(View.GONE);
                ui.btnBenefit.setVisibility(View.GONE);
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

    @Override
    public void onClickCommon(final View v) {
        Log.v("test duplicate", "id:" + v.getId());
        if (v != null) {
            switch (v.getId()) {
                case R.id.btn_search:
                    startActivitySingleTop(new Intent(this, MyGMenuActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.btn_my_info: //내정보보기

                    break;
                case R.id.l_point: //블루멤버스 사용 가능 포인트
                    startActivitySingleTop(new Intent(this, MyGMembershipActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(),VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.l_mobility_care: //혜택 쿠폰
                    break;
                case R.id.btn_benefit: //프리빌리지 혜택
                    break;
                case R.id.btn_status: //프리빌리지 현황
                    break;
                case R.id.btn_car_list: //프리빌리지 차량목록
                    break;
                case R.id.btn_apply: //프리빌리지 신청하기
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
                    startActivitySingleTop(new Intent(this, MyGNotiActivity.class), 0,VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.l_terms_2://이용약관
                    startActivitySingleTop(new Intent(this, MyGTermsActivity.class).putExtra(MyGTermsActivity.TERMS_CODE, MyGTermsActivity.TERMS_1000), 0,VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.l_terms_3://개인정보처리방침
                    startActivitySingleTop(new Intent(this, MyGTermsActivity.class).putExtra(MyGTermsActivity.TERMS_CODE, MyGTermsActivity.TERMS_2000), 0,VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.l_terms_4://오픈소스 라이선스
                    startActivitySingleTop(new Intent(this, MyGTermsActivity.class).putExtra(MyGTermsActivity.TERMS_CODE, MyGTermsActivity.TERMS_6000), 0,VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.l_terms_5://버전 정보
                    startActivitySingleTop(new Intent(this, MyGVersioniActivity.class), 0,VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                    break;
                case R.id.l_app_connected://커넥티트 아이콘
                case R.id.l_app_digitalkey://디지털 키 아이콘
                case R.id.l_app_carpay://카페이 아이콘
                case R.id.l_app_cam://빌트인캠 아이콘
                    PackageUtil.runApp(this, v.getTag().toString());
                    break;

            }
        }

    }
}
