package com.genesis.apps.ui.main;

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
        return new ItemAlarmCenter(getView(parent, R.layout.item_accodian_alarm), selectedItems);
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        holder.onBindView(getItem(position), position);
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    private static class ItemAlarmCenter extends BaseViewHolder<NotiInfoVO, ItemAccodianAlarmBinding> {

        private SparseBooleanArray selectedItems;

        public ItemAlarmCenter(View itemView, SparseBooleanArray selectedItems) {
            super(itemView);

            this.selectedItems = selectedItems;

            getBinding().lTitle.setOnClickListener(new OnSingleClickListener() {
                @Override
                public void onSingleClick(View v) {

                    NotiInfoVO item = (NotiInfoVO)v.getTag(R.id.noti_info);
                    //아코디언 형태의 아이템인 경우 펼침/접음 처리 진행
                    if (item != null && getAccordionType(item).equalsIgnoreCase(AlarmCenterRecyclerAdapter.ALARM_TYPE_ACCORDION)) {
                        int position = Integer.parseInt(v.getTag(R.id.position).toString());

                        if (selectedItems.get(position)) {
                            // 펼쳐진 Item을 클릭 시
                            selectedItems.delete(position);
                        } else {
                            // 클릭한 Item의 position을 저장
                            selectedItems.put(position, true);
                        }
                        changeViewStatus(selectedItems.get(position));
                    }
                    onSingleClickListener.onSingleClick(v);
                }
            });
        }

        @Override
        public void onBindView(NotiInfoVO item) {

        }

        @Override
        public void onBindView(NotiInfoVO item, int pos) {
            getBinding().btnDetail.setOnClickListener(null);
            getBinding().lTitle.setTag(R.id.noti_info, item);
            getBinding().lTitle.setTag(R.id.position, pos);

            getBinding().tvCateNm.setText(item.getCateNm() != null ? String.format(getContext().getString(R.string.alrm01_6), item.getCateNm()) : "");
            getBinding().tvTitle.setText(item.getTitle());
            getBinding().tvTitle.setMaxLines(selectedItems.get(pos) ? Integer.MAX_VALUE : 1);
            getBinding().tvTitle.setEllipsize(selectedItems.get(pos) ? null : TextUtils.TruncateAt.END);
            getBinding().tvDate.setText(DateUtil.getDate(DateUtil.getDefaultDateFormat(item.getNotDt(), DateUtil.DATE_FORMAT_yyyyMMddHHmmss), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot));
            getBinding().ivBadge.setVisibility(item.getReadYn().equalsIgnoreCase("Y") ? View.GONE : View.VISIBLE);

            switch (AlarmCenterRecyclerAdapter.getAccordionType(item)){
                case ALARM_TYPE_NORMAL_NATIVE:
                case ALARM_TYPE_NORMAL_WEBVIEW:
                    getBinding().ivArrow.setBackgroundResource(R.drawable.btn_arrow_open_r);
                    getBinding().lContents.setVisibility(View.GONE);
                    break;
                case ALARM_TYPE_ACCORDION:
                default:
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
                    break;
            }
        }

        @Override
        public void onBindView(NotiInfoVO item, int pos, SparseBooleanArray selectedItems) {

        }


        //세부사항 뷰의 개폐 상태가 변경되는 애니메이션을 처리
        // (클릭해서 상태를 토글할 때 호출됨)
        private void changeViewStatus(boolean opened) {
            getBinding().ivArrow.setBackgroundResource(opened ? R.drawable.g_list_icon_close : R.drawable.g_list_icon_open);
            changeVisibility(
                    getBinding().lContents, //개폐 애니메이션 대상 뷰
                    getBinding().lWhole, //애니 재생동안 스크롤 막아야되는 뷰
                    opened); //개폐 상태
        }

    }

    public static final String ALARM_TYPE_NORMAL_NATIVE="NORMAL_NATIVE";
    public static final String ALARM_TYPE_NORMAL_WEBVIEW="NORMAL_WEBVIEW";
    public static final String ALARM_TYPE_ACCORDION="ACCORDION";
    public static String getAccordionType(NotiInfoVO item){
        if (item.getMsgLnkCd().equalsIgnoreCase("I") && !TextUtils.isEmpty(item.getMsgLnkUri())) { //메시지 링크 코드가 대표앱이고 네이티브로 이동 가능한 URI가 있을 경우
           return ALARM_TYPE_NORMAL_NATIVE;
        } else if (item.getMsgLnkCd().equalsIgnoreCase("O") && !TextUtils.isEmpty(item.getMsgLnkUri())) {//메시지 링크 코드가 외부링크이고 웹뷰로 이동 가능한 URI가 있을 경우
            return ALARM_TYPE_NORMAL_WEBVIEW;
        } else {
            return ALARM_TYPE_ACCORDION;
        }
    }
}