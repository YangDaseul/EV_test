package com.genesis.apps.ui.main.home.view;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.databinding.ItemMyCarBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listener.ViewPressEffectHelper;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class CarHorizontalAdapter extends BaseRecyclerViewAdapter2<VehicleVO> {

    private static OnSingleClickListener onSingleClickListener;

    public CarHorizontalAdapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("recyclerview test2", "create :");
        return new ItemCar(getView(parent, R.layout.item_my_car));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);
        holder.onBindView(getItem(position), position);

    }

    private static class ItemCar extends BaseViewHolder<VehicleVO, ItemMyCarBinding> {
        public ItemCar(View itemView) {
            super(itemView);
            getBinding().ivCar.setOnClickListener(onSingleClickListener);
            ViewPressEffectHelper.attach(getBinding().ivCar);
        }

        @Override
        public void onBindView(VehicleVO item) {

        }

        @Override
        public void onBindView(VehicleVO item, final int pos) {
            getBinding().tvModel.setVisibility(View.VISIBLE);
            getBinding().ivCar.setVisibility(View.VISIBLE);
            getBinding().ivCar.setTag(R.id.vehicle, item);
            getBinding().tvCarRgstNo.setVisibility(View.GONE);
            getBinding().ivFavorite.setVisibility(View.GONE);
            getBinding().tvCarStatus.setVisibility(View.GONE);
            getBinding().btnDelete.setVisibility(View.GONE);
            getBinding().btnModify.setVisibility(View.GONE);
            getBinding().btnRecovery.setVisibility(View.GONE);
            getBinding().ivFavorite.setOnClickListener(onSingleClickListener);
            getBinding().ivFavorite.setTag(R.id.vehicle, item);

            Glide
                    .with(getContext())
                    .load(item.getVhclImgUri())
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .error(R.drawable.img_car_339_2)
                    .placeholder(R.drawable.img_car_339_2)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(getBinding().ivCar);

            getBinding().tvModel.setText(item.getCustGbCd().equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_OV) ? item.getMdlCd() + "\n" + item.getMdlNm() : item.getSaleMdlCd() + "\n" + item.getSaleMdlNm());

            if (item.getDelExpYn().equalsIgnoreCase(VariableType.DELETE_EXPIRE_Y)) {
                //삭제 예정 차량
                getBinding().tvCarStatus.setVisibility(View.VISIBLE);
                getBinding().tvCarStatus.setText(String.format(getContext().getString(R.string.gm_carlst_04_4), (((TextUtils.isEmpty(item.getDelExpDay())) ? "0" : item.getDelExpDay()))));
            } else {
                //정상차량
                switch (item.getCustGbCd()) {
                    case VariableType.MAIN_VEHICLE_TYPE_OV:
                        getBinding().ivFavorite.setVisibility(View.VISIBLE);
                        getBinding().ivFavorite.setImageResource(item.getMainVhclYn().equalsIgnoreCase(VariableType.MAIN_VEHICLE_N) ? R.drawable.ic_star_s : R.drawable.ic_star_l_s);
                        break;
                    case VariableType.MAIN_VEHICLE_TYPE_CV:
                        getBinding().tvCarStatus.setVisibility(View.VISIBLE);
                        getBinding().tvCarStatus.setText(R.string.gm_carlst_01_5);
                        break;
                }
            }
        }

        @Override
        public void onBindView(VehicleVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }

    public int getVehicleOwnerCnt(){
        int cnt=0;

        try{
            for(VehicleVO vehicleVO : getItems()){
                if(vehicleVO.getCustGbCd().equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_OV)){
                    cnt++;
                }
            }
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
        return cnt;
    }

//    //소멸 및 정지상태 아이템은 제거
//    public void applyFilter() {
//        for (int i = 0; i < getItems().size(); i++) {
//            if (getItems().get(i).getCardStusNm().equalsIgnoreCase(CARD_STATUS_20)
//                    || getItems().get(i).getCardStusNm().equalsIgnoreCase(CARD_STATUS_30)) {
//                remove(i);
//            }
//        }
//    }
//
//    //카드 추가 레이아웃 생성
//    public void addCard() {
//        VehicleVO VehicleVO = new VehicleVO("", "", "", VehicleVO.CARD_STATUS_99, "", "", "", false);
//        addRow(VehicleVO);
//    }

}