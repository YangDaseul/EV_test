package com.genesis.apps.comm.viewmodel;

import com.genesis.apps.comm.model.repo.MapRepository;
import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.map.AroundPOIReqVO;
import com.genesis.apps.comm.model.vo.map.FindPathReqVO;
import com.genesis.apps.comm.model.vo.map.FindPathResVO;
import com.genesis.apps.comm.model.vo.map.ReverseGeocodingReqVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.excutor.ExecutorService;
import com.hmns.playmap.extension.PlayMapGeoItem;
import com.hmns.playmap.extension.PlayMapPoiItem;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import lombok.Data;

public @Data
class MapViewModel extends ViewModel {

    private final MapRepository repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<FindPathResVO>> findPathResVo;
    private MutableLiveData<NetUIResponse<ArrayList<PlayMapPoiItem>>> playMapPoiItem;
    private MutableLiveData<NetUIResponse<ArrayList<PlayMapPoiItem>>> playMapPoiItemList;
    private MutableLiveData<NetUIResponse<PlayMapGeoItem>> playMapGeoItem;

    private MutableLiveData<NetUIResponse<ArrayList<PlayMapGeoItem>>> playMapGeoItemList;

    @ViewModelInject
    MapViewModel(
            MapRepository repository,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;

        playMapPoiItem = repository.playMapPoiItem;
        playMapPoiItemList = repository.playMapPoiItemList;
        findPathResVo = repository.findPathResVo;
        playMapGeoItem = repository.playMapGeoItem;
        playMapGeoItemList = repository.playMapGeoItemList;
    }

    public void reqFindPathResVo(final FindPathReqVO findPathReqVO){
        repository.findPathDataJson(findPathReqVO);
    }

    public void reqPlayMapPoiItemList(final AroundPOIReqVO aroundPOIReqVO){
        repository.aroundPOIserch(aroundPOIReqVO);
    }

    public void reqPlayMapGeoItem(final ReverseGeocodingReqVO reverseGeocodingReqVO){
        repository.reverseGeocoding(reverseGeocodingReqVO);
    }

    public void reqPlayMapGeoItemList(final String keyword){
        repository.searchGeocoding(keyword);
    }

    public void reqFindAllPOI(final String keyword, final double lat, final double lon){
        repository.findAllPOI(keyword, lat, lon);
    }

    public void reqFindAllPOI(final String keyword, double lat, double lon, int sort, String intent, int from, int size, int language){
        repository.findAllPOI(keyword, lat, lon, sort, intent, from, size, language);
    }

    public List<AddressVO> getRecentlyAddressVO() throws ExecutionException, InterruptedException{
        ExecutorService es = new ExecutorService("");
        Future<List<AddressVO>> future = es.getListeningExecutorService().submit(()->{
            List<AddressVO> list = new ArrayList<>();
            try {
                list = repository.getRecentlyAddressVO();
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            return list;
        });

        try {
            return future.get();
        }finally {
            es.shutDownExcutor();
        }
    }

    public Boolean insertRecentlyAddressVO(AddressVO addressVO) throws ExecutionException, InterruptedException{
        ExecutorService es = new ExecutorService("");
        Future<Boolean> future = es.getListeningExecutorService().submit(()->{
            Boolean result = false;
            try {
                result = repository.insertRecentlyAddressVO(addressVO);
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            return result;
        });

        try {
            return future.get();
        }finally {
            es.shutDownExcutor();
        }
    }

    public Boolean deleteRecentlyAddressVO(AddressVO addressVO) throws ExecutionException, InterruptedException{
        ExecutorService es = new ExecutorService("");
        Future<Boolean> future = es.getListeningExecutorService().submit(()->{
            Boolean result = false;
            try {
                result = repository.deleteRecentlyAddressVO(addressVO);
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            return result;
        });

        try {
            return future.get();
        }finally {
            es.shutDownExcutor();
        }
    }
}