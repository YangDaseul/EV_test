package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.databinding.DialogBottomListBinding;

import java.util.ArrayList;
import java.util.List;

public class BottomListDialog extends BaseBottomDialog<DialogBottomListBinding> {

    private List<String> datas = new ArrayList<>();

    public BottomListDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_list);
        setAllowOutTouch(true);

        ui.rv.setLayoutManager(new LinearLayoutManager(getContext()));




//        ui.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
//
//        ui.editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean hasFocus) {
//
////                if(hasFocus)
////                    behavior.setState(BottomSheetBehavior.STATE_DRAGGING);
//
//            }
//        });

    }


    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }
}
