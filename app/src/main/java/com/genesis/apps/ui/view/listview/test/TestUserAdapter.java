package com.genesis.apps.ui.view.listview.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.genesis.apps.R;


public class TestUserAdapter extends ListAdapter<Link, TestUserAdapter.MyViewHolder> {

        MyItemClickListener myItemClickListener;
        public TestUserAdapter(@NonNull DiffUtil.ItemCallback diffCallback,
                             MyItemClickListener myItemClickListener) {
                super(diffCallback);
                this.myItemClickListener = myItemClickListener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemLayoutView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_layout, null);

                return new MyViewHolder(itemLayoutView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
                myViewHolder.bind(getItem(position));
        }

        // inner class
        public class MyViewHolder extends RecyclerView.ViewHolder {

                private View itemLayoutView;
                private TextView itemTitle;
                private TextView itemUrl;
                private ImageView itemIcon;

                public MyViewHolder(View itemLayoutView) {
                        super(itemLayoutView);
                        this.itemLayoutView = itemLayoutView;
                        this.itemTitle = itemLayoutView.findViewById(R.id.item_title);
                        this.itemUrl = itemLayoutView.findViewById(R.id.item_url);
                        this.itemIcon = itemLayoutView.findViewById(R.id.item_icon);
                }
                public void bind(Link link){
                        this.itemIcon.setImageResource(link.getIcon());
                        this.itemTitle.setText(link.getTitle());
                        this.itemUrl.setText(link.getUrl());

                        this.itemLayoutView.setOnClickListener(
                                v -> myItemClickListener.onClick(link));

                }
        }
}