package com.genesis.apps.ui.main.service;

import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.api.WSH_1004;
import com.genesis.apps.comm.model.vo.WashGoodsVO;
import com.genesis.apps.comm.model.vo.WashReserveVO;
import com.genesis.apps.databinding.ItemCarWashHistoryBinding;
import com.genesis.apps.databinding.ItemServiceCarWashBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;

import static com.genesis.apps.comm.model.gra.api.WSH_1004.PAY_CASH;
import static com.genesis.apps.comm.model.gra.api.WSH_1004.RESERVE_COMPLETED;

public class FragmentCarWashAdapter extends BaseRecyclerViewAdapter2<WashGoodsVO> {
    private static OnSingleClickListener singleClickListener;

    public FragmentCarWashAdapter(OnSingleClickListener listener) {
        singleClickListener = listener;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CarWashTicketViewHolder(getView(parent, R.layout.item_service_car_wash));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);
    }

    //세차 예약 내역 뷰홀더
    public static class CarWashTicketViewHolder extends BaseViewHolder<WashGoodsVO, ItemServiceCarWashBinding> {

        public CarWashTicketViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(WashGoodsVO item) {
            //do nothing
        }

        @Override
        public void onBindView(WashGoodsVO item, int pos) {
            //TODO  : 여기 기획 바뀌고 디자인 안 나왔음 ㅡㅡ;;
            // 스토리 보드 문서 보니 가격 표시 삭제되고 할인율만 표시하는 거 같음

            //상품명
            getBinding().tvCarWashItemName.setText(item.getGodsNm());

            //할인정보
            //TODO : 디자인 고칠 때 뷰 이름 바꾸기. 지금은 가격이라 명명함
            // 특가 뱃지는 살아있나?
            getBinding().tvCarWashItemCurrPrice.setText(item.getDsctNm());

            //버튼 클릭 리스너 및 해당 버튼 처리에 필요한 데이터 세팅
            setSingleClickListenerAndData(item);

            Glide.with(getContext())
                    .load(item.getGodsImgUri())
                    .format(DecodeFormat.PREFER_ARGB_8888)
                    .error(R.drawable.img_car_339_2) //todo 대체 이미지 필요
                    .placeholder(R.drawable.img_car_339_2) //todo 에러시 대체 이미지 필요
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(getBinding().ivCarWashItemImg);
        }

        @Override
        public void onBindView(WashGoodsVO item, int pos, SparseBooleanArray selectedItems) {
            //do nothing
        }

        //리스너를 연결하고, 이를 처리하는데 필요한 데이터도 저장
        private void setSingleClickListenerAndData(WashGoodsVO item) {
            getBinding().lServiceCarWashItem.setOnClickListener(singleClickListener);
            getBinding().lServiceCarWashItem.setTag(item);
        }
    }
}
