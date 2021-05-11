package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.gra.EPT_1001;
import com.genesis.apps.comm.model.api.gra.EPT_1002;
import com.genesis.apps.comm.model.api.gra.EPT_1003;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

/**
 * @author Ki-man Kim
 */
public class EPTRepo {
    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<EPT_1001.Response>> RES_EPT_1001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<EPT_1002.Response>> RES_EPT_1002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<EPT_1003.Response>> RES_EPT_1003 = new MutableLiveData<>();

    @Inject
    public EPTRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<EPT_1001.Response>> REQ_EPT_1001(final EPT_1001.Request reqData) {
        RES_EPT_1001.setValue(NetUIResponse.loading(null));
//        String dummyData = "{\"rtCd\":\"0000\",\"rtMsg\":\"test message\",\"chgList\":[{\"spid\":\"11\",\"csid\":\"22222222\",\"espid\":\"333\",\"ecsid\":\"1234567890123456789012345678901234560\",\"csnm\":\"현대EV스테이션 현대EV스테이션 현대EV스테이션 강남\",\"dist\":\"1.1\",\"lat\":\"37.480511\",\"lot\":\"126.883668\",\"daddr\":\"서울특별시 금천구 가산동 602-13\",\"addrDtl\":\"우림라이온스밸리 12층\",\"spnm\":\"운영사업자명\",\"useYn\":\"Y\",\"useTime\":\"12:00 ~ 13:00\",\"spcall\":\"00-0000-0000\",\"reservYn\":\"Y\",\"gcpYn\":\"Y\",\"genYn\":\"Y\",\"superSpeedCnt\":\"11\",\"highSpeedCnt\":\"2\",\"slowSpeedCnt\":\"1\"},{\"spid\":\"11\",\"csid\":\"22222223\",\"espid\":\"333\",\"ecsid\":\"1234567890123456789012345678901234561\",\"csnm\":\"현대EV스테이션 1\",\"dist\":\"1.1\",\"lat\":\"37.482991\",\"lot\":\"126.881369\",\"daddr\":\"서울특별시 금천구 470-5\",\"addrDtl\":\"에이스테크\",\"spnm\":\"운영사업자명\",\"useYn\":\"Y\",\"useTime\":\"12:00 ~ 13:00\",\"spcall\":\"00-0000-0000\",\"reservYn\":\"Y\",\"gcpYn\":\"Y\",\"genYn\":\"Y\",\"superSpeedCnt\":\"11\",\"highSpeedCnt\":\"2\",\"slowSpeedCnt\":\"0\"},{\"spid\":\"11\",\"csid\":\"22222223\",\"espid\":\"333\",\"ecsid\":\"1234567890123456789012345678901234562\",\"csnm\":\"현대EV스테이션 2\",\"dist\":\"11.1\",\"lat\":\"37.479533\",\"lot\":\"126.879282\",\"daddr\":\"서울특별시 금천구 가산동\",\"addrDtl\":\"\",\"spnm\":\"운영사업자명\",\"useYn\":\"Y\",\"useTime\":\"12:00 ~ 13:00\",\"spcall\":\"00-0000-0000\",\"reservYn\":\"Y\",\"gcpYn\":\"Y\",\"genYn\":\"Y\",\"superSpeedCnt\":\"11\",\"highSpeedCnt\":\"0\",\"slowSpeedCnt\":\"1\"},{\"spid\":\"11\",\"csid\":\"22222223\",\"espid\":\"333\",\"ecsid\":\"1234567890123456789012345678901234563\",\"csnm\":\"현대EV스테이션 3\",\"dist\":\"11.1\",\"lat\":\"37.477494\",\"lot\":\"126.885952\",\"daddr\":\"서울특별시 금천구 가산동\",\"addrDtl\":\"\",\"spnm\":\"운영사업자명\",\"useYn\":\"Y\",\"useTime\":\"12:00 ~ 13:00\",\"spcall\":\"00-0000-0000\",\"reservYn\":\"N\",\"gcpYn\":\"Y\",\"genYn\":\"Y\",\"superSpeedCnt\":\"11\",\"highSpeedCnt\":\"0\",\"slowSpeedCnt\":\"1\"}]}";
//        RES_EPT_1001.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, EPT_1001.Response.class)));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_EPT_1001.setValue(NetUIResponse.success(new Gson().fromJson(object, EPT_1001.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_EPT_1001.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_EPT_1001.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_EPT_1001, reqData);


        return RES_EPT_1001;
    }

