package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.comm.model.vo.RepairReserveVO;
import com.genesis.apps.comm.model.vo.RepairTypeVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.databinding.FragmentServiceBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;
import com.genesis.apps.ui.common.fragment.SubFragment;
import com.genesis.apps.ui.main.MainActivity;
import com.genesis.apps.ui.myg.MyGEntranceActivity;
import com.google.android.material.tabs.TabLayoutMediator;


public class FragmentService extends SubFragment<FragmentServiceBinding> {
    private static final String TAG = FragmentService.class.getSimpleName();

    private final int PAGE_NUM = 3;//정비 세차 대리
    private final int[] TAB_ID_LIST = {R.string.sm01_header_1, R.string.sm01_header_2, R.string.sm01_header_3};

    public FragmentStateAdapter serviceTabAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.setContentView(inflater, R.layout.fragment_service);
    }

    private void initView() {
        serviceTabAdapter = new ServiceViewpagerAdapter(this, PAGE_NUM);
        me.vpServiceContentsViewPager.setAdapter(serviceTabAdapter);
//        me.vpServiceContentsViewPager.setUserInputEnabled(false);
        setTabView();

        //ViewPager Setting
        me.vpServiceContentsViewPager.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        me.vpServiceContentsViewPager.setCurrentItem(0);
        me.vpServiceContentsViewPager.setOffscreenPageLimit(PAGE_NUM);

        //TODO : MAinActivity에서 복붙. 스와이프 리스너인데 필요한가?
//        me.vpServiceContentsViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//                if (positionOffsetPixels == 0) {
//                    me.vpServiceContentsViewPager.setCurrentItem(position);
//                }
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                super.onPageSelected(position);
//
////                ui.indicator.animatePageSelected(position%num_page);
//            }
//
//        });

        //TODO : MAinActivity에서 복붙. 페이지 넘기는 이펙트 그럼 메인 탭이 넘어가야되나, 서비스 내부 탭이 넘어가야되나???
//        final float pageMargin = getResources().getDimensionPixelOffset(R.dimen.pageMargin);
//        final float pageOffset = getResources().getDimensionPixelOffset(R.dimen.offset);
//        me.vpServiceContentsViewPager.setPageTransformer((page, position) -> {
//            float myOffset = position * -(2 * pageOffset + pageMargin);
//            if (me.vpServiceContentsViewPager.getOrientation() == ViewPager2.ORIENTATION_HORIZONTAL) {
//                if (ViewCompat.getLayoutDirection(me.vpServiceContentsViewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
//                    page.setTranslationX(-myOffset);
//                } else {
//                    page.setTranslationX(myOffset);
//                }
//            } else {
//                page.setTranslationY(myOffset);
//            }
//        });
    }

    //탭 헤더 세팅
    private void setTabView() {
        new TabLayoutMediator(me.tlServiceTabs, me.vpServiceContentsViewPager, (tab, position) -> {
        }).attach();

        for (int i = 0; i < PAGE_NUM; i++) {
            TextView tabTitle = new TextView(getActivity());
            tabTitle.setText(TAB_ID_LIST[i]);
            tabTitle.setTextAppearance(R.style.ServiceMainTabs);
            me.tlServiceTabs.getTabAt(i).setCustomView(tabTitle);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        me.setLifecycleOwner(getViewLifecycleOwner());
        me.setFragment(this);
        initViewPager();
    }

    private void initViewPager() {
        //ViewPager Setting

    }

    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onClickCommon(View v) {

        switch (v.getId()) {

        }
    }

    @Override
    public void onRefresh() {

        Log.e("onResume", "onReusme contents");


        ((MainActivity) getActivity()).setGNB(getString(R.string.main_word_3), View.VISIBLE, false, true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == ResultCodes.REQ_CODE_SERVICE_RESERVE_AUTOCARE.getCode()) {
            //오토케어 서비스 예약 완료 시 페이지 이동
            RepairReserveVO repairReserveVO = (RepairReserveVO) data.getSerializableExtra(KeyNames.KEY_NAME_SERVICE_RESERVE_INFO);
            if (repairReserveVO != null) {
                ((SubActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceAutocare4ResultActivity.class).putExtra(KeyNames.KEY_NAME_SERVICE_RESERVE_INFO, repairReserveVO)
                        , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                        , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
            }
        } else if (resultCode == ResultCodes.REQ_CODE_SERVICE_RESERVE_HOMETOHOME.getCode()) {
            //홈투홈 서비스 예약 완료 시 페이지 이동
            RepairReserveVO repairReserveVO = (RepairReserveVO) data.getSerializableExtra(KeyNames.KEY_NAME_SERVICE_RESERVE_INFO);
            if (repairReserveVO != null) {
                ((SubActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceHomeToHome4ResultActivity.class).putExtra(KeyNames.KEY_NAME_SERVICE_RESERVE_INFO, repairReserveVO)
                        , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                        , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
            }
        } else if (resultCode == ResultCodes.REQ_CODE_SERVICE_RESERVE_REPAIR.getCode()) {
            //정비 서비스 예약 완료 시 페이지 이동
            RepairReserveVO repairReserveVO = (RepairReserveVO) data.getSerializableExtra(KeyNames.KEY_NAME_SERVICE_RESERVE_INFO);
            if (repairReserveVO != null) {
                ((SubActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceRepair4ResultActivity.class).putExtra(KeyNames.KEY_NAME_SERVICE_RESERVE_INFO, repairReserveVO)
                        , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                        , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
            }

        } else if (resultCode == ResultCodes.REQ_CODE_SERVICE_RESERVE_REMOTE.getCode()) {
            //todo 원격진단 서비스 예약 완료 시 페이지 이동
        } else if (resultCode == ResultCodes.REQ_CODE_SERVICE_NETWORK_RESERVE.getCode()) {
            //서비스네트워크에서 예약 선택 시
            RepairTypeVO repairTypeVO = (RepairTypeVO) data.getSerializableExtra(KeyNames.KEY_NAME_SERVICE_REPAIR_TYPE_CODE);
            BtrVO btrVO = (BtrVO) data.getSerializableExtra(KeyNames.KEY_NAME_BTR);
            ((SubActivity) getActivity()).startActivitySingleTop(new Intent(getActivity(), ServiceRepair2ApplyActivity.class)
                            .putExtra(KeyNames.KEY_NAME_SERVICE_REPAIR_TYPE_CODE, repairTypeVO)
                            .putExtra(KeyNames.KEY_NAME_BTR, btrVO)
                    , RequestCodes.REQ_CODE_ACTIVITY.getCode()
                    , VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    public boolean checkCustGbCd(int viewId, String custGbCd){
        boolean isAllow=true;

        try {
            switch (viewId) {
                case R.id.l_service_maintenance_reservation_btn://정비예약
                case R.id.l_service_maintenance_history_btn: //정비 현황/예약 내역
                case R.id.l_service_maintenance_emergency_btn: //긴급출동
                case R.id.l_service_maintenance_customercenter_btn://원격진단 신청
                case R.id.l_service_maintenance_remote_servie_list_btn:// 원격 진단 내역
                case R.id.l_service_maintenance_defect_btn: //하자재발통보
                case R.id.l_service_car_wash_history_btn: //세차 서비스 예약내역 버튼
                case R.id.l_service_car_wash_item: // 세차 쿠폰 선택
                case R.id.tv_service_drive_req_btn: //대리운전 신청 버튼
                    switch (custGbCd) {
                        //소유차량인 경우 기존 메뉴 정상 이용
                        case VariableType.MAIN_VEHICLE_TYPE_OV:
                            isAllow=true;
                            break;
                        case VariableType.MAIN_VEHICLE_TYPE_CV:
                        case VariableType.MAIN_VEHICLE_TYPE_NV:
                            //계약 혹은 차량 미 보유 고객인 경우 스낵바 알림 메시지 노출
                            isAllow=false;
                            SnackBarUtil.show(getActivity(), getString(R.string.sm01_snack_bar));
                            break;
                        case VariableType.MAIN_VEHICLE_TYPE_0000:
                        default:
                            //미로그인 고객인 경우 로그인 유도
                            isAllow=false;

                            MiddleDialog.dialogLogin(getActivity(), () -> {
                                ((SubActivity)getActivity()).startActivitySingleTop(new Intent(getActivity(), MyGEntranceActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                            }, () -> {

                            });
                            break;
                    }
                    break;
                default:
                    //그 외 버튼
                    isAllow=true;
                    break;
            }
        }catch (Exception ignore){

        }

        return isAllow;
    }


}
