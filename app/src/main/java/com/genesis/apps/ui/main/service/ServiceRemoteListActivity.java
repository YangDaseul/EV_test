package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.RMT_1003;
import com.genesis.apps.comm.viewmodel.RMTViewModel;
import com.genesis.apps.databinding.ActivityServiceRemoteListBinding;
import com.genesis.apps.ui.common.activity.SubActivity;

/**
 * Class Name : ServiceRemoteListActivity
 *
 * @author Ki-man Kim
 * @since 2020-12-21
 */
public class ServiceRemoteListActivity extends SubActivity<ActivityServiceRemoteListBinding> {
    private RMTViewModel rmtViewModel;

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

        rmtViewModel.reqRMT1003(new RMT_1003.Request(APPIAInfo.R_REMOTE01.getId()));
    }

    /****************************************************************************************************
     * Override Method - Event
     ****************************************************************************************************/
    @Override
    public void onClickCommon(View v) {

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
            Log.d("FID", "test :: getRES_RMT_1003 :: status=" + result.status);
            Log.d("FID", "test :: data=" + result.data);
            switch (result.status) {
                case LOADING: {
                    break;
                }
                case SUCCESS: {
                    break;
                }
                case ERROR: {
                    break;
                }
            }
        });
    }

    @Override
    public void getDataFromIntent() {

    }
    /****************************************************************************************************
     * Private Method
     ****************************************************************************************************/
} // end of class ServiceRemoteListActivity