    public MutableLiveData<NetUIResponse<EPT_1002.Response>> REQ_EPT_1002(final EPT_1002.Request reqData) {
        RES_EPT_1002.setValue(NetUIResponse.loading(null));
//        String dummyData = "{\"rtCd\":\"0000\",\"rtMsg\":\"testmsg\",\"chgInfo\":{\"spid\":\"11\",\"csid\":\"12345678\",\"espid\":\"123\",\"ecsid\":\"1234567890123456789012345678901234567\",\"csnm\":\"충전소명\",\"dist\":\"11.11\",\"lat\":\"37.524683\",\"lot\":\"126.669469\",\"daddr\":\"주소\",\"addrDtl\":\"상세주소\",\"spnm\":\"운영사업자명\",\"useYn\":\"Y\",\"useTime\":\"이용가능시간\",\"spcall\":\"00-0000-0000\",\"reservYn\":\"Y\",\"gcpYn\":\"Y\",\"genYn\":\"Y\",\"superSpeedCnt\":\"1\",\"highSpeedCnt\":\"11\",\"slowSpeedCnt\":\"3\"},\"chgrList\":[{\"cpid\":\"01\",\"cpnm\":\"충전기명\",\"chargeUcost\":\"10000\",\"chargeDiv\":\"SUPER\",\"useYn\":\"Y\",\"statusCd\":\"AVAILABLE\",\"reservYn\":\"Y\",\"payType\":[\"GCP\",\"STP\",\"CRT\"]},{\"cpid\":\"02\",\"cpnm\":\"충전기명\",\"chargeUcost\":\"10000\",\"chargeDiv\":\"SUPER\",\"useYn\":\"Y\",\"statusCd\":\"UNKNOWN\",\"reservYn\":\"Y\",\"payType\":[\"GCP\",\"STP\",\"CRT\"]},{\"cpid\":\"03\",\"cpnm\":\"충전기명\",\"chargeUcost\":\"10000\",\"chargeDiv\":\"SUPER\",\"useYn\":\"Y\",\"statusCd\":\"CHARGING\",\"reservYn\":\"Y\",\"payType\":[\"GCP\",\"STP\",\"CRT\"]},{\"cpid\":\"04\",\"cpnm\":\"충전기명\",\"chargeUcost\":\"10000\",\"chargeDiv\":\"SUPER\",\"useYn\":\"Y\",\"statusCd\":\"OUTOFORDER\",\"reservYn\":\"Y\",\"payType\":[\"GCP\",\"STP\",\"CRT\"]},{\"cpid\":\"05\",\"cpnm\":\"충전기명\",\"chargeUcost\":\"10000\",\"chargeDiv\":\"SUPER\",\"useYn\":\"Y\",\"statusCd\":\"COM_ERROR\",\"reservYn\":\"Y\",\"payType\":[\"GCP\",\"STP\",\"CRT\"]},{\"cpid\":\"06\",\"cpnm\":\"충전기명\",\"chargeUcost\":\"10000\",\"chargeDiv\":\"SUPER\",\"useYn\":\"Y\",\"statusCd\":\"DISCONNECTION\",\"reservYn\":\"Y\",\"payType\":[\"GCP\",\"STP\",\"CRT\"]},{\"cpid\":\"07\",\"cpnm\":\"충전기명\",\"chargeUcost\":\"10000\",\"chargeDiv\":\"SUPER\",\"useYn\":\"Y\",\"statusCd\":\"CHARGED\",\"reservYn\":\"Y\",\"payType\":[\"GCP\",\"STP\",\"CRT\"]},{\"cpid\":\"08\",\"cpnm\":\"충전기명\",\"chargeUcost\":\"10000\",\"chargeDiv\":\"SUPER\",\"useYn\":\"Y\",\"statusCd\":\"PLANNED\",\"reservYn\":\"Y\",\"payType\":[\"GCP\",\"STP\",\"CRT\"]},{\"cpid\":\"09\",\"cpnm\":\"충전기명\",\"chargeUcost\":\"10000\",\"chargeDiv\":\"SUPER\",\"useYn\":\"Y\",\"statusCd\":\"RESERVED\",\"reservYn\":\"Y\",\"payType\":[\"GCP\",\"STP\",\"CRT\"]},{\"cpid\":\"091\",\"cpnm\":\"충전기명\",\"chargeUcost\":\"10000\",\"chargeDiv\":\"SUPER\",\"useYn\":\"N\",\"statusCd\":\"RESERVED\",\"reservYn\":\"Y\",\"payType\":[\"GCP\",\"STP\",\"CRT\"]},{\"cpid\":\"092\",\"cpnm\":\"충전기명\",\"chargeUcost\":\"10000\",\"chargeDiv\":\"SUPER\",\"useYn\":\"Y\",\"statusCd\":\"RESERVED\",\"reservYn\":\"N\",\"payType\":[\"GCP\",\"STP\",\"CRT\"]},{\"cpid\":\"093\",\"cpnm\":\"충전기명\",\"chargeUcost\":\"10000\",\"chargeDiv\":\"SUPER\",\"useYn\":\"N\",\"statusCd\":\"RESERVED\",\"reservYn\":\"N\",\"payType\":[\"GCP\",\"STP\",\"CRT\"]},{\"cpid\":\"10\",\"cpnm\":\"충전기명\",\"chargeUcost\":\"\",\"chargeDiv\":\"SUPER\",\"useYn\":\"Y\",\"statusCd\":\"RESERVED\",\"reservYn\":\"Y\",\"payType\":[\"GCP\",\"STP\",\"CRT\"]},{\"cpid\":\"11\",\"cpnm\":\"충전기명\",\"chargeUcost\":\"\",\"chargeDiv\":\"HIGH\",\"useYn\":\"Y\",\"statusCd\":\"RESERVED\",\"reservYn\":\"Y\",\"payType\":[\"GCP\",\"STP\",\"CRT\"]},{\"cpid\":\"12\",\"cpnm\":\"충전기명\",\"chargeUcost\":\"\",\"chargeDiv\":\"SLOW\",\"useYn\":\"Y\",\"statusCd\":\"RESERVED\",\"reservYn\":\"Y\",\"payType\":[\"GCP\",\"STP\",\"CRT\"]}]}";
//        RES_EPT_1002.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, EPT_1002.Response.class)));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_EPT_1002.setValue(NetUIResponse.success(new Gson().fromJson(object, EPT_1002.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_EPT_1002.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_EPT_1002.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_EPT_1002, reqData);

        return RES_EPT_1002;
    }

