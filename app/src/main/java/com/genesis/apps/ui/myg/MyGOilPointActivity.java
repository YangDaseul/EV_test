package com.genesis.apps.ui.myg;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.RequestCodes;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.MYP_1006;
import com.genesis.apps.comm.model.gra.OIL_0003;
import com.genesis.apps.comm.model.gra.viewmodel.MYPViewModel;
import com.genesis.apps.comm.model.gra.viewmodel.OILViewModel;
import com.genesis.apps.comm.model.vo.OilPointVO;
import com.genesis.apps.comm.util.BarcodeUtil;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ActivityMygOilPointBinding;
import com.genesis.apps.databinding.ActivityMygVersionBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.myg.view.OilView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import androidx.lifecycle.ViewModelProvider;

import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_GSCT;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_HDOL;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_SKNO;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_SOIL;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_JOIN_CODE_Y;

public class MyGOilPointActivity extends SubActivity<ActivityMygOilPointBinding> {

    private MYPViewModel mypViewModel;
    private OilView oilView;
    private String selectOil;


    enum OilInfo {
        HDOL(OIL_CODE_HDOL,0, "com.hyundaioilbank.android"),
        GSCT(OIL_CODE_GSCT,0, "kr.co.gscaltex.gsnpoint"),
        SOIL(OIL_CODE_SOIL,0, "com.soilbonus.goodoilfamily"),
        SKNO(OIL_CODE_SKNO,0, "com.ske.phone.epay");
        private String code;
        private int src;
        private String schema;
        OilInfo(String code, int src, String schema){
            this.code = code;
            this.src = src;
        }
        public static OilInfo findCode(String code){
            return Arrays.asList(OilInfo.values()).stream().filter(data->data.getCode().equalsIgnoreCase(code)).findAny().orElse(HDOL);
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
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_oil_point);
        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
        oilView = new OilView(ui.lOil, v -> onClickEvent(v));
        ui.setLifecycleOwner(this);
        ui.setView(oilView);
        observerData();
    }

    private void setOilDetailView(List<OilPointVO> list){
        initOilInfo(list);
        for(OilPointVO pointVO : list){
            if(pointVO.getOilRfnCd().equalsIgnoreCase(selectOil)){
                ui.ivCi.setImageResource(OilInfo.findCode(selectOil).getSrc());
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
        if(TextUtils.isEmpty(selectOil)){
            for(OilPointVO pointVO : list){
                if(pointVO.getRgstYn().equalsIgnoreCase(OIL_JOIN_CODE_Y)){
                    selectOil = pointVO.getOilRfnCd();
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
                mypViewModel.reqOIL0003(new OIL_0003.Request(APPIAInfo.MG_CON01.getId(), selectOil)); //연동해제 요청
                break;
            case R.id.btn_check_point:
                PackageUtil.runApp(this, OilInfo.findCode(selectOil).getSchema());
                break;

        }

    }

}
