package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.WSH_1002;
import com.genesis.apps.comm.model.vo.WashBrnVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.viewmodel.WSHViewModel;
import com.genesis.apps.databinding.ActivityCarWashFindSonaxBranchBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

import java.util.List;

public class CarWashFindSonaxBranchActivity extends SubActivity<ActivityCarWashFindSonaxBranchBinding> {
    private static final String TAG = CarWashFindSonaxBranchActivity.class.getSimpleName();

    private WSHViewModel viewModel;
    private CarWashFindSonaxBranchAdapter adapter;

    private String godsSeqNo;
    private double custX;
    private double custY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash_find_sonax_branch);

        //앞 단계에서 선택한 쿠폰 정보 및 고객 좌표를 알 수 없으면 지점 검색을 할 수 없다
        if (!initDataFromIntent()) {
            finish();
            return;
        }

        ui.setActivity(this);
        setViewModel();
        setAdapter();
        setObserver();
    }

    private boolean initDataFromIntent() {
        godsSeqNo = getIntent().getStringExtra(WSH_1002.GOODS_SEQ_NUM);
        //경도, 위도에서 나올 수 없는 값을 defaultValue로 정해서 유효성 검사에 사용
        custX = getIntent().getDoubleExtra(WSH_1002.CUST_X, 360.0);
        custY = getIntent().getDoubleExtra(WSH_1002.CUST_Y, 360.0);

        Log.d(TAG, "Intent godsSeqNo: " + godsSeqNo);
        Log.d(TAG, "Intent custX: " + custX);
        Log.d(TAG, "Intent custY: " + custY);
        return godsSeqNo != null &&
                custX != 360.0 &&
                custY != 360.0;
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(WSHViewModel.class);
    }

    @Override
    public void setObserver() {
        //예약 내역 옵저버
        viewModel.getRES_WSH_1002().observe(this, result -> {

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getBrnhList() != null) {
                        List<WashBrnVO> list = result.data.getBrnhList();
                        adapter.setRows(list);
                        adapter.notifyDataSetChanged();

                        setResultCount(list.size());

                        //성공 후 데이터 로딩까지 다 되면 로딩 치우고 break;
                        showProgressDialog(false);
                        break;
                    }
                    //결과 없으면 카운트 초기화
                    setResultCount(0);

                    //not break; 데이터 이상하면 default로 진입시킴

                default:
                    showProgressDialog(false);
                    SnackBarUtil.show(this, getString(result.message));
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
    public void onClickCommon(View v) {
        Log.d(TAG, "onClickCommon()");
        int id = v.getId();

        switch (id) {
            case R.id.tv_car_wash_find_branch_location_select:
                //todo 호출위치 수정;
                viewModel.reqWSH1002(
                        new WSH_1002.Request(
                                APPIAInfo.SM_CW01_A02.getId(),
                                godsSeqNo,
                                WSHViewModel.SONAX,
                                "" + custX,
                                "" + custY,
                                "todo 주소 입력"));
                break;

            default:
                //do nothing
                break;
        }

    }

    //검색 결과 갯수 바인딩. 결과 없으면 목록 사라지도록 하는 거랑 결과 수를 표시하는 거
    private void setResultCount(int count) {
        ui.setResultCount(count);
        ui.lSonaxFindList.setResultCount("" + count);
    }

    private void setAdapter() {
        //지점 검색 어댑터 (인스턴스 타입 맞나 확인)
        adapter = new CarWashFindSonaxBranchAdapter(onSingleClickListener);
        ui.lSonaxFindList.rvMapFindResultList.setLayoutManager(new LinearLayoutManager(this));
        ui.lSonaxFindList.rvMapFindResultList.setHasFixedSize(true);
        ui.lSonaxFindList.rvMapFindResultList.setAdapter(adapter);
    }
}
