package com.genesis.apps.ui.main;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.ImageVO;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivityTutorialBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.view.TutorialHorizontalAdapter;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

public class TutorialActivity extends SubActivity<ActivityTutorialBinding> {

    private LGNViewModel lgnViewModel;
    private TutorialHorizontalAdapter adapter;
    private final List<ImageVO> imageList = new ArrayList<>();
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial);
        setViewModel();
        getDataFromIntent();
        setObserver();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void getDataFromIntent() {
        Intent intent = getIntent();
        try {
            type = intent.getIntExtra(KeyNames.KEY_NAME_TUTORIAL_TYPE, VariableType.TUTORIAL_TYPE_HOME);
        }catch (Exception e){
            type = VariableType.TUTORIAL_TYPE_HOME;
        }

        switch (type){
            case VariableType.TUTORIAL_TYPE_DIGITAL_WALLET:
                imageList.add(new ImageVO("", R.drawable.tutorial_img_03, 0,0, "", "",""));
                imageList.add(new ImageVO("", R.drawable.tutorial_img_04, 0, 0,"", "",""));
                imageList.add(new ImageVO("", R.drawable.tutorial_img_05, R.drawable.tutorial_img_06,0,"https://www.youtube.com/watch?v=wT9oBD7tFgg", "https://www.youtube.com/watch?v=I771Tpp0FuA",""));
                break;
            case VariableType.TUTORIAL_TYPE_CHARGE:
                imageList.add(new ImageVO("", R.drawable.tutorial_img_07, 0,0,"", "",""));
                imageList.add(new ImageVO("", R.drawable.tutorial_img_08, 0,0, "", "",""));
                imageList.add(new ImageVO("", R.drawable.tutorial_img_09, 0,0, "", "",""));
                break;
            case VariableType.TUTORIAL_TYPE_HOME:
            default:
                imageList.add(new ImageVO("", R.drawable.tutorial_img_10, R.drawable.tutorial_img_05, R.drawable.tutorial_img_06,"", "https://www.youtube.com/watch?v=wT9oBD7tFgg", "https://www.youtube.com/watch?v=I771Tpp0FuA"));
                imageList.add(new ImageVO("", R.drawable.tutorial_img_01, 0,0, "", "",""));
                imageList.add(new ImageVO("", R.drawable.tutorial_img_02, 0,0, "", "",""));
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        ui.setActivity(this);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
    }

    @Override
    public void setObserver() {
    }
    private int beforePostion = -1;
    private void initView() {
        adapter = new TutorialHorizontalAdapter(onSingleClickListener);
        ui.vpTutorial.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        ui.vpTutorial.setAdapter(adapter);
        ui.indicator.setViewPager(ui.vpTutorial);
        adapter.setRows(imageList);
        ui.indicator.createIndicators(imageList.size(), 0);
        ui.vpTutorial.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0 && beforePostion != 0 && beforePostion != -1) {
                    if(adapter.getItemCount()-1==position){
                        ui.btnOk.setText(R.string.dialog_common_4);
                    }else{
                        ui.btnOk.setText(R.string.dialog_common_5);
                    }
                }
                beforePostion = positionOffsetPixels;
            }
        });
//        final float pageMargin = getResources().getDimensionPixelOffset(R.dimen.offset2);
//        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset2);
//        ui.vpCar.setPageTransformer((page, position) -> {
//            float myOffset = position * -(2 * pageOffset + pageMargin);
//            if (position < -1) {
//                page.setTranslationX(-myOffset);
//
//                Log.v("viewpager bug1","offset:"+myOffset);
//            } else if (position <= 1) {
//                float scaleFactor = Math.max(1f, 1 - Math.abs(position - 0.14285715f));
//                page.setTranslationX(myOffset);
//                page.setScaleY(scaleFactor);
//                page.setScaleX(scaleFactor);
//                page.setAlpha(scaleFactor);
//
//                Log.v("viewpager bug2","offset:"+myOffset+ " Scale factor:"+scaleFactor);
//            } else {
//                page.setAlpha(0f);
//                page.setTranslationX(myOffset);
//                Log.v("viewpager bug3","offset:"+myOffset);
//            }
//
//        });
    }



    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.btn_ok:
                if((adapter.getItemCount()-1)==ui.vpTutorial.getCurrentItem()){
                    //확인
                    exitTutorial();
                }else{
                    //다음
                    ui.vpTutorial.setCurrentItem(ui.vpTutorial.getCurrentItem()+1, true);
                }
                break;
            case R.id.iv_tutorial_1:
            case R.id.iv_tutorial_2:
            case R.id.iv_tutorial_3:
                String url = (String)v.getTag(R.id.url);
                try {
                    if (!TextUtils.isEmpty(url)) {
                        startActivity(new Intent(Intent.ACTION_VIEW)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .setData(Uri.parse(url)) // edit this url
                                .setPackage("com.google.android.youtube"));
                    }
                }catch (ActivityNotFoundException e){
                    if (!TextUtils.isEmpty(url)) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    }
                }
                break;
        }

    }

    private void exitTutorial(){
        lgnViewModel.updateGlobalDataToDB(KeyNames.KEY_NAME_TUTORIAL_TYPE+type, VariableType.COMMON_MEANS_YES);
        if(type==VariableType.TUTORIAL_TYPE_DIGITAL_WALLET) setResult(ResultCodes.REQ_CODE_TUTORIAL_DIGITAL_WALLET.getCode());
        finish();
        closeTransition();
    }

    @Override
    public void onBackPressed() {
        exitTutorial();
    }

    @Override
    public void onBackButton(){
        exitTutorial();
    }
}