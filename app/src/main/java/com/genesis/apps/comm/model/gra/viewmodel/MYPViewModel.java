package com.genesis.apps.comm.model.gra.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.gra.MYP_0001;
import com.genesis.apps.comm.model.gra.MYP_1003;
import com.genesis.apps.comm.model.gra.MYP_1005;
import com.genesis.apps.comm.model.gra.MYP_1006;
import com.genesis.apps.comm.model.gra.MYP_2001;
import com.genesis.apps.comm.model.gra.MYP_2002;
import com.genesis.apps.comm.model.gra.MYP_2005;
import com.genesis.apps.comm.model.gra.MYP_2006;
import com.genesis.apps.comm.model.gra.MYP_8001;
import com.genesis.apps.comm.model.gra.MYP_8004;
import com.genesis.apps.comm.model.gra.MYP_8005;
import com.genesis.apps.comm.model.gra.OIL_0001;
import com.genesis.apps.comm.model.gra.OIL_0002;
import com.genesis.apps.comm.model.gra.OIL_0003;
import com.genesis.apps.comm.model.gra.OIL_0004;
import com.genesis.apps.comm.model.gra.OIL_0005;
import com.genesis.apps.comm.model.gra.repo.MYPRepo;
import com.genesis.apps.comm.model.gra.repo.OILRepo;
import com.genesis.apps.comm.model.main.myg.CardRepository;
import com.genesis.apps.comm.model.vo.CardVO;
import com.genesis.apps.comm.net.NetUIResponse;

import java.util.List;

import lombok.Data;

public @Data
class MYPViewModel extends ViewModel {

    private final MYPRepo repository;
    private final OILRepo oIlRepository;
    private final CardRepository cardRepository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<MYP_0001.Response>> RES_MYP_0001;

    private MutableLiveData<NetUIResponse<MYP_1003.Response>> RES_MYP_1003;
    private MutableLiveData<NetUIResponse<MYP_1005.Response>> RES_MYP_1005;
    private MutableLiveData<NetUIResponse<MYP_1006.Response>> RES_MYP_1006;

    private MutableLiveData<NetUIResponse<MYP_8001.Response>> RES_MYP_8001;
    private MutableLiveData<NetUIResponse<MYP_8004.Response>> RES_MYP_8004;
    private MutableLiveData<NetUIResponse<MYP_8005.Response>> RES_MYP_8005;

    private MutableLiveData<NetUIResponse<MYP_2001.Response>> RES_MYP_2001;
    private MutableLiveData<NetUIResponse<MYP_2002.Response>> RES_MYP_2002;
    private MutableLiveData<NetUIResponse<MYP_2005.Response>> RES_MYP_2005;
    
    private MutableLiveData<NetUIResponse<MYP_2006.Response>> RES_MYP_2006;

//    public final LiveData<VehicleVO> carVO = Transformations.map(RES_LGN_0001, input -> input.data.getCarVO());
//    public final LiveData<VehicleVO> carVO =
//            Transformations.switchMap(RES_LGN_0001, new Function<NetUIResponse<LGN_0001.Response>, LiveData<VehicleVO>>() {
//                @Override
//                public LiveData<VehicleVO> apply(NetUIResponse<LGN_0001.Response> input) {
//                    return input.data.getCarVO(); repo에서 getcarvo로 가저올수있는.. 다른걸 요청 가능
//                }
//            });

    private MutableLiveData<NetUIResponse<OIL_0001.Response>> RES_OIL_0001;
    private MutableLiveData<NetUIResponse<OIL_0002.Response>> RES_OIL_0002;
    private MutableLiveData<NetUIResponse<OIL_0003.Response>> RES_OIL_0003;
    private MutableLiveData<NetUIResponse<OIL_0004.Response>> RES_OIL_0004;
    private MutableLiveData<NetUIResponse<OIL_0005.Response>> RES_OIL_0005;


