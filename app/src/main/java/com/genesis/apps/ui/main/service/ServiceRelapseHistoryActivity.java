package com.genesis.apps.ui.main.service;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.DDS_1003;
import com.genesis.apps.comm.model.vo.DriveServiceVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.DDSViewModel;
import com.genesis.apps.databinding.ActivityServiceDriveHistoryBinding;
import com.genesis.apps.databinding.ActivityServiceRelapseHistoryBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import java.util.List;

public class ServiceRelapseHistoryActivity extends SubActivity<ActivityServiceRelapseHistoryBinding> {
    private static final String TAG = ServiceRelapseHistoryActivity.class.getSimpleName();
    private static final int PAGE_SIZE = 20;

    //todo 하자재발 뷰 모델로 변경
    private DDSViewModel viewModel;
    private ServiceRelapseHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_relapse_history);

        setAdapter();
        setViewModel();
        setObserver();

        ui.setActivity(this);
        reqNextPage();//초기값이 0이니까 1페이지를 요청한다
    }

    @Override
    public void onClickCommon(View v) {
        Log.d(TAG, "onClickCommon: ");

        switch (v.getId()) {
            case R.id.tv_relapse_history_req_btn:
                //todo impl
                break;

            default:
                //do nothing
                break;
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(DDSViewModel.class);
    }

    @Override
    public void setObserver() {
        Log.d(TAG, "setObserver: ");

        //TODO 뷰모델만 바꾸고 안쪽은 거의 그대로 써먹기 가능할듯?
        viewModel.getRES_DDS_1003().observe(this, result -> {
            Log.d(TAG, "getRES_DDS_1003 service drive history obs" + result.status);

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getSvcInfo() != null) {
                        //수신 성공했으니 페이지 카운트 증가
                        adapter.incPageNo();

                        //수신된 데이터를 꺼내고
                        List<DriveServiceVO> list = result.data.getSvcInfo();

                        //받은 목록의 길이가 1 이상이면 데이터 추가
                        //todo ...중간에 끼워넣어야되는데 ㅡㅡ;; concatAdapter 써야되나
                        if (list.size() > 0) {
                            adapter.addRows(list);
                            adapter.notifyDataSetChanged();
                        }
                        //데이터가 없으면 목록을 숨겨서 '내역 없음'뷰가 보이도록 함
                        //todo 데이터 없으면.... 안내문 세 개가 들어있어야되는데......
                        else if (adapter.getItemCount() == 0) {
                            //todo impl
                        }

                        ui.setItemCount("" + adapter.getItemCount());

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
        //do nothing
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setAdapter() {
        //하자 재발 신청 내역 어댑터 (인스턴스 타입 맞나 확인)
        adapter = new ServiceRelapseHistoryAdapter();
        ui.rvServiceDriveHistoryList.setLayoutManager(new LinearLayoutManager(this));
        ui.rvServiceDriveHistoryList.setHasFixedSize(true);
        ui.rvServiceDriveHistoryList.setAdapter(adapter);

        //끝까지 스크롤하면 다음 페이지 요청
        ui.rvServiceDriveHistoryList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!ui.rvServiceDriveHistoryList.canScrollVertically(1) &&//scroll end
                        adapter.getItemCount() >= PAGE_SIZE * adapter.getPageNo()) {      //페이지 포화
                    reqNextPage();
                }
            }
        });
    }

    //이용 내역 한 페이지 요청
    private void reqNextPage() {
        Log.d(TAG, "reqNextPage: " + (adapter.getPageNo() + 1));

        //todo api 반영
        viewModel.reqDDS1003(
                new DDS_1003.Request(
                        APPIAInfo.SM_DRV05.getId(),
                        "" + (adapter.getPageNo() + 1),
                        "" + PAGE_SIZE
                ));
    }
}
