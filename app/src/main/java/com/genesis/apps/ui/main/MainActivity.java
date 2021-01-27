package com.genesis.apps.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.genesis.apps.R;
import com.genesis.apps.comm.hybrid.MyWebViewFrament;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.BAR_1001;
import com.genesis.apps.comm.model.api.gra.NOT_0003;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.StoreInfo;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.databinding.ActivityMainBinding;
import com.genesis.apps.databinding.ItemTabBinding;
import com.genesis.apps.databinding.ItemTabDayBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;
import com.genesis.apps.ui.main.home.FragmentHome1;
import com.genesis.apps.ui.main.insight.FragmentInsight;
import com.genesis.apps.ui.main.service.FragmentService;
import com.genesis.apps.ui.main.store.FragmentStore;
import com.genesis.apps.ui.myg.MyGEntranceActivity;
import com.genesis.apps.ui.myg.MyGHomeActivity;
import com.genesis.apps.ui.myg.MyGMenuActivity;
import com.google.android.material.tabs.TabLayoutMediator;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

public class MainActivity extends GpsBaseActivity<ActivityMainBinding> {
    private final int pageNum = 5;
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
        initBarcode();
//        startActivitySingleTop(new Intent(this, APPIAInfo.GM_CARLST01.getActivity()), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
    }

    private void initBarcode() {
        String custGbCd="";
        try {
            custGbCd = lgnViewModel.getUserInfoFromDB().getCustGbCd();
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            if(!TextUtils.isEmpty(custGbCd)&&!custGbCd.equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_0000)){
                cmnViewModel.reqBAR1001(new BAR_1001.Request(APPIAInfo.GM01.getId()));
            }
        }
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
            case R.id.btn_login:
                startActivitySingleTop(new Intent(this, MyGEntranceActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case R.id.btn_profile:
                startActivitySingleTop(new Intent(this, MyGHomeActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//                try {
//                    switch (lgnViewModel.getUserInfoFromDB().getCustGbCd()){
//                        case VariableType.MAIN_VEHICLE_TYPE_0000:
//                            startActivitySingleTop(new Intent(this, MyGEntranceActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//                            break;
//                        case VariableType.MAIN_VEHICLE_TYPE_OV:
//                        default:
//                            startActivitySingleTop(new Intent(this, MyGHomeActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//                            break;
//                    }
//                }catch (Exception ignore){
//                    ignore.printStackTrace();
//                }
                break;
            case R.id.btn_search:
                startActivitySingleTop(new Intent(this, MyGMenuActivity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case R.id.btn_cart_list:
                try {
                    loginChk(StoreInfo.STORE_PURCHASE_URL, lgnViewModel.getUserInfoFromDB().getCustGbCd());
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            case R.id.btn_store_cart:
                try {
                    loginChk(StoreInfo.STORE_CART_URL, lgnViewModel.getUserInfoFromDB().getCustGbCd());
                }catch (Exception ignore){

                }
                break;
            case R.id.btn_store_search:
                try {
                    loginChk(StoreInfo.STORE_SEARCH_URL, lgnViewModel.getUserInfoFromDB().getCustGbCd());
                }catch (Exception e){

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

        cmnViewModel.getRES_BAR_1001().observe(this, result -> {
            switch (result.status){
                case LOADING:
                    break;
                case SUCCESS:
                    if(result.data!=null&&result.data.getCardList()!=null&&result.data.getCardList().size()>0){
                        ui.lGnb.setUseBarcode(true);
//                        ui.lGnb.btnBarcode.setVisibility(View.VISIBLE);
                        break;
                    }
                default:
                    ui.lGnb.setUseBarcode(false);
//                    ui.lGnb.btnBarcode.setVisibility(View.GONE);
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
        if ( (requestCode == RequestCodes.REQ_CODE_PERMISSIONS_MEDIAPROJECTION.getCode())
                ||requestCode == RequestCodes.REQ_CODE_PLAY_VIDEO.getCode()
                ||(requestCode == RequestCodes.REQ_CODE_GPS.getCode() && resultCode == RESULT_OK)
        ) {
            for (Fragment fragmentParent : getSupportFragmentManager().getFragments()) {
                if (fragmentParent instanceof FragmentHome) {
                    for(Fragment fragmentChild : fragmentParent.getChildFragmentManager().getFragments()){
                        if (fragmentChild instanceof FragmentHome1) {
                            fragmentChild.onActivityResult(requestCode, resultCode, data);
                            return;
                        }
                    }
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
        }else if(resultCode==ResultCodes.REQ_CODE_INSIGHT_EXPN_ADD.getCode()){
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment instanceof FragmentInsight) {
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
            {R.string.main_word_1, R.drawable.ic_tabbar_home_bs},
            {R.string.main_word_2, R.drawable.ic_tabbar_insight_w},
            {R.string.main_word_3, R.drawable.ic_tabbar_service_w},
            {R.string.main_word_5, R.drawable.ic_tabbar_store_w},
            {R.string.main_word_4, R.drawable.ic_tabbar_contents_w}
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

    /**
     *
     * @param dayCd 낮 : 1 , 밤 : 2
     */
    public void setTab(int dayCd) {
        ui.tabs.removeAllTabs();
        ui.tabs.setBackgroundResource(dayCd == 1 ? R.drawable.bg_ffffff_topline_f8f8f8 : R.drawable.bg_000000_topline_262626);
        new TabLayoutMediator(ui.tabs, ui.viewpager, (tab, position) -> {

        }).attach();

        for(int i=0 ; i<pageNum; i++) {
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view = null;
            if(dayCd == 1) {
                final ItemTabDayBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_tab_day, null, false);

                view = binding.getRoot();
                binding.tvTab.setText(TAB_INFO[i][0]);
                binding.ivTab.setImageResource(TAB_INFO[i][1]);
            } else {
                final ItemTabBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_tab, null, false);

                view = binding.getRoot();
                binding.tvTab.setText(TAB_INFO[i][0]);
                binding.ivTab.setImageResource(TAB_INFO[i][1]);
            }

            ui.tabs.getTabAt(i).setCustomView(view);
        }
    }

    public void setGNB(String menu, int isVisibility) {
        setGNB(menu, isVisibility, false, false);
    }

    public void setGNB(String menu, int isVisibility, boolean isStore, boolean isBgWhite) {
        try {
            ui.lGnb.setMenu(menu);
            ui.lGnb.setIsAlarm(isNewAlarm());
            ui.lGnb.setCustGbCd(lgnViewModel.getUserInfoFromDB().getCustGbCd());
            ui.lGnb.lWhole.setVisibility(isVisibility);
            ui.lGnb.setIsStore(isStore);
            ui.lGnb.setIsBgWhite(isBgWhite);
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
        }
        if (newNotiCnt_i > 0) {
            return true;
        } else {
            return false;
        }

    }


    private long backKeyPressedTime = 0;
    @Override
    public void onBackPressed() {
        // 현재 화면이 Store 화면일때 WebView 뒤로가기 처리
        if(getViewPager().getCurrentItem() == 3) {
            for(Fragment fragment : getSupportFragmentManager().getFragments()) {
                if(fragment instanceof FragmentStore) {
                    FragmentStore fragmentStore = (FragmentStore) fragment;
                    MyWebViewFrament webFragment = (MyWebViewFrament) fragment.getChildFragmentManager().findFragmentById(R.id.fm_holder);

                    if(fragmentStore.isDlp.equals("YES")) {
                        webFragment.loadUrl("javascript:bwcAppClose();");
                    } else {
                        Log.d("JJJJ", "canGoBack : " + webFragment.canGoBack());
                        if(!TextUtils.isEmpty(fragmentStore.fn)){
                            if(webFragment.openWindows.size()>0){
                                webFragment.openWindows.get(0).loadUrl("javascript:"+fragmentStore.fn);
                            }else{
                                webFragment.loadUrl("javascript:"+fragmentStore.fn);
                            }
                        } else {
                            if(webFragment.canGoBack()) {
                                webFragment.goBack();
                                return;
                            }
                        }
                    }
                }
            }
        }

        if (System.currentTimeMillis() > backKeyPressedTime + (2 * 1000)) {
            backKeyPressedTime = System.currentTimeMillis();
            SnackBarUtil.show(this, getString(R.string.comm_msg_1));
        } else {
            super.onBackPressed();
        }
    }
}
