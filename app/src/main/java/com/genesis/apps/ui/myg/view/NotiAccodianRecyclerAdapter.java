package com.genesis.apps.ui.myg.view;

import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.NotiVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.databinding.ItemAccodianNotiBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class NotiAccodianRecyclerAdapter extends BaseRecyclerViewAdapter2<NotiVO> {

    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private int pageNo = 0;
    private static OnSingleClickListener onSingleClickListener;


    public NotiAccodianRecyclerAdapter(OnSingleClickListener onSingleClickListener) {
        NotiAccodianRecyclerAdapter.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("recyclerview test2", "create :");
        return new ItemNoti(getView(parent, R.layout.item_accodian_noti));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position, selectedItems);
    }

    public void eventAccordion(int position){
        Log.v("recyclerview onclick", "position pos:" + position);
        if (selectedItems.get(position)) {
            // 펼쳐진 Item을 클릭 시
            selectedItems.delete(position);
        } else {
            // 클릭한 Item의 position을 저장
            selectedItems.put(position, true);
        }
        notifyItemChanged(position);
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    private static class ItemNoti extends BaseViewHolder<NotiVO, ItemAccodianNotiBinding> {

        public ItemNoti(View itemView) {
            super(itemView);

            getBinding().lTitle.setOnClickListener(onSingleClickListener);

//            getBinding().lTitle.setOnClickListener(view -> {
//                try {
//                    int position = Integer.parseInt(view.getTag(R.id.position).toString());
//
//                    Log.v("recyclerview onclick", "position pos:" + position);
//                    if (selectedItems.get(position)) {
//                        // 펼쳐진 Item을 클릭 시
//                        selectedItems.delete(position);
//                    } else {
//                        // 클릭한 Item의 position을 저장
//                        selectedItems.put(position, true);
//                    }
//
//
////                    getBinding().tvContents.setVisibility(selectedItems.get(position) ? View.VISIBLE : View.GONE);
////                    changeViewStatus(selectedItems.get(position));
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
        }

        @Override
        public void onBindView(NotiVO item) {

        }

        @Override
        public void onBindView(NotiVO item, int pos) {
        }

        @Override
        public void onBindView(NotiVO item, int pos, SparseBooleanArray selectedItems) {
            getBinding().lTitle.setTag(R.id.position, pos);
            getBinding().tvTitle.setText(item.getNotiTtl());
            getBinding().tvTitle.setMaxLines(selectedItems.get(pos) ? Integer.MAX_VALUE : 1);
            getBinding().tvTitle.setEllipsize(selectedItems.get(pos) ? null : TextUtils.TruncateAt.END);
            getBinding().tvDate.setText(DateUtil.getDate(DateUtil.getDefaultDateFormat(item.getTrmsSrtDtm(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));
            getBinding().tvContents.setVisibility(selectedItems.get(pos) ? View.VISIBLE : View.GONE);
            getBinding().tvContents.setText(item.getNotiCont());
            getBinding().ivArrow.setBackgroundResource(selectedItems.get(pos) ? R.drawable.g_list_icon_close : R.drawable.g_list_icon_open);
            getBinding().ivBadge.setVisibility(DateUtil.getDiffMillis(item.getTrmsSrtDtm(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss) > DateUtils.WEEK_IN_MILLIS ? View.GONE : View.VISIBLE);


//            changeVisibility(getBinding().tvContents, selectedItems.get(pos));
        }
    }
}