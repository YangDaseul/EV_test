package com.genesis.apps.comm.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.repo.MapRepository;
import com.genesis.apps.comm.model.vo.map.AroundPOIReqVO;
import com.genesis.apps.comm.model.vo.map.FindPathReqVO;
import com.genesis.apps.comm.model.vo.map.FindPathResVO;
import com.genesis.apps.comm.model.vo.map.ReverseGeocodingReqVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.hmns.playmap.extension.PlayMapGeoItem;
import com.hmns.playmap.extension.PlayMapPoiItem;

import java.util.ArrayList;

public class MapViewModel extends ViewModel {

    private final MapRepository repository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<FindPathResVO>> findPathResVo;
    private MutableLiveData<NetUIResponse<ArrayList<PlayMapPoiItem>>> playMapPoiItemList;
    private MutableLiveData<NetUIResponse<PlayMapGeoItem>> playMapGeoItem;

    private MutableLiveData<NetUIResponse<ArrayList<PlayMapGeoItem>>> playMapGeoItemList;



    private MutableLiveData<Integer> testCount;
//    public final LiveData<ArrayList<ExampleResVO>> datas = Transformations.map(exampleResVo, exampleResVo->parsingOnlyData(exampleResVo));
    @ViewModelInject
    MapViewModel(
            MapRepository repository,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;

        playMapPoiItemList = repository.playMapPoiItemList;
        findPathResVo = repository.findPathResVo;
        playMapGeoItem = repository.playMapGeoItem;
        playMapGeoItemList = repository.playMapGeoItemList;

        testCount = repository.testCount;
    }

    public void reqFindPathResVo(final FindPathReqVO findPathReqVO){
        findPathResVo.setValue(repository.findPathDataJson(findPathReqVO).getValue());
    }

    public LiveData<NetUIResponse<FindPathResVO>> getFindPathResVo() {
        return findPathResVo;
    }

    public void reqPlayMapPoiItemList(final AroundPOIReqVO aroundPOIReqVO){
//        playMapPoiItemList.setValue(repository.aroundPOIserch(aroundPOIReqVO).getValue());
        repository.aroundPOIserch(aroundPOIReqVO);
    }

    public LiveData<NetUIResponse<ArrayList<PlayMapPoiItem>>> getPlayMapPoiItemList() {
        return playMapPoiItemList;
    }

    public void reqPlayMapGeoItem(final ReverseGeocodingReqVO reverseGeocodingReqVO){
        repository.reverseGeocoding(reverseGeocodingReqVO);
    }

    public LiveData<NetUIResponse<PlayMapGeoItem>> getPlayMapGeoItem() {
        return playMapGeoItem;
    }

    public void reqTestCount(){
        repository.addCount();
    }

    public MutableLiveData<Integer> getTestCount() {
        return testCount;
    }

    public void reqPlayMapGeoItemList(final String keyword){
        playMapGeoItemList.setValue(repository.searchGeocoding(keyword).getValue());
    }

    public LiveData<NetUIResponse<ArrayList<PlayMapGeoItem>>> getPlayMapGeoItemList() {
        return playMapGeoItemList;
    }


    public void reqFindAllPOI(final String keyword, final double lat, final double lon){
        repository.findAllPOI(keyword, lat, lon);
    }

    public void reqFindAllPOI(final String keyword, double lat, double lon, int sort, String intent, int from, int size, int language){
        repository.findAllPOI(keyword, lat, lon, sort, intent, from, size, language);
    }

}