package com.genesis.apps.comm.model.constants;

public interface RoadWinInfo {
    String[] ROADWIN_INFO = {"https://api.roadwin.shop/g", "https://prod.roadwin.net/h"};
    String ROADWIN_URL = ROADWIN_INFO[GAInfo.SERVER_TYPE];
    String ROADWIN_PAYMENT = "/order?id=";
}
