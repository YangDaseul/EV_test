package com.genesis.apps.ui.view.viewholder;

import android.animation.ValueAnimator;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.NotiVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.databinding.ItemAccodianNotiBinding;
import com.genesis.apps.ui.view.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.view.BaseViewHolder;


public class NotiAccodianRecyclerAdapter extends BaseRecyclerViewAdapter2<NotiVO> {

        // Item의 클릭 상태를 저장할 array 객체
        private SparseBooleanArray selectedItems = new SparseBooleanArray();

        public NotiAccodianRecyclerAdapter() {
        }

        @Override
        public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                Log.v("recyclerview test2","create :");
                return new ItemNoti(getView(parent, R.layout.item_accodian_noti));
        }

        @Override
        public void onBindViewHolder(final BaseViewHolder holder, int position) {
                Log.v("recyclerview onBindViewHolder","position pos:"+position);
//                super.onBindViewHolder(holder, position);
                ItemNoti viewHolder = ((ItemNoti)holder);

                viewHolder.onBindView(getItem(position),position, selectedItems);

                viewHolder.getBinding().lTitle.setOnClickListener(view -> {
                        Log.v("recyclerview onclick","position pos:"+position);
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

//        public interface AutoScroll{
//                void updateScroll(final int position);
//        }


        private static class ItemNoti extends BaseViewHolder<NotiVO, ItemAccodianNotiBinding> {
                public ItemNoti(View itemView) {
                        super(itemView);
                }

                @Override
                public void onBindView(NotiVO item) {

                }

                @Override
                public void onBindView(NotiVO item, int pos) {

                }

                @Override
                public void onBindView(NotiVO item, int pos, SparseBooleanArray selectedItems) {
                        getBinding().tvTitle.setText(item.getNotiTitle());
                        getBinding().tvTitle.setMaxLines(selectedItems.get(pos) ? Integer.MAX_VALUE : 1);
                        getBinding().tvTitle.setEllipsize(selectedItems.get(pos) ? null : TextUtils.TruncateAt.END);
                        getBinding().tvDate.setText(DateUtil.getDate(DateUtil.getDefaultDateFormat(item.getNotDt(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));
                        getBinding().tvContents.setVisibility(selectedItems.get(pos) ? View.VISIBLE : View.GONE);
                        getBinding().tvContents.setText(item.getNotiCont());
                        getBinding().ivArrow.setBackgroundResource(selectedItems.get(pos) ? R.drawable.g_list_icon_close : R.drawable.g_list_icon_open);
                        getBinding().ivBadge.setVisibility(DateUtil.getDiffMillis(item.getNotDt(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss) > DateUtils.WEEK_IN_MILLIS ? View.GONE : View.VISIBLE);
                        changeVisibility(selectedItems.get(pos),pos);
                }


                private void changeVisibility(final boolean isExpanded, final int position) {

                        if (isExpanded) {
                                getBinding().tvContents.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                                getBinding().tvContents.requestLayout();
                        }

                        if (getBinding().tvContents.getVisibility() == View.VISIBLE && isExpanded)
                                return;

                        // ValueAnimator.ofInt(int... values)는 View가 변할 값을 지정, 인자는 int 배열
                        ValueAnimator va = isExpanded ? ValueAnimator.ofInt(0, getBinding().tvContents.getHeight()) : ValueAnimator.ofInt(getBinding().tvContents.getHeight(), 0);
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







}