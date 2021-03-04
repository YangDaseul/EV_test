package com.genesis.apps.ui.main.service;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.VOC_1003;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.RequestCodes;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.VOCInfoVO;
import com.genesis.apps.comm.util.DeviceUtil;
import com.genesis.apps.comm.util.RecyclerViewDecoration;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.VOCViewModel;
import com.genesis.apps.databinding.ActivityServiceRelapseHistoryBinding;
import com.genesis.apps.ui.common.activity.GpsBaseActivity;

import java.util.List;

public class ServiceRelapseHistoryActivity extends GpsBaseActivity<ActivityServiceRelapseHistoryBinding> {
    private static final String TAG = ServiceRelapseHistoryActivity.class.getSimpleName();
//    private static final int PAGE_SIZE = 20;

    private VOCViewModel viewModel;
    private ServiceRelapseHistoryAdapter adapter;
    private AddressVO addressVO;
    private String vin;
    private String mdlNm;

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == ResultCodes.REQ_CODE_APPLY_RELAPSE.getCode()){
            SnackBarUtil.show(this, getString(R.string.relapse_succ));
            reqNextPage();
        } else if(resultCode == ResultCodes.REQ_CODE_APPLY_RELAPSE_EXIT.getCode()){
            //신청하기 페이지에서 나가기를 진행하면 페이지 종료
            finish();
            closeTransition();
        }
    }

    @Override
    public void onClickCommon(View v) {
        Log.d(TAG, "onClickCommon: ");
        Intent intent;
        switch (v.getId()) {
            //신청 내역 목록에서 [접수중] 상태인 아이템
            case R.id.l_relapse_history_item:
                intent = new Intent(this, ServiceRelapseReqResultActivity.class)
                        .putExtra(KeyNames.KEY_NAME_SERVICE_VOC_INFO_VO, (VOCInfoVO) v.getTag(R.id.tag_relapse_history));

                startActivitySingleTop(
                        intent,
                        RequestCodes.REQ_CODE_ACTIVITY.getCode(),
                        VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
                break;

            //신청 버튼
            case R.id.tv_relapse_history_req_btn:
                intent = new Intent(this, ServiceRelapseApply1Activity.class)
                        .putExtra(KeyNames.KEY_NAME_ADDR, addressVO);

                startActivitySingleTop(
                        intent,
                        RequestCodes.REQ_CODE_RELAPSE_REQ.getCode(),
                        VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
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
                    if (result.data != null) {

                        //수신된 데이터를 꺼내고
                        List<VOCInfoVO> list = result.data.getDfctList();

                        //받은 목록의 길이가 1 이상이면 어댑터에 넣는다
                        if (list != null && list.size() > 0) {
                            adapter.setRows(list);
                        }
                        //데이터가 없으면 '내역 없음'뷰를 출력할 더미 추가
                        else {
                            adapter.addRow(null);
                        }

                        //목록 뒤에 더미 하나 더 추가. 이건 안내문 페이지가 된다.
                        adapter.addRow(null);
                        adapter.notifyDataSetChanged();

                        //이용 내역 수를 표시
                        ui.setItemCount("" + (list == null ? "0" : list.size()));

                        //성공 후 데이터 로딩까지 다 되면 로딩 치우고 break;
                        showProgressDialog(false);
                        reqMyLocation();
                        break;
                    }
                    //not break; 데이터 이상하면 default로 진입시킴

                default:
                    showProgressDialog(false);
                    reqMyLocation();
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        if (TextUtils.isEmpty(serverMsg)) {
                            serverMsg = getString(R.string.r_flaw06_p02_snackbar_1);
                        }
                        SnackBarUtil.show(this, serverMsg);
                    }
                    break;
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        try {
            addressVO = (AddressVO) getIntent().getSerializableExtra(KeyNames.KEY_NAME_ADDR);
            vin = getIntent().getStringExtra(KeyNames.KEY_NAME_VIN);
            mdlNm = getIntent().getStringExtra(KeyNames.KEY_NAME_MDL_NM);
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
        adapter = new ServiceRelapseHistoryAdapter(onSingleClickListener, mdlNm);
        ui.rvServiceDriveHistoryList.addItemDecoration(new RecyclerViewDecoration((int) DeviceUtil.dip2Pixel(this,4.0f)));
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
                        APPIAInfo.SM_FLAW01.getId(),vin
                ));
    }


    private void reqMyLocation() {
        if(addressVO==null) {
            showProgressDialog(true);
            findMyLocation(location -> {
                showProgressDialog(false);
                if (location == null) {
                    return;
                }
                addressVO = new AddressVO();
                addressVO.setCenterLat(location.getLatitude());
                addressVO.setCenterLon(location.getLongitude());
            }, 5000,GpsRetType.GPS_RETURN_FIRST, false);
        }
    }
}
