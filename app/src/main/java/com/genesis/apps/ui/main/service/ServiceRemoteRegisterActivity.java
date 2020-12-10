package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.api.gra.RMT_1001;
import com.genesis.apps.comm.model.api.gra.SOS_1001;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.viewmodel.RMTViewModel;
import com.genesis.apps.comm.viewmodel.SOSViewModel;
import com.genesis.apps.databinding.ActivityServiceRemoteRegisterBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.genesis.apps.ui.common.dialog.middle.MiddleDialog;

import java.util.concurrent.ExecutionException;

/**
 * Created by Ki-man, Kim on 12/10/20
 */
public class ServiceRemoteRegisterActivity extends SubActivity<ActivityServiceRemoteRegisterBinding> {

    private RMTViewModel rmtViewModel;
    private SOSViewModel sosViewModel;


    /****************************************************************************************************
     * Override Method - LifeCycle
     ****************************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_remote_register);
        getDataFromIntent();
        setViewModel();
        setObserver();
        initView();

        try {
            rmtViewModel.reqRMT1001(new RMT_1001.Request(APPIAInfo.R_REMOTE01.getId(), rmtViewModel.getMainVehicle().getVin()));
        } catch (ExecutionException ee) {
            // TODO : 차대번호 조회 오류 처리 필요.
        } catch (InterruptedException ie) {
            // TODO : 차대번호 조회 오류 처리 필요.
        }
    }

    /****************************************************************************************************
     * Override Method - Event
     ****************************************************************************************************/
    @Override
    public void onClickCommon(View v) {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        rmtViewModel = new ViewModelProvider(this).get(RMTViewModel.class);
        sosViewModel = new ViewModelProvider(this).get(SOSViewModel.class);
    }

    @Override
    public void setObserver() {
        rmtViewModel.getRES_RMT_1001().observe(this, result -> {
            Log.d("FID", "test :: getRES_RMT_1001 :: status=" + result.status);
            switch (result.status) {
                case LOADING: {
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);
                    RMT_1001.Response response = result.data;

                    if (response != null) {
                        Log.d("FID", "test :: getRES_RMT_1001 :: rtCd=" + response.getRtCd());
                        Log.d("FID", "test :: getRES_RMT_1001 :: rmtExitYn=" + response.getRmtExitYn());
                        Log.d("FID", "test :: getRES_RMT_1001 :: message=" + response.getRtMsg());
                        if (BaseResponse.RETURN_CODE_SUCC.equals(response.getRtCd())) {
                            // 성공.
                        } else {
                            // 실패.
                            // 사유에 대해 서버에서 온 메시지를 표시.
                            MiddleDialog.dialogServiceRemoteRegisterErr(this, response.getRtMsg(), () -> finish());
                        }
                        if ("N".equalsIgnoreCase(response.getRmtExitYn())) {
                            // 신청건이 없는 경우.
                        } else {
                            // 신청건이 있는 경우.
                            //
                        }
                    } else {
                        // TODO : 조회된 데이터가 없어 예외처리 필요.
                    }
                    break;
                }
                case ERROR: {
                    showProgressDialog(false);
                    break;
                }
            }
        });
    }

    @Override
    public void getDataFromIntent() {

    }

    /****************************************************************************************************
     * Method - Private
     ****************************************************************************************************/
    private void initView() {

    }
} // end of class ServiceREmoteRegisterActivity
