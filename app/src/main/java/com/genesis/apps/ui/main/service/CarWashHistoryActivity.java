package com.genesis.apps.ui.main.service;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.api.gra.WSH_1004;
import com.genesis.apps.comm.model.api.gra.WSH_1005;
import com.genesis.apps.comm.model.api.gra.WSH_1006;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.WashReserveVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.SoftKeyboardUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.LGNViewModel;
import com.genesis.apps.comm.viewmodel.WSHViewModel;
import com.genesis.apps.databinding.ActivityCarWashHistoryBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.bottom.BottomDialogInputBranchCode;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import java.util.concurrent.ExecutionException;

public class CarWashHistoryActivity extends SubActivity<ActivityCarWashHistoryBinding> {
    private static final String TAG = CarWashHistoryActivity.class.getSimpleName();

    private WSHViewModel viewModel;
    private LGNViewModel lgnViewModel;
    private CarWashHistoryAdapter adapter;
    private VehicleVO mainVehicle;

    private int itemPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash_history);

        setAdapter();
        setViewModel();
        initMainVehicle();
        setObserver();

        viewModel.reqWSH1004(new WSH_1004.Request(APPIAInfo.SM_CW01.getId(), WSHViewModel.SONAX));
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        viewModel = new ViewModelProvider(this).get(WSHViewModel.class);
        lgnViewModel = new ViewModelProvider(this).get(LGNViewModel.class);
    }

    @Override
    public void onClickCommon(View v) {
        Log.d(TAG, "onClickCommon()");
        int id = v.getId();

        WashReserveVO tag = (WashReserveVO) v.getTag(R.id.tag_wash_history);
        String rsvtSeqNo;

        switch (id) {
            //????????????
            case R.id.tv_car_wash_history_call:
            case R.id.l_whole:
                String phoneNumber = tag.getTelNo();
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL + phoneNumber)));
                break;

            //???????????? ??????
            case R.id.tv_car_wash_history_confirm:
                rsvtSeqNo = tag.getRsvtSeqNo();
                String oldBrnhCd = tag.getBrnhCd();

                final BottomDialogInputBranchCode inputBranchCodeDialog = new BottomDialogInputBranchCode(this, oldBrnhCd, R.style.BottomSheetDialogTheme, adapter!=null ? adapter.getItems() : null);
                inputBranchCodeDialog.setOnDismissListener(
                        dialogInterface -> {
                            if (inputBranchCodeDialog.isInputConfirmed()) {
                                SoftKeyboardUtil.hideKeyboard(this, getWindow().getDecorView().getWindowToken());
                                itemPosition = (int) v.getTag(R.id.item_position); //?????? ????????? ????????? ?????? ?????? ??????
                                String newBrnhCd = inputBranchCodeDialog.getBranchCode();
                                viewModel.reqWSH1005(new WSH_1005.Request(APPIAInfo.SM_CW01_P02.getId(), rsvtSeqNo, newBrnhCd));
                            }
                        });
                inputBranchCodeDialog.show();
                break;

            //?????? ??????
            case R.id.tv_car_wash_history_cancel:
                rsvtSeqNo = tag.getRsvtSeqNo();
                String brnhCd = tag.getBrnhCd();
                WashReserveVO item = (WashReserveVO) v.getTag(R.id.tag_wash_history);
                itemPosition = (int) v.getTag(R.id.item_position); //?????? ????????? ????????? ?????? ?????? ??????

                String msg = mainVehicle.getMdlNm() + " " + item.getGodsNm() + " " + getString(R.string.cw_reserve_cancel_msg);

                MiddleDialog.dialogCarWashCancel(
                        this,
                        msg,
                        () -> viewModel.reqWSH1006(new WSH_1006.Request(APPIAInfo.SM_CW01_P01.getId(), rsvtSeqNo, brnhCd)));

                break;
            //TODO : ???????????? ?????? ????????? ?????? ????????? ??????.
            //?????? ??????
