package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.net.HttpRequest;

public enum APIInfo {

    GRA_CMN_0001("인트로", "CMN-0001", HttpRequest.METHOD_POST, "/graapi/cmn/intro.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_CMN_0002("컨텐츠다운로드", "CMN-0002", HttpRequest.METHOD_POST, "/graapi/cmn/contentDn.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_CMN_0003("서비스약관조회", "CMN-0003", HttpRequest.METHOD_POST, "/graapi/cmn/svcTerms.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_CMN_0004("날씨예보", "CMN-0004", HttpRequest.METHOD_POST, "/graapi/cmn/svcTerms.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),//TODO 날씨예보 주소 수정 필요

    GRA_MBR_0001("서비스가입요청", "MBR-0001", HttpRequest.METHOD_POST, "/graapi/mbr/svcJoin.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_LGN_0001("로그인+서비스 로그인/접속", "LGN-0001", HttpRequest.METHOD_POST, "/graapi/lgn/svcLogin.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_LGN_0002("로그인+Main 화면 주행거리", "LGN-0002", HttpRequest.METHOD_POST, "/graapi/lgn/mainDrivDistInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_LGN_0003("로그인+Main 화면 정비 현황", "LGN-0003", HttpRequest.METHOD_POST, "/graapi/lgn/mainMaintainInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_LGN_0004("PUSH ID 변경 요청", "LGN-0004", HttpRequest.METHOD_POST, "/graapi/lgn/updPushId.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_LGN_0005("로그인+날씨예보", "LGN-0005", HttpRequest.METHOD_POST, "/graapi/lgn/weatherInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_MYP_1003("MyG+ 블루멤버스 정보", "MYP-1003", HttpRequest.METHOD_POST, "/graapi/myg/blueMbr.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_MYP_0001("계정 정보 요청", "MYP-0001", HttpRequest.METHOD_POST, "/graapi/myp/acctInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_MYP_0004("마케팅 수신동의 변경 요청", "MYP-0004", HttpRequest.METHOD_POST, "/graapi/myp/mrktAgree.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_MYP_0005("휴대폰 번호 변경 요청", "MYP-0005", HttpRequest.METHOD_POST, "/graapi/myp/celphNoReg.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_MYP_8001("MyG+ 이용약관", "MYP-8001", HttpRequest.METHOD_POST, "/graapi/myp/trmsInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_MYP_8002("MyG+ 개인정보처리방침", "MYP-8002", HttpRequest.METHOD_POST, "/graapi/myp/privacyPolicy.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_MYP_8003("MyG+ 오픈소스라이센스", "MYP-8003", HttpRequest.METHOD_POST, "/graapi/myp/openLicense.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_MYP_8004("MyG+ 버전정보", "MYP-8004", HttpRequest.METHOD_POST, "/graapi/myp/verInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_NOT_0001("알림센터+알림목록 조회", "NOT-0001", HttpRequest.METHOD_POST, "/graapi/lgn/notiInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8);

    private String description;
    private String ifCd;
    private String reqType;
    private String URI;
    private String contentsType;
    private String charSet;

    APIInfo(String description, String ifCd, String reqType, String URI, String contentsType, String charSet) {
        this.description = description;
        this.ifCd = ifCd;
        this.reqType = reqType;
        this.URI = URI;
        this.contentsType = contentsType;
        this.charSet = charSet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIfCd() {
        return ifCd;
    }

    public void setIfCd(String ifCd) {
        this.ifCd = ifCd;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getContentsType() {
        return contentsType;
    }

    public void setContentsType(String contentsType) {
        this.contentsType = contentsType;
    }

    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }
//    public static APIInfo findCode(int code){
//        return Arrays.asList(APIInfo.values()).stream().filter(data->data.getCode()==code).findAny().orElse(REQ_CODE_DEFAULT);
//    }

}
