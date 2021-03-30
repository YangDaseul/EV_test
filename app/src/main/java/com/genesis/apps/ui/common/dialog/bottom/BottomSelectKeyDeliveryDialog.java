package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.genesis.apps.R;
import com.genesis.apps.databinding.DialogBottomSelectKeyDeliveryBinding;

public class BottomSelectKeyDeliveryDialog extends BaseBottomDialog<DialogBottomSelectKeyDeliveryBinding> {

    String keyDeliveryCd = null;

    public BottomSelectKeyDeliveryDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_select_key_delivery);
        setAllowOutTouch(true);

//        ui.lTitle.setValue(title);

        String strDkDesc = ui.tvDkDesc.getText().toString();
        String target = getContext().getString(R.string.service_charge_btr_txt_15); // 디지털 키 APP
        if (strDkDesc.contains(target)) {
            int start = strDkDesc.lastIndexOf(target.charAt(0));
            int end = start + target.length();
            Spannable span = (Spannable) ui.tvDkDesc.getText();
            span.setSpan(new ForegroundColorSpan(ContextCompat.getColor(getContext(), R.color.x_996449)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        ui.rgKeyDeliveryRadiogroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId) {
                case R.id.rb_fob_btn:
                    // 차량 키 직접 전달(대면)
                    setSelectItem("fob");
                    break;
                case R.id.rb_dk_btn:
                    // 디지털 키 공유(비대면)
                    setSelectItem("dk");
                    break;
            }
        });

        ui.btnOk.setOnClickListener(view -> {
            switch (ui.rgKeyDeliveryRadiogroup.getCheckedRadioButtonId()) {
                case R.id.rb_fob_btn:
                    // 차량 키 직접 전달(대면)
                    setSelectItem("fob");
                    break;
                case R.id.rb_dk_btn:
                    // 디지털 키 공유(비대면)
                    setSelectItem("dk");
                    break;
            }
            dismiss();
        });
    }

    public String getSelectItem() {
        return keyDeliveryCd;
    }

    public void setSelectItem(String keyDeliveryCd) {
        this.keyDeliveryCd = keyDeliveryCd;
    }
}


