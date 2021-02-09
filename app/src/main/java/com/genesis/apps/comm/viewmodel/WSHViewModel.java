package com.genesis.apps.comm.viewmodel;

import com.genesis.apps.comm.model.api.gra.EVL_1001;
import com.genesis.apps.comm.model.api.gra.WSH_1001;
import com.genesis.apps.comm.model.api.gra.WSH_1002;
import com.genesis.apps.comm.model.api.gra.WSH_1003;
import com.genesis.apps.comm.model.api.gra.WSH_1004;
import com.genesis.apps.comm.model.api.gra.WSH_1005;
import com.genesis.apps.comm.model.api.gra.WSH_1006;
import com.genesis.apps.comm.model.api.gra.WSH_1007;
import com.genesis.apps.comm.model.api.gra.WSH_1008;
import com.genesis.apps.comm.model.repo.EVLRepo;
import com.genesis.apps.comm.model.repo.WSHRepo;
import com.genesis.apps.comm.model.vo.WashReserveVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.excutor.ExecutorService;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import lombok.Data;

//CarWashHistory : 세차 예약 내역 뷰 모델
public @Data
class WSHViewModel extends ViewModel {
    public static final String SONAX = "SONAX";

    private final WSHRepo repository;
    private final EVLRepo evlRepo;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<WSH_1001.Response>> RES_WSH_1001;
    private MutableLiveData<NetUIResponse<WSH_1002.Response>> RES_WSH_1002;
    private MutableLiveData<NetUIResponse<WSH_1003.Response>> RES_WSH_1003;
    private MutableLiveData<NetUIResponse<WSH_1004.Response>> RES_WSH_1004;
    private MutableLiveData<NetUIResponse<WSH_1005.Response>> RES_WSH_1005;
    private MutableLiveData<NetUIResponse<WSH_1006.Response>> RES_WSH_1006;
    private MutableLiveData<NetUIResponse<WSH_1007.Response>> RES_WSH_1007;
    private MutableLiveData<NetUIResponse<WSH_1008.Response>> RES_WSH_1008;

    private MutableLiveData<NetUIResponse<EVL_1001.Response>> RES_EVL_1001;

    @ViewModelInject
    WSHViewModel(
            WSHRepo repository,
            EVLRepo evlRepo,
            @Assisted SavedStateHandle savedStateHandle) {

        this.repository = repository;
        this.evlRepo = evlRepo;
        this.savedStateHandle = savedStateHandle;

        RES_WSH_1001 = repository.RES_WSH_1001;
        RES_WSH_1002 = repository.RES_WSH_1002;
        RES_WSH_1003 = repository.RES_WSH_1003;
        RES_WSH_1004 = repository.RES_WSH_1004;
        RES_WSH_1005 = repository.RES_WSH_1005;
        RES_WSH_1006 = repository.RES_WSH_1006;
        RES_WSH_1007 = repository.RES_WSH_1007;
        RES_WSH_1008 = repository.RES_WSH_1008;
        RES_EVL_1001 = evlRepo.RES_EVL_1001;

    }

    public void reqWSH1001(final WSH_1001.Request reqData) {
        repository.REQ_WSH_1001(reqData);
    }

    public void reqWSH1002(final WSH_1002.Request reqData) {
        repository.REQ_WSH_1002(reqData);
    }

    public void reqWSH1003(final WSH_1003.Request reqData) {
        repository.REQ_WSH_1003(reqData);
    }

    public void reqWSH1004(final WSH_1004.Request reqData) {
        repository.REQ_WSH_1004(reqData);
    }

    public void reqWSH1005(final WSH_1005.Request reqData) {
        repository.REQ_WSH_1005(reqData);
    }

    public void reqWSH1006(final WSH_1006.Request reqData) {
        repository.REQ_WSH_1006(reqData);
    }

    public void reqWSH1007(final WSH_1007.Request reqData) {
        repository.REQ_WSH_1007(reqData);
    }

    public void reqWSH1008(final WSH_1008.Request reqData) {
        repository.REQ_WSH_1008(reqData);
    }

    public void reqEVL1001(final EVL_1001.Request reqData) {
        evlRepo.REQ_EVL_1001(reqData);
    }


    public List<WashReserveVO> getWashList(List<WashReserveVO> oriList) throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<List<WashReserveVO>> future = es.getListeningExecutorService().submit(() -> {

            final List<String> distinctRsvtDtm = oriList.stream().map(x -> x.getRsvtDtm().substring(0, 8)).distinct().sorted().collect(Collectors.toList());
            try {

                for (int i = 0; i < distinctRsvtDtm.size(); i++) {
                    for (int n = 0; n < oriList.size(); n++) {
                        String subRsvtDtm = "";
                        try {
                            subRsvtDtm = oriList.get(n).getRsvtDtm().substring(0, 8);
                        } catch (Exception e) {
                            subRsvtDtm = "";
                        }
                        if (subRsvtDtm.equalsIgnoreCase(distinctRsvtDtm.get(i))) {
                            oriList.get(n).setFirst(true);
                            break;
                        }
                    }
                }
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            oriList.sort(Comparator.comparing(WashReserveVO::getRsvtDtm).reversed());
            return oriList;
        });

        try {
            return future.get();
        } finally {
            //todo 테스트 필요
            es.shutDownExcutor();
        }
    }
    

}
