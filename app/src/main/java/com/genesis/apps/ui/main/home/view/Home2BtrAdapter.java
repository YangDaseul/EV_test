package com.genesis.apps.ui.main.home.view;

import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.gra.api.LGN_0003;
import com.genesis.apps.databinding.ItemBtrNewBinding;
import com.genesis.apps.ui.common.view.listener.OnSingleClickListener;
import com.genesis.apps.ui.common.view.listview.BaseRecyclerViewAdapter2;
import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;


public class Home2BtrAdapter extends BaseRecyclerViewAdapter2<LGN_0003.Response> {


    private static OnSingleClickListener onSingleClickListener;

    public Home2BtrAdapter(OnSingleClickListener onSingleClickListener) {
        this.onSingleClickListener = onSingleClickListener;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemBtrNew(getView(parent, R.layout.item_btr_new));
    }

    @Override
    public void onBindViewHolder(final BaseViewHolder holder, int position) {
        Log.v("recyclerview onBindViewHolder", "position pos:" + position);
//                super.onBindViewHolder(holder, position);
        holder.onBindView(getItem(position), position);
    }

    private static class ItemBtrNew extends BaseViewHolder<LGN_0003.Response, ItemBtrNewBinding> {
        public ItemBtrNew(View itemView) {
            super(itemView);
        }

        @Override
        public void onBindView(LGN_0003.Response item) {

        }

        @Override
        public void onBindView(LGN_0003.Response item, final int pos) {

            if(pos==0)
                getBinding().tvTitleBtr.setVisibility(View.VISIBLE);
            else
                getBinding().tvTitleBtr.setVisibility(View.GONE);

            getBinding().setListener(onSingleClickListener);

            if(item!=null) {
                switch (item.getButlSubsCd()) {
                    case VariableType.BTR_APPLY_CODE_2000:
                        getBinding().tvBtrApply.setVisibility(View.GONE);
                        getBinding().tvBtrStatus.setVisibility(View.GONE);
                        getBinding().ivBtrArrow.setVisibility(View.VISIBLE);
                        getBinding().lBtr.setOnClickListener(view -> onSingleClickListener.onClick(view));
                        break;
                    case VariableType.BTR_APPLY_CODE_3000:
                        getBinding().tvBtrApply.setVisibility(View.GONE);
                        getBinding().tvBtrStatus.setVisibility(View.VISIBLE);
                        getBinding().ivBtrArrow.setVisibility(View.VISIBLE);
                        getBinding().lBtr.setOnClickListener(view -> onSingleClickListener.onClick(view));
                        break;
                    case VariableType.BTR_APPLY_CODE_1000:
                    default:
                        getBinding().tvBtrApply.setVisibility(View.VISIBLE);
                        getBinding().tvBtrStatus.setVisibility(View.GONE);
                        getBinding().ivBtrArrow.setVisibility(View.GONE);
                        getBinding().lBtr.setOnClickListener(null);
                        break;
                }
            }else{
                getBinding().tvBtrApply.setVisibility(View.VISIBLE);
                getBinding().tvBtrStatus.setVisibility(View.GONE);
                getBinding().ivBtrArrow.setVisibility(View.GONE);
                getBinding().lBtr.setOnClickListener(null);
            }
            
        }

        @Override
        public void onBindView(LGN_0003.Response item, int pos, SparseBooleanArray selectedItems) {

        }
    }

}