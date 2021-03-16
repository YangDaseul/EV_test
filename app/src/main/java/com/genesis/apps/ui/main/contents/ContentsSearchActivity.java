package com.genesis.apps.ui.main.contents;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import androidx.core.view.ViewCompat;
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
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.viewmodel.CTTViewModel;
import com.genesis.apps.databinding.ActivityContentsSearchBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import java.util.ArrayList;
import java.util.List;

public class ContentsSearchActivity extends SubActivity<ActivityContentsSearchBinding> {
    private final String TAG = getClass().getSimpleName();
    private ContentsSearchActivity mActivity = this;
    private CTTViewModel cttViewModel;
    private ContentsAdapter contentsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contents_search);

        ui.etSearch.setOnEditorActionListener(editorActionListener);

        intViewModel();
        initViewPager();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        SoftKeyboardUtil.hideKeyboard(mActivity, mActivity.getWindow().getDecorView().getWindowToken());
    }

    @Override
    public void onClickCommon(View v) {
        switch (v.getId()){
            case R.id.iv_image:
                ContentsVO item = ((ContentsVO)v.getTag(R.id.item));
                if(item!=null){
                    cttViewModel.reqCTT1004(new CTT_1004.Request(APPIAInfo.CM01.getId(), item.getListSeqNo()));
                }

                break;
        }
    }

    @Override
    public void setViewModel() {

    }

    @Override
    public void setObserver() {

    }

    @Override
    public void getDataFromIntent() {

    }

    private void intViewModel() {
        cttViewModel = new ViewModelProvider(mActivity).get(CTTViewModel.class);
        ui.setLifecycleOwner(mActivity);

        cttViewModel.getRES_CTT_1001().observe(mActivity, result -> {

            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);

                    List<ContentsVO> list = new ArrayList<>();

                    if(result.data!=null&&result.data.getTtlList()!=null){
                        list.addAll(result.data.getTtlList());
                    }

                    int itemSizeBefore = contentsAdapter.getItemCount();
//                    if (contentsAdapter.getPageNo() == 0) {
                        contentsAdapter = new ContentsAdapter(onSingleClickListener);
                        ui.vp.setAdapter(contentsAdapter);
                        contentsAdapter.setRows(list);
                        contentsAdapter.notifyDataSetChanged();
//                    } else {
//                        contentsAdapter.addRows(list);
//                    }
//                    contentsAdapter.setPageNo(contentsAdapter.getPageNo() + 1);
                    contentsAdapter.notifyItemRangeInserted(itemSizeBefore, contentsAdapter.getItemCount());
                    setViewEmpty();
                    break;
                default:
                    String serverMsg="";
                    try {
                        setViewEmpty();
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        SnackBarUtil.show(mActivity, TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                        showProgressDialog(false);
                    }
                    break;
            }
        });

        cttViewModel.getRES_CTT_1004().observe(mActivity, result -> {

            switch (result.status){
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    showProgressDialog(false);
                    if(result.data!=null
                            &&result.data.getRtCd().equalsIgnoreCase("0000")
                            &&!TextUtils.isEmpty(result.data.getDtlViewCd())
                            &&result.data.getDtlList()!=null
                            &&result.data.getDtlList().size()>0){

                        CTT_1004.Response contentsVO = result.data;
                        startActivitySingleTop(new Intent(mActivity, ContentsDetailWebActivity.class).putExtra(KeyNames.KEY_NAME_CONTENTS_VO, contentsVO), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);

                        break;
                    }
                default:
                    String serverMsg="";
                    try {
                        serverMsg = result.data.getRtMsg();
                    }catch (Exception e){
                        e.printStackTrace();
                    }finally{
                        SnackBarUtil.show(mActivity, TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                        showProgressDialog(false);
                    }
                    break;
            }
        });
    }

    private void setViewEmpty() {
        ui.tvEmpty.setVisibility(contentsAdapter.getItemCount()<1 ? View.VISIBLE : View.GONE);
    }

    private void initViewPager(){
        //ViewPager Setting
        contentsAdapter = new ContentsAdapter(onSingleClickListener);
        ui.vp.setAdapter(contentsAdapter);
        ui.vp.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        ui.vp.setCurrentItem(0);
        ui.vp.setOffscreenPageLimit(3);

        ui.vp.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    ui.vp.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }

        });

        final float pageMargin= getResources().getDimensionPixelOffset(R.dimen.vpMargin);

        ui.vp.setPageTransformer((view, position) -> {
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

    EditText.OnEditorActionListener editorActionListener = (textView, actionId, keyEvent) -> {
        if(actionId== EditorInfo.IME_ACTION_SEARCH){
            searchContents();
        }
        return false;
    };

    private void searchContents(){
        //end
        String keyword = ui.etSearch.getText().toString().trim();

//        contentsAdapter.setPageNo(0);
        cttViewModel.reqCTT1001(new CTT_1001.Request(APPIAInfo.CM01.getId(),"",keyword));

        SoftKeyboardUtil.hideKeyboard(mActivity, mActivity.getWindow().getDecorView().getWindowToken());
    }
}