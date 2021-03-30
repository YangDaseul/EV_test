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
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.MYP_1006;
import com.genesis.apps.comm.model.api.gra.OIL_0003;
import com.genesis.apps.comm.model.constants.OilCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.OilPointVO;
import com.genesis.apps.comm.util.BarcodeUtil;
import com.genesis.apps.comm.util.PackageUtil;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.comm.viewmodel.OILViewModel;
import com.genesis.apps.databinding.ActivityMygOilPointBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.myg.view.OilView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.util.List;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_GSCT;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_HDOL;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_SOIL;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_JOIN_CODE_Y;

public class MyGOilPointActivity extends SubActivity<ActivityMygOilPointBinding> {
    private MYPViewModel mypViewModel;
    private OILViewModel oilViewModel;
    private OilView oilView;
    private String oilRfnCd;
    private ConstraintSet[] constraintSets = new ConstraintSet[OilCodes.values().length];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myg_oil_point);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initConstraintSets();
        oilView = new OilView(ui.lOil, v -> onSingleClickListener.onClick(v));
        ui.setView(oilView);
    }

    private void initConstraintSets() {
        for (int i = 0; i < OilCodes.values().length; i++) {
            constraintSets[i] = new ConstraintSet();
            constraintSets[i].clone(this, OilCodes.values()[i].getLayout());
        }
    }

    private void doTransition(int pos) {
        Transition changeBounds = new ChangeBounds();
        changeBounds.setInterpolator(new OvershootInterpolator());
        TransitionManager.beginDelayedTransition(ui.lOil.lParent);
        constraintSets[pos].applyTo(ui.lOil.lParent);

        switch (pos) {
            case 0:
                ui.lOil.lHo.setElevation(1);
                ui.lOil.lSoil.setElevation(2);

//                    ui.lOil.lHo.setPadding(15,0,15,20);
//                    ui.lOil.lSk.setPadding(15,0,15,20);
//                    ui.lOil.lSoil.setPadding(15,0,15,0);

                break;
            case 1:
                ui.lOil.lSoil.setElevation(1);
                ui.lOil.lGs.setElevation(2);

//                    ui.lOil.lSk.setPadding(15,0,15,20);
//                    ui.lOil.lSoil.setPadding(15,0,15,20);
//                    ui.lOil.lGs.setPadding(15,0,15,0);

                break;
//                case 2:
//                    ui.lOil.lSoil.setElevation(1);
//                    ui.lOil.lGs.setElevation(2);
//                    ui.lOil.lHo.setElevation(3);
//
////                    ui.lOil.lSoil.setPadding(15,0,15,20);
////                    ui.lOil.lGs.setPadding(15,0,15,20);
////                    ui.lOil.lHo.setPadding(15,0,15,0);
//                    break;
            case 2:
                ui.lOil.lGs.setElevation(1);
                ui.lOil.lHo.setElevation(2);

//                    ui.lOil.lGs.setPadding(15,0,15,20);
//                    ui.lOil.lHo.setPadding(15,0,15,20);
//                    ui.lOil.lSk.setPadding(15,0,15,0);
                break;


        }
    }


    private void setOilDetailView(List<OilPointVO> list) {
        if(list!=null) {
            initOilInfo(list);
            for (OilPointVO pointVO : list) {
                if (pointVO.getOilRfnCd().equalsIgnoreCase(oilRfnCd)) {
                    ui.ivCi.setImageResource(OilCodes.findCode(oilRfnCd).getSmallSrc());
                    ui.tvPoint.setText(StringUtil.getDigitGroupingString(TextUtils.isEmpty(pointVO.getPont()) ? "0" : pointVO.getPont()));
                    if(!TextUtils.isEmpty(pointVO.getCardNo())) {
                        ui.tvCardNo.setText(StringRe2j.replaceAll(pointVO.getCardNo(), getString(R.string.card_original), getString(R.string.card_mask)));
                    }else{
                        ui.tvCardNo.setText("");
                    }
                    setBarcode(pointVO.getCardNo());
                    break;
                }
            }
        }
    }

    private void setBarcode(String cardNo) {
        try {
            Bitmap bitmap = new BarcodeUtil().encodeAsBitmap(cardNo.replace("-", ""), BarcodeFormat.CODE_128, ui.ivBarcode.getWidth(), ui.ivBarcode.getHeight());
            ui.ivBarcode.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    private void initOilInfo(List<OilPointVO> list) {
        if (TextUtils.isEmpty(oilRfnCd)) {
            for (OilPointVO pointVO : list) {
                if (pointVO.getRgstYn().equalsIgnoreCase(OIL_JOIN_CODE_Y)) {
                    oilRfnCd = pointVO.getOilRfnCd();
                    break;
                }
            }
        }
    }


    @Override
    public void onClickCommon(View v) {

        switch (v.getId()) {
            case R.id.tv_integration_gs: //gs칼텍스 연동하기
                oilView.reqIntegrateOil(OIL_CODE_GSCT);
                break;
            case R.id.tv_integration_ho: //hyundai oilbank 연동하기
                oilView.reqIntegrateOil(OIL_CODE_HDOL);
                break;
//            case R.id.tv_integration_sk: //sk에너지 연동하기
//                oilView.reqIntegrateOil(OIL_CODE_SKNO);
//                break;
            case R.id.tv_integration_soil: //S-OIL 연동하기
                oilView.reqIntegrateOil(OIL_CODE_SOIL);
                break;
            case R.id.btn_release:
                MiddleDialog.dialogOilDisconnect(this, () -> {
                    oilViewModel.reqOIL0003(new OIL_0003.Request(APPIAInfo.MG_CON01.getId(), oilRfnCd)); //연동해제 요청
                }, () -> {

                });
                break;
            case R.id.btn_check_point:
                PackageUtil.runApp(this, OilCodes.findCode(oilRfnCd).getSchema());
                break;
            case R.id.btn_refresh:
                mypViewModel.reqMYP1006(new MYP_1006.Request(APPIAInfo.MG_CON01.getId()));
                break;
            case R.id.btn_barcode_soil:
            case R.id.btn_barcode_ho:
//            case R.id.btn_barcode_sk:
            case R.id.btn_barcode_gs:
                oilRfnCd = v.getTag().toString();
                doTransition(OilCodes.findCode(oilRfnCd).ordinal());
                setOilDetailView(mypViewModel.getRES_MYP_1006().getValue().data.getOilRfnPontList());
                break;
        }

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        mypViewModel = new ViewModelProvider(this).get(MYPViewModel.class);
        oilViewModel = new ViewModelProvider(this).get(OILViewModel.class);
    }

    @Override
    public void setObserver() {
        mypViewModel.getRES_MYP_1006().observe(this, responseNetUI -> {
            switch (responseNetUI.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if(responseNetUI.data!=null&&StringUtil.isValidString(responseNetUI.data.getRtCd()).equalsIgnoreCase("0000")) {
                        oilView.setOilLayout(responseNetUI.data);
                        doTransition(OilCodes.findCode(oilRfnCd).ordinal());
                        setOilDetailView(responseNetUI.data.getOilRfnPontList());
                        showProgressDialog(false);
                    }
                    break;
                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = responseNetUI.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (TextUtils.isEmpty(serverMsg))
                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });
        oilViewModel.getRES_OIL_0003().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase(RETURN_CODE_SUCC)) {
                        showProgressDialog(false);
                        exitPage(getString(R.string.mg_con01_p01_snackbar_1), ResultCodes.REQ_CODE_NORMAL.getCode());
                        break;
                    }
                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (TextUtils.isEmpty(serverMsg))
                            serverMsg = getString(R.string.mg_con01_p01_snackbar_2);
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });

//        oilViewModel.getRES_OIL_0005().observe(this, result -> {
//            switch (result.status) {
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    showProgressDialog(false);
//                    if (result.data != null && result.data.getRtCd().equalsIgnoreCase(RETURN_CODE_SUCC)) {
//                        mypViewModel.reqMYP1006(new MYP_1006.Request(APPIAInfo.MG01.getId()));
//                        SnackBarUtil.show(this, "연동이 완료되었습니다.");
//                        break;
//                    }
//                default:
//                    showProgressDialog(false);
//                    String serverMsg = "";
//                    try {
//                        serverMsg = result.data.getRtMsg();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    } finally {
//                        if (TextUtils.isEmpty(serverMsg))
//                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
//                        SnackBarUtil.show(this, serverMsg);
//                    }
//                    break;
//            }
//        });


    }

    @Override
    public void getDataFromIntent() {
        try {
            oilRfnCd = getIntent().getStringExtra(OilCodes.KEY_OIL_CODE);
            if (TextUtils.isEmpty(oilRfnCd)) {
                exitPage("정유소 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }
        } catch (Exception e) {
            e.printStackTrace();
            exitPage("정유소 정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mypViewModel.reqMYP1006(new MYP_1006.Request(APPIAInfo.MG_CON01.getId()));
    }
}
