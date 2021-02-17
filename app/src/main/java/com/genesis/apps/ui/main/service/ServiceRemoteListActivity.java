package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.RMT_1003;
import com.genesis.apps.comm.model.api.gra.RMT_1005;
import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.RemoteHistoryVO;
import com.genesis.apps.comm.util.SnackBarUtil;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.viewmodel.RMTViewModel;
import com.genesis.apps.databinding.ActivityServiceRemoteListBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.google.gson.Gson;

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

    private ArrayList<RemoteHistoryVO> datas = new ArrayList<>();
    private String vin;

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
    }

    /****************************************************************************************************
     * Override Method - Event
     ****************************************************************************************************/
    @Override
    public void onClickCommon(View v) {
        int id = v.getId();
        Object tag = v.getTag();
        if (id == R.id.tv_service_remote_cancel_btn && tag instanceof RemoteHistoryVO) {
            // 예약 취소 버튼.
            RemoteHistoryVO vo = (RemoteHistoryVO) tag;
            rmtViewModel.reqRMT1005(new RMT_1005.Request(APPIAInfo.R_REMOTE01.getId(), vo.getTmpAcptCd(), vo.getRcptCd()));
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
                    exitPage(getString(R.string.r_flaw06_p02_snackbar_1), ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
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
                case ERROR: {
                    showProgressDialog(false);
                    SnackBarUtil.show(this, getString(R.string.r_flaw06_p02_snackbar_1));
                    break;
                }
            }
        });
    }

    @Override
    public void getDataFromIntent() {
        try {
            vin = getIntent().getStringExtra(KeyNames.KEY_NAME_VIN);
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

            ServiceRemoteListAdapter adapter = new ServiceRemoteListAdapter(onSingleClickListener);
            adapter.setRows(datas);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            ui.rvServiceRemoteList.setLayoutManager(layoutManager);
            ui.rvServiceRemoteList.setAdapter(adapter);
        } else {
            ui.tvServiceRemoteNoData.setVisibility(View.VISIBLE);
            ui.rvServiceRemoteList.setVisibility(View.INVISIBLE);
        }
    }
} // end of class ServiceRemoteListActivity
