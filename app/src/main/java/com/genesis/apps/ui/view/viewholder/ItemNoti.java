package com.genesis.apps.ui.view.viewholder;

import android.animation.ValueAnimator;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.SparseBooleanArray;
import android.view.View;

import com.bumptech.glide.Glide;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.NotiVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.databinding.ItemAccodianNotiBinding;
import com.genesis.apps.databinding.ItemTestBinding;
import com.genesis.apps.ui.view.BaseRecyclerViewAdapter;
import com.genesis.apps.ui.view.BaseViewHolder;

public class ItemNoti extends BaseViewHolder<NotiVO, ItemAccodianNotiBinding> {
    public ItemNoti(View itemView) {
        super(itemView);
    }

//    @Override
//    public int getLayout() {
//        return R.layout.item_accodian_noti;
//    }

    @Override
    public void onBindView(NotiVO item) {
//        getBinding().tvTitle.setText(item.getNotiTitle());
//        getBinding().tvDate.setText(DateUtil.getDate(DateUtil.getDefaultDateFormat(item.getNotiCont(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));
//        getBinding().tvContents.setVisibility(item.isOpen() ? View.VISIBLE : View.GONE);
//        getBinding().tvContents.setText(item.getNotiCont());
//
//        //TODO ONCLICK 에 대한 처리..필요
//        getBinding().ivArrow.setBackgroundResource(item.isOpen() ? R.drawable.g_list_icon_close : R.drawable.g_list_icon_open);
//        getBinding().ivBadge.setVisibility( DateUtil.getDiffMillis(item.getNotDt(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss) > DateUtils.WEEK_IN_MILLIS ? View.GONE : View.VISIBLE);
//        changeVisibility(item.isOpen());
//        if(!TextUtils.isEmpty(item.getItem().getImgUrl())) {
//            getBinding().ivContents.setVisibility(View.VISIBLE);
//            Glide.with(getContext())
//                    .load(item.getItem().getImgUrl())
//                    .override(200, 600) // ex) override(600, 200)
//                    .into(getBinding().ivContents);
//        }else{
//            getBinding().ivContents.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onBindView(NotiVO item, int pos) {
//        getBinding().tvTitle.setText(item.getNotiTitle());
//        getBinding().tvDate.setText(DateUtil.getDate(DateUtil.getDefaultDateFormat(item.getNotiCont(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));
//        getBinding().tvContents.setVisibility(item.isOpen() ? View.VISIBLE : View.GONE);
//        getBinding().tvContents.setText(item.getNotiCont());
//
//        //TODO ONCLICK 에 대한 처리..필요
//        getBinding().ivArrow.setBackgroundResource(item.isOpen() ? R.drawable.g_list_icon_close : R.drawable.g_list_icon_open);
//        getBinding().ivBadge.setVisibility( DateUtil.getDiffMillis(item.getNotDt(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss) > DateUtils.WEEK_IN_MILLIS ? View.GONE : View.VISIBLE);
//        changeVisibility(item.isOpen());
//        if(!TextUtils.isEmpty(item.getItem().getImgUrl())) {
//            getBinding().ivContents.setVisibility(View.VISIBLE);
//            Glide.with(getContext())
//                    .load(item.getItem().getImgUrl())
//                    .override(200, 600) // ex) override(600, 200)
//                    .into(getBinding().ivContents);
//        }else{
//            getBinding().ivContents.setVisibility(View.GONE);
//        }
    }

    @Override
    public void onBindView(NotiVO item, int pos, SparseBooleanArray selectedItems) {
        getBinding().tvTitle.setText(item.getNotiTitle());
        getBinding().tvDate.setText(DateUtil.getDate(DateUtil.getDefaultDateFormat(item.getNotiCont(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));
        getBinding().tvContents.setVisibility(selectedItems.get(pos) ? View.VISIBLE : View.GONE);
        getBinding().tvContents.setText(item.getNotiCont());

        //TODO ONCLICK 에 대한 처리..필요
        getBinding().ivArrow.setBackgroundResource(selectedItems.get(pos) ? R.drawable.g_list_icon_close : R.drawable.g_list_icon_open);
        getBinding().ivBadge.setVisibility( DateUtil.getDiffMillis(item.getNotDt(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss) > DateUtils.WEEK_IN_MILLIS ? View.GONE : View.VISIBLE);
        changeVisibility(selectedItems.get(pos));
    }


    private void changeVisibility(final boolean isExpanded) {

        if(getBinding().tvContents.getVisibility()==View.VISIBLE&&isExpanded)
            return;

        // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, 600) : ValueAnimator.ofInt(600, 0);
        // Animation이 실행되는 시간, n/1000초
        va.setDuration(500);
        va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // imageView의 높이 변경
                getBinding().tvContents.getLayoutParams().height = (int) animation.getAnimatedValue();
                getBinding().tvContents.requestLayout();
                // imageView가 실제로 사라지게하는 부분
                getBinding().tvContents.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
            }
        });
        // Animation start
        va.start();
    }

}

