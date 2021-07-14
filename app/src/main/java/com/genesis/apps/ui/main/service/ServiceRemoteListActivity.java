package com.genesis.apps.ui.main.service;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.RMT_1003;
import com.genesis.apps.comm.model.api.gra.RMT_1004;
import com.genesis.apps.comm.model.api.gra.RMT_1005;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.RemoteHistoryVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.RMTViewModel;
import com.genesis.apps.databinding.ActivityServiceRemoteListBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;

import java.util.ArrayList;

import static com.genesis.apps.comm.model.api.BaseResponse.RETURN_CODE_SUCC;

/**
 * Class Name : ServiceRemoteListActivity
 *
 * @author Ki-man Kim
 * @since 2020-12-21
 */
public class ServiceRemoteListActivity extends SubActivity<ActivityServiceRemoteListBinding> {
    private RMTViewModel rmtViewModel;

    private ServiceRemoteListAdapter adapter = new ServiceRemoteListAdapter(onSingleClickListener);
    private ArrayList<RemoteHistoryVO> datas = new ArrayList<>();
    private String vin;
    private boolean isShowRegistComplete = false;

    /****************************************************************************************************
     * Override Method - LifeCycle
     ****************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_remote_list);
        getDataFromIntent();
        setViewModel();
        setObserver();

        rmtViewModel.reqRMT1003(new RMT_1003.Request(APPIAInfo.R_REMOTE01.getId(), vin));

        if (isShowRegistComplete) {
            // 등록 완료인 경우 1회 안내 팝업 1회 표시.
            SnackBarUtil.show(this, getString(R.string.sm_remote01_msg_register_success));
        }
    }

    /****************************************************************************************************
     * Override Method - Event
     ****************************************************************************************************/
    /**
     *
     * @param v 
     */
    @Override
    public void onClickCommon(View v) {
        int id = v.getId();
        Object tag = v.getTag(ServiceRemoteListAdapter.TAG_KEY_ITEM);

        if (tag instanceof RemoteHistoryVO == false) {
//                tag가 RemoteHistoryVO 객체가 아닌 경우 중지.
            return;
        }

        if (id == R.id.tv_service_remote_cancel_btn) {
            // 예약 취소 버튼.
            MiddleDialog.dialogServiceRemoteCancel(this, () -> {
                RemoteHistoryVO vo = (RemoteHistoryVO) tag;
                rmtViewModel.reqRMT1005(new RMT_1005.Request(APPIAInfo.R_REMOTE01.getId(), vo.getTmpAcptCd(), vo.getRcptCd()));
            }, () -> {

            });
            return;
        }

        if (id == R.id.tv_service_remote_detail_btn) {
            // 진단 결과 정보 요청.
            RemoteHistoryVO vo = (RemoteHistoryVO) tag;
            rmtViewModel.reqRMT1004(new RMT_1004.Request(APPIAInfo.R_REMOTE01.getId(), vo.getTmpAcptCd(), vo.getRcptCd()));
            return;
        }
    }

    /****************************************************************************************************
     * Override Method
     ****************************************************************************************************/
    @Override
    public void setViewModel() {
        rmtViewModel = new ViewModelProvider(this).get(RMTViewModel.class);
    }

    @Override
    public void setObserver() {
        rmtViewModel.getRES_RMT_1003().observe(this, result -> {

            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    RMT_1003.Response response = result.data;

                    if (response != null && response.getAplyList() != null) {
                        datas.clear();
                        datas.addAll(response.getAplyList());
                        initView();
                    } else if (result.data != null && StringUtil.isValidString(result.data.getRtCd()).equalsIgnoreCase("2005")) {
                        //조회된 정보가 없습니다 코드.
                        initView();
                    } else {
                        exitPage(getString(R.string.r_flaw06_p02_snackbar_1), ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                    }
                    break;
                }
                case ERROR: {
                    showProgressDialog(false);
                    // 통신 오류 안내.
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        exitPage(TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg, ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
                    }
                    break;
                }
            }
        });
        rmtViewModel.getRES_RMT_1004().observe(this, result -> {
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    RMT_1004.Response response = result.data;
                    if(adapter.setChckRslt(response)) {
                        // 데이터 설정이 성공한 경우 목록 갱신
                        adapter.notifyDataSetChanged();
                    }
                    break;
                }
                case ERROR: {
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(this, TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
                }
            }
        });
        rmtViewModel.getRES_RMT_1005().observe(this, result -> {
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    RMT_1005.Response response = result.data;

                    if (response != null && RETURN_CODE_SUCC.equals(response.getRtCd())) {
                        // 예약 취소 성공.
                        SnackBarUtil.show(this, getString(R.string.sm_remote01_msg_cancel_success));

                        // 데이터 재 조회.
                        rmtViewModel.reqRMT1003(new RMT_1003.Request(APPIAInfo.R_REMOTE01.getId(), vin));
                    } else {
                        // 예약 취소 실패.
                        SnackBarUtil.show(this, getString(R.string.sm_remote01_msg_cancel_fail));
                    }

                    break;
                }
                default: {
                    showProgressDialog(false);
                    String serverMsg = "";
                    try {
                        serverMsg = result.data.getRtMsg();
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        SnackBarUtil.show(this, TextUtils.isEmpty(serverMsg) ? getString(R.string.r_flaw06_p02_snackbar_1) : serverMsg);
                    }
                    break;
                }
            }
        });
    }


    @Override
    public void getDataFromIntent() {
        try {
            Intent getIntent = getIntent();
            vin = getIntent.getStringExtra(KeyNames.KEY_NAME_VIN);
            isShowRegistComplete = getIntent.getBooleanExtra(KeyNames.KEY_NAME_IS_SHOW_COMPLETE, false);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (TextUtils.isEmpty(vin))
                exitPage("차대정보가 존재하지 않습니다.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
        }
    }

    /****************************************************************************************************
     * Private Method
     ****************************************************************************************************/
    private void initView() {
        if (datas != null && datas.size() > 0) {
            ui.tvServiceRemoteNoData.setVisibility(View.GONE);
            ui.rvServiceRemoteList.setVisibility(View.VISIBLE);
            ui.tvServiceRemoteInfo.setVisibility(View.VISIBLE);
            adapter.setRows(datas);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            ui.rvServiceRemoteList.setLayoutManager(layoutManager);
            ui.rvServiceRemoteList.setAdapter(adapter);
        } else {
            ui.tvServiceRemoteNoData.setVisibility(View.VISIBLE);
            ui.rvServiceRemoteList.setVisibility(View.INVISIBLE);
            ui.tvServiceRemoteInfo.setVisibility(View.INVISIBLE);
        }
    }
} // end of class ServiceRemoteListActivity
