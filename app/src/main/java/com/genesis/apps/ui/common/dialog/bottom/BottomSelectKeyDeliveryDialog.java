package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.databinding.DialogBottomSelectKeyDeliveryBinding;

public class BottomSelectKeyDeliveryDialog extends BaseBottomDialog<DialogBottomSelectKeyDeliveryBinding> {

    boolean isDkAvl;
    String keyTransferType;

    public BottomSelectKeyDeliveryDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_select_key_delivery);
        setAllowOutTouch(true);

        if (isDkAvl()) {
            ui.rbDkBtn.setVisibility(View.VISIBLE);
            ui.tvDkDesc.setVisibility(View.VISIBLE);
        }

        if (!TextUtils.isEmpty(keyTransferType)) {
            if (isDkAvl() && VariableType.SERVICE_CHARGE_BTR_KEY_TRANSFER_TYPE_DKC.equals(keyTransferType))
                ui.rbDkBtn.setChecked(true);
        }

        ui.btnOk.setOnClickListener(view -> {

            switch (ui.rgKeyDeliveryRadiogroup.getCheckedRadioButtonId()) {
                case R.id.rb_fob_btn:
                    // 차량 키 직접 전달(대면)
                    setSelectItem(VariableType.SERVICE_CHARGE_BTR_KEY_TRANSFER_TYPE_FOB);
                    break;
                case R.id.rb_dk_btn:
                    // 디지털 키 공유(비대면)
                    setSelectItem(VariableType.SERVICE_CHARGE_BTR_KEY_TRANSFER_TYPE_DKC);
                    break;
            }
            dismiss();
        });
    }

    public boolean isDkAvl() {
        return isDkAvl;
    }

    public void setDkAvl(boolean dkAvl) {
        isDkAvl = dkAvl;
    }

    public String getSelectItem() {
        return keyTransferType;
    }

    public void setSelectItem(String type) {
        this.keyTransferType = type;
    }
}


