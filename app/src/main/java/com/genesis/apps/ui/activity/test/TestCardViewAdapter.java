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

        public static final int TYPE_CARD = 0;
        public static final int TYPE_LINE = 1;
        private int VIEW_TYPE = TYPE_CARD;

        public int getVIEW_TYPE() {
                return VIEW_TYPE;
        }

        public void setVIEW_TYPE(int VIEW_TYPE) {
                this.VIEW_TYPE = VIEW_TYPE;
        }





        MyItemClickListener myItemClickListener;
        public TestCardViewAdapter(@NonNull DiffUtil.ItemCallback diffCallback,
                                   MyItemClickListener myItemClickListener) {
                super(diffCallback);
                this.myItemClickListener = myItemClickListener;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                int layout = R.layout.square_view;
                if(VIEW_TYPE==TYPE_LINE)
                        layout = R.layout.square_view_line;

                View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(layout, parent,false);
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