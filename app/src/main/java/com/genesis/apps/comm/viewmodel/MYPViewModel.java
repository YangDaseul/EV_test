package com.genesis.apps.comm.viewmodel;

import com.genesis.apps.comm.model.api.gra.MYP_0001;
import com.genesis.apps.comm.model.api.gra.MYP_0004;
import com.genesis.apps.comm.model.api.gra.MYP_0005;
import com.genesis.apps.comm.model.api.gra.MYP_1003;
import com.genesis.apps.comm.model.api.gra.MYP_1005;
import com.genesis.apps.comm.model.api.gra.MYP_1006;
import com.genesis.apps.comm.model.api.gra.MYP_2001;
import com.genesis.apps.comm.model.api.gra.MYP_2002;
import com.genesis.apps.comm.model.api.gra.MYP_2003;
import com.genesis.apps.comm.model.api.gra.MYP_2004;
import com.genesis.apps.comm.model.api.gra.MYP_2005;
import com.genesis.apps.comm.model.api.gra.MYP_2006;
import com.genesis.apps.comm.model.api.gra.MYP_8001;
import com.genesis.apps.comm.model.api.gra.MYP_8004;
import com.genesis.apps.comm.model.api.gra.MYP_8005;
import com.genesis.apps.comm.model.repo.CardRepository;
import com.genesis.apps.comm.model.repo.DBContentsRepository;
import com.genesis.apps.comm.model.repo.DBUserRepo;
import com.genesis.apps.comm.model.repo.MYPRepo;
import com.genesis.apps.comm.model.vo.CardVO;
import com.genesis.apps.comm.model.vo.FamilyAppVO;
import com.genesis.apps.comm.model.vo.PrivilegeVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.net.ga.GA;
import com.genesis.apps.comm.util.excutor.ExecutorService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import lombok.Data;

import static com.genesis.apps.comm.model.vo.PrivilegeVO.JOIN_CODE_UNABLE_APPLY;

public @Data
class MYPViewModel extends ViewModel {

    private final MYPRepo repository;
    private final DBUserRepo dbUserRepo;
    private final CardRepository cardRepository;
    private final DBContentsRepository dbContentsRepository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<MYP_0001.Response>> RES_MYP_0001;
    private MutableLiveData<NetUIResponse<MYP_0004.Response>> RES_MYP_0004;
    private MutableLiveData<NetUIResponse<MYP_0005.Response>> RES_MYP_0005;

    private MutableLiveData<NetUIResponse<MYP_1003.Response>> RES_MYP_1003;
    private MutableLiveData<NetUIResponse<MYP_1005.Response>> RES_MYP_1005;
    private MutableLiveData<NetUIResponse<MYP_1006.Response>> RES_MYP_1006;

    private MutableLiveData<NetUIResponse<MYP_8001.Response>> RES_MYP_8001;
    private MutableLiveData<NetUIResponse<MYP_8004.Response>> RES_MYP_8004;
    private MutableLiveData<NetUIResponse<MYP_8005.Response>> RES_MYP_8005;

    private MutableLiveData<NetUIResponse<MYP_2001.Response>> RES_MYP_2001;
    private MutableLiveData<NetUIResponse<MYP_2002.Response>> RES_MYP_2002;
    private MutableLiveData<NetUIResponse<MYP_2003.Response>> RES_MYP_2003;
    private MutableLiveData<NetUIResponse<MYP_2004.Response>> RES_MYP_2004;
    private MutableLiveData<NetUIResponse<MYP_2005.Response>> RES_MYP_2005;
    private MutableLiveData<NetUIResponse<MYP_2006.Response>> RES_MYP_2006;

    private MutableLiveData<NetUIResponse<List<CardVO>>> cardVoList;

    @ViewModelInject
    MYPViewModel(
            MYPRepo repository,
            DBUserRepo dbUserRepo,
            CardRepository cardRepository,
            DBContentsRepository dbContentsRepository,
            @Assisted SavedStateHandle savedStateHandle)
    {
        this.savedStateHandle = savedStateHandle;
        this.dbContentsRepository = dbContentsRepository;
        this.repository = repository;
        this.dbUserRepo = dbUserRepo;
        RES_MYP_0001 = repository.RES_MYP_0001;
        RES_MYP_0004 = repository.RES_MYP_0004;
        RES_MYP_0005 = repository.RES_MYP_0005;

        RES_MYP_1003 = repository.RES_MYP_1003;
        RES_MYP_1005 = repository.RES_MYP_1005;
        RES_MYP_1006 = repository.RES_MYP_1006;
        RES_MYP_8001 = repository.RES_MYP_8001;
        RES_MYP_8004 = repository.RES_MYP_8004;
        RES_MYP_8005 = repository.RES_MYP_8005;
        RES_MYP_2001 = repository.RES_MYP_2001;
        RES_MYP_2002 = repository.RES_MYP_2002;
        RES_MYP_2003 = repository.RES_MYP_2003;
        RES_MYP_2004 = repository.RES_MYP_2004;
        RES_MYP_2005 = repository.RES_MYP_2005;
        RES_MYP_2006 = repository.RES_MYP_2006;

        this.cardRepository = cardRepository;
        cardVoList = cardRepository.cardVoList;

    }

    public void reqMYP0001(final MYP_0001.Request reqData){
        repository.REQ_MYP_0001(reqData);
    }

    public void reqMYP0004(final MYP_0004.Request reqData){
        repository.REQ_MYP_0004(reqData);
    }

    public void reqMYP0005(final MYP_0005.Request reqData){
        repository.REQ_MYP_0005(reqData);
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
    public void reqMYP2003(final MYP_2003.Request reqData){
        repository.REQ_MYP_2003(reqData);
    }
    public void reqMYP2004(final MYP_2004.Request reqData){
        repository.REQ_MYP_2004(reqData);
    }
    public void reqMYP2005(final MYP_2005.Request reqData){
        repository.REQ_MYP_2005(reqData);
    }

    public void reqMYP2006(final MYP_2006.Request reqData){
       repository.REQ_MYP_2006(reqData);
    }

    public void reqNewCardList(final List<CardVO> cardVOList){
        cardRepository.getNewCardList(cardVOList);
    }

    public void reqChangeFavoriteCard(String cardNo, final List<CardVO> cardVOList){
        if(cardRepository.updateCard(cardNo))
            cardRepository.getNewCardList(cardVOList);
    }

    public List<FamilyAppVO> getFamilyAppList(){
        return dbContentsRepository.getFamilyApp();
    }

    public List<PrivilegeVO> getPossibleApplyPrivilegeList(List<PrivilegeVO> list){

        List<PrivilegeVO> applyList;

        try{
            applyList = list.stream().filter(data -> !data.getJoinPsblCd().equalsIgnoreCase(JOIN_CODE_UNABLE_APPLY)).collect(Collectors.toList());
        }catch (Exception e){
            applyList = new ArrayList<>();
        }

        return applyList;
    }

    public String reqSingOut(GA ga) throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<String> future = es.getListeningExecutorService().submit(()->{
            String result = null;
            try {
                result = ga.getSignoutUrl();
            } catch (Exception ignore) {
                ignore.printStackTrace();
                result = null;
            }
            return result;
        });

        try {
            return future.get();
        }finally {
            es.shutDownExcutor();
        }
    }

    public boolean clearUserInfo(){
        return dbUserRepo.clearUserInfo();
    }

}