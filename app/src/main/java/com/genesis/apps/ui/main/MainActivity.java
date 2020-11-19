package com.genesis.apps.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.NOT_0003;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivityMainBinding;
import com.genesis.apps.databinding.ItemTabBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.main.home.FragmentHome1;
import com.genesis.apps.ui.main.service.FragmentService;
import com.genesis.apps.ui.main.service.MapSearchMyPositionActivity;
import com.genesis.apps.ui.myg.MyGEntranceActivity;
import com.genesis.apps.ui.myg.MyGHomeActivity;
import com.google.android.material.tabs.TabLayoutMediator;

public class MainActivity extends GpsBaseActivity<ActivityMainBinding> {
    private final int pageNum = 4;
    public FragmentStateAdapter pagerAdapter;
    private LGNViewModel lgnViewModel;
    private CMNViewModel cmnViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
//                startActivitySingleTop(new Intent(this, MyGEntranceActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//                startActivitySingleTop(new Intent(this, ServiceJoinActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//                return;

                try {
                    switch (lgnViewModel.getUserInfoFromDB().getCustGbCd()){
                        case VariableType.MAIN_VEHICLE_TYPE_0000:
                            startActivitySingleTop(new Intent(this, MyGEntranceActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
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
        cmnViewModel = new ViewModelProvider(this).get(CMNViewModel.class);
    }

    @Override
    public void setObserver() {

        cmnViewModel.getRES_NOT_0003().observe(this, result -> {



            switch (result.status){
                case SUCCESS:
                    if(result.data!=null){
                        setGnB();
                    }
                    break;
                default:
                    //실패시는 무시
                    break;
            }

        });

    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void onResume(){
        super.onResume();
        Log.e("onResume","onReusme Mainactivity");
        checkPushCode();
        reqNewNotiCnt();
//        setGNBColor(1);
//        for (Fragment fragment: getSupportFragmentManager().getFragments()) {
//            if (fragment.isVisible()) {
//                if(fragment instanceof FragmentInsight){
//                    setGNBColor(0);
//                    break;
//                }
//            }
//        }


        //TODO 이것은 테스트 액티비티 호출
//        startActivitySingleTop(new Intent(this, MapSearchMyPositionActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//        FirebaseMessagingService.notifyMessageTest(this, new PushVO(), PushCode.CAT_0E);
    }

    private void reqNewNotiCnt() {
        String custGbCd="";
        try{
            custGbCd = lgnViewModel.getUserInfoFromDB().getCustGbCd();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            //고객구분코드가 0000(비회원)이 아니면 신규 알림 갯수 요청
            if(!TextUtils.isEmpty(custGbCd)&&!custGbCd.equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_0000)){
                cmnViewModel.reqNOT0003(new NOT_0003.Request(APPIAInfo.GM03.getId()));
            }
        }
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
        }else if(resultCode==ResultCodes.REQ_CODE_SERVICE_RESERVE_AUTOCARE.getCode()
                ||resultCode==ResultCodes.REQ_CODE_SERVICE_RESERVE_HOMETOHOME.getCode()
                ||resultCode==ResultCodes.REQ_CODE_SERVICE_RESERVE_REPAIR.getCode()
                ||resultCode==ResultCodes.REQ_CODE_SERVICE_RESERVE_REMOTE.getCode()
                ||resultCode==ResultCodes.REQ_CODE_SERVICE_NETWORK_RESERVE.getCode()){
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment instanceof FragmentService) {
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


    public void setGNB(boolean isSearch, int value, int isVisibility) {
        try {
            ui.lGnb.setIsAlarm(isNewAlarm());
            ui.lGnb.setIsSearch(isSearch);
            ui.lGnb.setCustGbCd(lgnViewModel.getUserInfoFromDB().getCustGbCd());
            ui.lGnb.setBackground(value);
            ui.lGnb.lWhole.setVisibility(isVisibility);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void setGnB(){
        try {
            ui.lGnb.setIsAlarm(isNewAlarm());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private boolean isNewAlarm(){
        String newNotiCnt = "";
        int newNotiCnt_i=0;
        try{
            newNotiCnt = cmnViewModel.getRES_NOT_0003().getValue().data.getNewNotiCnt();
            newNotiCnt_i = Integer.parseInt(newNotiCnt);
        }catch (Exception e){
            newNotiCnt_i = 0;
        }finally{
            if(newNotiCnt_i>0){
                return true;
            }else{
                return false;
            }
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
