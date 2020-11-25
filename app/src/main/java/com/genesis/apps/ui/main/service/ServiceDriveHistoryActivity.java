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
import com.genesis.apps.ui.common.activity.SubActivity;

import java.util.List;

public class ServiceDriveHistoryActivity extends SubActivity<ActivityServiceDriveHistoryBinding> {
    private static final String TAG = ServiceDriveHistoryActivity.class.getSimpleName();
    private static final int PAGE_SIZE = 20;

    private DDSViewModel viewModel;
    private ServiceDriveHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_drive_history);

        setAdapter();
        setViewModel();
        setObserver();

        reqNextPage();//초기값이 0이니까 1페이지를 요청한다
    }

    @Override
    public void onClickCommon(View v) {
        Log.d(TAG, "onClickCommon: ");
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(DDSViewModel.class);
    }

    @Override
    public void setObserver() {
        Log.d(TAG, "setObserver: ");

        viewModel.getRES_DDS_1003().observe(this, result -> {
            Log.d(TAG, "getRES_DDS_1003 service drive history obs" + result.status);

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getSvcInfo() != null) {
                        //수신 성공했으니 페이지 카운트 증가
                        // (목록이 비었어도 상관없음, 그러면 어차피 더는 없는 거니까 다음 페이지 요청도 더 이상 안 하게 됨)
                        adapter.incPageNo();

                        //수신된 데이터를 꺼내고
                        List<DriveServiceVO> list = result.data.getSvcInfo();

                        //완료 또는 취소가 확정된 건수가 아니면 '이용내역' 목록에 표시하지 않으므로 목록에서 제거
                        for (int i = list.size() - 1; i >= 0; i--) {
                            DriveServiceVO item = list.get(i);
                            if (adapter.findItemType(item.getSvcStusCd()) == ServiceDriveHistoryAdapter.TYPE_NOT_HISTORY) {
                                list.remove(i);
                            }
                        }

                        //받은 목록(에서 현재 진행형인 건수 제거하고 남은 것)의 길이가 1 이상이면 데이터 추가
                        if (list.size() > 0) {
                            adapter.addRows(list);
                            adapter.notifyDataSetChanged();
                        }
                        //데이터가 없으면 목록을 숨겨서 '내역 없음'뷰가 보이도록 함
                        else if (adapter.getItemCount() == 0) {
                            hideList();
                        }

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
        //대리운전 이용 내역 어댑터 (인스턴스 타입 맞나 확인)
        adapter = new ServiceDriveHistoryAdapter();
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

        viewModel.reqDDS1003(
                new DDS_1003.Request(
                        APPIAInfo.SM_DRV05.getId(),
                        "" + (adapter.getPageNo() + 1),
                        "" + PAGE_SIZE
                ));
    }

    //이용 내역이 없으면 "내역이 없습니다"를 보여줌(목록 뷰가 '내역없음'메시지를 가리고있음)
    private void hideList() {
        ui.rvServiceDriveHistoryList.setVisibility(View.GONE);
    }

}
