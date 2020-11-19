package com.genesis.apps.ui.main.contents;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CTT_1001;
import com.genesis.apps.comm.model.vo.ContentsVO;
import com.genesis.apps.comm.viewmodel.CTTViewModel;
import com.genesis.apps.databinding.FragmentContentsBinding;
import com.genesis.apps.ui.common.activity.WebviewActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentContents extends SubFragment<FragmentContentsBinding> {

    private CTTViewModel cttViewModel;
    private ContentsAdapter contentsAdapter;
    private boolean isEvent=false;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_contents);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        cttViewModel = new ViewModelProvider(getActivity()).get(CTTViewModel.class);
        me.setLifecycleOwner(getViewLifecycleOwner());
        me.setFragment(this);
        initViewPager();
        cttViewModel.getRES_CTT_1001().observe(getViewLifecycleOwner(), result -> {

            switch (result.status){
                case LOADING:
                    ((MainActivity)getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((MainActivity)getActivity()).showProgressDialog(false);


                    List<ContentsVO> list = new ArrayList<>();

                    if(result.data!=null&&result.data.getTtlList()!=null){
                        list = result.data.getTtlList();
                    }

                    int itemSizeBefore = contentsAdapter.getItemCount();
                    if (contentsAdapter.getPageNo() == 0) {
                        contentsAdapter.setRows(list);
                    } else {
                        contentsAdapter.addRows(list);
                    }
                    contentsAdapter.setPageNo(contentsAdapter.getPageNo() + 1);
                    contentsAdapter.notifyItemRangeInserted(itemSizeBefore, contentsAdapter.getItemCount());
                    me.lEmpty.setVisibility(contentsAdapter.getItemCount()==0 ? View.VISIBLE : View.GONE);
                    break;
                default:
                    ((MainActivity)getActivity()).showProgressDialog(false);
                    break;
            }
        });
    }

    private void initViewPager(){
        //ViewPager Setting
        contentsAdapter = new ContentsAdapter(onSingleClickListener);
        me.vp.setAdapter(contentsAdapter);
        me.vp.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        me.vp.setCurrentItem(0);
        me.vp.setOffscreenPageLimit(3);

        me.vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    me.vp.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

        });

        final float pageMargin= getResources().getDimensionPixelOffset(R.dimen.vpMargin);

        me.vp.setPageTransformer((view, position) -> {
            float myOffset = position * -(pageMargin);
//                page.setScaleX(1 - (0.25f * abs(position)));
//                page.setTranslationY(pageTranslationY * position);
            final float tmp = 0.75f;

            if (position <= -1) { // [-Infinity,-1)
                // This page is way off-screen to the left.
                view.setAlpha(0);

            } else if (position <= 0) { // [-1,0]
                // Use the default slide transition when moving to the left/top page
                view.setAlpha(1);
                ViewCompat.setElevation(view, 1);
                // Counteract the default slide transition
                view.setTranslationY(view.getWidth() * -position); //위로 올리는 형태
                view.setTranslationY(view.getHeight() * -position); //덮는 형태
                view.setTranslationX(0);

                //set Y position to swipe in from top
                float scaleFactor = tmp + (1 - tmp) * (1 - Math.abs(position));
                view.setScaleX(scaleFactor);
                view.setScaleY(scaleFactor);

            } else if (position <= 1) { // [0,1]
                view.setAlpha(1);
                ViewCompat.setElevation(view, 2);

                // Counteract the default slide transition
                view.setTranslationX(0);
//                    view.setTranslationY(position * view.getHeight());
                view.setTranslationY(myOffset);

                // Scale the page down (between MIN_SCALE and 1)
                view.setScaleX(1);
                view.setScaleY(1);
            } else { // (1,+Infinity]
                // This page is way off-screen to the right.
                view.setAlpha(0);
            }
        });

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            //다시시도
            case R.id.btn_retry:
                contentsAdapter.setPageNo(0);
                cttViewModel.reqCTT1001(new CTT_1001.Request(APPIAInfo.CM01.getId(),"","",(contentsAdapter.getPageNo()+1)+"","20"));
                break;

            //이벤트 버튼
            case R.id.btn_event:
                if(isEvent){
                    //일반목록요청
                    isEvent=false;
                    me.btnEvent.setBackgroundResource(R.drawable.bg_00000000_underline_6f6f6f);
                }else{
                    //이벤트목록요청
                    isEvent=true;
                    me.btnEvent.setBackgroundResource(R.drawable.ripple_bg_000000);
                }

                contentsAdapter.setPageNo(0);
                cttViewModel.reqCTT1001(new CTT_1001.Request(APPIAInfo.CM01.getId(),isEvent ? "1000" :"0","",(contentsAdapter.getPageNo()+1)+"","20"));
                break;

            case R.id.iv_image:
                //todo 컨텐츠에 대한 임시조치
                String url = v.getTag(R.id.url).toString();
                ((MainActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), WebviewActivity.class).putExtra(KeyNames.KEY_NAME_URL, url),RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
        }
    }

    @Override
    public void onRefresh() {

        Log.e("onResume","onReusme contents");

        if(contentsAdapter.getPageNo()==0)
            cttViewModel.reqCTT1001(new CTT_1001.Request(APPIAInfo.CM01.getId(),"","",(contentsAdapter.getPageNo()+1)+"","20"));

        ((MainActivity)getActivity()).setGNB(true, 0, View.VISIBLE);
    }


}
