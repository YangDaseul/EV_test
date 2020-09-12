//package com.genesis.apps.ui.common.view.viewholder;
//
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.DiffUtil;
//import androidx.recyclerview.widget.ListAdapter;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.genesis.apps.R;
//import com.genesis.apps.comm.model.vo.NotiVO;
//import com.genesis.apps.ui.common.view.viewholder.BaseViewHolder;
//import com.genesis.apps.ui.common.view.ViewHolderC;
//import com.genesis.apps.ui.common.view.listview.test.Link;
//import com.genesis.apps.ui.common.view.listview.test.MyItemClickListener;
//
//
//public class NotiAccodianListAdapter extends ListAdapter<NotiVO, BaseViewHolder> {
//        ItemClickCallBack itemClickCallBack;
//        public NotiAccodianListAdapter(@NonNull DiffUtil.ItemCallback diffCallback,
//                                       ItemClickCallBack itemClickCallBack) {
//                super(diffCallback);
//                this.itemClickCallBack = itemClickCallBack;
//        }
//
//        @NonNull
//        @Override
//        public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return new ItemNoti(getView(parent, R.layout.item_accodian_noti));
//        }
//
//        @Override
//        public void onBindViewHolder(@NonNull BaseViewHolder viewHolder, int position) {
//                viewHolder.onBindView(getItem(position),position);
//                ((ItemNoti)viewHolder).getBinding().ivArrow.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
//                                itemClickCallBack.onClick(getItem(position), position);
//                        }
//                });
//        }
//
//        public View getView(ViewGroup parent,int layout){
//                return LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
//        }
//
//
//        public interface ItemClickCallBack {
//                void onClick(NotiVO data, int position);
//        }
//
//}