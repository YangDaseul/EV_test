package com.genesis.apps.ui.myg;

import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;

import androidx.lifecycle.ViewModelProvider;

import com.genesis.apps.R;
import com.genesis.apps.comm.hybrid.MyWebViewFrament;
import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.api.gra.MYP_8001;
import com.genesis.apps.comm.viewmodel.MYPViewModel;
import com.genesis.apps.ui.common.activity.WebviewActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import static com.genesis.apps.comm.model.constants.VariableType.TERM_SERVICE_JOIN_GRA0001;
import static com.genesis.apps.comm.model.constants.VariableType.TERM_SERVICE_JOIN_GRA0002;
import static com.genesis.apps.comm.model.constants.VariableType.TERM_SERVICE_JOIN_GRA0003;
import static com.genesis.apps.comm.model.constants.VariableType.TERM_SERVICE_JOIN_GRA0004;
import static com.genesis.apps.comm.model.constants.VariableType.TERM_SERVICE_JOIN_GRA0005;

/**
 * 앱 이용약관 : 1000
 * 개인정보처리방침 : 2000
 * 개인정보 수집/이용 : 3000
 * 광고성 정보 수신동의 : 4000
 * 제네시스 멤버십 가입 약관  : 5000
 */
public class MyGTermsActivity extends WebviewActivity {
    public static final String TERMS_CODE="termsCd";
    public static final String TERMS_6000="6000"; //오픈소스 라이선스
    public String termsCd;
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
            case TERM_SERVICE_JOIN_GRA0001:
                titleId = R.string.title_terms_1;
                menuId = APPIAInfo.MG_MENU01.getId();
                break;
            case TERM_SERVICE_JOIN_GRA0002:
                titleId = R.string.title_terms_2;
                menuId = APPIAInfo.MG_MENU02.getId();
                break;
            case TERM_SERVICE_JOIN_GRA0003:
                titleId = R.string.title_terms_3;
                break;
            case TERM_SERVICE_JOIN_GRA0005:
                titleId = R.string.title_terms_4;
                break;
            case TERM_SERVICE_JOIN_GRA0004:
                titleId = R.string.title_terms_5;
                break;
            default:
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
                            loadTerms(responseNetUI.data.getTermList().get(0));
                            return;
                        }
                    default:
                        showProgressDialog(false);
                        break;
                }
            });
            mypViewModel.reqMYP8001(new MYP_8001.Request(menuId, termsCd));
        }else{
//            loadTerms(new TermVO("",TERMS_6000,getString(R.string.title_terms_6),getStringFromAssetsFile(),""));
            initWebview("file:///android_asset/6000.html");
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
        try {
            termsCd = getIntent().getStringExtra(TERMS_CODE);
        }catch (Exception e){

        }finally {
            if(TextUtils.isEmpty(termsCd)) termsCd = TERMS_6000;
        }
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
