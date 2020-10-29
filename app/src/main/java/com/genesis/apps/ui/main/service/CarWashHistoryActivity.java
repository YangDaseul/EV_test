package com.genesis.apps.ui.main.service;


import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.gra.api.WSH_1004;
import com.genesis.apps.comm.model.gra.api.WSH_1005;
import com.genesis.apps.comm.model.gra.api.WSH_1006;
import com.genesis.apps.comm.model.vo.WashReserveVO;
import com.genesis.apps.comm.util.PhoneUtil;
import com.genesis.apps.comm.viewmodel.WSHViewModel;
import com.genesis.apps.databinding.ActivityCarWashHistoryBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomDialogInputBranchCode;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;

import java.util.List;

public class CarWashHistoryActivity extends SubActivity<ActivityCarWashHistoryBinding> {
    private static final String TAG = CarWashHistoryActivity.class.getSimpleName();

    private WSHViewModel viewModel;
    private CarWashHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash_history);

        getDataFromIntent();
        setViewModel();
        setObserver();

        setAdapter();

        //TODO 저거 ("SONAX") 하드코딩하는 게 맞나
        viewModel.reqWSH1004(new WSH_1004.Request(APPIAInfo.SM_CW01.getId(), "SONAX"));
    }

    @Override
    public void onClickCommon(View v) {
        Log.d(TAG, "onClickCommon()");
        int id = v.getId();
//        CarWashHistoryViewHolder carWashHistoryItem = (CarWashHistoryViewHolder) v.getParent();

        String rsvtSeqNo = "dummy";
        String brnhCd = "code";

        switch (id) {
            case R.id.tv_car_wash_history_call:
                //todo 전화번호 알아내서 걸기
                String phoneNumber = "01012345678";
//                Log.d(TAG, "onClickCommon(): 통화하기 : " + carWashHistoryItem.getPhoneNumber());
//                Toast.makeText(this, carWashHistoryItem.getPhoneNumber(), Toast.LENGTH_LONG).show();

                PhoneUtil.phoneDial(this, phoneNumber);

                break;

            case R.id.tv_car_wash_history_confirm:
                // todo 반만 전산화.... 다른 지점으로 바뀌는 수가 있어서
                //  사용자가 받아적은 그 코드를 서버에 송신
                //todo : 예약 번호 및 지점코드 알아내기

                final BottomDialogInputBranchCode inputBranchCodeDialog = new BottomDialogInputBranchCode(this, R.style.BottomSheetDialogTheme);
                inputBranchCodeDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        if (
                                true
                                //todo inputBranchCodeDialog 인스턴스테서 입력값 꺼내고 유효성검사
                        ) {
                            viewModel.reqWSH1005(new WSH_1005.Request(APPIAInfo.SM_CW01_P02.getId(), rsvtSeqNo, brnhCd));
                        }
                    }
                });
                inputBranchCodeDialog.show();

                break;

            case R.id.tv_car_wash_history_cancel:
                // TODO 취소 성공 응답 받으면 취소됨 상태로 변해야되네
                // 어... visibility 스위칭으로 해놔서 접히기 애니메이션 적용 안 되는데;;;

                //todo : 예약 번호 및 지점코드 알아내기

                MiddleDialog.dialogCarWashCancel(
                        this,
                        () -> viewModel.reqWSH1006(new WSH_1006.Request(APPIAInfo.SM_CW01_P01.getId(), rsvtSeqNo, brnhCd)),
                        null);

                break;

            default:
                //do nothing
                break;
        }

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(WSHViewModel.class);
    }

    @Override
    public void setObserver() {
        //예약 내역 옵저버
        viewModel.getRES_WSH_1004().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getRsvtList() != null) {
                        List<WashReserveVO> list = result.data.getRsvtList();
                        adapter.setRows(list);
                        adapter.notifyDataSetChanged();
                        break;
                    }

                default:
                    showProgressDialog(false);
                    //실패하면 빈 목록이 뜰테니 사용자가 간접적으로 알 수 있다.
                    break;
            }
        });

        //예약 취소 옵저버
        viewModel.getRES_WSH_1006().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    showProgressDialog(false);
                    if (result.data != null && result.data.getRtCd() != null) {
                        //todo : 어댑터에서 해당 항목 정보 수정 (예약됨 -> 예약취소)
                        // 요청한 곳에서부터 여기로도 데이터 끌고 와야되네
                        // 그냥 싹 다 다시 받아올까 ㅡㅡ;;?
                        Toast.makeText(this, "예약 취소 성공", Toast.LENGTH_LONG).show();

                        adapter.notifyDataSetChanged();
                        break;
                    }

                default:
                    showProgressDialog(false);
                    //TODO 여기는 실패 관련 처리 안 해주면 사용자가 실패한 줄 모른다
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
        //세차 예약 내역 어댑터 (인스턴스 타입 맞나 확인)
        adapter = new CarWashHistoryAdapter(onSingleClickListener);
        ui.rvCarWashHistoryList.setLayoutManager(new LinearLayoutManager(this));
        ui.rvCarWashHistoryList.setHasFixedSize(true);
        ui.rvCarWashHistoryList.setAdapter(adapter);
    }

}
