package com.genesis.apps.ui.main;

import android.os.Bundle;
import android.text.TextUtils;

import com.genesis.apps.comm.model.constants.KeyNames;
import com.genesis.apps.comm.model.constants.ResultCodes;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.ui.common.activity.HtmlActivity;

public class ServiceTermDetailActivity extends HtmlActivity {
    private TermVO termVO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getDataFromIntent();
        setViewModel();
        setObserver();
        loadTermVo(termVO);

//        //todo 2020-10-26 임시 조치 코드...... 무조건 제거 및 기존 코드 테스트 필요
//        switch (termVO.getTermCd()){
//            case "2000":
//                loadTerms("http://211.54.75.18:7070/genesis/app/html/terms_GS01.html");
//                break;
//            case "2001":
//                loadTerms("http://211.54.75.18:7070/genesis/app/html/terms_GS02.html");
//                break;
//            case "2002":
//                loadTerms("http://211.54.75.18:7070/genesis/app/html/terms_GS03.html");
//                break;
//            case "2003":
//                loadTerms("http://211.54.75.18:7070/genesis/app/html/terms_GS04.html");
//                break;
//            case "2004":
//                loadTerms("http://211.54.75.18:7070/genesis/app/html/terms_GS05.html");
//                break;
//            case "2005":
//                loadTerms("http://211.54.75.18:7070/genesis/app/html/terms_GS06.html");
//                break;
//            case "2006":
//                loadTerms("http://211.54.75.18:7070/genesis/app/html/terms_GS07.html");
//                break;
//            case "3000":
//                loadTerms("http://211.54.75.18:7070/genesis/app/html/terms_HOB01.html");
//                break;
//            case "3001":
//                loadTerms("http://211.54.75.18:7070/genesis/app/html/terms_HOB02.html");
//                break;
//            case "3002":
//                loadTerms("http://211.54.75.18:7070/genesis/app/html/terms_HOB03.html");
//                break;
//            case "3003":
//                loadTerms("http://211.54.75.18:7070/genesis/app/html/terms_HOB04.html");
//                break;
//            case "3004":
//                loadTerms("http://211.54.75.18:7070/genesis/app/html/terms_HOB05.html");
//                break;
//        }

//            loadTerms(new TermVO("",TERMS_6000,getString(R.string.title_terms_6),getStringFromAssetsFile(),""));
    }

    @Override
    public void getDataFromIntent() {
        String title = "";
        try {
            termVO = (TermVO)getIntent().getSerializableExtra(VariableType.KEY_NAME_TERM_VO);
            title = getIntent().getStringExtra(KeyNames.KEY_NAME_MAP_SEARCH_TITLE_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (termVO == null
                    || TextUtils.isEmpty(termVO.getTermCd())
                    || TextUtils.isEmpty(termVO.getTermVer())) {
                exitPage("약관정보가 존재하지 않습니다.\n잠시후 다시 시도해 주십시오.", ResultCodes.REQ_CODE_EMPTY_INTENT.getCode());
            }else{
                if(TextUtils.isEmpty(title)) {
                    ui.setValue("자세히 보기");
                }else{
                    ui.setValue(title);
                }
            }
        }
    }

    @Override
    public void setViewModel() {
        ui.setLifecycleOwner(this);
    }

    @Override
    public void setObserver() {
    }
}
