package com.genesis.apps.ui.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.NOT_0001;
import com.genesis.apps.comm.model.api.gra.NOT_0002;
import com.genesis.apps.comm.model.constants.PushCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.NotiInfoVO;
import com.genesis.apps.comm.viewmodel.CMNViewModel;
import com.genesis.apps.databinding.ActivityAlarmCenterBinding;
import com.genesis.apps.databinding.ItemTabAlarmBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

/**
 * @author hjpark
 * @brief 알림센터
 */
public class AlarmCenterActivity extends SubActivity<ActivityAlarmCenterBinding> {
    private CMNViewModel cmnViewModel;
    private AlarmCenterRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_center);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();
        cmnViewModel.reqNOT0001(new NOT_0001.Request(APPIAInfo.ALRM01.getId(), ""));
    }

    private void initView() {
        ui.lTitle.ivTitlebarImgBtn.setOnClickListener(onSingleClickListener);
        adapter = new AlarmCenterRecyclerAdapter(onSingleClickListener);
        ui.rvNoti.setLayoutManager(new LinearLayoutManager(this));
        ui.rvNoti.setHasFixedSize(true);
        ui.rvNoti.setAdapter(adapter);
        initTabView();
    }

    private void initTabView() {
//        ui.tabs.addTab(ui.tabs.newTab().setText(R.string.alrm01_2));
        for (String codeNm : PushCodes.getPushListNm()) {
//            ui.tabs.addTab(ui.tabs.newTab().setText(codeNm));
            final LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final ItemTabAlarmBinding itemTabAlarmBinding = DataBindingUtil.inflate(inflater, R.layout.item_tab_alarm, null, false);
            final View view = itemTabAlarmBinding.getRoot();
            itemTabAlarmBinding.tvTab.setText(codeNm);
            ui.tabs.addTab(ui.tabs.newTab().setCustomView(view));
        }

        ui.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                getList(PushCodes.findCode(((ItemTabAlarmBinding) DataBindingUtil.bind(tab.getCustomView())).tvTab.getText().toString()).getCateCd(), "");
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getList(String cateCd, String search) {
        showProgressDialog(true);
        try {
            List<NotiInfoVO> list = cmnViewModel.getNotiInfoFromDB(cateCd, search);
            adapter.setRows(list);
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            showProgressDialog(false);
        }
    }

    @Override
    public void onClickCommon(View v) {
        NotiInfoVO item = null;
        switch (v.getId()) {
            //todo 어댑터 이벤트 정의 필요
            case R.id.iv_titlebar_img_btn:
                startActivitySingleTop(new Intent(this, AlarmCenterSearchActivity.class), 0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;
            case R.id.btn_detail:
                try {
                    item = (NotiInfoVO) v.getTag(R.id.noti_info);
                }catch (Exception e){
                    e.printStackTrace();
                }finally{
                    if(item!=null&&!TextUtils.isEmpty(item.getDtlLnkUri())&&!TextUtils.isEmpty(item.getDtlLnkCd())){
                        if(item.getDtlLnkCd().equalsIgnoreCase("I")){
                            moveToNativePage(item.getDtlLnkUri(), false);
                        }else{
                            moveToExternalPage(item.getDtlLnkUri(), "");
                        }
                    }
                }
                break;

            case R.id.l_title:
                int pos = 0;

                try {
                    item = (NotiInfoVO) v.getTag(R.id.noti_info);
                    pos = Integer.parseInt(v.getTag(R.id.position).toString());

                    //아이템을 선택했는데 읽음상태가 "읽지 않음인 경우"
                    if (item != null && item.getReadYn().equalsIgnoreCase(VariableType.COMMON_MEANS_NO)) {
                        //읽음상태 변경 요청
                        cmnViewModel.reqNOT0002(new NOT_0002.Request(APPIAInfo.ALRM01.getId(), item.getNotiNo()));
                        //일시적으로 해당페이지에서 읽음 상태로 처리하기 위해서 아래와 같이 데이터 변경
                        //읽음 요청 처리 응답이 성공이 아닐 경우 (서버에서 처리 못한 경우) 해당 페이지에 재 진입 시 새글알림 마크가 다시 보일 수 있음
                        ((NotiInfoVO) adapter.getItem(pos)).setReadYn(VariableType.COMMON_MEANS_YES);
                        cmnViewModel.updateNotiInfoReadYN(item);
                    }
                } catch (Exception e) {

                } finally {
                    if (item != null) {
                        switch (AlarmCenterRecyclerAdapter.getAccordionType(item)) {
                            case AlarmCenterRecyclerAdapter.ALARM_TYPE_NORMAL_NATIVE:
                                //TODO 클릭 시 상세페이지 이동 / getMsgLnkUri가 메뉴면 네이티브, 링크면 WEBVIEW로 이동시켜야하는데 확인 필요
                                adapter.notifyItemChanged(pos);
                                moveToNativePage(item.getMsgLnkUri(), false);
                                break;
                            case AlarmCenterRecyclerAdapter.ALARM_TYPE_NORMAL_WEBVIEW:
                                //TODO 클릭 시 외부 링크로 이동
                                adapter.notifyItemChanged(pos);
                                moveToExternalPage(item.getMsgLnkUri(), "");
                                break;
                            case AlarmCenterRecyclerAdapter.ALARM_TYPE_ACCORDION:
                            default:
                                adapter.eventAccordion(pos);
                                break;
                        }
                    }
                }


                break;


        }

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        cmnViewModel = new ViewModelProvider(this).get(CMNViewModel.class);
    }

    @Override
    public void setObserver() {
        cmnViewModel.getRES_NOT_0001().observe(this, result -> {

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getNotiInfoList() != null) {
                        try {
                            if (cmnViewModel.updateNotiList(result.data.getNotiInfoList())) {
                                adapter.setRows(result.data.getNotiInfoList());
                                adapter.notifyDataSetChanged();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        } finally {
                            showProgressDialog(false);
                        }
                    } else {
                        showProgressDialog(false);
                    }
                    break;

                default:
                    showProgressDialog(false);
                    break;
            }


        });

    }

    @Override
    public void getDataFromIntent() {
        //todo push로 실행되는 경우 pushVo를 받고 대분류 카테고리 일치하는 쪽으로 자동 이동?

    }
}
