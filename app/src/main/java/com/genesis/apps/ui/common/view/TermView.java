package com.genesis.apps.ui.common.view;

import android.widget.CheckBox;

import com.genesis.apps.comm.model.vo.TermVO;

public class TermView {
    private TermVO termVO;
    private CheckBox checkBox;

    public TermView(TermVO termVO, CheckBox checkBox){
        this.termVO = termVO;
        this.checkBox = checkBox;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public void setCheckBox(CheckBox checkBox) {
        this.checkBox = checkBox;
    }

    public TermVO getTermVO() {
        return termVO;
    }

    public void setTermVO(TermVO termVO) {
        this.termVO = termVO;
    }
}