    private MutableLiveData<NetUIResponse<List<CardVO>>> cardVoList;

    @ViewModelInject
    MYPViewModel(
            MYPRepo repository,
            OILRepo oIlRepository,
            CardRepository cardRepository,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.savedStateHandle = savedStateHandle;

        this.repository = repository;
        RES_MYP_0001 = repository.RES_MYP_0001;
        RES_MYP_1003 = repository.RES_MYP_1003;
        RES_MYP_1005 = repository.RES_MYP_1005;
        RES_MYP_1006 = repository.RES_MYP_1006;
        RES_MYP_8001 = repository.RES_MYP_8001;
        RES_MYP_8004 = repository.RES_MYP_8004;
        RES_MYP_8005 = repository.RES_MYP_8005;
        RES_MYP_2001 = repository.RES_MYP_2001;
        RES_MYP_2002 = repository.RES_MYP_2002;
        RES_MYP_2005 = repository.RES_MYP_2005;
        RES_MYP_2006 = repository.RES_MYP_2006;

        this.oIlRepository = oIlRepository;
        RES_OIL_0001 = oIlRepository.RES_OIL_0001;
        RES_OIL_0002 = oIlRepository.RES_OIL_0002;
        RES_OIL_0003 = oIlRepository.RES_OIL_0003;
        RES_OIL_0004 = oIlRepository.RES_OIL_0004;
        RES_OIL_0005 = oIlRepository.RES_OIL_0005;


        this.cardRepository = cardRepository;
        cardVoList = cardRepository.cardVoList;

    }

    public void reqMYP0001(final MYP_0001.Request reqData){
        repository.REQ_MYP_0001(reqData);
    }

    public void reqMYP1003(final MYP_1003.Request reqData){
        repository.REQ_MYP_1003(reqData);
    }

    public void reqMYP1005(final MYP_1005.Request reqData){
        repository.REQ_MYP_1005(reqData);
    }

    public void reqMYP1006(final MYP_1006.Request reqData){
        repository.REQ_MYP_1006(reqData);
    }


    public void reqMYP8001(final MYP_8001.Request reqData){
        repository.REQ_MYP_8001(reqData);
    }

    public void reqMYP8004(final MYP_8004.Request reqData){
        repository.REQ_MYP_8004(reqData);
    }

    public void reqMYP8005(final MYP_8005.Request reqData){
        repository.REQ_MYP_8005(reqData);
    }

    public void reqMYP2001(final MYP_2001.Request reqData){
        repository.REQ_MYP_2001(reqData);
    }
    public void reqMYP2002(final MYP_2002.Request reqData){
        repository.REQ_MYP_2002(reqData);
    }

    public void reqMYP2005(final MYP_2005.Request reqData){
        repository.REQ_MYP_2005(reqData);
    }

    public void reqMYP2006(final MYP_2006.Request reqData){
       repository.REQ_MYP_2006(reqData);
    }


    public void reqOIL0001(final OIL_0001.Request reqData){
        oIlRepository.REQ_OIL_0001(reqData);
    }
    public void reqOIL0002(final OIL_0002.Request reqData){
        oIlRepository.REQ_OIL_0002(reqData);
    }
    public void reqOIL0003(final OIL_0003.Request reqData){
        oIlRepository.REQ_OIL_0003(reqData);
    }
    public void reqOIL0004(final OIL_0004.Request reqData){
        oIlRepository.REQ_OIL_0004(reqData);
    }
    public void reqOIL0005(final OIL_0005.Request reqData){
        oIlRepository.REQ_OIL_0005(reqData);
    }

    public void reqNewCardList(final List<CardVO> cardVOList){
        cardRepository.getNewCardList(cardVOList);
    }

    public void reqChangeFavoriteCard(String cardNo, final List<CardVO> cardVOList){
        if(cardRepository.updateCard(cardNo))
            cardRepository.getNewCardList(cardVOList);
    }

}