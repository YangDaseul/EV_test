package com.genesis.apps.ui.main.home.view;

import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.MessageVO;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ItemHomeInsightEtcBinding;
import com.genesis.apps.databinding.ItemHomeInsightWeatherBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class HomeInsightHorizontalAdapter extends BaseRecyclerViewAdapter2<MessageVO> {

    private static OnSingleClickListener onSingleClickListener;
    private static int ITEM_WEATHER = 0;
    private static int ITEM_ETC = 1;
//    private int realItemCnt = 0;

    public HomeInsightHorizontalAdapter(OnSingleClickListener onSingleClickListener) {
        HomeInsightHorizontalAdapter.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("recyclerview", "recyclerview onCreateViewHolder:" + viewType);
        if (viewType == ITEM_WEATHER) {
            return new ItemHomeInsightWeather(getView(parent, R.layout.item_home_insight_weather));
        } else {
            return new ItemHomeInsightEtc(getView(parent, R.layout.item_home_insight_etc));
        }
    }


    @Override
    public int getItemViewType(int position) {
        try {
            Log.v("recyclerview", "recyclerview getItemViewType:" + position);
            int index = 0;
            if (getItems().size() > 0) {
                index = position % getItems().size();
            }
            if (((MessageVO) getItem(index)).getWeatherCodes() != null) {
                return ITEM_WEATHER;
            } else {
                return ITEM_ETC;
            }
        } catch (Exception e) {
            return ITEM_ETC;
        }
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);

        if (getItems().size() > 0) {
            int index = position % getItems().size();
            holder.onBindView(getItem(index), index);
        }
    }

    @Override
    public int getItemCount() {
        if (getItems() == null || getItems().size() == 0) {
            return 0;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    public int getRealItemCnt() {
        return getItems().size();
    }

//    public int getRealItemCnt() {
//        return realItemCnt;
//    }
//
//    public void setRealItemCnt(int realItemCnt) {
//        this.realItemCnt = realItemCnt;
//    }

    private static class ItemHomeInsightEtc extends BaseViewHolder<MessageVO, ItemHomeInsightEtcBinding> {
        public ItemHomeInsightEtc(View itemView) {
            super(itemView);

        }

        @Override
        public void onBindView(MessageVO item) {

        }

        @Override
        public void onBindView(MessageVO item, final int pos) {
            getBinding().setContext(getContext());
            getBinding().setData(item);
            getBinding().tvMsg.setVisibility(View.GONE);
            getBinding().tvMsg2.setVisibility(View.GONE);
            getBinding().ivImg.setVisibility(View.INVISIBLE);
            getBinding().lWhole.setOnClickListener(null);
            getBinding().lWhole.setTag(R.id.item, null);
            if (item.isBanner()) {
                //배너메시지
                getBinding().ivImg.setVisibility(View.VISIBLE);
                Log.v("test img log", "test:" + item.getImgUri() + "   pos:" + pos);
                Glide
                        .with(getContext())
                        .load(item.getImgUri())
//                        .override(Target.SIZE_ORIGINAL,63)
//                        .error(R.drawable.banner_home_960x480) //todo 기본이미지 적용 필요
//                        .placeholder(R.drawable.banner_home_960x480) //todo 기본이미지 적용 필요
//                        .onlyRetrieveFromCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(getBinding().ivImg);
            } else {
                //admin 메시지
                if (!TextUtils.isEmpty(item.getTxtMsg1())) {
                    getBinding().tvMsg.setVisibility(View.VISIBLE);
                    getBinding().tvMsg.setText(item.getTxtMsg1());
                }
                if (!TextUtils.isEmpty(item.getTxtMsg2())) {
                    getBinding().tvMsg2.setVisibility(View.VISIBLE);
                    getBinding().tvMsg2.setText(item.getTxtMsg2());
                }
            }

            if (!TextUtils.isEmpty(item.getLnkTypCd()) && !TextUtils.isEmpty(item.getLnkUri())) {
                getBinding().lWhole.setTag(R.id.item, item);
                getBinding().lWhole.setOnClickListener(onSingleClickListener);
            }

        }

        @Override
        public void onBindView(MessageVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }


    private static class ItemHomeInsightWeather extends BaseViewHolder<MessageVO, ItemHomeInsightWeatherBinding> {

        private final String[][] rule = {
                {"? ", "?\n"},
                {"?", "?\n"},
                {". ", ".\n"},
                {".", ".\n"},
                {"! ", "!\n"},
                {"!", "!\n"},
                {", ", ",\n"},
                {",", ",\n"}
        };

        private final String[][] ruleReplace = {
                {"\\? ", "\\?\n"},
                {"\\?", "\\?\n"},
                {"\\. ", "\\.\n"},
                {"\\.", "\\.\n"},
                {"! ", "!\n"},
                {"!", "!\n"},
                {", ", ",\n"},
                {",", ",\n"}
        };

        public ItemHomeInsightWeather(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(MessageVO item) {

        }

        @Override
        public void onBindView(MessageVO item, final int pos) {

//            int txtChar = 0;
//            txtChar += getCharNumber(item.getTxtMsg(), '?');
//            txtChar += getCharNumber(item.getTxtMsg(), '!');
//            txtChar += getCharNumber(item.getTxtMsg(), '.');
//            txtChar += getCharNumber(item.getTxtMsg(), ',');
////
//            Log.v("insight test","item name:"+item.getTxtMsg()+"  특수문자 수:"+txtChar);
//
//            try {
//                if (txtChar>1&&!item.getTxtMsg().contains("\n")) {
//                    for (int i = 0; i < rule.length; i++) {
//                        if(item.getTxtMsg().contains(rule[i][0])&&item.getTxtMsg().indexOf(rule[i][0])!=(item.getTxtMsg().length()-1)) {
//                            item.setTxtMsg(item.getTxtMsg().replaceFirst(ruleReplace[i][0], ruleReplace[i][1]));
//                            Log.v("insight test", "item name after:" + item.getTxtMsg());
//                            break;
//                        }
//                    }
//                }
//            }catch (Exception e ){
//                e.printStackTrace();
//            }finally {
//                getBinding().tvMsg.setText(item.getTxtMsg());
//            }
            getBinding().setContext(getContext());
            getBinding().setData(item);
            getBinding().tvMsg.setText(item.getTxtMsg());
            String spec = StringUtil.isValidString(item.getWthrCdNm());
            spec += (!StringUtil.isValidString(item.getT1h()).equalsIgnoreCase("") ? "   "+item.getT1h()+"℃" : "");
            spec += "    "+StringUtil.isValidString(item.getSiGuGun());
            getBinding().tvMsg2.setText(spec);

            if (!StringUtil.isValidString(item.getIconImgUri()).equalsIgnoreCase("")) {
                Glide
                        .with(getContext())
                        .load(item.getIconImgUri())
//                        .override(Target.SIZE_ORIGINAL,63)
//                        .error(R.drawable.banner_home_960x480) //todo 기본이미지 적용 필요
//                        .placeholder(R.drawable.banner_home_960x480) //todo 기본이미지 적용 필요
//                        .onlyRetrieveFromCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                        .into(getBinding().ivIcon);
            }

        }

        @Override
        public void onBindView(MessageVO item, int pos, SparseBooleanArray selectedItems) {

        }

        private int getCharNumber(String str, char c) {
            int count = 0;
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) == c)
                    count++;
            }
            return count;
        }

    }


}