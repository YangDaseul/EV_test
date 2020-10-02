package com.genesis.apps.ui.myg.view;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.OilCodes;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.gra.api.MYP_1006;
import com.genesis.apps.comm.model.vo.OilPointVO;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ViewOilBinding;
import com.genesis.apps.ui.common.activity.BaseActivity;
import com.genesis.apps.ui.common.view.listener.OnItemClickListener;
import com.genesis.apps.ui.myg.MyGOilIntegrationActivity;

import java.util.List;
import java.util.Locale;

public class OilView {

    private ViewOilBinding ui;
    private Context context;
    private MYP_1006.Response data;
    public OnItemClickListener onClickListener;

    public OilView(ViewOilBinding ui, OnItemClickListener onClickListener ){
        this.ui = ui;
        this.context = ui.getRoot().getContext();
        this.onClickListener = onClickListener;
        setOilLayout(null);
    }

    public void setOilLayout(MYP_1006.Response data) {

        this.data = data;
        if(data==null||data.getOilRfnPontList()==null||data.getOilRfnPontList().size()<1){
            ui.btnBarcodeGs.setVisibility(View.GONE);
            ui.tvPointGs.setVisibility(View.GONE);
            ui.btnBarcodeHo.setVisibility(View.GONE);
            ui.tvPointHo.setVisibility(View.GONE);
            ui.btnBarcodeSk.setVisibility(View.GONE);
            ui.tvPointSk.setVisibility(View.GONE);
            ui.btnBarcodeSoil.setVisibility(View.GONE);
            ui.tvPointSoil.setVisibility(View.GONE);

            ui.tvIntegrationGs.setVisibility(View.VISIBLE);
            ui.tvIntegrationHo.setVisibility(View.VISIBLE);
            ui.tvIntegrationSk.setVisibility(View.VISIBLE);
            ui.tvIntegrationSoil.setVisibility(View.VISIBLE);


//            ui.btnBarcodeGs.setVisibility(View.VISIBLE);
//            ui.tvPointGs.setVisibility(View.VISIBLE);
//            ui.btnBarcodeHo.setVisibility(View.VISIBLE);
//            ui.tvPointHo.setVisibility(View.VISIBLE);
//            ui.btnBarcodeSk.setVisibility(View.VISIBLE);
//            ui.tvPointSk.setVisibility(View.VISIBLE);
//            ui.btnBarcodeSoil.setVisibility(View.VISIBLE);
//            ui.tvPointSoil.setVisibility(View.VISIBLE);
//
//            ui.tvIntegrationGs.setVisibility(View.GONE);
//            ui.tvIntegrationHo.setVisibility(View.GONE);
//            ui.tvIntegrationSk.setVisibility(View.GONE);
//            ui.tvIntegrationSoil.setVisibility(View.GONE);
        }else{
            for(int i=0; i<data.getOilRfnPontList().size(); i++){
                switch (data.getOilRfnPontList().get(i).getOilRfnCd()){
                    case OilPointVO.OIL_CODE_SOIL:
                        setOilView(data.getOilRfnPontList().get(i), ui.btnBarcodeSoil, ui.tvPointSoil, ui.tvIntegrationSoil);
                        break;
                    case OilPointVO.OIL_CODE_GSCT:
                        setOilView(data.getOilRfnPontList().get(i), ui.btnBarcodeGs, ui.tvPointGs, ui.tvIntegrationGs);
                        break;
                    case OilPointVO.OIL_CODE_HDOL:
                        setOilView(data.getOilRfnPontList().get(i), ui.btnBarcodeHo, ui.tvPointHo, ui.tvIntegrationHo);
                        break;
                    case OilPointVO.OIL_CODE_SKNO:
                        setOilView(data.getOilRfnPontList().get(i), ui.btnBarcodeSk, ui.tvPointSk, ui.tvIntegrationSk);
                        break;
                    default:
                        break;
                }
            }
        }
    }

    public void setOilView(OilPointVO data, View barcode, TextView point, View integration){
        switch (data.getRgstYn()){
            case OilPointVO.OIL_JOIN_CODE_Y:
                barcode.setVisibility(View.VISIBLE);
                point.setVisibility(View.VISIBLE);
                point.setText(String.format(Locale.getDefault(),context.getString(R.string.word_home_24), StringUtil.getDigitGrouping(Integer.parseInt(data.getPont()))));
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

    public void reqIntegrateOil(String rfnCd){
        List<OilPointVO> list = data.getOilRfnPontList();
        for(int i=0; i<list.size(); i++){
            if(rfnCd.equalsIgnoreCase(list.get(i).getOilRfnCd())){

                switch (list.get(i).getRgstYn()){
                    case OilPointVO.OIL_JOIN_CODE_R:
                        //TODO R일 경우 처리
                        break;
                    case OilPointVO.OIL_JOIN_CODE_N:
                        ((BaseActivity)context).startActivitySingleTop(new Intent(context, MyGOilIntegrationActivity.class).putExtra(OilCodes.KEY_OIL_CODE, rfnCd), RequestCodes.REQ_CODE_ACTIVITY.getCode());
                        //TODO N일 경우 처리
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
