package com.genesis.apps.ui.main.contents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.CatTypeVO;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.databinding.FragmentContentsBinding;
import com.genesis.apps.databinding.ItemTabContentsBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class FragmentContents extends SubFragment<FragmentContentsBinding> {

    private CMNViewModel cmnViewModel;

    private List<CatTypeVO> mCatTypeList;

    public FragmentStateAdapter serviceTabAdapter;

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

        intViewModel();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == ResultCodes.REQ_CODE_CONTENTS_RELOAD.getCode()) {
            intViewModel();
        }
    }

    private void initView() {
        serviceTabAdapter = new ContentsViewpagerAdapter(this, mCatTypeList);

        me.vpContentsViewPager.setAdapter(serviceTabAdapter);
//        me.vpContentsViewPager.setUserInputEnabled(false);

        //ViewPager Setting
        me.vpContentsViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        me.vpContentsViewPager.setCurrentItem(0);
        me.vpContentsViewPager.setOffscreenPageLimit(mCatTypeList.size());

        me.vpContentsViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    me.vpContentsViewPager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                initContentsPageScrollPosition(position);
            }

        });
    }

    private void intViewModel() {
        cmnViewModel = new ViewModelProvider(getActivity()).get(CMNViewModel.class);
//        cttViewModel = new ViewModelProvider(this).get(CTTViewModel.class);
        me.setLifecycleOwner(getViewLifecycleOwner());
        me.setFragment(this);

        mCatTypeList = new ArrayList<>();
        mCatTypeList.add(new CatTypeVO("", "전체"));

        if(cmnViewModel != null && cmnViewModel.getCatTypeList() != null && cmnViewModel.getCatTypeList().size() > 0) {
            for(int i=0; i<cmnViewModel.getCatTypeList().size(); i++) {
                CatTypeVO catItem = cmnViewModel.getCatTypeList().get(i);

                mCatTypeList.add(catItem);
            }
        }

        initView();
        initTabView();

//        cttViewModel.getRES_CTT_1001().observe(getViewLifecycleOwner(), result -> {
//
//            switch (result.status){
//                case LOADING:
//                    ((MainActivity)getActivity()).showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    ((MainActivity)getActivity()).showProgressDialog(false);
//
//                    mCatTypeList = new ArrayList<>();
//                    mCatTypeList.add(new CatTypeVO("", "전체"));
//
//                    // 컨텐츠 내용 없는건 표시 포함 안함
//                    if(cmnViewModel != null && cmnViewModel.getCatTypeList() != null && cmnViewModel.getCatTypeList().size() > 0) {
//                        for(int i=0; i<cmnViewModel.getCatTypeList().size(); i++) {
//                            CatTypeVO catItem = cmnViewModel.getCatTypeList().get(i);
//
//                            if(result.data!=null&&result.data.getTtlList()!=null){
//                                for(int j=0; j<result.data.getTtlList().size(); j++) {
//                                    ContentsVO item = result.data.getTtlList().get(j);
//
//                                    if(catItem.getCd().equals(item.getCatCd())) {
//                                        mCatTypeList.add(catItem);
//
//                                        break;
//                                    }
//                                }
//                            }
//                        }
//                    }
//
//                    initView();
//                    initTabView();
//
////                    if(result.data!=null&&result.data.getTtlList()!=null){
////                        list.addAll(result.data.getTtlList());
////                    }
////
////                    int itemSizeBefore = 0;
////                    if (contentsAdapter.getPageNo() == 0) {
////                        contentsAdapter.setRows(list);
////                    } else {
////                        itemSizeBefore = contentsAdapter.getItemCount();
////                        contentsAdapter.addRows(list);
////                    }
////
////                    contentsAdapter.notifyDataSetChanged();
////                    if (contentsAdapter.getPageNo() == 0) {
////                        me.vp.setCurrentItem(0);
////                    }
////
////
////                    contentsAdapter.setPageNo(contentsAdapter.getPageNo() + 1);
////                    me.lEmpty.setVisibility(contentsAdapter.getItemCount()==0 ? View.VISIBLE : View.GONE);
//                    break;
//                default:
//                    String serverMsg="";
//                    try {
//                        serverMsg = result.data.getRtMsg();
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }finally{
//                        SnackBarUtil.show(getActivity(), TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
//                        ((MainActivity)getActivity()).showProgressDialog(false);
//                    }
//                    break;
//            }
//        });



//        cttViewModel.getRES_CTT_1004().observe(getViewLifecycleOwner(), result -> {
//
//            switch (result.status){
//                case LOADING:
//                    ((MainActivity)getActivity()).showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    ((MainActivity)getActivity()).showProgressDialog(false);
//                    if(result.data!=null
//                            &&result.data.getRtCd().equalsIgnoreCase("0000")
//                            &&!TextUtils.isEmpty(result.data.getDtlViewCd())
//                            &&result.data.getDtlList()!=null
//                            &&result.data.getDtlList().size()>0){
//
////                        String linkUrl = result.data.getDtlViewCd().equalsIgnoreCase("3000") ? result.data.getDtlList().get(0).getHtmlFilUri() : result.data.getDtlList().get(0).getImgFilUri() ;
//                        CTT_1004.Response contentsVO = result.data;
//
//                        ((MainActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), ContentsDetailWebActivity.class).putExtra(KeyNames.KEY_NAME_CONTENTS_VO, contentsVO),RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//
//                        break;
//                    }
//                default:
//                    String serverMsg="";
//                    try {
//                        serverMsg = result.data.getRtMsg();
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }finally{
//                        SnackBarUtil.show(getActivity(), TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
//                        ((MainActivity)getActivity()).showProgressDialog(false);
//                    }
//                    break;
//            }
//        });

//        cttViewModel.reqCTT1001(new CTT_1001.Request(APPIAInfo.CM01.getId(),"",""));
    }

    private void initTabView() {
        new TabLayoutMediator(me.tlCategoryTabs, me.vpContentsViewPager, (tab, position) -> {

        }).attach();

        for(int i=0 ; i<mCatTypeList.size(); i++) {
            final LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ItemTabContentsBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_tab_contents, null, false);
            final View view = binding.getRoot();
            binding.tvTab.setText(mCatTypeList.get(i).getCdNm());
            me.tlCategoryTabs.getTabAt(i).setCustomView(view);

            if(i == 0) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMarginStart((int) DeviceUtil.dip2Pixel(getActivity(), 20));
                me.tlCategoryTabs.getTabAt(i).view.setLayoutParams(params);
            }

            if(i == mCatTypeList.size()-1) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMarginEnd((int) DeviceUtil.dip2Pixel(getActivity(), 20));
                me.tlCategoryTabs.getTabAt(i).view.setLayoutParams(params);
            }
        }


        me.tlCategoryTabs.setOnScrollChangeListener((view, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            if(scrollX>0){
                me.vGradientStart.setVisibility(View.VISIBLE);
            }else{
                me.vGradientStart.setVisibility(View.GONE);
            }

            final int maxScrollX = me.tlCategoryTabs.getChildAt(0).getRight() - me.tlCategoryTabs.getWidth() + me.tlCategoryTabs.getPaddingLeft();
            if(maxScrollX<=scrollX){
                me.vGradientEnd.setVisibility(View.GONE);
            }else{
                me.vGradientEnd.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {
    }

    @Override
    public void onRefresh() {
        Log.e("onResume","onReusme contents");
        SubActivity.setStatusBarColor(getActivity(), R.color.x_ffffff);

        ((MainActivity)getActivity()).setGNB(getString(R.string.main_word_4), View.VISIBLE, false, true);
    }

    private void initContentsPageScrollPosition(int position){
        if(mCatTypeList!=null&&mCatTypeList.size()>position) {
            for (Fragment fragmentChild : getChildFragmentManager().getFragments()) {
                if (fragmentChild instanceof FragmentContentsList) {
                    String mCatCd = "";
                    try {
                        mCatCd = ((FragmentContentsList) fragmentChild).getmCatCd();
                    } catch (Exception e) {
                        mCatCd = "";
                    }
                    if (mCatCd.equalsIgnoreCase(StringUtil.isValidString(mCatTypeList.get(position).getCd()))) {
                        ((FragmentContentsList) fragmentChild).initAdapterPosition();
                        break;
                    }
                }
            }
        }
    }

    public void initPage(){
        //컨텐츠 이동 시 첫번째 탭이 선택되어있지 않은 경우
        if(serviceTabAdapter!=null&&me.vpContentsViewPager.getCurrentItem()>0){
            //첫번째 탭 선택 및 컨텐츠 리스트도 첫번째 아이템으로 초기화
            me.vpContentsViewPager.setCurrentItem(0);
        }else if(serviceTabAdapter!=null&&me.vpContentsViewPager.getCurrentItem()==0){
            //첫번째 탭일경우 리스트도 초기화 진행
            initContentsPageScrollPosition(0);
        }
    }
}
