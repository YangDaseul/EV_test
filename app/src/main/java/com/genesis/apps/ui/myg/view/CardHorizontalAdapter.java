package com.genesis.apps.ui.myg.view;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.CardVO;
import com.genesis.apps.comm.util.BarcodeUtil;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.databinding.ItemCardBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import static com.genesis.apps.comm.model.vo.CardVO.CARD_STATUS_20;
import static com.genesis.apps.comm.model.vo.CardVO.CARD_STATUS_30;


public class CardHorizontalAdapter extends BaseRecyclerViewAdapter2<CardVO> {

    private static OnSingleClickListener onSingleClickListener;

    public CardHorizontalAdapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.v("recyclerview test2", "create :");
        return new ItemCard(getView(parent, R.layout.item_card));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);
        holder.onBindView(getItem(position), position);

    }

    private static class ItemCard extends BaseViewHolder<CardVO, ItemCardBinding> {
        public ItemCard(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(CardVO item) {

        }

        @Override
        public void onBindView(CardVO item, final int pos) {
            getBinding().tvCardName.setVisibility(View.VISIBLE);
            getBinding().tvCardNo2.setVisibility(View.VISIBLE);
            getBinding().tvCardNo.setVisibility(View.VISIBLE);
            getBinding().tvCardDate.setVisibility(View.VISIBLE);
            getBinding().ivBarcode.setVisibility(View.VISIBLE);
            getBinding().ivFavorite.setVisibility(View.VISIBLE);
            getBinding().ivCard.setImageResource(0);
            getBinding().ivCard.setImageResource(R.drawable.img_card_320);
            getBinding().ivCard.setAdjustViewBounds(true);
            getBinding().ivAdd.setVisibility(View.GONE);
            getBinding().tvAdd.setVisibility(View.GONE);
            getBinding().lWhole.setOnClickListener(null);
            getBinding().tvStatus.setVisibility(View.GONE);
            getBinding().lWhole.setElevation(3);
            getBinding().lWhole.setTag(R.id.item_position, pos);
            getBinding().ivFavorite.setTag(R.id.item_position, pos);
            getBinding().lWhole.setBackgroundResource(R.drawable.bg_ffffff_round_10);
            switch (item.getCardStusNm()) {
                case CardVO.CARD_STATUS_99://추가
                    getBinding().tvCardName.setVisibility(View.INVISIBLE);
                    getBinding().tvCardNo2.setVisibility(View.INVISIBLE);
                    getBinding().tvCardNo.setVisibility(View.INVISIBLE);
                    getBinding().tvCardDate.setVisibility(View.INVISIBLE);
                    getBinding().ivBarcode.setVisibility(View.INVISIBLE);
                    getBinding().ivFavorite.setVisibility(View.INVISIBLE);
                    getBinding().ivCard.setImageResource(0);
                    getBinding().ivCard.setImageResource(R.drawable.img_box_addcard);
                    getBinding().ivAdd.setVisibility(View.VISIBLE);
                    getBinding().tvAdd.setVisibility(View.VISIBLE);
                    getBinding().lWhole.setElevation(0);
                    getBinding().lWhole.setBackgroundColor(getContext().getColor(R.color.x_00000000));
                    getBinding().lWhole.setOnClickListener(onSingleClickListener);
                    return;
                case CardVO.CARD_STATUS_10://정상
                    getBinding().ivFavorite.setBackgroundResource(item.isFavorite() ? R.drawable.ic_star_l_s : R.drawable.ic_star_l_b2);
                    getBinding().ivFavorite.setOnClickListener(onSingleClickListener);
                    break;
                case CardVO.CARD_STATUS_0://발급중
                    getBinding().tvStatus.setVisibility(View.VISIBLE);
                    getBinding().ivFavorite.setVisibility(View.INVISIBLE);
                    break;

                case CARD_STATUS_20://정지 처리안함
                case CARD_STATUS_30://소멸
                default:
                    return;
            }


            getBinding().tvCardName.setText(item.getCardNm() + " " + item.getCardClsNm());
            getBinding().tvCardDate.setText(DateUtil.getDate(DateUtil.getDefaultDateFormat(item.getCardIsncSubspDt(), DateUtil.DATE_FORMAT_yyyyMMdd), DateUtil.DATE_FORMAT_yyyy_mm_dd_dot)); //데이트 포맷정의

            if (!TextUtils.isEmpty(item.getCardNo())) {
                getBinding().tvCardNo2.setText(StringRe2j.replaceAll(item.getCardNo(), getContext().getString(R.string.card_original), getContext().getString(R.string.card_mask)));
                getBinding().tvCardNo.setText(StringRe2j.replaceAll(item.getCardNo(), getContext().getString(R.string.card_original), getContext().getString(R.string.card_mask)));
                getBinding().ivBarcode.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        try {
                            getBinding().ivBarcode.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                            Bitmap bitmap = new BarcodeUtil().encodeAsBitmap(item.getCardNo().replace("-", ""), BarcodeFormat.CODE_128, (int) DeviceUtil.dip2Pixel(getContext(), (float) getBinding().ivBarcode.getWidth()), (int) DeviceUtil.dip2Pixel(getContext(), (float) getBinding().ivBarcode.getHeight()));
                            Log.v("barcodetest", "pos:" + pos + "       width:" + (int) DeviceUtil.dip2Pixel(getContext(), (float) getBinding().ivCard.getWidth()) + "    height:" + (int) DeviceUtil.dip2Pixel(getContext(), (float) getBinding().ivCard.getHeight()) + "   mwight:" + getBinding().ivCard.getMeasuredWidth());
                            getBinding().ivBarcode.setImageBitmap(bitmap);
                        } catch (WriterException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                getBinding().tvCardNo2.setVisibility(View.INVISIBLE);
                getBinding().tvCardNo.setVisibility(View.INVISIBLE);
                getBinding().ivBarcode.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void onBindView(CardVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }

    //소멸 및 정지상태 아이템은 제거
    public void applyFilter() {
        for (int i = 0; i < getItems().size(); i++) {
            if (getItems().get(i).getCardStusNm().equalsIgnoreCase(CARD_STATUS_20)
                    || getItems().get(i).getCardStusNm().equalsIgnoreCase(CARD_STATUS_30)) {
                remove(i);
            }
        }
    }

    //카드 추가 레이아웃 생성
    public void addCard() {
        CardVO cardVO = new CardVO("", "", "", CardVO.CARD_STATUS_99, "", "", "", false);
        addRow(cardVO);
    }

}