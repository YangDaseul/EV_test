package com.genesis.apps.comm.model.constants;

public interface TsAuthInfo {
    String[] TS_AUTH_INFO = {"http://101.1.58.24/wsvc/kr/front/biz/smartaccess", "https://carlnk.hmc.co.kr/wsvc/kr/front/biz/smartaccess"};
    String TS_AUTH_URL = TS_AUTH_INFO[GAInfo.SERVER_TYPE];
}
