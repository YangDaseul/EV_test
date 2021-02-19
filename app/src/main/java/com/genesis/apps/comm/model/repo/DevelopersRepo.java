package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.developers.Agreements;
import com.genesis.apps.comm.model.api.developers.CarCheck;
import com.genesis.apps.comm.model.api.developers.CarConnect;
import com.genesis.apps.comm.model.api.developers.CarId;
import com.genesis.apps.comm.model.api.developers.CarList;
import com.genesis.apps.comm.model.api.developers.CheckJoinCCS;
import com.genesis.apps.comm.model.api.developers.Detail;
import com.genesis.apps.comm.model.api.developers.Distance;
import com.genesis.apps.comm.model.api.developers.Dtc;
import com.genesis.apps.comm.model.api.developers.Dte;
import com.genesis.apps.comm.model.api.developers.Odometer;
import com.genesis.apps.comm.model.api.developers.Odometers;
import com.genesis.apps.comm.model.api.developers.ParkLocation;
import com.genesis.apps.comm.model.api.developers.Replacements;
import com.genesis.apps.comm.model.api.developers.Target;
import com.genesis.apps.comm.model.constants.GAInfo;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class DevelopersRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<Dtc.Response>> RES_DTC = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<Replacements.Response>> RES_REPLACEMENTS = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<Target.Response>> RES_TARGET = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<Detail.Response>> RES_DETAIL = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CarList.Response>> RES_CARLIST = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<Dte.Response>> RES_DTE = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<Odometer.Response>> RES_ODOMETER = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<Odometers.Response>> RES_ODOMETERS = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<ParkLocation.Response>> RES_PARKLOCATION = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<Distance.Response>> RES_DISTANCE = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CarCheck.Response>> RES_CAR_CHECK = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CarId.Response>> RES_CAR_ID = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<CarConnect.Response>> RES_CAR_CONNECT = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<Agreements.Response>> RES_CAR_AGREEMENTS = new MutableLiveData<>();



    @Inject
    public DevelopersRepo(NetCaller netCaller) {
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<Dtc.Response>> REQ_DTC(final Dtc.Request reqData) {
        RES_DTC.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_DTC.setValue(NetUIResponse.success(new Gson().fromJson(object, Dtc.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_DTC.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_DTC.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, GAInfo.CCSP_URL, APIInfo.DEVELOPERS_DTC, reqData);

        return RES_DTC;
    }


    public MutableLiveData<NetUIResponse<Replacements.Response>> REQ_REPLACEMENTS(final Replacements.Request reqData) {
        RES_REPLACEMENTS.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_REPLACEMENTS.setValue(NetUIResponse.success(new Gson().fromJson(object, Replacements.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_REPLACEMENTS.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_REPLACEMENTS.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, GAInfo.CCSP_URL, APIInfo.DEVELOPERS_REPLACEMENTS, reqData);

        return RES_REPLACEMENTS;
    }

    public MutableLiveData<NetUIResponse<Target.Response>> REQ_TARGET(final Target.Request reqData) {
        RES_TARGET.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_TARGET.setValue(NetUIResponse.success(new Gson().fromJson(object, Target.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_TARGET.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_TARGET.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, GAInfo.CCSP_URL, APIInfo.DEVELOPERS_TARGET, reqData);

        return RES_TARGET;
    }

    public MutableLiveData<NetUIResponse<Detail.Response>> REQ_DETAIL(final Detail.Request reqData) {
        RES_DETAIL.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_DETAIL.setValue(NetUIResponse.success(new Gson().fromJson(object, Detail.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_DETAIL.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_DETAIL.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, GAInfo.CCSP_URL, APIInfo.DEVELOPERS_DETAIL, reqData);

        return RES_DETAIL;
    }

    public MutableLiveData<NetUIResponse<CarList.Response>> REQ_CARLIST(final CarList.Request reqData) {
        RES_CARLIST.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CARLIST.setValue(NetUIResponse.success(new Gson().fromJson(object, CarList.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CARLIST.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CARLIST.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, GAInfo.CCSP_URL, APIInfo.DEVELOPERS_CARLIST, reqData);

        return RES_CARLIST;
    }


    public MutableLiveData<NetUIResponse<Dte.Response>> REQ_DTE(final Dte.Request reqData) {
        RES_DTE.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_DTE.setValue(NetUIResponse.success(new Gson().fromJson(object, Dte.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_DTE.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_DTE.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, GAInfo.CCSP_URL, APIInfo.DEVELOPERS_DTE, reqData);

        return RES_DTE;
    }

    public MutableLiveData<NetUIResponse<Odometer.Response>> REQ_ODOMETER(final Odometer.Request reqData) {
        RES_ODOMETER.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_ODOMETER.setValue(NetUIResponse.success(new Gson().fromJson(object, Odometer.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_ODOMETER.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_ODOMETER.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, GAInfo.CCSP_URL, APIInfo.DEVELOPERS_ODOMETER, reqData);

        return RES_ODOMETER;
    }

    public MutableLiveData<NetUIResponse<Odometers.Response>> REQ_ODOMETERS(final Odometers.Request reqData) {
        RES_ODOMETERS.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_ODOMETERS.setValue(NetUIResponse.success(new Gson().fromJson(object, Odometers.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_ODOMETERS.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_ODOMETERS.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, GAInfo.CCSP_URL, APIInfo.DEVELOPERS_ODOMETERS, reqData);

        return RES_ODOMETERS;
    }



    public MutableLiveData<NetUIResponse<ParkLocation.Response>> REQ_PARKLOCATION(final ParkLocation.Request reqData) {
        RES_PARKLOCATION.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_PARKLOCATION.setValue(NetUIResponse.success(new Gson().fromJson(object, ParkLocation.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_PARKLOCATION.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_PARKLOCATION.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, GAInfo.CCSP_URL, APIInfo.DEVELOPERS_PARKLOCATION, reqData);

        return RES_PARKLOCATION;
    }

    public MutableLiveData<NetUIResponse<Distance.Response>> REQ_DISTANCE(final Distance.Request reqData) {
        RES_DISTANCE.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                Distance.Response response = null;
                try {
                    response = new Gson().fromJson(object, Distance.Response.class);
                }catch (Exception e){
                    e.printStackTrace();
                }
                RES_DISTANCE.setValue(NetUIResponse.success(response));
            }

            @Override
            public void onFail(NetResult e) {
                RES_DISTANCE.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_DISTANCE.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, GAInfo.CCSP_URL, APIInfo.DEVELOPERS_DISTANCE, reqData);

        return RES_DISTANCE;
    }

    public MutableLiveData<NetUIResponse<CarCheck.Response>> REQ_CAR_CHECK(final CarCheck.Request reqData) {
        RES_CAR_CHECK.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CAR_CHECK.setValue(NetUIResponse.success(new Gson().fromJson(object, CarCheck.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CAR_CHECK.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CAR_CHECK.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, GAInfo.CCSP_URL, APIInfo.DEVELOPERS_CAR_CHECK, reqData);

        return RES_CAR_CHECK;
    }

    public MutableLiveData<NetUIResponse<CarId.Response>> REQ_CAR_ID(final CarId.Request reqData) {
        RES_CAR_ID.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CAR_ID.setValue(NetUIResponse.success(new Gson().fromJson(object, CarId.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CAR_ID.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CAR_ID.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, GAInfo.CCSP_URL, APIInfo.DEVELOPERS_CAR_ID, reqData);

        return RES_CAR_ID;
    }

    public MutableLiveData<NetUIResponse<CarConnect.Response>> REQ_CAR_CONNECT(final CarConnect.Request reqData) {
        RES_CAR_CONNECT.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CAR_CONNECT.setValue(NetUIResponse.success(new Gson().fromJson(object, CarConnect.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CAR_CONNECT.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CAR_CONNECT.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, GAInfo.CCSP_URL, APIInfo.DEVELOPERS_CAR_CONNECT, reqData);

        return RES_CAR_CONNECT;
    }

    public MutableLiveData<NetUIResponse<Agreements.Response>> REQ_AGREEMENTS_ASYNC(final Agreements.Request reqData) {
        RES_CAR_AGREEMENTS.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_CAR_AGREEMENTS.setValue(NetUIResponse.success(new Gson().fromJson(object, Agreements.Response.class)));
            }

            @Override
            public void onFail(NetResult e) {
                RES_CAR_AGREEMENTS.setValue(NetUIResponse.error(e.getMseeage(), null));
            }

            @Override
            public void onError(NetResult e) {
                RES_CAR_AGREEMENTS.setValue(NetUIResponse.error(R.string.error_msg_4, null));
            }
        }, GAInfo.CCSP_URL, APIInfo.DEVELOPERS_AGREEMENTS, reqData);

        return RES_CAR_AGREEMENTS;
    }

    public CarCheck.Response REQ_SYNC_CAR_CHECK(final CarCheck.Request reqData) {
        CarCheck.Response response;
        try{
            response = new Gson().fromJson(netCaller.reqDataFromAnonymous(GAInfo.CCSP_URL, APIInfo.DEVELOPERS_CAR_CHECK, null), CarCheck.Response.class);
        }catch (Exception e){
            response = null;
        }
        return response;
    }

    public CarId.Response REQ_SYNC_CAR_ID(final CarId.Request reqData) {
        CarId.Response response;
        try{
            response = new Gson().fromJson(netCaller.reqDataFromAnonymous(GAInfo.CCSP_URL, APIInfo.DEVELOPERS_CAR_ID, reqData), CarId.Response.class);
        }catch (Exception e){
            response = null;
        }
        return response;
    }

    public CarConnect.Response REQ_SYNC_CAR_CONNECT(final CarConnect.Request reqData) {
        CarConnect.Response response;
        try{
            response = new Gson().fromJson(netCaller.reqDataFromAnonymous(GAInfo.CCSP_URL, APIInfo.DEVELOPERS_CAR_CONNECT, reqData), CarConnect.Response.class);
        }catch (Exception e){
            response = null;
        }
        return response;
    }

    public CheckJoinCCS.Response REQ_CHECK_JOIN_CCS(final CheckJoinCCS.Request reqData) {
        CheckJoinCCS.Response response;
        try{
            response = new Gson().fromJson(netCaller.reqDataFromAnonymous(GAInfo.CCSP_URL, APIInfo.DEVELOPERS_CHECK_JOIN_CCS, reqData), CheckJoinCCS.Response.class);
        }catch (Exception e){
            response = null;
        }
        return response;
    }

    public Agreements.Response REQ_AGREEMENTS(final Agreements.Request reqData) {
        Agreements.Response response;
        try{
            response = new Gson().fromJson(netCaller.reqDataFromAnonymous(GAInfo.CCSP_URL, APIInfo.DEVELOPERS_AGREEMENTS, reqData), Agreements.Response.class);
        }catch (Exception e){
            response = null;
        }
        return response;
    }

}
