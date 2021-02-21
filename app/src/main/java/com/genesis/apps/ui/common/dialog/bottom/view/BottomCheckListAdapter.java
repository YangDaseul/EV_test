package com.genesis.apps.ui.common.dialog.bottom.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.genesis.apps.R;
import com.genesis.apps.databinding.ItemSurveyCheckBinding;
import com.genesis.apps.ui.common.dialog.bottom.BottomCheckListDialog;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class BottomCheckListAdapter extends RecyclerView.Adapter<BottomCheckListAdapter.ViewHolder> {

    private Context mContext = null;
    private View.OnClickListener mItemClick = null;
    private ArrayList<BottomCheckListDialog.CheckInfo> mItems;

    public BottomCheckListAdapter(Context context, View.OnClickListener itemClick) {
        mItems = new ArrayList<>();
        mContext = context;
        mItemClick = itemClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_survey_check, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BottomCheckListDialog.CheckInfo info = mItems.get(position);

        holder.binding.cbContent.setTag(position);
        holder.binding.cbContent.setChecked(info.isCheck());
        holder.binding.cbContent.setText(info.getName());
        holder.binding.cbContent.setOnClickListener(mItemClick);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemSurveyCheckBinding binding;

        public ViewHolder(View itemView) {
            super(itemView);
            binding = DataBindingUtil.bind(itemView);
        }
    }

    public void setData(ArrayList<BottomCheckListDialog.CheckInfo> items) {
        mItems = items;
        notifyDataSetChanged();
    }
}
