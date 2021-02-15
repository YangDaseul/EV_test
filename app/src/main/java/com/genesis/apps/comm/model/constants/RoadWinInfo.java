package com.genesis.apps.comm.model.constants;

public interface RoadWinInfo {
    //대리운전 검증계 링크는 https://prod.roadwin.net/h 이나 실제 검증계에서 운영계 주소로 테스트 했기 때문에 의미 없어서 제거 됨
    String[] ROADWIN_INFO = {"https://api.roadwin.shop/g", "https://api.roadwin.shop/g"};
    String ROADWIN_URL = ROADWIN_INFO[GAInfo.SERVER_TYPE];
    String ROADWIN_PAYMENT = "/order?id=";
}
