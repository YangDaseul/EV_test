package com.genesis.apps.ui.common.dialog.bottom;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.genesis.apps.R;
import com.genesis.apps.databinding.DialogBottomCheckListBinding;
import com.genesis.apps.ui.common.dialog.bottom.view.BottomCheckListAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

public class BottomCheckListDialog extends BaseBottomDialog<DialogBottomCheckListBinding> {

    private ArrayList<CheckInfo> datas = new ArrayList<>();
    private BottomCheckListAdapter adapter = null;
    private ArrayList<String> selectItems;
    private String title;
    private String bottomBtnTitle;
    public BottomCheckListDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_bottom_check_list);
        setAllowOutTouch(true);
        ui.lTitle.setValue(title);
        if(bottomBtnTitle != null) {
            ui.tvNextBtn.setText(bottomBtnTitle);
        }
        if(datas!=null) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
            ui.rv.setLayoutManager(layoutManager);
            adapter = new BottomCheckListAdapter(getContext(), new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = (int) view.getTag();
                    CheckBox checkBox = (CheckBox) view;
                    datas.get(position).setCheck(checkBox.isChecked());
                }
            });
            adapter.setData(datas);
            ui.rv.setAdapter(adapter);
            ui.tvNextBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectItems = new ArrayList<>();
                    for(int i=0; i<datas.size(); i++) {
                        if(datas.get(i).isCheck()) {
                            selectItems.add(datas.get(i).getName());
                        }
                    }

                    dismiss();
                }
            });
        }
    }

    public void setDatas(List<String> datas) {
        this.datas = new ArrayList<>();
        for(int i=0; i<datas.size(); i++) {
            this.datas.add(new CheckInfo(datas.get(i)));
        }
    }

    public void setCheckDatas(List<String> checkDatas) {
        for(int i=0; i<checkDatas.size(); i++) {
            for(int j=0; j<datas.size(); j++) {
                if(checkDatas.get(i).equals(datas.get(j).getName())) {
                    datas.get(j).setCheck(true);
                }
            }
        }
    }

    public ArrayList<String> getSelectItems() {
        return selectItems;
    }

    public void setSelectItems(ArrayList<String> selectItems) {
        this.selectItems = selectItems;
    }

    public void setBottomBtnTitle(String bottomBtnTitle) {
        this.bottomBtnTitle = bottomBtnTitle;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public class CheckInfo {
        private String name;
        private boolean isCheck;

        public CheckInfo(String name) {
            isCheck = false;
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setCheck(boolean isCheck) {
            this.isCheck = isCheck;
        }

        public boolean isCheck() {
            return isCheck;
        }
    }
}
