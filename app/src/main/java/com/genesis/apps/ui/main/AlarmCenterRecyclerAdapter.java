package com.genesis.apps.ui.main;

import android.animation.ValueAnimator;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.NotiInfoVO;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.databinding.ItemAccodianAlarmBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class AlarmCenterRecyclerAdapter extends BaseRecyclerViewAdapter2<NotiInfoVO> {

    // Item의 클릭 상태를 저장할 array 객체
    private SparseBooleanArray selectedItems = new SparseBooleanArray();
    private int pageNo = 0;
    private static OnSingleClickListener onSingleClickListener;
    public AlarmCenterRecyclerAdapter(OnSingleClickListener onSingleClickListener) {
            this.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("recyclerview test2", "create :");
        return new ItemAlarmCenter(getView(parent, R.layout.item_accodian_alarm));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);
        ItemAlarmCenter viewHolder = ((ItemAlarmCenter) holder);

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


    private static class ItemAlarmCenter extends BaseViewHolder<NotiInfoVO, ItemAccodianAlarmBinding> {
        public ItemAlarmCenter(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(NotiInfoVO item) {

        }

        @Override
        public void onBindView(NotiInfoVO item, int pos) {

        }

        @Override
        public void onBindView(NotiInfoVO item, int pos, SparseBooleanArray selectedItems) {
            getBinding().lTitle.setOnClickListener(null);
            getBinding().btnDetail.setOnClickListener(null);

            getBinding().tvCateNm.setText(item.getCateNm() != null ? String.format(getContext().getString(R.string.alrm01_6), item.getCateNm()) : "");
            getBinding().tvTitle.setText(item.getTitle());
            getBinding().tvTitle.setMaxLines(selectedItems.get(pos) ? Integer.MAX_VALUE : 1);
            getBinding().tvTitle.setEllipsize(selectedItems.get(pos) ? null : TextUtils.TruncateAt.END);
            getBinding().tvDate.setText(DateUtil.getDate(DateUtil.getDefaultDateFormat(item.getNotDt(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));
            getBinding().ivBadge.setVisibility(item.getReadYn().equalsIgnoreCase("Y") ? View.GONE : View.VISIBLE);

            if (item.getMsgLnkCd().equalsIgnoreCase("I") && !TextUtils.isEmpty(item.getMsgLnkUri())) {
                getBinding().ivArrow.setBackgroundResource(R.drawable.btn_arrow_open_r);
                getBinding().lContents.setVisibility(View.GONE);
                getBinding().lTitle.setTag(R.id.noti_info, item);
                getBinding().lTitle.setOnClickListener(onSingleClickListener);
                //TODO 클릭 시 상세페이지 이동 / getMsgLnkUri가 메뉴면 네이티브, 링크면 WEBVIEW로 이동시켜야하는데 확인 필요
            } else if (item.getMsgLnkCd().equalsIgnoreCase("O") && !TextUtils.isEmpty(item.getMsgLnkUri())) {
                getBinding().ivArrow.setBackgroundResource(R.drawable.btn_arrow_open_r);
                getBinding().lContents.setVisibility(View.GONE);

                getBinding().lTitle.setTag(R.id.noti_info, item);
                getBinding().lTitle.setOnClickListener(onSingleClickListener);
                //TODO 클릭 시 외부 링크로 이동
            } else {
                if (TextUtils.isEmpty(item.getContents())) {
                    getBinding().tvContents.setVisibility(View.GONE);
                } else {
                    getBinding().tvContents.setText(item.getContents());//때에 따라서 메시지는 뭇시됨
                    getBinding().tvContents.setVisibility(View.VISIBLE);
                }

                if (TextUtils.isEmpty(item.getDtlLnkUri())) {
                    getBinding().btnDetail.setVisibility(View.GONE);
                } else {
                    getBinding().btnDetail.setVisibility(View.VISIBLE);
                    getBinding().btnDetail.setTag(R.id.noti_info, item);
                    getBinding().btnDetail.setOnClickListener(onSingleClickListener);

                    //TODO dtlLnkCd 코드를 보고 이벤트 리스너..결정해야함
                }

                if (TextUtils.isEmpty(item.getImgFilUri1())) {
                    getBinding().ivImg.setVisibility(View.GONE);
                } else {
                    getBinding().ivImg.setVisibility(View.VISIBLE);
                    Glide
                            .with(getContext())
                            .load(item.getImgFilUri1())
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .error(R.drawable.img_car_339_2) //todo 대체 이미지 필요
                            .placeholder(R.drawable.img_car_339_2) //todo 에러시 대체 이미지 필요
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(getBinding().ivImg);
                }

                getBinding().ivArrow.setBackgroundResource(selectedItems.get(pos) ? R.drawable.g_list_icon_close : R.drawable.g_list_icon_open);
                getBinding().lContents.setVisibility(selectedItems.get(pos) ? View.VISIBLE : View.GONE);
                changeVisibility(selectedItems.get(pos), pos);
            }
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