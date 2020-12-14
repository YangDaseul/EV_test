package com.genesis.apps.ui.common.view;

import android.widget.CheckBox;

import com.genesis.apps.comm.model.vo.TermOilVO;
import com.genesis.apps.comm.model.vo.TermVO;

public class TermView {
    private TermVO termVO;
    private TermOilVO termOilVO;
    private CheckBox checkBox;

    public TermView(TermVO termVO, CheckBox checkBox){
        this.termVO = termVO;
        this.checkBox = checkBox;
    }

    public TermView(TermOilVO termOilVO, CheckBox checkBox){
        this.termOilVO = termOilVO;
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

    public TermOilVO getTermOilVO() {
        return termOilVO;
    }

    public void setTermOilVO(TermOilVO termOilVO) {
        this.termOilVO = termOilVO;
    }
}
