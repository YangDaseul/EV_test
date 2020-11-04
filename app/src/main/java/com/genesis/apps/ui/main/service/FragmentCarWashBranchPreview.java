package com.genesis.apps.ui.main.service;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.WashBrnVO;
import com.genesis.apps.databinding.FragmentCarWashBranchPreviewBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentCarWashBranchPreview extends SubFragment<FragmentCarWashBranchPreviewBinding> {
    private static final String TAG = FragmentCarWashBranchPreview.class.getSimpleName();

    private BranchPreviewAdapter adapter;
    private List<String> urlList;

    public FragmentCarWashBranchPreview(WashBrnVO pickedBranch) {
        Log.d(TAG, "FragmentCarWashBranchPreview: ");

        urlList = new ArrayList<>();

        //... 아무리 생각해도 서버가 이걸 처음부터 리스트로 주는 게 맞는 거 같은데 ㅡㅡ;;
        String temp = pickedBranch.getBrnhImgUri1();//1번
        if (temp != null && !temp.equals("")) {
            urlList.add(temp);
        }

        temp = pickedBranch.getBrnhImgUri2();//2번
        if (temp != null && !temp.equals("")) {
            urlList.add(temp);
        }

        temp = pickedBranch.getBrnhImgUri3();//3번
        if (temp != null && !temp.equals("")) {
            urlList.add(temp);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        return super.setContentView(inflater, R.layout.fragment_car_wash_branch_preview);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new BranchPreviewAdapter(getContext(), urlList);
        me.vpBranchPreview.setAdapter(adapter);
        me.vpBranchPreview.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        me.vpBranchPreview.setCurrentItem(0);

        me.ivCloseBranchPreview.setOnClickListener(onSingleClickListener);
    }

    @Override
    public void onRefresh() {
    }

    @Override
    public boolean onBackPressed() {
        getActivity().onBackPressed();
        return false;
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()) {
            case R.id.iv_close_branch_preview:
                onBackPressed();
                break;

            default:
                //do nothing
                break;
        }
    }

    //어댑터
    private static class BranchPreviewAdapter extends RecyclerView.Adapter<BranchPreviewAdapter.BranchPreviewViewHolder> {
        Context context;
        List<String> urlList;

        public BranchPreviewAdapter(Context context, List<String> list) {
            this.context = context;
            urlList = list;
        }

        @NonNull
        @Override
        public BranchPreviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            Log.d(TAG, "onCreateViewHolder: ");
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_image_view, parent, false);
            return new BranchPreviewViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull BranchPreviewViewHolder holder, int position) {
            holder.onBindView(urlList.get(position));
        }

        @Override
        public int getItemCount() {
            return urlList.size();
        }

        @Override
        public int getItemViewType(int position) {
            return 0;//don't care
        }

        //뷰홀더
        private class BranchPreviewViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public BranchPreviewViewHolder(View view) {
                super(view);
                Log.d(TAG, "BranchPreviewViewHolder: ");
                imageView = view.findViewById(R.id.iv_layout_image_view_image);
            }

            public void onBindView(String item) {
                Log.d(TAG, "onBindView: ");
                Glide.with(context)
                        .load(item)
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .error(R.drawable.img_car_339_2) //todo 대체 이미지 필요
                        .placeholder(R.drawable.img_car_339_2) //todo 에러시 대체 이미지 필요
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(imageView);
            }
        }
    }
}

