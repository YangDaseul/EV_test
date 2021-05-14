package com.genesis.apps.comm.model.vo;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
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
class ChargeSearchCategoryVO extends BaseData implements Parcelable {
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

    protected ChargeSearchCategoryVO(Parcel in) {
        titleResId = in.readInt();
        componentType = COMPONENT_TYPE.valueOf(in.readString());
        isSelected = in.readByte() != 0;

        ArrayList<String> orgSelectedList = new ArrayList<>();
        in.readList(orgSelectedList, String.class.getClassLoader());
        selectedItem.addAll(orgSelectedList.stream().map(ChargeSearchCategorytype::valueOf).collect(Collectors.toList()));

        if (in.readInt() > 0) {
            typeList = new ArrayList<>();
            ArrayList<String> orgTypeList = new ArrayList<>();
            in.readList(orgTypeList, String.class.getClassLoader());
            if (orgTypeList != null) {
                typeList.addAll(orgTypeList.stream().map(ChargeSearchCategorytype::valueOf).collect(Collectors.toList()));
            }
        }
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
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(titleResId);
        dest.writeString(componentType.name());
        dest.writeByte((byte) (isSelected ? 1 : 0));
        dest.writeList(selectedItem.stream().map(Enum::name).collect(Collectors.toList()));
        dest.writeInt(typeList == null ? 0 : typeList.size());
        if (typeList != null) {
            dest.writeList(typeList.stream().map(Enum::name).collect(Collectors.toList()));
        }
    }

    public static final Creator<ChargeSearchCategoryVO> CREATOR = new Creator<ChargeSearchCategoryVO>() {
        @Override
        public ChargeSearchCategoryVO createFromParcel(Parcel in) {
            return new ChargeSearchCategoryVO(in);
        }

        @Override
        public ChargeSearchCategoryVO[] newArray(int size) {
            return new ChargeSearchCategoryVO[size];
        }
    };

    @Override
    public Object clone() throws CloneNotSupportedException {
        ChargeSearchCategoryVO clone = (ChargeSearchCategoryVO) super.clone();
        clone.setSelectedItem(new ArrayList<>(this.selectedItem));
        clone.isSelected = this.isSelected;
        return clone;
    }
} // end of class ChargeSearchCategory
