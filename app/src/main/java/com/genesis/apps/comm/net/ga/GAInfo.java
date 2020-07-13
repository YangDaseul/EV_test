package com.genesis.apps.comm.net.ga;

public interface GAInfo {

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
    
    String[][] ServerInfos = {
            //00   업체구분
            //01 , ccSP URL, CallBack URL, Client ID, Client Secret
            //05 , GA URL, CallBack URL, redirect URL

            // 0: 제네시스 검증계 접속 (ccSP:검증, DKC:검증, GA:검증)
            {
                    "/G/"
                    , "https://stg-kr-ccapi.genesis.com:8081", "/api/authorize/ccsp/redirect", "37c04bd3-2929-49b2-a555-0c174085760e", "secret"
                    , "https://t-accounts.genesis.com", "/api/cmmn/message", "/api/test/redirect.do"
            }
            // 1: 제네시스 운영계 접속 (ccSP:운영, DKC:운영, GA:운영)
            , {
            "/G/"
            , "https://prd-kr-ccapi.genesis.com:8081", "/api/authorize/ccsp/redirect", "37c04bd3-2929-49b2-a555-0c174085760e", "a3D0iTYUqxpK6qJhE0tVDFrWXJd2NFeJEeygrq7zzYyh5K1s"
            , "https://accounts.genesis.com", "/api/cmmn/message", "/api/test/redirect.do"
            }
            // 2: 제네시스 개발계 접속 (ccSP:개발, DKC:개발, GA:개발)
            , {
            "/G/"
            , "http://genesis.connected-car.io", "/api/authorize/ccsp/redirect", "37c04bd3-2929-49b2-a555-0c174085760e", "secret"
            , "https://d-accounts.genesis.com", "/api/cmmn/message", "/api/test/redirect.do"
        }
    };


}
