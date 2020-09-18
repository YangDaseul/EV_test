package com.genesis.apps.ui.myg;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.constraintlayout.widget.ConstraintSet;
import androidx.lifecycle.ViewModelProvider;
import androidx.transition.ChangeBounds;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.ResultCodes;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.MYP_1006;
import com.genesis.apps.comm.model.gra.OIL_0003;
import com.genesis.apps.comm.model.gra.viewmodel.MYPViewModel;
import com.genesis.apps.comm.model.vo.OilPointVO;
import com.genesis.apps.comm.util.BarcodeUtil;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ActivityMygOilPointBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.myg.view.OilView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.util.Arrays;
import java.util.List;

import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_GSCT;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_HDOL;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_SKNO;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_SOIL;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_JOIN_CODE_Y;

public class MyGOilPointActivity extends SubActivity<ActivityMygOilPointBinding> {
    private MYPViewModel mypViewModel;
    private OilView oilView;
    public static final String KEY_OIL_CODE="oilRfnCd";
    private String oilRfnCd;
    private ConstraintSet[] constraintSets = new ConstraintSet[OilInfo.values().length];
    enum OilInfo {
        GSCT(OIL_CODE_GSCT,0, "kr.co.gscaltex.gsnpoint" ,R.layout.view_oil_1_1),
        HDOL(OIL_CODE_HDOL,0, "com.hyundaioilbank.android",R.layout.view_oil_1_2),
        SKNO(OIL_CODE_SKNO,0, "com.ske.phone.epay",R.layout.view_oil_1_3),
        SOIL(OIL_CODE_SOIL,0, "com.soilbonus.goodoilfamily",R.layout.view_oil_1_4);
        private String code;
        private int src;
        private String schema;
        private int layout;
        OilInfo(String code, int src, String schema, int layout){
            this.code = code;
            this.src = src;
            this.schema = schema;
            this.layout = layout;
        }
        public static OilInfo findCode(String code){
            return Arrays.asList(OilInfo.values()).stream().filter(data->data.getCode().equalsIgnoreCase(code)).findAny().orElse(GSCT);
        }

        public int getSrc() {
            return src;
        }

        public void setSrc(int src) {
            this.src = src;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getSchema() {
            return schema;
        }

        public void setSchema(String schema) {
            this.schema = schema;
        }

        public int getLayout() {
            return layout;
        }

        public void setLayout(int layout) {
            this.layout = layout;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_oil_point);
        initConstraintSets();
        getOilCode();
        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
        oilView = new OilView(ui.lOil, v -> onClickEvent(v));
        ui.setLifecycleOwner(this);
        ui.setView(oilView);
        observerData();
    }

    private void initConstraintSets() {
        for (int i = 0; i < OilInfo.values().length; i++) {
            constraintSets[i] = new ConstraintSet();

                constraintSets[i].clone(this, OilInfo.values()[i].getLayout());
        }
    }

    private void doTransition(int pos) {
            Transition changeBounds = new ChangeBounds();
            changeBounds.setInterpolator(new OvershootInterpolator());
            TransitionManager.beginDelayedTransition(ui.lOil.lParent);
            constraintSets[pos].applyTo(ui.lOil.lParent);
    }



