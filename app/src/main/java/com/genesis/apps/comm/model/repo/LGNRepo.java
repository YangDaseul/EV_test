package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.constants.TestCode;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.api.LGN_0001;
import com.genesis.apps.comm.model.gra.api.LGN_0002;
import com.genesis.apps.comm.model.gra.api.LGN_0003;
import com.genesis.apps.comm.model.gra.api.LGN_0004;
import com.genesis.apps.comm.model.gra.api.LGN_0005;
import com.genesis.apps.comm.model.gra.api.LGN_0006;
import com.genesis.apps.comm.model.gra.api.LGN_0007;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetResultCallback;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;

import javax.inject.Inject;

public class LGNRepo {

    NetCaller netCaller;

    public final MutableLiveData<NetUIResponse<LGN_0001.Response>> RES_LGN_0001 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<LGN_0002.Response>> RES_LGN_0002 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<LGN_0003.Response>> RES_LGN_0003 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<LGN_0004.Response>> RES_LGN_0004 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<LGN_0005.Response>> RES_LGN_0005 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<LGN_0006.Response>> RES_LGN_0006 = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<LGN_0007.Response>> RES_LGN_0007 = new MutableLiveData<>();

    @Inject
    public LGNRepo(NetCaller netCaller){
        this.netCaller = netCaller;
    }

    public MutableLiveData<NetUIResponse<LGN_0001.Response>> REQ_LGN_0001(final LGN_0001.Request reqData){

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_LGN_0001.setValue(NetUIResponse.success(new Gson().fromJson(object, LGN_0001.Response.class)));
            }
            @Override
            public void onFail(NetResult e) {
                RES_LGN_0001.setValue(NetUIResponse.success(TestCode.LGN_0001));
//                RES_LGN_0001.setValue(NetUIResponse.error(e.getMseeage(),null));
            }
            @Override
            public void onError(NetResult e) {
                RES_LGN_0001.setValue(NetUIResponse.error(R.string.error_msg_4,null));
            }
        }, APIInfo.GRA_LGN_0001, reqData);

        return RES_LGN_0001;
    }

    public MutableLiveData<NetUIResponse<LGN_0002.Response>> REQ_LGN_0002(final LGN_0002.Request reqData){

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_LGN_0002.setValue(NetUIResponse.success(new Gson().fromJson(object, LGN_0002.Response.class)));
            }
            @Override
            public void onFail(NetResult e) {
                RES_LGN_0002.setValue(NetUIResponse.error(e.getMseeage(),null));
            }
            @Override
            public void onError(NetResult e) {
                RES_LGN_0002.setValue(NetUIResponse.error(R.string.error_msg_4,null));
            }
        }, APIInfo.GRA_LGN_0002, reqData);

        return RES_LGN_0002;
    }

    public MutableLiveData<NetUIResponse<LGN_0003.Response>> REQ_LGN_0003(final LGN_0003.Request reqData){

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_LGN_0003.setValue(NetUIResponse.success(new Gson().fromJson(object, LGN_0003.Response.class)));
            }
            @Override
            public void onFail(NetResult e) {
                RES_LGN_0003.setValue(NetUIResponse.error(e.getMseeage(),null));
            }
            @Override
            public void onError(NetResult e) {
                RES_LGN_0003.setValue(NetUIResponse.error(R.string.error_msg_4,null));
            }
        }, APIInfo.GRA_LGN_0003, reqData);

        return RES_LGN_0003;
    }


    public MutableLiveData<NetUIResponse<LGN_0004.Response>> REQ_LGN_0004(final LGN_0004.Request reqData){

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_LGN_0004.setValue(NetUIResponse.success(new Gson().fromJson(object, LGN_0004.Response.class)));
            }
            @Override
            public void onFail(NetResult e) {
                RES_LGN_0004.setValue(NetUIResponse.error(e.getMseeage(),null));
            }
            @Override
            public void onError(NetResult e) {
                RES_LGN_0004.setValue(NetUIResponse.error(R.string.error_msg_4,null));
            }
        }, APIInfo.GRA_LGN_0004, reqData);

        return RES_LGN_0004;
    }


    public MutableLiveData<NetUIResponse<LGN_0005.Response>> REQ_LGN_0005(final LGN_0005.Request reqData){

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_LGN_0005.setValue(NetUIResponse.success(new Gson().fromJson(object, LGN_0005.Response.class)));
            }
            @Override
            public void onFail(NetResult e) {
                RES_LGN_0005.setValue(NetUIResponse.error(e.getMseeage(),null));
            }
            @Override
            public void onError(NetResult e) {
                RES_LGN_0005.setValue(NetUIResponse.error(R.string.error_msg_4,null));
            }
        }, APIInfo.GRA_LGN_0005, reqData);

        return RES_LGN_0005;
    }

    public MutableLiveData<NetUIResponse<LGN_0006.Response>> REQ_LGN_0006(final LGN_0006.Request reqData){

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_LGN_0006.setValue(NetUIResponse.success(new Gson().fromJson(object, LGN_0006.Response.class)));
            }
            @Override
            public void onFail(NetResult e) {
                RES_LGN_0006.setValue(NetUIResponse.error(e.getMseeage(),null));
            }
            @Override
            public void onError(NetResult e) {
                RES_LGN_0006.setValue(NetUIResponse.error(R.string.error_msg_4,null));
            }
        }, APIInfo.GRA_LGN_0006, reqData);

        return RES_LGN_0006;
    }

    public MutableLiveData<NetUIResponse<LGN_0007.Response>> REQ_LGN_0007(final LGN_0007.Request reqData){

        netCaller.reqDataToGRA(new NetResultCallback() {
            @Override
            public void onSuccess(String object) {
                RES_LGN_0007.setValue(NetUIResponse.success(new Gson().fromJson(object, LGN_0007.Response.class)));
            }
            @Override
            public void onFail(NetResult e) {
                RES_LGN_0007.setValue(NetUIResponse.error(e.getMseeage(),null));
            }
            @Override
            public void onError(NetResult e) {
                RES_LGN_0007.setValue(NetUIResponse.error(R.string.error_msg_4,null));
            }
        }, APIInfo.GRA_LGN_0007, reqData);

        return RES_LGN_0007;
    }




