package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.hybrid.MyWebViewFrament;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.MYP_8001;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.ui.common.activity.WebviewActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * 앱 이용약관 : 1000
 * 개인정보처리방침 : 2000
 * 개인정보 수집/이용 : 3000
 * 광고성 정보 수신동의 : 4000
 * 제네시스 멤버십 가입 약관  : 5000
 */
public class MyGTermsActivity extends WebviewActivity {
    public static final String TERMS_CODE="termsCd";
    public static final String TERMS_1000="1000";
    public static final String TERMS_2000="2000";
    public static final String TERMS_3000="3000";
    public static final String TERMS_4000="4000";
    public static final String TERMS_5000="5000";
    public static final String TERMS_6000="6000"; //오픈소스 라이선스
    private String termsCd;
    private int titleId;
    private MYPViewModel mypViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        setViewModel();
        setObserver();
        String menuId="";
        switch (termsCd){
            case TERMS_1000:
                titleId = R.string.title_terms_1;
                menuId = APPIAInfo.MG_MENU01.getId();
                break;
            case TERMS_2000:
                titleId = R.string.title_terms_2;
                menuId = APPIAInfo.MG_MENU02.getId();
                break;
            case TERMS_3000:
                titleId = R.string.title_terms_3;
                break;
            case TERMS_4000:
                titleId = R.string.title_terms_4;
                break;
            case TERMS_5000:
                titleId = R.string.title_terms_5;
                break;
            case TERMS_6000:
                titleId = R.string.title_terms_6;
                break;
        }
        ui.setValue(getString(titleId));

        if(!termsCd.equalsIgnoreCase(TERMS_6000)) {
            mypViewModel.getRES_MYP_8001().observe(this, responseNetUI -> {
                switch (responseNetUI.status) {
                    case LOADING:
                        showProgressDialog(true);
                        break;
                    case SUCCESS:
                        showProgressDialog(false);
                        if (responseNetUI.data != null&&responseNetUI.data.getTermList()!=null&&responseNetUI.data.getTermList().size()>0) {
//                            loadTerms(responseNetUI.data.getTermList().get(0)); <---2020-10-26 검수용 버전으로 임시 삭선처리됨
                            initWebview(responseNetUI.data.getTermList().get(0).getTermCont());//임시코드
                            return;
                        }
                    default:
                        showProgressDialog(false);
                        break;
                }
            });
            //TODO LOADING에 대한 처리 해야함
            mypViewModel.reqMYP8001(new MYP_8001.Request(menuId, termsCd));
        }else{
            loadTerms(new TermVO("",TERMS_6000,getString(R.string.title_terms_6),getStringFromAssetsFile(),""));
        }
    }

//    private void loadTerms(TermVO data){
//        if(data!=null&& !TextUtils.isEmpty(data.getTermCont())){
//            Bundle bundle = new Bundle();
//            bundle.putString(WebViewFragment.EXTRA_HTML_BODY, data.getTermCont());
//
//            MyWebViewFrament fragment = new MyWebViewFrament();
//            fragment.setArguments(bundle);
//            fragment.setWebViewListener(webViewListener);
//
//            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//            ft.add(R.id.fm_holder, fragment);
//            ft.commitAllowingStateLoss();
//        }
//
//    }


    private MyWebViewFrament.WebViewListener webViewListener = new MyWebViewFrament.WebViewListener() {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            return false;
        }
        @Override
        public void onPageFinished(String url) { }
        @Override
        public boolean onBackPressed() {
            return false;
        }
        @Override
        public void onCloseWindow(WebView window) { }
    };


    /**
     * @brief 오픈소스 라이선스와 같은 html 파일을 asset 폴더에서 (local) 로드 시 사용
     * @return
     */
    private String getStringFromAssetsFile(){
        StringBuilder sb = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(getAssets().open(termsCd+".html"), StandardCharsets.UTF_8 ))){
            String str;
            while ((str = br.readLine()) != null) {
                sb.append(str);
            }
        } catch (IOException e) {
            sb=null;
        }

        return sb!=null?sb.toString():"";
    }

    @Override
    public void getDataFromIntent() {
        termsCd = getIntent().getStringExtra(TERMS_CODE);
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
        mypViewModel= new ViewModelProvider(this).get(MYPViewModel.class);
    }

    @Override
    public void setObserver() {

    }
}
