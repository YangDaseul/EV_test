package com.genesis.apps.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivityMainBinding;
import com.genesis.apps.databinding.ItemTabBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.main.home.FragmentHome1;
import com.genesis.apps.ui.myg.MyGHomeActivity;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends GpsBaseActivity<ActivityMainBinding> {
    private final int pageNum = 4;
    public FragmentStateAdapter pagerAdapter;
    private LGNViewModel lgnViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);
        ui.setActivity(this);
        getDataFromIntent();
        setViewModel();
        setObserver();

        initView();
    }
    private void initView() {
        pagerAdapter = new MainViewpagerAdapter(this, pageNum);
        ui.viewpager.setAdapter(pagerAdapter);
        ui.viewpager.setUserInputEnabled(false);
        setTabView();

        //ViewPager Setting
        ui.viewpager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        ui.viewpager.setCurrentItem(0);
        ui.viewpager.setOffscreenPageLimit(4);

        ui.viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                if (positionOffsetPixels == 0) {
                    ui.viewpager.setCurrentItem(position);
                }
            }

            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

//                ui.indicator.animatePageSelected(position%num_page);
            }

        });

        final float pageMargin= getResources().getDimensionPixelOffset(R.dimen.pageMargin);
        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);
        ui.viewpager.setPageTransformer((page, position) -> {
            float myOffset = position * -(2 * pageOffset + pageMargin);
            if (ui.viewpager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(ui.viewpager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.setTranslationX(-myOffset);
                } else {
                    page.setTranslationX(myOffset);
                }
            } else {
                page.setTranslationY(myOffset);
            }
        });

    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()){
            case R.id.btn_barcode:
                startActivitySingleTop(new Intent(this, BarcodeActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_NONE);
                break;
            case R.id.btn_alarm:
                startActivitySingleTop(new Intent(this, AlarmCenterActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case R.id.btn_profile:
                try {
                    switch (lgnViewModel.getUserInfoFromDB().getCustGbCd()){
                        case VariableType.MAIN_VEHICLE_TYPE_0000:

                            break;
                        case VariableType.MAIN_VEHICLE_TYPE_OV:
                        default:
                            startActivitySingleTop(new Intent(this, MyGHomeActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                            break;
                    }
                }catch (Exception ignore){
                    ignore.printStackTrace();
                }
                break;
        }

    }

    @Override
    public void setViewModel() {
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
    }

    @Override
    public void setObserver() {

    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void onResume(){
        super.onResume();
        Log.e("onResume","onReusme Mainactivity");
        checkPushCode();
//        setGNBColor(1);
//        for (Fragment fragment: getSupportFragmentManager().getFragments()) {
//            if (fragment.isVisible()) {
//                if(fragment instanceof FragmentInsight){
//                    setGNBColor(0);
//                    break;
//                }
//            }
//        }



        //TODO : 테스트 액티비티 호출
//        startActivitySingleTop(new Intent(this, ServiceDriveReqActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//        FirebaseMessagingService.notifyMessageTest(this, new PushVO(), PushCode.CAT_0E);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ( (requestCode == RequestCodes.REQ_CODE_PERMISSIONS_MEDIAPROJECTION.getCode() && resultCode == RESULT_OK)
                ||(requestCode == RequestCodes.REQ_CODE_PLAY_VIDEO.getCode())
        ) {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment instanceof FragmentHome1) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                    return;
                }
            }
        }else if(requestCode == RequestCodes.REQ_CODE_GPS.getCode() && resultCode == RESULT_OK){
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment instanceof FragmentHome1) {
                    fragment.onActivityResult(requestCode, resultCode, data);
                    return;
                }
            }
        }

    }

    public ViewPager2 getViewPager(){
        return ui.viewpager;
    }

    private final int TAB_INFO[][]={
            {R.string.main_word_1, R.drawable.ic_tabbar_home},
            {R.string.main_word_2, R.drawable.ic_tabbar_insight},
            {R.string.main_word_3, R.drawable.ic_tabbar_service},
            {R.string.main_word_4, R.drawable.ic_tabbar_contents}
    };

    private void setTabView(){
        new TabLayoutMediator(ui.tabs, ui.viewpager, (tab, position) -> {

        }).attach();

        for(int i=0 ; i<pageNum; i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ItemTabBinding itemTabBinding = DataBindingUtil.inflate(inflater, R.layout.item_tab, null, false);
            final View view = itemTabBinding.getRoot();
            itemTabBinding.tvTab.setText(TAB_INFO[i][0]);
            itemTabBinding.ivTab.setImageResource(TAB_INFO[i][1]);
            ui.tabs.getTabAt(i).setCustomView(view);
        }
    }


    public void setGNB(boolean isAlarm, boolean isSearch, int value, int isVisibility) {
        try {
            ui.lGnb.setIsAlarm(isAlarm);
            ui.lGnb.setIsSearch(isSearch);
            ui.lGnb.setCustGbCd(lgnViewModel.getUserInfoFromDB().getCustGbCd());
            ui.lGnb.setBackground(value);
            ui.lGnb.lWhole.setVisibility(isVisibility);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private long backKeyPressedTime = 0;
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + (2 * 1000)) {
            backKeyPressedTime = System.currentTimeMillis();
            SnackBarUtil.show(this, getString(R.string.comm_msg_1));
        } else {
            super.onBackPressed();
        }
    }
}
