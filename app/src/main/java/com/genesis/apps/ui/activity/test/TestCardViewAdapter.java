package com.genesis.apps.ui.activity.test;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.genesis.apps.R;
import com.genesis.apps.ui.view.listview.Link;
import com.genesis.apps.ui.view.listview.MyItemClickListener;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;


public class TestCardViewAdapter extends ListAdapter<Link, TestCardViewAdapter.MyViewHolder> {

        MyItemClickListener myItemClickListener;
        public TestCardViewAdapter(@NonNull DiffUtil.ItemCallback diffCallback,
                                   MyItemClickListener myItemClickListener) {
                super(diffCallback);
                this.myItemClickListener = myItemClickListener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.square_view, parent,false);

                return new MyViewHolder(itemLayoutView);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int position) {
                myViewHolder.bind(getItem(position));
        }

        // inner class
        public class MyViewHolder extends RecyclerView.ViewHolder {

                private ImageView image;

                public MyViewHolder(View itemLayoutView) {
                        super(itemLayoutView);
                        this.image = itemLayoutView.findViewById(R.id.image);
                }
                public void bind(Link link){

                        this.image.setImageResource(link.getIcon());
                        this.image.setOnClickListener(
                                v -> myItemClickListener.onClick(link));

                }
        }
}