//    public MutableLiveData<NetUIResponse<FindPathResVO>> findPathDataJson (final FindPathReqVO findPathReqVO){
//        netCaller.reqDataFromAnonymous(
//                () -> playMapRestApi.findPathDataJson(findPathReqVO.getRouteOption(), findPathReqVO.getFeeOption(), findPathReqVO.getRoadOption(), findPathReqVO.getCoordType(), findPathReqVO.getCarType(), findPathReqVO.getStartPoint(), findPathReqVO.getViaPoint(), findPathReqVO.getGoalPoint()), new NetCallback() {
//            @Override
//            public void onSuccess(Object object) {
//                findPathResVo.setValue(NetUIResponse.success(new Gson().fromJson(object.toString(), FindPathResVO.class)));
//            }
//
//            @Override
//            public void onFail(NetResult e) {
//                findPathResVo.setValue(NetUIResponse.error(e.getMseeage(),null));
//            }
//
//            @Override
//            public void onError(NetResult e) {
//                findPathResVo.setValue(NetUIResponse.error(R.string.error_msg_4,null));
//            }
//        });
//
//        return findPathResVo;
//    }
//
//
//    public MutableLiveData<NetUIResponse<ArrayList<PlayMapPoiItem>>> aroundPOIserch (final AroundPOIReqVO aroundPOIReqVO){
//        netCaller.reqDataFromAnonymous(
//                () -> playMapRestApi.aroundPOIserch(aroundPOIReqVO.getDepthText(), aroundPOIReqVO.getLat(), aroundPOIReqVO.getLon(), aroundPOIReqVO.getRadius(), aroundPOIReqVO.getSort(), aroundPOIReqVO.getRoadType(), aroundPOIReqVO.getFrom(), aroundPOIReqVO.getSize()), new NetCallback() {
//                    @Override
//                    public void onSuccess(Object object) {
//                        playMapPoiItemList.setValue(NetUIResponse.success((ArrayList<PlayMapPoiItem>)object));
//                    }
//
//                    @Override
//                    public void onFail(NetResult e) {
//                        playMapPoiItemList.setValue(NetUIResponse.error(e.getMseeage(),null));
//                    }
//
//                    @Override
//                    public void onError(NetResult e) {
//                        playMapPoiItemList.setValue(NetUIResponse.error(R.string.error_msg_4,null));
//                    }
//                });
//
//        return playMapPoiItemList;
//    }
//
//
//
//
//    public MutableLiveData<NetUIResponse<PlayMapGeoItem>> reverseGeocoding (final ReverseGeocodingReqVO reverseGeocodingReqVO){
//        netCaller.reqDataFromAnonymous(
//                () -> playMapRestApi.reverseGeocoding(reverseGeocodingReqVO.getLat(), reverseGeocodingReqVO.getLon(), reverseGeocodingReqVO.getAddrType()), new NetCallback() {
//                    @Override
//                    public void onSuccess(Object object) {
//                        playMapGeoItem.setValue(NetUIResponse.success((PlayMapGeoItem)object));
//                    }
//
//                    @Override
//                    public void onFail(NetResult e) {
//                        playMapGeoItem.setValue(NetUIResponse.error(e.getMseeage(),null));
//                    }
//
//                    @Override
//                    public void onError(NetResult e) {
//                        playMapGeoItem.setValue(NetUIResponse.error(R.string.error_msg_4,null));
//                    }
//                });
//
//        return playMapGeoItem;
//    }
//
//
//
//
//    public MutableLiveData<NetUIResponse<ArrayList<PlayMapGeoItem>>> searchGeocoding (String keyword){
//        netCaller.reqDataFromAnonymous(
//                () -> playMapRestApi.searchGeocoding(keyword), new NetCallback() {
//                    @Override
//                    public void onSuccess(Object object) {
//                        playMapGeoItemList.setValue(NetUIResponse.success((ArrayList<PlayMapGeoItem>)object));
//                    }
//
//                    @Override
//                    public void onFail(NetResult e) {
//                        playMapGeoItemList.setValue(NetUIResponse.error(e.getMseeage(),null));
//                    }
//
//                    @Override
//                    public void onError(NetResult e) {
//                        playMapGeoItemList.setValue(NetUIResponse.error(R.string.error_msg_4,null));
//                    }
//                });
//
//        return playMapGeoItemList;
//    }
}
