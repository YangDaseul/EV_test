package com.genesis.apps.comm.model.vo;

import android.content.Context;
import android.util.Log;

import androidx.annotation.StringRes;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.constants.ChargeSearchCategorytype;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ChargeSearchCategoryVO extends BaseData {
    @StringRes
    private int titleResId;
    private List<ChargeSearchCategorytype> typeList;
    private COMPONENT_TYPE componentType;

    private boolean isSelected = false;
    private ArrayList<ChargeSearchCategorytype> selectedItem = new ArrayList<>();

    public enum COMPONENT_TYPE {
        ONLY_ONE,
        RADIO,
        CHECK
    }

    public ChargeSearchCategoryVO(int titleResId, COMPONENT_TYPE componentType, List<ChargeSearchCategorytype> typeList) {
        this.titleResId = titleResId;
        this.componentType = componentType;
        this.typeList = typeList;
    }

    public List<String> getTypeStringList(Context context) {
        return typeList.stream().map(it -> context.getString(it.getTitleResId())).collect(Collectors.toList());
    }

    public ChargeSearchCategoryVO setSelected(boolean isSelected) {
        this.isSelected = isSelected;
        return this;
    }

    public ChargeSearchCategoryVO addSelectedItem(ChargeSearchCategorytype addItem) {
        this.selectedItem.add(addItem);
        this.isSelected = this.selectedItem.size() > 0;
        return this;
    }

    public void addSelectedItems(List<ChargeSearchCategorytype> addItemList) {
        this.selectedItem.addAll(addItemList);
        this.isSelected = this.selectedItem.size() > 0;
    }

    public List<String> getSelectedItemStringList(Context context) {
        return selectedItem.stream().map(it -> context.getString(it.getTitleResId())).collect(Collectors.toList());
    }

    public void clearSelectedItems() {
        this.isSelected = false;
        this.selectedItem.clear();
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ChargeSearchCategoryVO clone = (ChargeSearchCategoryVO) super.clone();
        clone.setSelectedItem(new ArrayList<>(this.selectedItem));
        clone.isSelected = this.isSelected;
        return clone;
    }
} // end of class ChargeSearchCategory
