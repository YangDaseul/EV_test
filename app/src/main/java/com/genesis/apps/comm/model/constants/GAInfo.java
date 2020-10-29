package com.genesis.apps.comm.model.constants;

public interface GAInfo {
    int SERVER_TYPE = 0;
    int CONNECTION_TIME_OUT = 10 * 1000;
    int READ_TIME_OUT = 10 * 1000;
    String GRANT_TYPE_AUTHORIZATION_CODE = "authorization_code";
    String GRANT_TYPE_REFRESH_TOKEN = "refresh_token";
    String GRANT_TYPE_DELETE = "delete";
    String LANG_KR = "kr";
    String HTTP_HEADER_NAME = "Authorization";
    String HTTP_HEADER_VALUE = "Bearer ";
    String TAG_MSG_BODY = "recv body [%s]";
    String TAG_MSG_LOGININFO = "loadLoginInfo() loginInfo";
    String[][] SERVER_INFOS = {
            //00   업체구분
            //01 , ccSP URL, CallBack URL, Client ID, Client Secret
            //05 , GA URL, CallBack URL, redirect URL

            // 0: 제네시스 검증계 접속 (ccSP:검증, DKC:검증, GA:검증)
            {
                    "/G/"
                    , "https://stg-kr-ccapi.genesis.com:8081/api/v", "/api/authorize/ccsp/redirect", "a18aa0f3-11a6-45ba-ba4c-52c54e68ab62", "Jp6nvjppZ8RFX7A4sRxFmU4U5PCT3y9KdvMtPjDgPPTLAy2g"
                    , "https://t-accounts.genesis.com", "/api/cmmn/message", "/api/test/redirect.do"
            }
            // 1: 제네시스 운영계 접속 (ccSP:운영, DKC:운영, GA:운영)
            , {
            "/G/"
            , "https://prd-kr-ccapi.genesis.com:8081/api/v1", "/api/authorize/ccsp/redirect", "f66fc416-3871-42c4-809b-abf780bee474", "o6KwPbwlFUP7xV4hAJ6fZlUsxK1Cl3OBtCPMSXVEUW0R1Qn4"
            , "https://accounts.genesis.com", "/api/cmmn/message", "/api/test/redirect.do"
    }
            // 2: 제네시스 개발계 접속 (ccSP:개발, DKC:개발, GA:개발)
            , {
            "/G/"
            , "http://genesis.connected-car.io/api/v1", "/api/authorize/ccsp/redirect", "b5c4787e-0f2c-4529-a247-ab946678c5e5", "secret"
            , "https://d-accounts.genesis.com", "/api/cmmn/message", "/api/test/redirect.do"
    }
    };

    String CCSP_URL = SERVER_INFOS[SERVER_TYPE][1];
    String CCSP_CLIENT_ID = SERVER_INFOS[SERVER_TYPE][3];
    String CCSP_SECRET = SERVER_INFOS[SERVER_TYPE][4];
    String GA_URL = SERVER_INFOS[SERVER_TYPE][5];
    String GA_CALLBACK_URL = SERVER_INFOS[SERVER_TYPE][5] + SERVER_INFOS[SERVER_TYPE][6];
    String GA_REDIRECT_URL = SERVER_INFOS[SERVER_TYPE][5] + SERVER_INFOS[SERVER_TYPE][7];

}
