package com.genesis.apps.comm.model.repo;

import com.genesis.apps.comm.net.NetCaller;

import javax.inject.Inject;

//CarWashHistory : 세차 예약 내역
public class CWHRepo {

    NetCaller netCaller;

//    public final MutableLiveData<NetUIResponse<LGN_0001.Response>> RES_LGN_0001 = new MutableLiveData<>();


    @Inject
    public CWHRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }
}