//            case R.id.tv_car_wash_history_service_end:
//                startActivitySingleTop(
//                        new Intent(this, ServiceReviewActivity.class)
//                        .putExtra(KeyNames.KEY_NAME_REVIEW_RSVT_SEQ_NO, tag.getRsvtSeqNo()),
//                        0, VariableType.ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE);
//                break;

            default:
                //do nothing
                break;
        }
    }

    @Override
    public void setObserver() {
        //?????? ?????? ?????????
        viewModel.getRES_WSH_1004().observe(this, result -> {
            Log.d(TAG, "getRES_WSH_1004 reserve history obs" + result.status);

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    //?????? ??? ????????? ???????????? ??? ?????? ?????? ????????? break;
                    showProgressDialog(false);

                    if (result.data != null && result.data.getRsvtList() != null) {

                        if(result.data.getRsvtList()==null||result.data.getRsvtList().size() == 0) {
                            ui.rvCarWashHistoryList.setVisibility(View.GONE);
                            ui.tvEmpty.setVisibility(View.VISIBLE);
                        } else {
                            try {
                                adapter.setRows(viewModel.getWashList(result.data.getRsvtList()));
                                adapter.notifyDataSetChanged();
                            }catch (Exception e){
                                e.printStackTrace();
                            }
                        }
                    } else {
                        ui.rvCarWashHistoryList.setVisibility(View.GONE);
                        ui.tvEmpty.setVisibility(View.VISIBLE);
                    }

                    break;
                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (result.data != null && StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase("2005"))
                        return;

                    SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    ui.rvCarWashHistoryList.setVisibility(View.GONE);
                    ui.tvEmpty.setVisibility(View.VISIBLE);
                    break;
            }
        });

        //???????????? ?????? ?????????
        viewModel.getRES_WSH_1005().observe(this, result -> {
            Log.d(TAG, "getRES_WSH_1005 confirm staff obs" + result.status);
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getRtCd().equals(BaseResponse.RETURN_CODE_SUCC)) {
                        adapter.setRsvtStusCd(itemPosition, WSH_1004.RESERVE_COMPLETED);
                        SnackBarUtil.show(this, getString(R.string.cw_confirm_staff));

                        //?????? ??? ????????? ???????????? ??? ?????? ?????? ????????? break;
                        showProgressDialog(false);
                        break;
                    }
                    //not break; ????????? ???????????? default??? ????????????

                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
            }
        });

        //?????? ?????? ?????????
        viewModel.getRES_WSH_1006().observe(this, result -> {
            Log.d(TAG, "getRES_WSH_1006 cancel reserve obs" + result.status);

            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;

                case SUCCESS:
                    if (result.data != null && result.data.getRtCd().equals(BaseResponse.RETURN_CODE_SUCC)) {
                        adapter.setRsvtStusCd(itemPosition, WSH_1004.RESERVE_CANCELED);
                        SnackBarUtil.show(this, getString(R.string.cw_cancel_reserve));

                        //?????? ??? ????????? ???????????? ??? ?????? ?????? ????????? break;
                        showProgressDialog(false);
                        break;
                    }
                    //not break; ????????? ???????????? default??? ????????????

                default:
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(this, (TextUtils.isEmpty(serverMsg)) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
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

    private void initMainVehicle() {
        try {
            mainVehicle = lgnViewModel.getMainVehicleSimplyFromDB();
        } catch (ExecutionException e){
            e.printStackTrace();
        } catch (InterruptedException e) {
            Log.d(TAG, "InterruptedException");
            Thread.currentThread().interrupt();
        }
    }

    private void setAdapter() {
        //?????? ?????? ?????? ????????? (???????????? ?????? ?????? ??????)
        adapter = new CarWashHistoryAdapter(onSingleClickListener);
        ui.rvCarWashHistoryList.setLayoutManager(new LinearLayoutManager(this));
        ui.rvCarWashHistoryList.setHasFixedSize(true);
        ui.rvCarWashHistoryList.setAdapter(adapter);
    }
}