    public MutableLiveData<NetUIResponse<EPT_1003.Response>> REQ_EPT_1003(final EPT_1003.Request reqData) {
        RES_EPT_1003.setValue(NetUIResponse.loading(null));
//        String dummyData = "{\"rtCd\":\"0000\",\"rtMsg\":\"test msg\",\"revwList\":[{\"rgstDtm\":\"20210506131101\",\"uid\":\"testi*\",\"uNm\":\"김기*\",\"starPoint\":\"0.0\",\"contents\":\"전기차 충전기라고는 주민센터에만 있는 줄 알았는데 이렇게 근처에 있어서 좋아요!\"},{\"rgstDtm\":\"20210506131101\",\"uid\":\"testi*\",\"uNm\":\"김기*\",\"starPoint\":\"1.4\",\"contents\":\"전기차 충전기라고는 주민센터에만 있는 줄 알았는데 이렇게 근처에 있어서 좋아요!\"},{\"rgstDtm\":\"20210506131101\",\"uid\":\"testi*\",\"uNm\":\"김기*\",\"starPoint\":\"2.4\",\"contents\":\"전기차 충전기라고는 주민센터에만 있는 줄 알았는데 이렇게 근처에 있어서 좋아요!\"},{\"rgstDtm\":\"20210506131101\",\"uid\":\"testi*\",\"uNm\":\"김기*\",\"starPoint\":\"3.5\",\"contents\":\"전기차 충전기라고는 주민센터에만 있는 줄 알았는데 이렇게 근처에 있어서 좋아요!\"},{\"rgstDtm\":\"20210506131101\",\"uid\":\"testi*\",\"uNm\":\"김기*\",\"starPoint\":\"4.4\",\"contents\":\"전기차 충전기라고는 주민센터에만 있는 줄 알았는데 이렇게 근처에 있어서 좋아요!\"},{\"rgstDtm\":\"20210506131101\",\"uid\":\"testi*\",\"uNm\":\"김기*\",\"starPoint\":\"5.0\",\"contents\":\"전기차 충전기라고는 주민센터에만 있는 줄 알았는데 이렇게 근처에 있어서 좋아요!\"}]}";
//        RES_EPT_1003.setValue(NetUIResponse.success(new Gson().fromJson(dummyData, EPT_1003.Response.class)));
        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_EPT_1003.setValue(NetUIResponse.success(new Gson().fromJson(object, EPT_1003.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_EPT_1003.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_EPT_1003.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, APIInfo.GRA_EPT_1003, reqData);
        return RES_EPT_1003;
    }
} // end of class EPTRepo
