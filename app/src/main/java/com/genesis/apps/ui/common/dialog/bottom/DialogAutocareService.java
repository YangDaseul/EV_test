package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.api.SOS_1006;
import com.genesis.apps.comm.model.vo.CouponVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.databinding.DialogBottomAutocareServiceBinding;
import com.genesis.apps.databinding.DialogBottomSosDriverInfoBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.genesis.apps.comm.model.constants.VariableType.SERVICE_SOS_AREA_CLS_CODE_H;
import static com.genesis.apps.comm.model.constants.VariableType.SERVICE_SOS_AREA_CLS_CODE_R;

public class DialogAutocareService extends BaseBottomDialog<DialogBottomAutocareServiceBinding> {


    private List<CouponVO> list = new ArrayList<>();
    private CheckBox[] checkBoxes;
    private boolean isNext=false;

    public DialogAutocareService(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_autocare_service);
        setAllowOutTouch(true);
        checkBoxes = new CheckBox[]{ui.lAutoSvc1.cbAutoSvc1, ui.lAutoSvc2.cbAutoSvc1, ui.lAutoSvc3.cbAutoSvc1 ,ui.lAutoSvc4.cbAutoSvc1};
        ui.lAutoSvc1.setRemCnt(getRemCnt(list.get(0).getRemCnt()));
        ui.lAutoSvc1.setIsEngine(true);
        ui.lAutoSvc1.cbAutoSvc1.setChecked(true);
        ui.lAutoSvc1.cbAutoSvc1.setTypeface(ResourcesCompat.getFont(getContext(), R.font.regular_genesissansheadglobal));



        ui.lAutoSvc2.setRemCnt(getRemCnt(list.get(1).getRemCnt()));
        ui.lAutoSvc3.setRemCnt(getRemCnt(list.get(2).getRemCnt()));
        ui.lAutoSvc4.setRemCnt(getRemCnt(list.get(3).getRemCnt()));

        ui.btnNext.setOnClickListener(new OnSingleClickListener() {
            @Override
            public void onSingleClick(View v) {
                setNext(true);
                dismiss();
            }
        });
    }


    public List<CouponVO> getList() {
        return list;
    }

    public void setList(List<CouponVO> list) {
        this.list = list;
    }

    private int getRemCnt(String remCnt){
        int value=0;

        try{
            value = Integer.parseInt(remCnt);
        }catch (Exception e){
            e.printStackTrace();
        }

        return value;
    }

    public List<CouponVO> getCheckService(){
        List<CouponVO> checkedService = new ArrayList<>();

        for(int i=0; i<checkBoxes.length;i++){
            if(i==0||checkBoxes[i].isChecked()) {
                checkedService.add(this.list.get(i));
            }
        }

        return checkedService;
    }

    public boolean isNext() {
        return isNext;
    }

    public void setNext(boolean next) {
        isNext = next;
    }
}
