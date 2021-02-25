package com.genesis.apps.ui.main.contents;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.CTT_1001;
import com.genesis.apps.comm.model.api.gra.CTT_1004;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.CatTypeVO;
import com.genesis.apps.comm.model.vo.ContentsVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.comm.viewmodel.CTTViewModel;
import com.genesis.apps.databinding.FragmentContentsBinding;
import com.genesis.apps.databinding.ItemTabContentsBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.List;

public class FragmentContents extends SubFragment<FragmentContentsBinding> {

    private CMNViewModel cmnViewModel;
    private CTTViewModel cttViewModel;
    private boolean isEvent=false;

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
        }
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


}
