package com.genesis.apps.ui.main.service;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.RMT_1003;
import com.genesis.apps.comm.model.vo.RemoteHistoryVO;
import com.genesis.apps.comm.viewmodel.RMTViewModel;
import com.genesis.apps.databinding.ActivityServiceRemoteListBinding;
import com.genesis.apps.ui.common.activity.SubActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Class Name : ServiceRemoteListActivity
 *
 * @author Ki-man Kim
 * @since 2020-12-21
 */
public class ServiceRemoteListActivity extends SubActivity<ActivityServiceRemoteListBinding> {
    private RMTViewModel rmtViewModel;

    private ArrayList<RemoteHistoryVO> datas = new ArrayList<>();

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
                    showProgressDialog(true);
                    break;
                }
                case SUCCESS: {
                    showProgressDialog(false);

                    String dummyData = "{\n" +
                            "  \"rtCd\": \"0000\",\n" +
                            "  \"rtMsg\": \"Success\",\n" +
                            "  \"aplyList\": [\n" +
                            "    {\n" +
                            "      \"dvcId\": \"idtest11\",\n" +
                            "      \"tmpAcptCd\": \"01039102839\",\n" +
                            "      \"rcptCd\": \"0102010203\",\n" +
                            "      \"fltCd\": \"080204\",\n" +
                            "      \"fltStmt\": \"테스트 고장 문구\",\n" +
                            "      \"rsrvDtm\": \"20201222113122\",\n" +
                            "      \"aplyStusCd\": \"F\"\n" +
                            "    }\n" +
                            "  ]\n" +
                            "}";
                    RMT_1003.Response response = new Gson().fromJson(dummyData, RMT_1003.Response.class);
                    datas.clear();
                    datas.addAll(response.getAplyList());
                    initView();
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
     * Private Method
     ****************************************************************************************************/
    private void initView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        ui.rvServiceRemoteList.setLayoutManager(layoutManager);
        ui.rvServiceRemoteList.setAdapter(new ServiceRemoteListAdapter(datas));
    }
} // end of class ServiceRemoteListActivity
