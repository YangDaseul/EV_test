package com.genesis.apps.comm.model.repo;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.map.AroundPOIReqVO;
import com.genesis.apps.comm.model.vo.map.FindPathReqVO;
import com.genesis.apps.comm.model.vo.map.FindPathResVO;
import com.genesis.apps.comm.model.vo.map.ReverseGeocodingReqVO;
import com.genesis.apps.comm.net.NetCallback;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.room.DatabaseHolder;
import com.google.gson.Gson;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.extension.PlayMapGeoItem;
import com.hmns.playmap.extension.PlayMapPoiItem;
import com.hmns.playmap.network.PlayMapRestApi;
import com.hmns.playmap.shape.PlayMapPolyLine;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MapRepository {

    PlayMapRestApi playMapRestApi;
    NetCaller netCaller;
    private DatabaseHolder databaseHolder;

    public final MutableLiveData<NetUIResponse<FindPathResVO>> findPathResVo = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<PlayMapPolyLine>> playMapPolyLine = new MutableLiveData<>();
    
    public final MutableLiveData<NetUIResponse<ArrayList<PlayMapPoiItem>>> playMapPoiItem = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<ArrayList<PlayMapPoiItem>>> playMapPoiItemList = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<PlayMapGeoItem>> playMapGeoItem = new MutableLiveData<>();
    public final MutableLiveData<NetUIResponse<ArrayList<PlayMapGeoItem>>> playMapGeoItemList = new MutableLiveData<>();

    @Inject
    public MapRepository(NetCaller netCaller, PlayMapRestApi playMapRestApi, DatabaseHolder databaseHolder){
        this.netCaller = netCaller;
        this.playMapRestApi = playMapRestApi;
        this.databaseHolder = databaseHolder;
    }
    public MutableLiveData<NetUIResponse<FindPathResVO>> findPathDataJson (final FindPathReqVO findPathReqVO){
        findPathResVo.setValue(NetUIResponse.loading(null));
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

    public MutableLiveData<NetUIResponse<PlayMapPolyLine>> findPathData (final String routeOption, final String feeOption, final String roadOption, final String coordType, final PlayMapPoint startPoint, final PlayMapPoint goalPoint){
        playMapPolyLine.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(
                () -> playMapRestApi.findPathData(routeOption, feeOption, roadOption, coordType, startPoint, goalPoint), new NetCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        playMapPolyLine.setValue(NetUIResponse.success((PlayMapPolyLine)object));
                    }

                    @Override
                    public void onFail(NetResult e) {
                        playMapPolyLine.setValue(NetUIResponse.error(e.getMseeage(),null));
                    }

                    @Override
                    public void onError(NetResult e) {
                        playMapPolyLine.setValue(NetUIResponse.error(R.string.error_msg_4,null));
                    }
                });

        return playMapPolyLine;
    }



    public MutableLiveData<NetUIResponse<ArrayList<PlayMapPoiItem>>> findAllPOI (final String data,final double lat, final double lon){
        playMapPoiItemList.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(
                () -> playMapRestApi.findAllPOI(data, lat, lon),
                new NetCallback() {
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


    /**
     * @brief POI 검색
     * @param data 검색할 문자열
     * @param lat 위도
     * @param lon 경도
     * @param sort 정렬 옵션 (1=추천순, 2=명칭순, 3=거리순)
     * @param intent 검색 의도 요청
     * @param from 검색 결과 시작 위치
     * @param size 응답으로 받을 결과 개수
     * @param language 언어설정 영어 0 한국어 3(기본값)
     * @return
     */
    public MutableLiveData<NetUIResponse<ArrayList<PlayMapPoiItem>>> findAllPOI (String data, double lat, double lon, int sort, String intent, int from, int size, int language){
        playMapPoiItemList.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(
                () -> playMapRestApi.findAllPOI(data, lat, lon, sort, intent, from, size, language),
                new NetCallback() {
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



    public MutableLiveData<NetUIResponse<ArrayList<PlayMapPoiItem>>> aroundPOIserch (final AroundPOIReqVO aroundPOIReqVO){
        playMapPoiItem.setValue(NetUIResponse.loading(null));
        netCaller.reqDataFromAnonymous(
                () -> playMapRestApi.aroundPOIserch(aroundPOIReqVO.getDepthText(), aroundPOIReqVO.getLat(), aroundPOIReqVO.getLon(), aroundPOIReqVO.getRadius(), aroundPOIReqVO.getSort(), aroundPOIReqVO.getRoadType(), aroundPOIReqVO.getFrom(), aroundPOIReqVO.getSize()),
                new NetCallback() {
                    @Override
                    public void onSuccess(Object object) {
                        playMapPoiItem.setValue(NetUIResponse.success((ArrayList<PlayMapPoiItem>)object));
                    }

                    @Override
                    public void onFail(NetResult e) {
                        playMapPoiItem.setValue(NetUIResponse.error(e.getMseeage(),null));
                    }

                    @Override
                    public void onError(NetResult e) {
                        playMapPoiItem.setValue(NetUIResponse.error(R.string.error_msg_4,null));
                    }
                });

        return playMapPoiItem;
    }




    public MutableLiveData<NetUIResponse<PlayMapGeoItem>> reverseGeocoding (final ReverseGeocodingReqVO reverseGeocodingReqVO){
        playMapGeoItem.setValue(NetUIResponse.loading(null));
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
        playMapGeoItemList.setValue(NetUIResponse.loading(null));
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

    public List<AddressVO> getRecentlyAddressVO(){
        return databaseHolder.getDatabase().addressDao().selectAll();
    }

    public boolean insertRecentlyAddressVO(AddressVO addressVO) {
        try {
            databaseHolder.getDatabase().addressDao().insertAndDeleteInTransaction(addressVO);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean deleteRecentlyAddressVO(AddressVO addressVO) {
        try {
            databaseHolder.getDatabase().addressDao().deleteName(addressVO.getAddrRoad());
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
