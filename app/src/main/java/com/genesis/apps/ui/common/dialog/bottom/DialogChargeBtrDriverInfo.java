package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.gra.CHB_1021;
import com.genesis.apps.databinding.DialogBottomChargeBtrDriverInfoBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;

public class DialogChargeBtrDriverInfo extends BaseBottomDialog<DialogBottomChargeBtrDriverInfoBinding> {

    private CHB_1021.Response data;
    public DialogChargeBtrDriverInfo(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_charge_btr_driver_info);
        setAllowOutTouch(true);
        ui.back.lWhole.setElevation(0);

        if(data!=null){
            ui.setData(data);
            ui.btnCall.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {
                    if (data.getWorkerList().size() > 0 && data.getWorkerList().get(0) != null && !TextUtils.isEmpty(data.getWorkerList().get(0).getWorkerHpNo()))
                        getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL + data.getWorkerList().get(0).getWorkerHpNo())));
                }
            });
        }
    }

    public CHB_1021.Response getData() {
        return data;
    }

    public void setData(CHB_1021.Response data) {
        this.data = data;
    }

    public void setMinute(int minute) {
        try {
            if (ui != null) {
                ui.setMinute(minute);
            }
        }catch (Exception e){
            e.printStackTrace();
            //ignore
        }
    }
}
