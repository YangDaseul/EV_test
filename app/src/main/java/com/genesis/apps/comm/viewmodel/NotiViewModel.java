package com.genesis.apps.comm.viewmodel;

import com.genesis.apps.comm.model.repo.LGNRepo;
import com.genesis.apps.comm.model.vo.NotiVO;

import java.util.List;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import lombok.Data;

public @Data
class NotiViewModel extends ViewModel {

    private final LGNRepo repository;
    private final SavedStateHandle savedStateHandle;
    private MutableLiveData<List<NotiVO>> notiVOList = new MutableLiveData<>();


    @ViewModelInject
    NotiViewModel(
            LGNRepo repository,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;
    }

    public void reqNotiVoList(final List<NotiVO> list){
        notiVOList.setValue(list);
    }
}