    private void getOilCode(){
        try {
            oilRfnCd = getIntent().getStringExtra(KEY_OIL_CODE);
            if(TextUtils.isEmpty(oilRfnCd)){
                exitPage("정유소 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        }catch (Exception e){
            e.printStackTrace();
            exitPage("정유소 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }

    private void setOilDetailView(List<OilPointVO> list){
        initOilInfo(list);
        for(OilPointVO pointVO : list){
            if(pointVO.getOilRfnCd().equalsIgnoreCase(oilRfnCd)){
                ui.ivCi.setImageResource(OilInfo.findCode(oilRfnCd).getSrc());
                ui.tvPoint.setText(StringUtil.getDigitGrouping(TextUtils.isEmpty(pointVO.getPont()) ? 0 : Integer.parseInt(pointVO.getPont())));
                ui.tvCardNo.setText(pointVO.getCardNo());
                setBarcode(pointVO.getCardNo());
                break;
            }
        }
    }

    private void setBarcode(String cardNo){
        try {
            Bitmap bitmap = new BarcodeUtil().encodeAsBitmap(cardNo.replace("-","") , BarcodeFormat.CODE_128 , ui.ivBarcode.getWidth() , ui.ivBarcode.getHeight());
            ui.ivBarcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void initOilInfo(List<OilPointVO> list) {
        if(TextUtils.isEmpty(oilRfnCd)){
            for(OilPointVO pointVO : list){
                if(pointVO.getRgstYn().equalsIgnoreCase(OIL_JOIN_CODE_Y)){
                    oilRfnCd = pointVO.getOilRfnCd();
                    break;
                }
            }
        }
    }

    private void observerData() {
        mypViewModel.getRES_MYP_1006().observe(this, responseNetUI -> {
            switch (responseNetUI.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    doTransition(OilInfo.findCode(oilRfnCd).ordinal());
                    oilView.setOilLayout(responseNetUI.data);
                    setOilDetailView(responseNetUI.data.getOilRfnPontList());
                    showProgressDialog(false);
                    break;
                case ERROR:
                    showProgressDialog(false);
                    break;
            }
        });
        mypViewModel.getRES_OIL_0003().observe(this, responseNetUI -> {
            switch (responseNetUI.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    //TODO 연동 해지 완료 후 처리.. 스낵바를 띄워야하는데 이전 페이지에. .넘기면서 띄워야할듯
                    //BASEACTIVITY의 ONACTIVITYTRESULT에 스낵바 처리하는 부분을 공통을 넣어놓는게 나을듯..
                    showProgressDialog(false);
                    break;
                case ERROR:
                    showProgressDialog(false);
                    break;
            }
        });

        mypViewModel.reqMYP1006(new MYP_1006.Request(APPIAInfo.MG_CON01.getId()));
    }

    @Override
    public void onSingleClick(View v) {

        switch (v.getId()){
            case R.id.tv_integration_gs: //gs칼텍스 연동하기
                oilView.reqIntegrateOil(mypViewModel.getRES_MYP_1006().getValue().data.getOilRfnPontList(), OIL_CODE_GSCT);
                break;
            case R.id.tv_integration_ho: //hyundai oilbank 연동하기
                oilView.reqIntegrateOil(mypViewModel.getRES_MYP_1006().getValue().data.getOilRfnPontList(), OIL_CODE_HDOL);
                break;
            case R.id.tv_integration_sk: //sk에너지 연동하기
                oilView.reqIntegrateOil(mypViewModel.getRES_MYP_1006().getValue().data.getOilRfnPontList(), OIL_CODE_SKNO);
                break;
            case R.id.tv_integration_soil: //S-OIL 연동하기
                oilView.reqIntegrateOil(mypViewModel.getRES_MYP_1006().getValue().data.getOilRfnPontList(), OIL_CODE_SOIL);
                break;
            case R.id.btn_release:
                mypViewModel.reqOIL0003(new OIL_0003.Request(APPIAInfo.MG_CON01.getId(), oilRfnCd)); //연동해제 요청
                break;
            case R.id.btn_check_point:
                PackageUtil.runApp(this, OilInfo.findCode(oilRfnCd).getSchema());
                break;
            case R.id.btn_refresh:
                mypViewModel.reqMYP1006(new MYP_1006.Request(APPIAInfo.MG_CON01.getId()));
                break;
            case R.id.btn_barcode_soil:
            case R.id.btn_barcode_ho:
            case R.id.btn_barcode_sk:
            case R.id.btn_barcode_gs:
                oilRfnCd = v.getTag().toString();
                doTransition(OilInfo.findCode(oilRfnCd).ordinal());
                break;

//            case R.id.btn_barcode_soil:
//            case R.id.btn_barcode_ho:
//            case R.id.btn_barcode_sk:
//            case R.id.btn_barcode_gs:
//                oilRfnCd = v.getTag().toString();
//                mypViewModel.reqMYP1006(new MYP_1006.Request(APPIAInfo.MG_CON01.getId()));
//                break;

        }

    }

}
