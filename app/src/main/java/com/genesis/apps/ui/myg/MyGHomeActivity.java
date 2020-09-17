package com.genesis.apps.ui.myg;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.MYP_0001;
import com.genesis.apps.comm.model.gra.MYP_1003;
import com.genesis.apps.comm.model.gra.MYP_1005;
import com.genesis.apps.comm.model.gra.MYP_1006;
import com.genesis.apps.comm.model.gra.viewmodel.MYPViewModel;
import com.genesis.apps.comm.model.vo.OilPointVO;
import com.genesis.apps.comm.model.vo.PrivilegeVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ActivityMygHomeBinding;
import com.genesis.apps.databinding.ActivityMygVersionBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.view.listener.ViewPressEffectHelper;
import com.genesis.apps.ui.myg.view.OilView;

import java.util.List;
import java.util.Locale;

import static com.genesis.apps.comm.util.PackageUtil.isInstallApp;

public class MyGHomeActivity extends SubActivity<ActivityMygHomeBinding> {

    private MYPViewModel mypViewModel;
    private OilView oilView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_home);
        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
        oilView = new OilView(ui.lOil, v -> onClickEvent(v));
        ui.lTitle.title.setText(R.string.title_home);
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        ui.setView(oilView);

        observerData();
        setIvEffect();
    }

    private void observerData() {

        mypViewModel.getRES_MYP_0001().observe(this, responseNetUI -> {

            switch (responseNetUI.status){
                case SUCCESS:
                    ui.tvName.setText(String.format(Locale.getDefault(),getString(R.string.word_home_23), responseNetUI.data.getMbrNm()));
                    ui.tvMail.setText(responseNetUI.data.getCcspEmail());
                    break;
                case LOADING:
                    break;
                case ERROR:
                    break;
            }
        });

        mypViewModel.getRES_MYP_1003().observe(this, responseNetUI -> {
            switch (responseNetUI.status){
                case SUCCESS:
                    ui.tvPoint.setText(String.format(Locale.getDefault(),getString(R.string.word_home_24), StringUtil.getDigitGrouping(Integer.parseInt(responseNetUI.data.getBludMbrPoint()))));
                    break;
                case LOADING:
                    break;
                case ERROR:
                    break;
            }
        });

        mypViewModel.getRES_MYP_1005().observe(this, responseNetUI -> {
            switch (responseNetUI.status){
                case SUCCESS:
                    setPrivilegeLayout(responseNetUI.data);
                    break;
                case LOADING:
                    break;
                case ERROR:
                    break;
            }
        });

        mypViewModel.getRES_MYP_1006().observe(this, responseNetUI -> {
            switch (responseNetUI.status){
                case SUCCESS:
                    oilView.setOilLayout(responseNetUI.data);
                    break;
                case LOADING:
                    break;
                case ERROR:
                    break;
            }
        });




    }


    private void setPrivilegeLayout(MYP_1005.Response data) {

        if(data.getMbrshJoinYn().equalsIgnoreCase("N")||data.getPvilList().size()<1){
            ui.lPrivilege.setVisibility(View.GONE);
        }else{
            ui.lPrivilege.setVisibility(View.VISIBLE);

            if(data.getPvilList().size()==1){
                ui.btnCarList.setVisibility(View.GONE);

                switch (data.getPvilList().get(0).getJoinPsblCd()){
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
            }else{
                ui.btnCarList.setVisibility(View.VISIBLE);
                ui.btnStatus.setVisibility(View.GONE);
                ui.btnBenefit.setVisibility(View.GONE);
                ui.btnApply.setVisibility(View.GONE);
            }
        }


    }

//    private void setOilLayout(MYP_1006.Response data) {
//
//        if(data.getOilRfnPontList()==null||data.getOilRfnPontList().size()<1){
//            ui.btnBarcodeGs.setVisibility(View.GONE);
//            ui.tvPointGs.setVisibility(View.GONE);
//            ui.btnBarcodeHo.setVisibility(View.GONE);
//            ui.tvPointHo.setVisibility(View.GONE);
//            ui.btnBarcodeSk.setVisibility(View.GONE);
//            ui.tvPointSk.setVisibility(View.GONE);
//            ui.btnBarcodeSoil.setVisibility(View.GONE);
//            ui.tvPointSoil.setVisibility(View.GONE);
//
//            ui.tvIntegrationGs.setVisibility(View.VISIBLE);
//            ui.tvIntegrationHo.setVisibility(View.VISIBLE);
//            ui.tvIntegrationSk.setVisibility(View.VISIBLE);
//            ui.tvIntegrationSoil.setVisibility(View.VISIBLE);
//        }else{
//            for(int i=0; i<data.getOilRfnPontList().size(); i++){
//                switch (data.getOilRfnPontList().get(i).getOilRfnCd()){
//                    case OilPointVO.OIL_CODE_SOIL:
//                        setOilView(data.getOilRfnPontList().get(i), ui.btnBarcodeSoil, ui.tvPointSoil, ui.tvIntegrationSoil);
//                        break;
//                    case OilPointVO.OIL_CODE_GSCT:
//                        setOilView(data.getOilRfnPontList().get(i), ui.btnBarcodeGs, ui.tvPointGs, ui.tvIntegrationGs);
//                        break;
//                    case OilPointVO.OIL_CODE_HDOL:
//                        setOilView(data.getOilRfnPontList().get(i), ui.btnBarcodeHo, ui.tvPointHo, ui.tvIntegrationHo);
//                        break;
//                    case OilPointVO.OIL_CODE_SKNO:
//                        setOilView(data.getOilRfnPontList().get(i), ui.btnBarcodeSk, ui.tvPointSk, ui.tvIntegrationSk);
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
//    }
//
//    private void setOilView(OilPointVO data, View barcode, TextView point, View integration){
//        switch (data.getRgstYn()){
//            case OilPointVO.OIL_JOIN_CODE_Y:
//                barcode.setVisibility(View.VISIBLE);
//                point.setVisibility(View.VISIBLE);
//                point.setText(String.format(Locale.getDefault(),getString(R.string.word_home_24), StringUtil.getDigitGrouping(Integer.parseInt(data.getPont()))));
//                integration.setVisibility(View.GONE);
//                break;
//            case OilPointVO.OIL_JOIN_CODE_R:
//            case OilPointVO.OIL_JOIN_CODE_N:
//            default:
//                barcode.setVisibility(View.GONE);
//                point.setVisibility(View.GONE);
//                integration.setVisibility(View.VISIBLE);
//                break;
//        }
//    }
//
//    private void reqIntegrateOil(String rfnCd){
//        List<OilPointVO> list = mypViewModel.getRES_MYP_1006().getValue().data.getOilRfnPontList();
//        for(int i=0; i<list.size(); i++){
//            if(rfnCd.equalsIgnoreCase(list.get(i).getOilRfnCd())){
//
//                switch (list.get(i).getRgstYn()){
//                    case OilPointVO.OIL_JOIN_CODE_R:
//                        //TODO R일 경우 처리
//                        break;
//                    case OilPointVO.OIL_JOIN_CODE_N:
//                        //TODO N일 경우 처리
//                        break;
//                    default:
//                        break;
//                }
//            }
//        }
//    }


    private void setIvEffect() {
        ViewPressEffectHelper.attach(ui.lAppConnected);
        ViewPressEffectHelper.attach(ui.lAppDigitalkey);
        ViewPressEffectHelper.attach(ui.lAppCarpay);
        ViewPressEffectHelper.attach(ui.lAppCam);
    }

    @Override
    public void onSingleClick(final View v) {
        Log.v("test duplicate","id:"+v.getId());
        if (v != null) {
            switch (v.getId()) {
                case R.id.btn_search:
                    startActivitySingleTop(new Intent(this, MyGMenuActivity.class), 0);
                    break;
                case R.id.btn_my_info: //내정보보기

                    break;
                case R.id.l_point: //블루멤버스 사용 가능 포인트

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
                    oilView.reqIntegrateOil(mypViewModel.getRES_MYP_1006().getValue().data.getOilRfnPontList(), OilPointVO.OIL_CODE_GSCT);
                    break;
                case R.id.tv_integration_ho: //hyundai oilbank 연동하기
                    oilView.reqIntegrateOil(mypViewModel.getRES_MYP_1006().getValue().data.getOilRfnPontList(),OilPointVO.OIL_CODE_HDOL);
                    break;
                case R.id.tv_integration_sk: //sk에너지 연동하기
                    oilView.reqIntegrateOil(mypViewModel.getRES_MYP_1006().getValue().data.getOilRfnPontList(),OilPointVO.OIL_CODE_SKNO);
                    break;
                case R.id.tv_integration_soil: //S-OIL 연동하기
                    oilView.reqIntegrateOil(mypViewModel.getRES_MYP_1006().getValue().data.getOilRfnPontList(),OilPointVO.OIL_CODE_SOIL);
                    break;
                case R.id.btn_barcode_gs: //GS 바코드, 포인트 화면 이동
                    break;
                case R.id.btn_barcode_ho: //hyundai oilbank 바코드, 포인트 화면 이동
                    break;
                case R.id.btn_barcode_sk: //sk 에너지 바코드, 포인트 화면 이동
                    break;
                case R.id.btn_barcode_soil: //S-OIL 바코드, 포인트 화면 이동
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
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL+ui.tvCenterMsg4.getText().toString())));
                    break;
                case R.id.l_terms_1://공지사항
                    startActivitySingleTop(new Intent(this, MyGNotiActivity.class), 0);
                    break;
                case R.id.l_terms_2://이용약관
                    startActivitySingleTop(new Intent(this, MyGTermsActivity.class).putExtra(MyGTermsActivity.TERMS_CODE, MyGTermsActivity.TERMS_1000), 0);
                    break;
                case R.id.l_terms_3://개인정보처리방침
                    startActivitySingleTop(new Intent(this, MyGTermsActivity.class).putExtra(MyGTermsActivity.TERMS_CODE, MyGTermsActivity.TERMS_2000), 0);
                    break;
                case R.id.l_terms_4://오픈소스 라이선스
                    startActivitySingleTop(new Intent(this, MyGTermsActivity.class).putExtra(MyGTermsActivity.TERMS_CODE, MyGTermsActivity.TERMS_6000), 0);
                    break;
                case R.id.l_terms_5://버전 정보
                    startActivitySingleTop(new Intent(this, MyGVersioniActivity.class), 0);
                    break;
                case R.id.l_app_connected://커넥티트 아이콘
                case R.id.l_app_digitalkey://디지털 키 아이콘
                case R.id.l_app_carpay://카페이 아이콘
                case R.id.l_app_cam://빌트인캠 아이콘
                    runApp(v.getTag().toString());
                    break;

            }
        }

    }


    /**
     * @brief 패키지명에 해당하는 앱 실행
     * @param pakageName 앱 패키지 명
     */
    private void runApp(String pakageName) {
        if(isInstallApp(this, pakageName)) {
            Intent intent = getPackageManager().getLaunchIntentForPackage(pakageName);
            if(intent != null) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }
        else {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + pakageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + pakageName)));
            }
        }
    }

}
