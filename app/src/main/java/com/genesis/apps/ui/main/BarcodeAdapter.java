package com.genesis.apps.ui.main;

import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.CardVO;
import com.genesis.apps.comm.util.BarcodeUtil;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.StringRe2j;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.databinding.ItemBarcodeBinding;
import com.genesis.apps.databinding.ItemBarcodeModifyBinding;
import com.genesis.apps.ui.common.activity.test.ItemMoveCallback;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
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

    private static OnSingleClickListener onSingleClickListener;

    public BarcodeAdapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
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

            getBinding().tvMembershipInfo.setOnClickListener(onSingleClickListener);
            getBinding().tvIntegration.setOnClickListener(onSingleClickListener);

        }

        @Override
        public void onBindView(CardVO item, final int pos) {

            getBinding().tvCardBg.setText("");
            getBinding().tvInfo.setVisibility(View.GONE);
            getBinding().tvMembershipInfo.setVisibility(View.GONE);
            getBinding().tvMembershipInfo.setTag(R.id.item, item);
            getBinding().tvIntegration.setTag(R.id.item, item);
            int imageId = R.drawable.bg_111111_round_10;
            int iconId = R.drawable.logo_genesis_w;
            boolean isReg = false;
            boolean isIntegration = true;
            switch (StringUtil.isValidString(item.getIsncCd())){
                case OIL_CODE_HDOL:
                    imageId = R.drawable.bg_025ea9_round_10;
                    iconId = R.drawable.logo_hyundaioilbank_w;
                    getBinding().tvMembershipInfo.setVisibility(View.VISIBLE);
                    isReg = StringUtil.isValidString(item.getRgstYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES)&&!TextUtils.isEmpty(item.getCardNo());
                    break;
                case OIL_CODE_GSCT:
                    imageId = R.drawable.bg_009999_round_10;
                    iconId = R.drawable.logo_gs_w;
                    getBinding().tvMembershipInfo.setVisibility(View.VISIBLE);
                    isReg = StringUtil.isValidString(item.getRgstYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES)&&!TextUtils.isEmpty(item.getCardNo());
                    break;
                case OIL_CODE_SOIL:
                    imageId = R.drawable.bg_fbb900_round_10;
                    iconId = R.drawable.logo_soil_w;
                    getBinding().tvMembershipInfo.setVisibility(View.VISIBLE);
                    isReg = StringUtil.isValidString(item.getRgstYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES)&&!TextUtils.isEmpty(item.getCardNo());
                    break;
                case OIL_CODE_BLUE:
                default:
                    imageId = R.drawable.bg_111111_round_10;
                    iconId = R.drawable.logo_genesis_w;
                    getBinding().tvInfo.setVisibility(View.VISIBLE);
                    isReg = !TextUtils.isEmpty(item.getCardNo());
                    isIntegration = false;
                    break;
            }
            getBinding().ivCard.setImageResource(imageId);
            getBinding().ivLogo.setImageResource(iconId);


            if(isReg) {
                getBinding().tvIntegration.setVisibility(View.GONE);
                getBinding().tvCardNo.setVisibility(View.VISIBLE);
                getBinding().tvCardNo.setText(StringRe2j.replaceAll(item.getCardNo(), getContext().getString(R.string.card_original), getContext().getString(R.string.card_mask)));
                getBinding().ivBarcode.setVisibility(View.VISIBLE);
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
            }else{
                getBinding().tvCardNo.setVisibility(View.INVISIBLE);
                if(isIntegration) {
                    getBinding().tvIntegration.setVisibility(View.VISIBLE);
                    getBinding().ivBarcode.setVisibility(View.VISIBLE);
                    //                getBinding().ivBarcode.setImageResource(0);
                }else{
                    getBinding().tvIntegration.setVisibility(View.GONE);
                    getBinding().tvCardBg.setText(R.string.bcode01_5);
                    getBinding().ivBarcode.setVisibility(View.GONE);
                }
            }

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

            int iconId = R.drawable.logo_genesis_barcode_w;
            int bgId = R.color.x_111111;
            switch (item.getIsncCd()){
                case OIL_CODE_HDOL:
                    iconId = R.drawable.logo_hyundaioilbank_barcode_w;
                    bgId = R.color.x_025ea9;
                    break;
                case OIL_CODE_GSCT:
                    iconId = R.drawable.logo_gs_barcode_w;
                    bgId = R.color.x_009999;
                    break;
                case OIL_CODE_SOIL:
                    iconId = R.drawable.logo_soil_w;
                    bgId = R.color.x_fbb900;
                    break;
//                case OIL_CODE_SKNO:
//                    iconId = R.drawable.logo_sk_w;
//                    bgId = R.color.x_4f585b;
//                    break;
                case OIL_CODE_BLUE:
                default:
                    iconId = R.drawable.logo_genesis_barcode_w;
                    bgId = R.color.x_111111;
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