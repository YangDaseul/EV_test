package com.genesis.apps.ui.myg;

import android.os.Bundle;

import static com.genesis.apps.comm.model.constants.VariableType.TERM_SERVICE_JOIN_GRA0013;

/**
 * 앱 이용약관 : 1000
 * 개인정보처리방침 : 2000
 * 개인정보 수집/이용 : 3000
 * 광고성 정보 수신동의 : 4000
 * 제네시스 멤버십 가입 약관  : 5000
 */
public class MyGTerms0013Activity extends MyGTermsActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void getDataFromIntent() {
        termsCd = TERM_SERVICE_JOIN_GRA0013;
    }

}
