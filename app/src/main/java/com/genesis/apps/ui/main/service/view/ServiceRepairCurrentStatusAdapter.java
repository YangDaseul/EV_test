package com.genesis.apps.ui.main.service.view;

import android.app.Activity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.RepairVO;
import com.genesis.apps.databinding.ItemServiceRepairCurrentStatusBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class ServiceRepairCurrentStatusAdapter extends BaseRecyclerViewAdapter2<RepairVO> {

    private Activity activity;

    public ServiceRepairCurrentStatusAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemServiceRepairCurrentStatus(getView(parent, R.layout.item_service_repair_current_status), this);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);
    }


    public boolean isFinish(String stusCd){
        boolean isFinish=false;

        try {
            switch (stusCd) {
                case "4850"://작업완료
                case "6500":
                case "7500":
                case "8500"://딜리버리완료
                    isFinish = true;
                    break;
            }
        }catch (Exception e){
            isFinish = false;
        }finally{
            return isFinish;
        }
    }


    public String getStusName(String stusCd) {

        int textId=0;

        try {
            switch (stusCd) {
                case "1100":
                case "2100":
                case "3100":
                case "4100":
                    textId = R.string.sm_r_rsv05_15;
                    break;
                case "1200":
                case "2200":
                case "3200":
                case "4200":
                    textId = R.string.sm_r_rsv05_16;
                    break;
                case "1300":
                case "2300":
                case "3300":
                    textId = R.string.sm_r_rsv05_10;
                    break;
                case "1400":
                case "2400":
                case "3400":
                    textId = R.string.sm_r_rsv05_11;
                    break;
                case "1500":
                case "2500":
                case "3500":
                    textId = R.string.sm_r_rsv05_12;
                    break;
                case "4610":
                    textId = R.string.sm_r_rsv05_5;
                    break;
                case "4720":
                    textId = R.string.sm_r_rsv05_6;
                    break;
                case "4730":
                    textId = R.string.sm_r_rsv05_7;
                    break;
                case "4740":
                    textId = R.string.sm_r_rsv05_8;
                    break;
                case "4850":
                    textId = R.string.sm_r_rsv05_9;
                    break;
                case "6300":
                case "7300":
                case "8300":
                    textId = R.string.sm_r_rsv05_13;
                    break;
                case "6400":
                case "7400":
                case "8400":
                    textId = R.string.sm_r_rsv05_38;
                    break;
                case "6500":
                case "7500":
                case "8500":
                    textId = R.string.sm_r_rsv05_14;
                    break;
                case "6800":
                case "7800":
                case "8800":
                case "9800":
                    textId = R.string.sm_r_rsv05_31;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            Log.v("test","testStringLog:"+activity.getString(textId) +"      stusCd:"+stusCd);
            return activity.getString(textId);
        }
    }


    private static class ItemServiceRepairCurrentStatus extends BaseViewHolder<RepairVO, ItemServiceRepairCurrentStatusBinding> {

        private ServiceRepairCurrentStatusAdapter adapter = null;

        public ItemServiceRepairCurrentStatus(View itemView, ServiceRepairCurrentStatusAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
        }

        @Override
        public void onBindView(RepairVO item) {

        }

        @Override
        public void onBindView(RepairVO item, final int pos) {
            getBinding().setAdapter(adapter);
            getBinding().setData(item);
        }

        @Override
        public void onBindView(RepairVO item, int pos, SparseBooleanArray selectedItems) {

        }
    }

}