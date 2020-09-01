package com.genesis.apps.ui.view.viewholder;

import android.text.TextUtils;
import android.view.View;

import com.bumptech.glide.Glide;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.NotiVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.databinding.ItemAccodianNotiBinding;
import com.genesis.apps.databinding.ItemTestBinding;
import com.genesis.apps.ui.view.BaseRecyclerViewAdapter;
import com.genesis.apps.ui.view.BaseViewHolder;

public class ItemAccodianNoti extends BaseViewHolder<NotiVO, ItemAccodianNotiBinding> {
    public ItemAccodianNoti(View itemView) {
        super(itemView);
    }

    @Override
    public int getLayout() {
        return R.layout.item_accodian_noti;
    }

    @Override
    public void onBindView(BaseRecyclerViewAdapter.Row<NotiVO> item) {
        getBinding().tvTitle.setText(item.getItem().getNotiTitle());
        getBinding().tvDate.setText(DateUtil.getDate(DateUtil.getDefaultDateFormat(item.getItem().getNotiCont(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));
        getBinding().tvContents.setVisibility( TextUtils.isEmpty(item.getItem().getNotiCont()) ? View.GONE : View.VISIBLE);
        getBinding().tvContents.setText(item.getItem().getNotiCont());

        if(!TextUtils.isEmpty(item.getItem().getImgUrl())) {
            getBinding().ivContents.setVisibility(View.VISIBLE);
            Glide.with(getContext())
                    .load(item.getItem().getImgUrl())
                    .override(200, 600) // ex) override(600, 200)
                    .into(getBinding().ivContents);
        }else{
            getBinding().ivContents.setVisibility(View.GONE);
        }

        if(item.getItem().isOpen()){
            getBinding().ivArrow.setBackgroundResource(R.drawable.g_list_icon_close);
        }else{
            getBinding().ivArrow.setBackgroundResource(R.drawable.g_list_icon_open);
        }

    }

}

