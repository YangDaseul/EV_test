package com.genesis.apps.comm.model.repo;

import android.text.TextUtils;

import androidx.lifecycle.MutableLiveData;

import com.genesis.apps.comm.model.api.APPIAInfo;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.MenuVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.SoundSearcher;
import com.genesis.apps.comm.util.excutor.ExecutorService;
import com.genesis.apps.room.DatabaseHolder;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MenuRepository {
    public static final int ACTION_ADD_MENU=0;
    public static final int ACTION_REMOVE_MENU=1;
    public static final int ACTION_GET_MENU_ALL=2;

    private DatabaseHolder databaseHolder;

    public final MutableLiveData<NetUIResponse<List<MenuVO>>> menuList = new MutableLiveData<>(); //검색가능한 전체 메뉴
    public final MutableLiveData<NetUIResponse<List<MenuVO>>> recentlyMenuList = new MutableLiveData<>(); //최근검색한메뉴리스트
    public final MutableLiveData<NetUIResponse<List<MenuVO>>> keywordMenuList = new MutableLiveData<>(); //키워드 메뉴 리스트

    @Inject
    public MenuRepository(DatabaseHolder databaseHolder){
        this.databaseHolder = databaseHolder;
    }

    /**
     * @brief 전체 메뉴 리스트
     * 전체 메뉴를 정의된 enum에서 로드
     * @return
     */
    public MutableLiveData<NetUIResponse<List<MenuVO>>> getMenuList(){
        menuList.setValue(NetUIResponse.success(APPIAInfo.getQuickMenus(VariableType.getQuickMenuCode(getCustGbCd()))));
        return menuList;
    }

    private String getCustGbCd(){
        String custGbCd="";
        try {
            custGbCd = databaseHolder.getDatabase().userDao().select().getCustGbCd();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(TextUtils.isEmpty(custGbCd))
                custGbCd = VariableType.MAIN_VEHICLE_TYPE_0000;
        }
        return custGbCd;
    }


    /**
     * @brief 최근검색어 리스트
     * 최근검색어 리스트를 select, insert, delete 시 사용
     * @param action
     * @param menuVO
     * @return
     */
    public MutableLiveData<NetUIResponse<List<MenuVO>>> getRecentlyMenuList(int action, MenuVO menuVO){
        ExecutorService es = new ExecutorService("");
        Futures.addCallback(es.getListeningExecutorService().submit(() -> {
            List<MenuVO> list = null;
            try {
                switch (action){
                    case ACTION_ADD_MENU:
                        if(addRecentlyMenu(menuVO)){
                            list = databaseHolder.getDatabase().menuDao().selectAll();
                        }
                        break;
                    case ACTION_REMOVE_MENU:
                        if(removeRecentlyMenu(menuVO.getName())){
                            list = databaseHolder.getDatabase().menuDao().selectAll();
                        }
                        break;
                    default:
                        list = databaseHolder.getDatabase().menuDao().selectAll();
                        break;
                }
            }  catch (Exception e1) {
                e1.printStackTrace();
                list=null;
            }
            return list;
        }), new FutureCallback<List<MenuVO>>() {
            @Override
            public void onSuccess(@NullableDecl List<MenuVO> result) {
                if(result==null){
                    recentlyMenuList.setValue(NetUIResponse.error(0,null));
                }else{
                    if(action!=ACTION_ADD_MENU) //최근검색어를 ADD하는 경우에는 DATA를 SET하지 않음 (최근검색어를 OBSERVE 할 필요 없음)
                        recentlyMenuList.setValue(NetUIResponse.success(result));
                }
                es.shutDownExcutor();
            }

            @Override
            public void onFailure(Throwable t) {
                recentlyMenuList.setValue(NetUIResponse.error(0,null));
                es.shutDownExcutor();
            }
        }, es.getUiThreadExecutor());

        return recentlyMenuList;
    }


    /**
     * @brief 검색 결과
     * 전체메뉴를 대상으로 키워드에 해당하는 리스트를 추출시 사용
     * @param menuVO
     * @return
     */
    public MutableLiveData<NetUIResponse<List<MenuVO>>> getKeywordMenuList(MenuVO menuVO){
        ExecutorService es = new ExecutorService("");
        Futures.addCallback(es.getListeningExecutorService().submit(() -> {
            List<MenuVO> list = null;
            try {
                list = getMenuListKeyword(menuVO);
            }  catch (Exception e1) {
                e1.printStackTrace();
                list=null;
            }
            return list;
        }), new FutureCallback<List<MenuVO>>() {
            @Override
            public void onSuccess(@NullableDecl List<MenuVO> result) {
                if(result==null){
                    keywordMenuList.setValue(NetUIResponse.error(0,null));
                }else{
                    keywordMenuList.setValue(NetUIResponse.success(result));
                }
                es.shutDownExcutor();
            }

            @Override
            public void onFailure(Throwable t) {
                keywordMenuList.setValue(NetUIResponse.error(0,null));
                es.shutDownExcutor();
            }
        }, es.getUiThreadExecutor());

        return keywordMenuList;
    }

















//    public MutableLiveData<NetUIResponse<List<MenuVO>>> getMenuList(int action, MenuVO menuVO){
//        ExecutorService es = new ExecutorService("");
//        menuList.setValue(NetUIResponse.loading(null));
//        Futures.addCallback(es.getListeningExecutorService().submit(() -> {
//            List<MenuVO> list = null;
//            try {
//                switch (action){
//                    case ACTION_ADD_MENU:
//                        if(addRecentlyMenu(menuVO)){
//                            list = databaseHolder.getDatabase().menuDao().selectAll();
//                        }
//                        break;
//                    case ACTION_REMOVE_MENU:
//                        if(removeRecentlyMenu(menuVO.getCode())){
//                            list = databaseHolder.getDatabase().menuDao().selectAll();
//                        }
//                        break;
//                    case ACTION_GET_MENU_KEYWORD:
//                        list = getMenuListKeyword(menuVO);
//                        break;
//                    case ACTION_GET_MENU_ALL:
//                    default:
//                        list = APPIAInfo.getQuickMenus();
//                        break;
//                }
//            }  catch (Exception e1) {
//                e1.printStackTrace();
//                list=null;
//            }
//            return list;
//        }), new FutureCallback<List<MenuVO>>() {
//            @Override
//            public void onSuccess(@NullableDecl List<MenuVO> result) {
//               if(result==null){
//                   menuList.setValue(NetUIResponse.error(0,null));
//               }else{
//                   menuList.setValue(NetUIResponse.success(result));
//               }
//               es.shutDownExcutor();
//            }
//
//            @Override
//            public void onFailure(Throwable t) {
//                menuList.setValue(NetUIResponse.error(0,null));
//                es.shutDownExcutor();
//            }
//        }, es.getUiThreadExecutor());
//
//        return menuList;
//    }

    public boolean removeRecentlyMenu(String name){
        boolean isDel = false;
        try {
            databaseHolder.getDatabase().menuDao().deleteName(name);
            isDel=true;
        }catch (Exception ignore){
            ignore.printStackTrace();
        }
        return isDel;
    }

    public boolean addRecentlyMenu(MenuVO menuVO){
        boolean isAdd = false;
        try{
            databaseHolder.getDatabase().menuDao().deleteName(menuVO.getName());
            databaseHolder.getDatabase().menuDao().insert(menuVO);
            databaseHolder.getDatabase().menuDao().deleteAuto();
            isAdd=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isAdd;
    }

    private List<MenuVO> getMenuListKeyword(final MenuVO menuVO) {
        List<MenuVO> temps = new ArrayList<>();
        String keyword = menuVO.getName();

        if(TextUtils.isEmpty(keyword)){
//            temps = databaseHolder.getDatabase().menuDao().selectAll();
        }else{
            List<MenuVO> menuList = APPIAInfo.getQuickMenus(VariableType.getQuickMenuCode(getCustGbCd()));
            for(MenuVO data : menuList){
                String[] compares = {data.getName()};
                for (String compare : compares) {
                    if (!TextUtils.isEmpty(compare)) {
                        boolean hasSearch = SoundSearcher.matchString(compare.toLowerCase(), menuVO.getName().toLowerCase());
                        if (hasSearch) {
                            temps.add(data);
                            break;
                        }
                    }
                }
            }
        }
        return temps;
    }

//    public void search(final String search, TextView empty) {
//
//        this.search = search;
//
//        if (TextUtils.isEmpty(search)) {
//            addAllItem(dataRecently);
//            empty.setVisibility(View.GONE);
//            layoutRecently.setVisibility(dataRecently.size()>0 ? View.VISIBLE : View.GONE);
//        } else {
//            ArrayList<DummyVehicle> temps = new ArrayList<DummyVehicle>();
//
//            if (dataOriginal != null && dataOriginal.size() > 0) {
//                for (int i = 0; i < dataOriginal.size(); i++) {
//                    DummyVehicle info = dataOriginal.get(i);
//                    String[] compares = {info.vrn,info.vin,info.brand,info.code};
//                    for (String compare : compares) {
//                        if (!TextUtils.isEmpty(compare)) {
//                            boolean hasSearch = SoundSearcher.matchString(compare.toLowerCase(), search.toLowerCase());
//                            if (hasSearch) {
//                                temps.add(info);
//                                break;
//                            }
//                        }
//                    }
//                }
//            }
//            addAllItem(temps);
//            empty.setVisibility(temps.size()<1 ? View.VISIBLE : View.GONE);
//            layoutRecently.setVisibility(View.GONE);
//        }
//        //notifyDataSetChanged();
//
//
//        notifyItemRangeChanged(0, getItemCount());
//    }



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
