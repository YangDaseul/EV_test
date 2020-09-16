package com.genesis.apps.comm.model.main.myg;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.R;
import com.genesis.apps.comm.model.gra.APPIAInfo;
import com.genesis.apps.comm.model.map.AroundPOIReqVO;
import com.genesis.apps.comm.model.map.FindPathReqVO;
import com.genesis.apps.comm.model.map.FindPathResVO;
import com.genesis.apps.comm.model.map.ReverseGeocodingReqVO;
import com.genesis.apps.comm.model.vo.MenuVO;
import com.genesis.apps.comm.net.NetCallback;
import com.genesis.apps.comm.net.NetCaller;
import com.genesis.apps.comm.net.NetResult;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.room.DatabaseHolder;
import com.google.gson.Gson;
import com.hmns.playmap.extension.PlayMapGeoItem;
import com.hmns.playmap.extension.PlayMapPoiItem;
import com.hmns.playmap.network.PlayMapRestApi;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MenuRepository {

    DatabaseHolder databaseHolder;

    final MutableLiveData<NetUIResponse<FindPathResVO>> findPathResVo = new MutableLiveData<>();
    final MutableLiveData<NetUIResponse<ArrayList<PlayMapPoiItem>>> playMapPoiItemList = new MutableLiveData<>();
    final MutableLiveData<NetUIResponse<PlayMapGeoItem>> playMapGeoItem = new MutableLiveData<>();
    final MutableLiveData<NetUIResponse<ArrayList<PlayMapGeoItem>>> playMapGeoItemList = new MutableLiveData<>();

    final LiveData<List<MenuVO>> menuList = new MutableLiveData<>(APPIAInfo.getQuickMenus()); //검색가능한 전체 메뉴
    final MutableLiveData<List<MenuVO>> recentlyList = new MutableLiveData<>(); //최근검색항목
    @Inject
    public MenuRepository(DatabaseHolder databaseHolder){
        this.databaseHolder = databaseHolder;
    }

    /**
     * @brief DB에서 최근검색항목 로드
     *
     * @return
     */
    public MutableLiveData<List<MenuVO>> getRecentlyList(){
        recentlyList.setValue(databaseHolder.getDatabase().menuDao().selectAll());
        return recentlyList;
    }

    public boolean removeRecentlyList(String code){
        boolean isDel = false;
        try {
            databaseHolder.getDatabase().menuDao().deleteCode(code);
            getRecentlyList();
            isDel=true;
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
        return isDel;
    }

    public boolean addRecentlyList(MenuVO menuVO){
        boolean isAdd = false;
        try{
            databaseHolder.getDatabase().menuDao().insert(menuVO);
            getRecentlyList();
            isAdd=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isAdd;
    }

    public void search(final String search, TextView empty) {

        this.search = search;

        if (TextUtils.isEmpty(search)) {
            addAllItem(dataRecently);
            empty.setVisibility(View.GONE);
            layoutRecently.setVisibility(dataRecently.size()>0 ? View.VISIBLE : View.GONE);
        } else {
            ArrayList<DummyVehicle> temps = new ArrayList<DummyVehicle>();

            if (dataOriginal != null && dataOriginal.size() > 0) {
                for (int i = 0; i < dataOriginal.size(); i++) {
                    DummyVehicle info = dataOriginal.get(i);
                    String[] compares = {info.vrn,info.vin,info.brand,info.code};
                    for (String compare : compares) {
                        if (!TextUtils.isEmpty(compare)) {
                            boolean hasSearch = SoundSearcher.matchString(compare.toLowerCase(), search.toLowerCase());
                            if (hasSearch) {
                                temps.add(info);
                                break;
                            }
                        }
                    }
                }
            }
            addAllItem(temps);
            empty.setVisibility(temps.size()<1 ? View.VISIBLE : View.GONE);
            layoutRecently.setVisibility(View.GONE);
        }
        //notifyDataSetChanged();


        notifyItemRangeChanged(0, getItemCount());
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
//                () -> playMapRestApi.aroundPOIserch(aroundPOIReqVO.getDepthText(), aroundPOIReqVO.getLat(), aroundPOIReqVO.getLon(), aroundPOIReqVO.getRadius(), aroundPOIReqVO.getSort(), aroundPOIReqVO.getRoadType(), aroundPOIReqVO.getFrom(), aroundPOIReqVO.getSize()),
//                new NetCallback() {
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
