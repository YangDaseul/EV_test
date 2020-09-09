package com.genesis.apps.comm.model.gra.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.gra.repo.LGNRepo;
import com.genesis.apps.comm.model.vo.NotiVO;

import java.util.List;

import lombok.Data;

public @Data
class NotiViewModel extends ViewModel {

    private final LGNRepo repository;
    private final SavedStateHandle savedStateHandle;

//    private MutableLiveData<NetUIResponse<LGN_0001.Response>> RES_LGN_0001;
//    private MutableLiveData<NetUIResponse<LGN_0002.Response>> RES_LGN_0002;
//    private MutableLiveData<NetUIResponse<LGN_0003.Response>> RES_LGN_0003;
//    private MutableLiveData<NetUIResponse<LGN_0004.Response>> RES_LGN_0004;
//    private MutableLiveData<NetUIResponse<LGN_0005.Response>> RES_LGN_0005;
//
//    public final LiveData<VehicleVO> carVO = Transformations.map(RES_LGN_0001, input -> input.data.getCarVO());

    private MutableLiveData<List<NotiVO>> notiVOList = new MutableLiveData<>();


    @ViewModelInject
    NotiViewModel(
            LGNRepo repository,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;

//        RES_LGN_0001 = repository.RES_LGN_0001;
//        RES_LGN_0002 = repository.RES_LGN_0002;
//        RES_LGN_0003 = repository.RES_LGN_0003;
//        RES_LGN_0004 = repository.RES_LGN_0004;
//        RES_LGN_0005 = repository.RES_LGN_0005;
    }

//    public void reqLGN0001(final LGN_0001.Request reqData){
//        RES_LGN_0001.setValue(repository.REQ_LGN_0001(reqData).getValue());
//    }

    public void reqNotiVoList(final List<NotiVO> list){
        notiVOList.setValue(list);
    }

//    public void updateNotiVO(NotiVO data, int position){
//        for(int i=0; i<notiVOList.getValue().size(); i++){
//            if(i==position){
//                notiVOList.getValue().get(i).setOpen(data.isOpen() ? false : true);
//                notiVOList.setValue(notiVOList.getValue());
//                break;
//            }
//        }
//    }
}