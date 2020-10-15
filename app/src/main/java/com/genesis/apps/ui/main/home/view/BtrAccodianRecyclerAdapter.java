package com.genesis.apps.ui.main.home.view;

import android.animation.ValueAnimator;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.CounselVO;
import com.genesis.apps.databinding.ItemBtrCnslBinding;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class BtrAccodianRecyclerAdapter extends BaseRecyclerViewAdapter2<CounselVO> {

    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private int pageNo = 0;


    public BtrAccodianRecyclerAdapter() {
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("recyclerview test2", "create :");
        return new ItemBtrCnsl(getView(parent, R.layout.item_btr_cnsl));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);
        ItemBtrCnsl viewHolder = ((ItemBtrCnsl) holder);

        viewHolder.onBindView(getItem(position), position, selectedItems);

        viewHolder.getBinding().lTitle.setOnClickListener(view -> {
            Log.v("recyclerview onclick", "position pos:" + position);
            if (selectedItems.get(position)) {
                // 펼쳐진 Item을 클릭 시
                selectedItems.delete(position);
            } else {
                // 클릭한 Item의 position을 저장
                selectedItems.put(position, true);
            }
            notifyItemChanged(position);

        });

    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

//        public interface AutoScroll{
//                void updateScroll(final int position);
//        }


    private static class ItemBtrCnsl extends BaseViewHolder<CounselVO, ItemBtrCnslBinding> {
        public ItemBtrCnsl(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(CounselVO item) {

        }

        @Override
        public void onBindView(CounselVO item, int pos) {

        }

        @Override
        public void onBindView(CounselVO item, int pos, SparseBooleanArray selectedItems) {

//            getBinding().tvConslDt.setText(DateUtil.getDate(DateUtil.getDefaultDateFormat(item.getConslDt(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));

            getBinding().tvConslDt.setText(item.getConslDt());
            getBinding().tvCatNm.setText(item.getCatNm());
            getBinding().tvConslTtl.setText(item.getConslTtl());

                if (TextUtils.isEmpty(item.getRespCont())) {
                        //답변없음
                        getBinding().tvReply.setVisibility(View.GONE);
                        getBinding().tvContentsReply.setVisibility(View.GONE);
                } else {
                        //답변있음
                        getBinding().tvReply.setVisibility(View.VISIBLE);
                        getBinding().tvContentsReply.setVisibility(View.VISIBLE);
                        getBinding().tvContentsReply.setText(item.getRespCont());
                }

                if (item.getConslCont().contains(VariableType.BTR_REQUEST_CALL)) {
                        getBinding().tvReplyCall.setVisibility(View.VISIBLE);
                } else {
                        getBinding().tvReplyCall.setVisibility(View.GONE);
                }
                getBinding().tvContents.setText(item.getConslCont().replace(VariableType.BTR_REQUEST_CALL, "").replace(VariableType.BTR_REQUEST_APP, ""));

            getBinding().tvConslTtl.setMaxLines(selectedItems.get(pos) ? Integer.MAX_VALUE : 1);
            getBinding().tvConslTtl.setEllipsize(selectedItems.get(pos) ? null : TextUtils.TruncateAt.END);

            getBinding().lContents.setVisibility(selectedItems.get(pos) ? View.VISIBLE : View.GONE);
            getBinding().ivArrow.setBackgroundResource(selectedItems.get(pos) ? R.drawable.g_list_icon_close : R.drawable.g_list_icon_open);
            changeVisibility(selectedItems.get(pos), pos);
        }


        private void changeVisibility(final boolean isExpanded, final int position) {

            if (isExpanded) {
                getBinding().lContents.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                getBinding().lContents.requestLayout();
            }

            if (getBinding().lContents.getVisibility() == View.VISIBLE && isExpanded)
                return;

            // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
            ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, getBinding().lContents.getHeight()) : ValueAnimator.ofInt(getBinding().lContents.getHeight(), 0);
            // Animation이 실행되는 시간, n/1000초
            va.setDuration(500);
            va.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    // imageView의 높이 변경
                    getBinding().lContents.getLayoutParams().height = (int) animation.getAnimatedValue();
                    getBinding().lContents.requestLayout();
                    // imageView가 실제로 사라지게하는 부분
                    getBinding().lContents.setVisibility(isExpanded ? View.VISIBLE : View.GONE);
                }
            });
            // Animation start
            va.start();
        }

    }


}