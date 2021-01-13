package com.genesis.apps.ui.myg;

import android.os.Bundle;

import com.genesis.apps.R;
import com.genesis.apps.ui.common.activity.WebviewActivity;

public class MyGMembershipUseCaseActivity extends WebviewActivity {
    private final String URL = "https://www.genesis.com/kr/ko/members/genesis-membership/membership-overview/benefit-service.html?anchorID=members_teb_01"; //사용처 안내 하드코딩 URL
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui.setValue(getString(R.string.mg_member01_5));

    }

    @Override
    public void getDataFromIntent() {
        url = URL;
    }

    @Override
    public void setViewModel() {
//        ui.setLifecycleOwner(this);
//        mypViewModel= new ViewModelProvider(this).get(MYPViewModel.class);
    }

    @Override
    public void setObserver() {
//        mypViewModel.getRES_MYP_2003().observe(this, result -> {
//            switch (result.status) {
//                case LOADING:
//                    showProgressDialog(true);
//                    break;
//                case SUCCESS:
//                    if (result.data != null&&!TextUtils.isEmpty(result.data.getCont())) {
//                        loadTerms(new TermVO("","","",result.data.getCont(),""));
//
//                        if(!TextUtils.isEmpty(result.data.getTtl())){
//                            ui.lTitle.setValue(result.data.getTtl());
//                        }
//
//                        showProgressDialog(false);
//                        break;
//                    }
//                default:
//                    showProgressDialog(false);
//                    break;
//            }
//        });
    }
}
