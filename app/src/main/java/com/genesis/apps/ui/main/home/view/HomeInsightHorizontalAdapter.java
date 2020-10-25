package com.genesis.apps.ui.main.home.view;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.constants.WeatherCodes;
import com.genesis.apps.comm.model.vo.MessageVO;
import com.genesis.apps.databinding.ItemHomeInsightEtcBinding;
import com.genesis.apps.databinding.ItemHomeInsightWeatherBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class HomeInsightHorizontalAdapter extends BaseRecyclerViewAdapter2<MessageVO> {

    private static OnSingleClickListener onSingleClickListener;
    private static int ITEM_WEATHER=0;
    private static int ITEM_ETC=1;
    public HomeInsightHorizontalAdapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==ITEM_WEATHER){
            return new ItemHomeInsightWeather(getView(parent, R.layout.item_home_insight_weather));
        }else{
            return new ItemHomeInsightEtc(getView(parent, R.layout.item_home_insight_etc));
        }
    }


    @Override
    public int getItemViewType(int position) {
        try {
            int index = 0;
            if(getItems().size()>0) {
                index = position % getItems().size();
            }
            if (((MessageVO) getItem(index)).getWeatherCodes()!=null) {
                return ITEM_WEATHER;
            } else {
                return ITEM_ETC;
            }
        }catch (Exception e){
            return ITEM_ETC;
        }
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);

        if(getItems().size()>0) {
            int index = position % getItems().size();
            holder.onBindView(getItem(index), index);
        }
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE; //무한스크롤을 위한 설정
    }

    private static class ItemHomeInsightEtc extends BaseViewHolder<MessageVO, ItemHomeInsightEtcBinding> {
        public ItemHomeInsightEtc(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(MessageVO item) {

        }

        @Override
        public void onBindView(MessageVO item, final int pos) {
            getBinding().tvMsg.setVisibility(View.GONE);
            getBinding().ivImg.setVisibility(View.GONE);
            getBinding().lWhole.setOnClickListener(null);
            getBinding().lWhole.setTag(R.id.item_link,null);
            //TODO GLIDE 처리 부분에 대한 임시 이미지 수령 해야함..
            switch (item.getMsgTypCd()) {
                case VariableType.MAIN_HOME_INSIGHT_TXT://텍스트만 노출
                    getBinding().tvMsg.setVisibility(View.VISIBLE);
                    getBinding().tvMsg.setText(item.getTxtMsg());
                    break;
                case VariableType.MAIN_HOME_INSIGHT_IMG://이미지만 노출
                    getBinding().ivImg.setVisibility(View.VISIBLE);
                    Glide
                            .with(getContext())
                            .load(item.getImgUri())
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .error(R.drawable.banner_home_960x480)
                            .placeholder(R.drawable.banner_home_960x480)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(getBinding().ivImg);
                    break;
                case VariableType.MAIN_HOME_INSIGHT_TXL://텍스트 + 링크
                    getBinding().tvMsg.setVisibility(View.VISIBLE);
                    getBinding().tvMsg.setText(item.getTxtMsg());
                    getBinding().lWhole.setTag(R.id.item_link, item.getLnkUri());
                    getBinding().lWhole.setOnClickListener(onSingleClickListener);
                    break;
                case VariableType.MAIN_HOME_INSIGHT_IML://이미지 + 링크
                    getBinding().ivImg.setVisibility(View.VISIBLE);
                    Glide
                            .with(getContext())
                            .load(item.getImgUri())
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .error(R.drawable.banner_home_960x480)
                            .placeholder(R.drawable.banner_home_960x480)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(getBinding().ivImg);
                    getBinding().lWhole.setTag(R.id.item_link, item.getLnkUri());
                    getBinding().lWhole.setOnClickListener(onSingleClickListener);
                    break;
                case VariableType.MAIN_HOME_INSIGHT_TIL://텍스트 + 이미지 + 링크
                    getBinding().tvMsg.setVisibility(View.VISIBLE);
                    getBinding().tvMsg.setText(item.getTxtMsg());
                    getBinding().ivImg.setVisibility(View.VISIBLE);
                    Glide
                            .with(getContext())
                            .load(item.getImgUri())
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .error(R.drawable.banner_home_960x480)
                            .placeholder(R.drawable.banner_home_960x480)
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(getBinding().ivImg);
                    getBinding().lWhole.setTag(R.id.item_link, item.getLnkUri());
                    getBinding().lWhole.setOnClickListener(onSingleClickListener);
                    break;
                case VariableType.MAIN_HOME_INSIGHT_SYS://고정된 메시지 유형?

                    break;

                default:

                    break;
            }

        }

        @Override
        public void onBindView(MessageVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }


    private static class ItemHomeInsightWeather extends BaseViewHolder<MessageVO, ItemHomeInsightWeatherBinding> {
        public ItemHomeInsightWeather(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(MessageVO item) {

        }

        @Override
        public void onBindView(MessageVO item, final int pos) {

            getBinding().tvMsg.setVisibility(View.GONE);
//            getBinding().ivImg.setVisibility(View.GONE);
            getBinding().lWhole.setOnClickListener(null);
            getBinding().lWhole.setTag(R.id.item_link,null);
            getBinding().ivIcon.setImageResource(0);

            switch (item.getMsgTypCd()) {
                case VariableType.MAIN_HOME_INSIGHT_TXT://텍스트만 노출
                    getBinding().tvMsg.setVisibility(View.VISIBLE);
                    getBinding().tvMsg.setText(item.getTxtMsg());
                    getBinding().ivIcon.setImageResource(WeatherCodes.getIconResource(item.getWeatherCodes()));
                    break;
//                case VariableType.MAIN_HOME_INSIGHT_IMG://이미지만 노출
//                    getBinding().ivImg.setVisibility(View.VISIBLE);
//                    break;
                case VariableType.MAIN_HOME_INSIGHT_TXL://텍스트 + 링크
                    getBinding().tvMsg.setVisibility(View.VISIBLE);
                    getBinding().tvMsg.setText(item.getTxtMsg());
                    getBinding().lWhole.setTag(R.id.item_link, item.getLnkUri());
                    getBinding().lWhole.setOnClickListener(onSingleClickListener);
                    break;
//                case VariableType.MAIN_HOME_INSIGHT_IML://이미지 + 링크
//                    getBinding().ivImg.setVisibility(View.VISIBLE);
//                    getBinding().lWhole.setTag(R.id.item_link, item.getLnkUri());
//                    getBinding().lWhole.setOnClickListener(onSingleClickListener);
//                    break;
//                case VariableType.MAIN_HOME_INSIGHT_TIL://텍스트 + 이미지 + 링크
//                    getBinding().tvMsg.setVisibility(View.VISIBLE);
//                    getBinding().tvMsg.setText(item.getTxtMsg());
//                    getBinding().ivImg.setVisibility(View.VISIBLE);
//                    getBinding().lWhole.setTag(R.id.item_link, item.getLnkUri());
//                    getBinding().lWhole.setOnClickListener(onSingleClickListener);
//                    break;
                case VariableType.MAIN_HOME_INSIGHT_SYS://고정된 메시지 유형?

                    break;

                default:

                    break;
            }

        }

        @Override
        public void onBindView(MessageVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }

}