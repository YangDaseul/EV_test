package com.genesis.apps.comm.model.main.contents;

import com.genesis.apps.comm.model.map.AroundPOIReqVO;
import com.genesis.apps.comm.model.map.FindPathReqVO;
import com.genesis.apps.comm.model.map.FindPathResVO;
import com.genesis.apps.comm.model.map.MapRepository;
import com.genesis.apps.comm.model.map.ReverseGeocodingReqVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.hmns.playmap.extension.PlayMapGeoItem;
import com.hmns.playmap.extension.PlayMapPoiItem;

import java.util.ArrayList;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

public class ContentsViewModel extends ViewModel {

//    private final MapRepository repository;
    private final ContentsRepository contentsRepository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<ArrayList<ContentsResVO>>> contentsList;
    @ViewModelInject
    ContentsViewModel(
            ContentsRepository repository,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.contentsRepository = repository;
        this.savedStateHandle = savedStateHandle;

        this.contentsList = repository.contentsList;

    }

    public void reqTestData(){
        contentsList.setValue(contentsRepository.addTestValue().getValue());
    }

    public LiveData<NetUIResponse<ArrayList<ContentsResVO>>> getContentsList() {
        return contentsList;
    }


}