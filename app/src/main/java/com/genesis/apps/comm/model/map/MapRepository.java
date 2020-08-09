package com.genesis.apps.comm.model.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.net.NetCallback;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetUIResponse;
import com.google.gson.Gson;
import com.hmns.playmap.extension.PlayMapGeoItem;
import com.hmns.playmap.extension.PlayMapPoiItem;
import com.hmns.playmap.network.PlayMapRestApi;

import java.util.ArrayList;

import javax.inject.Inject;

public class MapRepository {

    PlayMapRestApi playMapRestApi;
    NetCaller netCaller;

    final MutableLiveData<NetUIResponse<FindPathResVO>> findPathResVo = new MutableLiveData<>();
    final MutableLiveData<NetUIResponse<ArrayList<PlayMapPoiItem>>> playMapPoiItemList = new MutableLiveData<>();
    final MutableLiveData<NetUIResponse<PlayMapGeoItem>> playMapGeoItem = new MutableLiveData<>();
    final MutableLiveData<NetUIResponse<ArrayList<PlayMapGeoItem>>> playMapGeoItemList = new MutableLiveData<>();

    final MutableLiveData<Integer> testCount = new MutableLiveData<>(1);
    @Inject
    public MapRepository(NetCaller netCaller, PlayMapRestApi playMapRestApi){
        this.netCaller = netCaller;
        this.playMapRestApi = playMapRestApi;
    }

    public MutableLiveData<Integer> addCount(){
        testCount.setValue(testCount.getValue()+1);
        return testCount;
    }


    public MutableLiveData<NetUIResponse<FindPathResVO>> findPathDataJson (final FindPathReqVO findPathReqVO){
        netCaller.reqDataFromAnonymous(
                () -> playMapRestApi.findPathDataJson(findPathReqVO.getRouteOption(), findPathReqVO.getFeeOption(), findPathReqVO.getRoadOption(), findPathReqVO.getCoordType(), findPathReqVO.getCarType(), findPathReqVO.getStartPoint(), findPathReqVO.getViaPoint(), findPathReqVO.getGoalPoint()), new NetCallback() {
            @Override
            public void onSuccess(Object object) {
                findPathResVo.setValue(NetUIResponse.success(new Gson().fromJson(object.toString(), FindPathResVO.class)));
            }

            @Override
            public void onFail(NetResult e) {
                findPathResVo.setValue(NetUIResponse.error(e.getMseeage(),null));
            }

            @Override
            public void onError(NetResult e) {
                findPathResVo.setValue(NetUIResponse.error(R.string.error_msg_4,null));
            }
        });

        return findPathResVo;
    }


    public MutableLiveData<NetUIResponse<ArrayList<PlayMapPoiItem>>> aroundPOIserch (final AroundPOIReqVO aroundPOIReqVO){
        netCaller.reqDataFromAnonymous(
                () -> playMapRestApi.aroundPOIserch(aroundPOIReqVO.getDepthText(), aroundPOIReqVO.getLat(), aroundPOIReqVO.getLon(), aroundPOIReqVO.getRadius(), aroundPOIReqVO.getSort(), aroundPOIReqVO.getRoadType(), aroundPOIReqVO.getFrom(), aroundPOIReqVO.getSize()), new NetCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        playMapPoiItemList.setValue(NetUIResponse.success((ArrayList<PlayMapPoiItem>)object));
                    }

                    @Override
                    public void onFail(NetResult e) {
                        playMapPoiItemList.setValue(NetUIResponse.error(e.getMseeage(),null));
                    }

                    @Override
                    public void onError(NetResult e) {
                        playMapPoiItemList.setValue(NetUIResponse.error(R.string.error_msg_4,null));
                    }
                });

        return playMapPoiItemList;
    }




    public MutableLiveData<NetUIResponse<PlayMapGeoItem>> reverseGeocoding (final ReverseGeocodingReqVO reverseGeocodingReqVO){
        netCaller.reqDataFromAnonymous(
                () -> playMapRestApi.reverseGeocoding(reverseGeocodingReqVO.getLat(), reverseGeocodingReqVO.getLon(), reverseGeocodingReqVO.getAddrType()), new NetCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        playMapGeoItem.setValue(NetUIResponse.success((PlayMapGeoItem)object));
                    }

                    @Override
                    public void onFail(NetResult e) {
                        playMapGeoItem.setValue(NetUIResponse.error(e.getMseeage(),null));
                    }

                    @Override
                    public void onError(NetResult e) {
                        playMapGeoItem.setValue(NetUIResponse.error(R.string.error_msg_4,null));
                    }
                });

        return playMapGeoItem;
    }




    public MutableLiveData<NetUIResponse<ArrayList<PlayMapGeoItem>>> searchGeocoding (String keyword){
        netCaller.reqDataFromAnonymous(
                () -> playMapRestApi.searchGeocoding(keyword), new NetCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        playMapGeoItemList.setValue(NetUIResponse.success((ArrayList<PlayMapGeoItem>)object));
                    }

                    @Override
                    public void onFail(NetResult e) {
                        playMapGeoItemList.setValue(NetUIResponse.error(e.getMseeage(),null));
                    }

                    @Override
                    public void onError(NetResult e) {
                        playMapGeoItemList.setValue(NetUIResponse.error(R.string.error_msg_4,null));
                    }
                });

        return playMapGeoItemList;
    }
}
