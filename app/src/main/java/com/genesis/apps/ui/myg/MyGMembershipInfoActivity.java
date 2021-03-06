package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.text.TextUtils;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.MYP_2004;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.ui.common.activity.TermActivity;

public class MyGMembershipInfoActivity extends TermActivity {
    private MYPViewModel mypViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        setViewModel();
        setObserver();
        mypViewModel.reqMYP2004(new MYP_2004.Request(APPIAInfo.MG_MEMBER01_P02.getId()));
    }

    @Override
    public void getDataFromIntent() {

    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        mypViewModel= new ViewModelProvider(this).get(MYPViewModel.class);
    }

    @Override
    public void setObserver() {
        mypViewModel.getRES_MYP_2004().observe(this, result -> {
            switch (result.status) {
                case LOADING:
                    showProgressDialog(true);
                    break;
                case SUCCESS:
                    if (result.data != null&&!TextUtils.isEmpty(result.data.getCont())) {
                        loadTerms(new TermVO("","","",result.data.getCont(),""));

                        if(!TextUtils.isEmpty(result.data.getTtl())){
                            ui.lTitle.setValue(result.data.getTtl());
                        }

                        showProgressDialog(false);
                        break;
                    }
                default:
                    showProgressDialog(false);
                    break;
            }
        });
    }

//        extends SubActivity<ActivityMygMembershipInfoBinding> {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_myg_membership_info);
//        getDataFromIntent();
//        setViewModel();
//        setObserver();
//    }
//
//    @Override
//    public void onClickCommon(View v) {
//        switch (v.getId()){
//            case R.id.btn_call:
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(WebView.SCHEME_TEL+ui.btnCall.getTag().toString()));
//                startActivity(intent);
//                break;
//        }
//    }
//
//    @Override
//    public void setViewModel() {
//        ui.setActivity(this);
//    }
//
//    @Override
//    public void setObserver() {
//
//    }
//
//    @Override
//    public void getDataFromIntent() {
//
//    }
}
