package com.genesis.apps.ui.main;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.CardVO;
import com.genesis.apps.comm.util.BarcodeUtil;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.databinding.ItemBarcodeBinding;
import com.genesis.apps.databinding.ItemBarcodeModifyBinding;
import com.genesis.apps.ui.common.activity.test.ItemMoveCallback;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.util.Collections;

import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_BLUE;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_GSCT;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_HDOL;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_SKNO;
import static com.genesis.apps.comm.model.vo.OilPointVO.OIL_CODE_SOIL;


public class BarcodeAdapter extends BaseRecyclerViewAdapter2<CardVO> implements ItemMoveCallback.ItemTouchHelperAdapter {

    public static final int TYPE_CARD = 0;
    public static final int TYPE_LINE = 1;
    private int VIEW_TYPE = TYPE_CARD;

    public int getViewType() {
        return VIEW_TYPE;
    }

    public void setViewType(int VIEW_TYPE) {
        this.VIEW_TYPE = VIEW_TYPE;
    }

    public BarcodeAdapter() {
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (VIEW_TYPE == TYPE_LINE)
            return new ItemBarcodeModify(getView(parent, R.layout.item_barcode_modify));
        else
            return new ItemBarcode(getView(parent, R.layout.item_barcode));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);
        holder.onBindView(getItem(position), position);
    }

    @Override
    public void onItemMove(int fromPos, int targetPos) {
        if (fromPos < targetPos) {
            for (int i = fromPos; i < targetPos; i++) {
                Collections.swap(getItems(), i, i + 1);
            }
        } else {
            for (int i = fromPos; i > targetPos; i--) {
                Collections.swap(getItems(), i, i - 1);
            }
        }
        notifyItemMoved(fromPos, targetPos);
    }

    @Override
    public void onItemDismiss(int pos) {
        getItems().remove(pos);
        notifyItemRemoved(pos);
    }


    private static class ItemBarcode extends BaseViewHolder<CardVO, ItemBarcodeBinding> {
        public ItemBarcode(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(CardVO item) {

        }

        @Override
        public void onBindView(CardVO item, final int pos) {

            int imageId = R.drawable.bg_141414_round_10;
            int iconId = R.drawable.logo_genesis_w;
            switch (item.getIsncCd()){
                case OIL_CODE_HDOL:
                    imageId = R.drawable.bg_323a3d_round_10;
                    iconId = R.drawable.logo_hyundaioilbank_w;
                    break;
                case OIL_CODE_GSCT:
                    imageId = R.drawable.bg_000000_round_10;
                    iconId = R.drawable.logo_gs_w;
                    break;
                case OIL_CODE_SOIL:
                    imageId = R.drawable.bg_82898b_round_10;
                    iconId = R.drawable.logo_soil_w;
                    break;
                case OIL_CODE_SKNO:
                    imageId = R.drawable.bg_4f585b_round_10;
                    iconId = R.drawable.logo_sk_w;
                    break;
                case OIL_CODE_BLUE:
                default:
                    imageId = R.drawable.bg_141414_round_10;
                    iconId = R.drawable.logo_genesis_w;
                    break;
            }

            getBinding().ivCard.setImageResource(imageId);
            getBinding().ivLogo.setImageResource(iconId);
            getBinding().tvCardNo.setText(StringRe2j.replaceAll(item.getCardNo(), getContext().getString(R.string.card_original), getContext().getString(R.string.card_mask)));
            getBinding().ivBarcode.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    try {
                        getBinding().ivBarcode.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        Bitmap bitmap = new BarcodeUtil().encodeAsBitmap(item.getCardNo().replace("-", ""), BarcodeFormat.CODE_128, (int) DeviceUtil.dip2Pixel(getContext(), (float) getBinding().ivBarcode.getWidth()), (int) DeviceUtil.dip2Pixel(getContext(), (float) getBinding().ivBarcode.getHeight()));
                        getBinding().ivBarcode.setImageBitmap(bitmap);
                    } catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            });

        }

        @Override
        public void onBindView(CardVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }


    private static class ItemBarcodeModify extends BaseViewHolder<CardVO, ItemBarcodeModifyBinding> {
        public ItemBarcodeModify(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(CardVO item) {

        }

        @Override
        public void onBindView(CardVO item, final int pos) {

            int iconId = R.drawable.logo_genesis_w;
            int bgId = R.color.x_141414;
            switch (item.getIsncCd()){
                case OIL_CODE_HDOL:
                    iconId = R.drawable.logo_hyundaioilbank_w;
                    bgId = R.color.x_323a3d;
                    break;
                case OIL_CODE_GSCT:
                    iconId = R.drawable.logo_gs_w;
                    bgId = R.color.x_000000;
                    break;
                case OIL_CODE_SOIL:
                    iconId = R.drawable.logo_soil_w;
                    bgId = R.color.x_82898b;
                    break;
                case OIL_CODE_SKNO:
                    iconId = R.drawable.logo_sk_w;
                    bgId = R.color.x_4f585b;
                    break;
                case OIL_CODE_BLUE:
                default:
                    iconId = R.drawable.logo_genesis_w;
                    bgId = R.color.x_141414;
                    break;
            }
            getBinding().ivLogo.setImageResource(iconId);
            getBinding().lCard.setBackgroundColor(getContext().getColor(bgId));
        }

        @Override
        public void onBindView(CardVO item, int pos, SparseBooleanArray selectedItems) {

        }

    }


}