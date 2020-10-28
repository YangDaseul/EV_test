package com.genesis.apps.comm.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.repo.CWHRepo;

import lombok.Data;

//CarWashHistory : 세차 예약 내역 뷰 모델
public @Data
class CWHViewModel extends ViewModel {
    private final CWHRepo repository;
    private final SavedStateHandle savedStateHandle;


    @ViewModelInject
    CWHViewModel(
            CWHRepo repository,
            @Assisted SavedStateHandle savedStateHandle) {

        this.repository = repository;
        this.savedStateHandle = savedStateHandle;

//        RES_LGN_0001 = repository.RES_LGN_0001;

    }
}
