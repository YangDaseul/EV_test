package com.genesis.apps.comm.model.map;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.ExampleReqVO;
import com.genesis.apps.comm.model.ExampleResVO;
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

        testCount = repository.testCount;
    }

    public void reqFindPathResVo(final FindPathReqVO findPathReqVO){
        findPathResVo.setValue(repository.findPathDataJson(findPathReqVO).getValue());
    }

    public LiveData<NetUIResponse<FindPathResVO>> getFindPathResVo() {
        return findPathResVo;
    }

    public void reqPlayMapPoiItemList(final AroundPOIReqVO aroundPOIReqVO){
        playMapPoiItemList.setValue(repository.aroundPOIserch(aroundPOIReqVO).getValue());
    }

    public LiveData<NetUIResponse<ArrayList<PlayMapPoiItem>>> getPlayMapPoiItemList() {
        return playMapPoiItemList;
    }

    public void reqPlayMapGeoItem(final ReverseGeocodingReqVO reverseGeocodingReqVO){
        playMapGeoItem.setValue(repository.reverseGeocoding(reverseGeocodingReqVO).getValue());
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
}