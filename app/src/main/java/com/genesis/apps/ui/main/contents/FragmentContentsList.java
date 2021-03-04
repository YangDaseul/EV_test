package com.genesis.apps.ui.main.contents;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CTT_1001;
import com.genesis.apps.comm.model.api.gra.CTT_1004;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.ContentsVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.CTTViewModel;
import com.genesis.apps.databinding.FragmentContentsListBinding;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class FragmentContentsList extends SubFragment<FragmentContentsListBinding> {

    private CTTViewModel cttViewModel;
    private ContentsAdapter contentsAdapter;

    private String mCatCd = "";

    public static Fragment newInstance(String catCd) {
        FragmentContentsList fragment = new FragmentContentsList();

        Bundle args = new Bundle();
        args.putString(KeyNames.KEY_NAME_CAT_NO, catCd);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getArguments() != null) {
            mCatCd = getArguments().getString(KeyNames.KEY_NAME_CAT_NO);
            Log.e("JJJJ", "cat Cd : " + mCatCd);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_contents_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        intViewModel();
        initViewPager();
    }

    private void intViewModel() {
        cttViewModel = new ViewModelProvider(this).get(CTTViewModel.class);
        me.setLifecycleOwner(getViewLifecycleOwner());
        me.setFragment(this);

        cttViewModel.getRES_CTT_1001().observe(getViewLifecycleOwner(), result -> {
            switch (result.status){
                case LOADING:
                    ((MainActivity)getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((MainActivity)getActivity()).showProgressDialog(false);

                    List<ContentsVO> list = new ArrayList<>();

                    if(result.data!=null&&result.data.getTtlList()!=null){
                        list.addAll(result.data.getTtlList());
                    }

                    int itemSizeBefore = 0;
                    if (contentsAdapter.getPageNo() == 0) {
                        contentsAdapter.setRows(list);
                    } else {
                        itemSizeBefore = contentsAdapter.getItemCount();
                        contentsAdapter.addRows(list);
                    }

                    contentsAdapter.notifyDataSetChanged();
                    if (contentsAdapter.getPageNo() == 0) {
                        me.vp.setCurrentItem(0);
                    }


                    contentsAdapter.setPageNo(contentsAdapter.getPageNo() + 1);
                    me.lEmpty.setVisibility(contentsAdapter.getItemCount()==0 ? View.VISIBLE : View.GONE);
                    me.tvEmpty.setText("9001".equals(result.data.getRtCd()) ? getString(R.string.cm02_14) : getString(R.string.cm02_2));
                    me.tvEmpty1.setVisibility("9001".equals(result.data.getRtCd()) ? View.GONE : View.VISIBLE);
                    me.btnRetry.setVisibility("9001".equals(result.data.getRtCd()) ? View.GONE : View.VISIBLE);
                    break;
                default:
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        SnackBarUtil.show(getActivity(), TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                        ((MainActivity)getActivity()).showProgressDialog(false);
                        me.lEmpty.setVisibility(contentsAdapter.getItemCount()==0 ? View.VISIBLE : View.GONE);
                        me.tvEmpty.setText(getString(R.string.cm02_2));
                        me.tvEmpty1.setVisibility(View.VISIBLE);
                        me.btnRetry.setVisibility(View.VISIBLE);
                    }
                    break;
            }
        });



        cttViewModel.getRES_CTT_1004().observe(getViewLifecycleOwner(), result -> {

            switch (result.status){
                case LOADING:
                    ((MainActivity)getActivity()).showProgressDialog(true);
                    break;
                case SUCCESS:
                    ((MainActivity)getActivity()).showProgressDialog(false);
                    if(result.data!=null
                            &&result.data.getRtCd().equalsIgnoreCase("0000")
                            &&!TextUtils.isEmpty(result.data.getDtlViewCd())
                            &&result.data.getDtlList()!=null
                            &&result.data.getDtlList().size()>0){

//                        String linkUrl = result.data.getDtlViewCd().equalsIgnoreCase("3000") ? result.data.getDtlList().get(0).getHtmlFilUri() : result.data.getDtlList().get(0).getImgFilUri() ;
                        CTT_1004.Response contentsVO = result.data;

                        ((MainActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), ContentsDetailWebActivity.class).putExtra(KeyNames.KEY_NAME_CONTENTS_VO, contentsVO),RequestCodes.REQ_CODE_CONTENTS_RELOAD.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);

                        break;
                    }
                default:
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        SnackBarUtil.show(getActivity(), TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                        ((MainActivity)getActivity()).showProgressDialog(false);
                    }
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
//        me.vp.setPageTransformer((view, position) -> {
//
//            // Next line scales the item's height. You can remove it if you don't want this effect
//            view.setScaleY(1 - (0.08f * abs(position))) ;
//        });

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
                cttViewModel.reqCTT1001(new CTT_1001.Request(APPIAInfo.CM01.getId(), mCatCd,""));
                break;

            case R.id.iv_image:
                ContentsVO item = ((ContentsVO)v.getTag(R.id.item));
                if(item!=null){
                    cttViewModel.reqCTT1004(new CTT_1004.Request(APPIAInfo.CM01.getId(), item.getListSeqNo()));
                }

                break;
        }
    }

    @Override
    public void onRefresh() {
        Log.e("onResume","onReusme contents");
        if(contentsAdapter.getPageNo()==0)
            cttViewModel.reqCTT1001(new CTT_1001.Request(APPIAInfo.CM01.getId(), mCatCd,""));
    }


}
