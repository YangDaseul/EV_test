package com.genesis.apps.ui.main.service;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.VOC_1003;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.VOCInfoVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.VOCViewModel;
import com.genesis.apps.databinding.ActivityServiceRelapseHistoryBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import java.util.List;

public class ServiceRelapseHistoryActivity extends SubActivity<ActivityServiceRelapseHistoryBinding> {
    private static final String TAG = ServiceRelapseHistoryActivity.class.getSimpleName();
//    private static final int PAGE_SIZE = 20;

    private VOCViewModel viewModel;
    private ServiceRelapseHistoryAdapter adapter;
    private AddressVO addressVO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_relapse_history);
        getDataFromIntent();
        setAdapter();
        setViewModel();
        setObserver();

        ui.setActivity(this);
        reqNextPage();//페이징 처리 없어서 그냥 한번에 전체가 다 온다
    }

    @Override
    public void onClickCommon(View v) {
        Log.d(TAG, "onClickCommon: ");

        switch (v.getId()) {
            //todo 임시 진입점. 개발 완료 후 삭제(레이아웃 가서도 관련 코드 삭제)
            case R.id.tv_relapse_history_list_title:
                startActivitySingleTop(new Intent(this, ServiceRelapse3Activity.class), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            //신청 내역 목록에서 [접수중] 상태인 아이템
            case R.id.l_relapse_history_item:

                //todo impl

                break;

            //신청 버튼
            case R.id.tv_relapse_history_req_btn:
                startActivitySingleTop(new Intent(this, ServiceRelapseApply1Activity.class).putExtra(KeyNames.KEY_NAME_ADDR, addressVO), RequestCodes.REQ_CODE_ACTIVITY.getCode(), VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            default:
                //do nothing
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(VOCViewModel.class);
    }

    @Override
    public void setObserver() {
        Log.d(TAG, "setObserver: ");

        viewModel.getRES_VOC_1003().observe(this, result -> {
            Log.d(TAG, "getRES_VOC_1003 relapse history obs" + result.status);

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getDfctList() != null) {

                        //수신된 데이터를 꺼내고
                        List<VOCInfoVO> list = result.data.getDfctList();

                        //받은 목록의 길이가 1 이상이면 어댑터에 추가
                        if (list.size() > 0) {
                            adapter.addRows(list);
                        }
                        //데이터가 없으면 '내역 없음'뷰를 출력할 더미 추가
                        else {
                            adapter.addRow(null);
                        }

                        //목록 뒤에 더미 하나 더 추가. 이건 안내문 페이지가 된다.
                        adapter.addRow(null);
                        adapter.notifyDataSetChanged();

                        //이용 내역 수를 표시
                        ui.setItemCount("" + list.size());

                        //성공 후 데이터 로딩까지 다 되면 로딩 치우고 break;
                        showProgressDialog(false);
                        break;
                    }
                    //not break; 데이터 이상하면 default로 진입시킴

                default:
                    showProgressDialog(false);
                    SnackBarUtil.show(this, "" + result.message);
                    //todo : 구체적인 예외처리
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        try {
            addressVO =(AddressVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_ADDR);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setAdapter() {
        //하자 재발 신청 내역 어댑터 (인스턴스 타입 맞나 확인)
        adapter = new ServiceRelapseHistoryAdapter(onSingleClickListener);
        ui.rvServiceDriveHistoryList.setLayoutManager(new LinearLayoutManager(this));
        ui.rvServiceDriveHistoryList.setHasFixedSize(true);
        ui.rvServiceDriveHistoryList.setAdapter(adapter);

        //끝까지 스크롤하면 다음 페이지 요청
        //페이징 처리 없는 api라서 페이징 관련 코드 주석화
//        ui.rvServiceDriveHistoryList.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (!ui.rvServiceDriveHistoryList.canScrollVertically(1) &&//scroll end
//                        adapter.getItemCount() >= PAGE_SIZE * adapter.getPageNo()) {      //페이지 포화
//                    reqNextPage();
//                }
//            }
//        });
    }

    //이용 내역 한 페이지 요청 : 그런 거 없고 통째로 다 준다
    private void reqNextPage() {
//        Log.d(TAG, "reqNextPage: " + (adapter.getPageNo() + 1));

        viewModel.reqVOC1003(
                new VOC_1003.Request(
                        APPIAInfo.SM_FLAW01.getId()
                ));
    }
}
