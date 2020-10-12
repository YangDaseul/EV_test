package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.genesis.apps.R;
import com.genesis.apps.databinding.DialogBottomListBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;

import java.util.ArrayList;
import java.util.List;

public class BottomListDialog extends BaseBottomDialog<DialogBottomListBinding> {

    private List<String> datas = new ArrayList<>();
    private ArrayAdapter adapter = null;
    private String selectItem;
    private String title;
    public BottomListDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_list);
        setAllowOutTouch(true);
        ui.lTitle.setValue(title);
        if(datas!=null) {
            adapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, datas);
            ui.lv.setAdapter(adapter);
            ui.lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    selectItem = ((TextView)view).getText().toString();
                    dismiss();
                }
            });
        }
    }


    public List<String> getDatas() {
        return datas;
    }

    public void setDatas(List<String> datas) {
        this.datas = datas;
    }


    public String getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(String selectItem) {
        this.selectItem = selectItem;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
