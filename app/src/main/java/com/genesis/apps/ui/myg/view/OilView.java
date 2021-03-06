package com.genesis.apps.ui.myg.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.gra.MYP_1006;
import com.genesis.apps.comm.model.constants.OilCodes;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.OilPointVO;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ViewOilBinding;
import com.genesis.apps.ui.common.activity.BaseActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.view.listener.OnItemClickListener;
import com.genesis.apps.ui.common.view.listener.ViewPressEffectHelper;
import com.genesis.apps.ui.myg.MyGOilIntegrationActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class OilView {

    private ViewOilBinding ui;
    private Context context;
    private MYP_1006.Response data;
    public OnItemClickListener onClickListener;

    public OilView(ViewOilBinding ui, OnItemClickListener onClickListener) {
        this.ui = ui;
        this.context = ui.getRoot().getContext();
        this.onClickListener = onClickListener;
        setOilLayout(null);
    }

    public void showPorgessBar(boolean isShow){
        if(isShow) {
            ui.btnBarcodeGs.setVisibility(View.GONE);
            ui.tvPointGs.setVisibility(View.GONE);
            ui.btnBarcodeHo.setVisibility(View.GONE);
            ui.tvPointHo.setVisibility(View.GONE);
            ui.btnBarcodeSoil.setVisibility(View.GONE);
            ui.tvPointSoil.setVisibility(View.GONE);
            ui.tvIntegrationGs.setVisibility(View.GONE);
            ui.tvIntegrationHo.setVisibility(View.GONE);
            ui.tvIntegrationSoil.setVisibility(View.GONE);
            ui.pGs.setVisibility(View.VISIBLE);
            ui.pHo.setVisibility(View.VISIBLE);
            ui.pSoil.setVisibility(View.VISIBLE);
            ui.pGs.show();
            ui.pHo.show();
            ui.pSoil.show();
        }else{
            ui.pGs.hide();
            ui.pHo.hide();
            ui.pSoil.hide();
        }
    }

    public void showErrorLayout(int visibility) {
        ui.lError.setVisibility(visibility);
    }


    public void setOilLayout(MYP_1006.Response data) {

        this.data = data;

        ui.tvOil.setText((data != null && data.getOilRfnPontList() != null) ? data.getOilRfnPontList().size() + "" : "0");

        ViewPressEffectHelper.attaches(ui.tvIntegrationGs, ui.tvIntegrationHo, ui.tvIntegrationSoil
                , ui.btnBarcodeGs, ui.btnBarcodeHo,  ui.btnBarcodeSoil);

        if (data == null || data.getOilRfnPontList() == null || data.getOilRfnPontList().size() < 1) {

            ui.btnBarcodeGs.setVisibility(View.GONE);
            ui.tvPointGs.setVisibility(View.GONE);
            ui.btnBarcodeHo.setVisibility(View.GONE);
            ui.tvPointHo.setVisibility(View.GONE);
            ui.btnBarcodeSoil.setVisibility(View.GONE);
            ui.tvPointSoil.setVisibility(View.GONE);
            ui.tvIntegrationGs.setVisibility(View.VISIBLE);
            ui.tvIntegrationHo.setVisibility(View.VISIBLE);
            ui.tvIntegrationSoil.setVisibility(View.VISIBLE);


            //            //TODO 2021-01-04 ?????? ?????? ?????? ??????
//            ui.lParent.setVisibility(View.GONE);
//            ui.lGs.setVisibility(View.GONE);
//            ui.lHo.setVisibility(View.GONE);
//            ui.lSoil.setVisibility(View.GONE);
//            ui.lSk.setVisibility(View.GONE);
        } else {
            setIntegrationView(data.getOilRfnPontList());
            for (int i = 0; i < data.getOilRfnPontList().size(); i++) {
                switch (data.getOilRfnPontList().get(i).getOilRfnCd()) {
                    case OilPointVO.OIL_CODE_SOIL:
                        ui.lSoil.setVisibility(View.VISIBLE);
                        setOilView(data.getOilRfnPontList().get(i), ui.btnBarcodeSoil, ui.tvPointSoil, ui.tvIntegrationSoil);
                        break;
                    case OilPointVO.OIL_CODE_GSCT:
                        ui.lGs.setVisibility(View.VISIBLE);
                        setOilView(data.getOilRfnPontList().get(i), ui.btnBarcodeGs, ui.tvPointGs, ui.tvIntegrationGs);
                        break;
                    case OilPointVO.OIL_CODE_HDOL:
                    default:
                        ui.lHo.setVisibility(View.VISIBLE);
                        setOilView(data.getOilRfnPontList().get(i), ui.btnBarcodeHo, ui.tvPointHo, ui.tvIntegrationHo);
                        break;
                }
            }
        }
    }

    /**
     * ???????????? ???????????? ?????? ??? ???????????? ?????? ?????? ?????? ??????
     * ???????????? ?????? ?????? ???????????? ????????? ????????? ?????????
     * @param oilRfnPontList
     */
    private void setIntegrationView(List<OilPointVO> oilRfnPontList) {
        if(oilRfnPontList==null||oilRfnPontList.size()<1){
            ui.tvIntegrationGs.setVisibility(View.VISIBLE);
            ui.tvIntegrationHo.setVisibility(View.VISIBLE);
            ui.tvIntegrationSoil.setVisibility(View.VISIBLE);
        }else{
            if(oilRfnPontList.stream().filter(data->data.getOilRfnCd().equalsIgnoreCase(OilPointVO.OIL_CODE_SOIL)).findAny().orElse(null)==null){
                ui.tvIntegrationSoil.setVisibility(View.VISIBLE);
            }
            if(oilRfnPontList.stream().filter(data->data.getOilRfnCd().equalsIgnoreCase(OilPointVO.OIL_CODE_HDOL)).findAny().orElse(null)==null){
                ui.tvIntegrationHo.setVisibility(View.VISIBLE);
            }
            if(oilRfnPontList.stream().filter(data->data.getOilRfnCd().equalsIgnoreCase(OilPointVO.OIL_CODE_GSCT)).findAny().orElse(null)==null){
                ui.tvIntegrationGs.setVisibility(View.VISIBLE);
            }
        }
    }


    public void setOilView(OilPointVO data, View barcode, TextView point, View integration) {
        switch (data.getRgstYn()) {
            case OilPointVO.OIL_JOIN_CODE_Y:
                barcode.setVisibility(View.VISIBLE);
                point.setVisibility(View.VISIBLE);
                point.setText(String.format(Locale.getDefault(), context.getString(R.string.word_home_24), StringUtil.getDigitGroupingString(data.getPont())));
                integration.setVisibility(View.GONE);
                break;
            case OilPointVO.OIL_JOIN_CODE_R:
            case OilPointVO.OIL_JOIN_CODE_N:
            default:
                barcode.setVisibility(View.GONE);
                point.setVisibility(View.GONE);
                integration.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void reqIntegrateOil(String rfnCd) {
        List<OilPointVO> list = new ArrayList<>();

        try {
            list.addAll(data.getOilRfnPontList());
        } catch (Exception e) {
            list = new ArrayList<>();
        }

        //??????????????? ???????????? ??????????????? ????????? ????????? ??????
        if (list == null || list.size() == 0) {
            list = new ArrayList<>();
            list.add(new OilPointVO(OilPointVO.OIL_CODE_HDOL, "", OilPointVO.OIL_JOIN_CODE_N, "", "", ""));
            list.add(new OilPointVO(OilPointVO.OIL_CODE_GSCT, "", OilPointVO.OIL_JOIN_CODE_N, "", "", ""));
            list.add(new OilPointVO(OilPointVO.OIL_CODE_SOIL, "", OilPointVO.OIL_JOIN_CODE_N, "", "", ""));
            list.add(new OilPointVO(OilPointVO.OIL_CODE_SKNO, "", OilPointVO.OIL_JOIN_CODE_N, "", "", ""));
        }


        for (int i = 0; i < list.size(); i++) {
            if (rfnCd.equalsIgnoreCase(list.get(i).getOilRfnCd())) {
                if(TextUtils.isEmpty(list.get(i).getErrMsg())) {

                    ((BaseActivity) context).startActivitySingleTop(new Intent(context, MyGOilIntegrationActivity.class)
                                    .putExtra(OilCodes.KEY_OIL_CODE, rfnCd)
                                    .putExtra(OilCodes.KEY_OIL_RGSTYN, StringUtil.isValidString(list.get(i).getRgstYn()))
                            , RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);

                }else{
                    try {
                        if (((Activity) ui.getRoot().getContext() != null)) {
                            MiddleDialog.dialogOilReject(((Activity) ui.getRoot().getContext()), list.get(i).getErrMsg(), null);
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

//                switch (list.get(i).getRgstYn()) {
//                    case OilPointVO.OIL_JOIN_CODE_R:
//                        oilViewModel.reqOIL0005(new OIL_0005.Request(APPIAInfo.MG01.getId(), rfnCd));
//                        break;
//                    case OilPointVO.OIL_JOIN_CODE_N:
//                        ((BaseActivity) context).startActivitySingleTop(new Intent(context, MyGOilIntegrationActivity.class)
//                                .putExtra(OilCodes.KEY_OIL_CODE, rfnCd)
//                                .putExtra(OilCodes.KEY_OIL_RGSTYN, StringUtil.isValidString(list.get(i).getRgstYn()))
//                                , RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//                        break;
//                    default:
//                        break;
//                }
            }
        }
    }

}
