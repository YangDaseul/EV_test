package com.genesis.apps.comm.viewmodel;

import android.text.TextUtils;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.api.gra.CHB_1002;
import com.genesis.apps.comm.model.api.gra.CHB_1003;
import com.genesis.apps.comm.model.api.gra.CHB_1004;
import com.genesis.apps.comm.model.api.gra.CHB_1005;
import com.genesis.apps.comm.model.api.gra.CHB_1006;
import com.genesis.apps.comm.model.api.gra.CHB_1007;
import com.genesis.apps.comm.model.api.gra.CHB_1008;
import com.genesis.apps.comm.model.api.gra.CHB_1009;
import com.genesis.apps.comm.model.api.gra.CHB_1010;
import com.genesis.apps.comm.model.api.gra.CHB_1011;
import com.genesis.apps.comm.model.api.gra.CHB_1013;
import com.genesis.apps.comm.model.api.gra.CHB_1015;
import com.genesis.apps.comm.model.api.gra.CHB_1016;
import com.genesis.apps.comm.model.api.gra.CHB_1017;
import com.genesis.apps.comm.model.api.gra.CHB_1019;
import com.genesis.apps.comm.model.api.gra.CHB_1020;
import com.genesis.apps.comm.model.api.gra.CHB_1021;
import com.genesis.apps.comm.model.api.gra.CHB_1022;
import com.genesis.apps.comm.model.api.gra.CHB_1023;
import com.genesis.apps.comm.model.api.gra.CHB_1024;
import com.genesis.apps.comm.model.api.gra.CHB_1025;
import com.genesis.apps.comm.model.api.gra.CHB_1026;
import com.genesis.apps.comm.model.api.gra.CHB_1027;
import com.genesis.apps.comm.model.api.gra.EVL_1001;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.repo.CHBRepo;
import com.genesis.apps.comm.model.repo.DBVehicleRepository;
import com.genesis.apps.comm.model.repo.EVLRepo;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.carlife.OptionVO;
import com.genesis.apps.comm.model.vo.carlife.PaymtCardVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.util.excutor.ExecutorService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import lombok.Data;

import static java.util.stream.Collectors.toList;

public @Data
class CHBViewModel extends ViewModel {

    private final CHBRepo repository;
    private final EVLRepo evlRepo;
    private final DBVehicleRepository dbVehicleRepository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<CHB_1002.Response>> RES_CHB_1002;
    private MutableLiveData<NetUIResponse<CHB_1003.Response>> RES_CHB_1003;
    private MutableLiveData<NetUIResponse<CHB_1004.Response>> RES_CHB_1004;
    private MutableLiveData<NetUIResponse<CHB_1005.Response>> RES_CHB_1005;
    private MutableLiveData<NetUIResponse<CHB_1006.Response>> RES_CHB_1006;
    private MutableLiveData<NetUIResponse<CHB_1007.Response>> RES_CHB_1007;
    private MutableLiveData<NetUIResponse<CHB_1008.Response>> RES_CHB_1008;
    private MutableLiveData<NetUIResponse<CHB_1009.Response>> RES_CHB_1009;
    private MutableLiveData<NetUIResponse<CHB_1010.Response>> RES_CHB_1010;

    private MutableLiveData<NetUIResponse<CHB_1011.Response>> RES_CHB_1011;
    private MutableLiveData<NetUIResponse<CHB_1013.Response>> RES_CHB_1013;
    private MutableLiveData<NetUIResponse<CHB_1015.Response>> RES_CHB_1015;
    private MutableLiveData<NetUIResponse<CHB_1016.Response>> RES_CHB_1016;
    private MutableLiveData<NetUIResponse<CHB_1017.Response>> RES_CHB_1017;

    private MutableLiveData<NetUIResponse<CHB_1019.Response>> RES_CHB_1019;
    private MutableLiveData<NetUIResponse<CHB_1020.Response>> RES_CHB_1020;
    private MutableLiveData<NetUIResponse<CHB_1021.Response>> RES_CHB_1021;
    private MutableLiveData<NetUIResponse<CHB_1022.Response>> RES_CHB_1022;
    private MutableLiveData<NetUIResponse<CHB_1023.Response>> RES_CHB_1023;
    private MutableLiveData<NetUIResponse<CHB_1024.Response>> RES_CHB_1024;
    private MutableLiveData<NetUIResponse<CHB_1025.Response>> RES_CHB_1025;
    private MutableLiveData<NetUIResponse<CHB_1026.Response>> RES_CHB_1026;
    private MutableLiveData<NetUIResponse<CHB_1027.Response>> RES_CHB_1027;

    private MutableLiveData<NetUIResponse<EVL_1001.Response>> RES_EVL_1001;

    private MutableLiveData<List<VehicleVO>> vehicleList;

    @ViewModelInject
    CHBViewModel(
            CHBRepo repository,
            EVLRepo evlRepo,
            DBVehicleRepository dbVehicleRepository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.evlRepo = evlRepo;
        this.repository = repository;
        this.dbVehicleRepository = dbVehicleRepository;
        this.savedStateHandle = savedStateHandle;

        RES_EVL_1001 = evlRepo.RES_EVL_1001;

        RES_CHB_1002 = repository.RES_CHB_1002;
        RES_CHB_1003 = repository.RES_CHB_1003;
        RES_CHB_1004 = repository.RES_CHB_1004;
        RES_CHB_1005 = repository.RES_CHB_1005;
        RES_CHB_1006 = repository.RES_CHB_1006;
        RES_CHB_1007 = repository.RES_CHB_1007;
        RES_CHB_1008 = repository.RES_CHB_1008;
        RES_CHB_1009 = repository.RES_CHB_1009;
        RES_CHB_1010 = repository.RES_CHB_1010;
        RES_CHB_1011 = repository.RES_CHB_1011;
        RES_CHB_1013 = repository.RES_CHB_1013;

        RES_CHB_1015 = repository.RES_CHB_1015;
        RES_CHB_1016 = repository.RES_CHB_1016;
        RES_CHB_1017 = repository.RES_CHB_1017;

        RES_CHB_1019 = repository.RES_CHB_1019;
        RES_CHB_1020 = repository.RES_CHB_1020;
        RES_CHB_1021 = repository.RES_CHB_1021;
        RES_CHB_1022 = repository.RES_CHB_1022;
        RES_CHB_1023 = repository.RES_CHB_1023;
        RES_CHB_1024 = repository.RES_CHB_1024;
        RES_CHB_1025 = repository.RES_CHB_1025;
        RES_CHB_1026 = repository.RES_CHB_1026;
        RES_CHB_1027 = repository.RES_CHB_1027;

        vehicleList = new MutableLiveData<>();
    }
    public void reqEVL1001(final EVL_1001.Request reqData) {
        evlRepo.REQ_EVL_1001(reqData);
    }

    public void reqCHB1002(final CHB_1002.Request reqData) {
        repository.REQ_CHB_1002(reqData);
    }
    public void reqCHB1003(final CHB_1003.Request reqData) {
        repository.REQ_CHB_1003(reqData);
    }
    public void reqCHB1004(final CHB_1004.Request reqData) {
        repository.REQ_CHB_1004(reqData);
    }
    public void reqCHB1005(final CHB_1005.Request reqData) {
        repository.REQ_CHB_1005(reqData);
    }
    public void reqCHB1006(final CHB_1006.Request reqData) {
        repository.REQ_CHB_1006(reqData);
    }
    public void reqCHB1007(final CHB_1007.Request reqData) {
        repository.REQ_CHB_1007(reqData);
    }
    public void reqCHB1008(final CHB_1008.Request reqData) {
        repository.REQ_CHB_1008(reqData);
    }
    public void reqCHB1009(final CHB_1009.Request reqData) {
        repository.REQ_CHB_1009(reqData);
    }
    public void reqCHB1010(final CHB_1010.Request reqData) {
        repository.REQ_CHB_1010(reqData);
    }
    public void reqCHB1011(final CHB_1011.Request reqData) {
        repository.REQ_CHB_1011(reqData);
    }
    public void reqCHB1013(final CHB_1013.Request reqData) {
        repository.REQ_CHB_1013(reqData);
    }
    public void reqCHB1015(final CHB_1015.Request reqData) {
        repository.REQ_CHB_1015(reqData);
    }
    public void reqCHB1016(final CHB_1016.Request reqData) {
        repository.REQ_CHB_1016(reqData);
    }
    public void reqCHB1017(final CHB_1017.Request reqData) {
        repository.REQ_CHB_1017(reqData);
    }

    public void reqCHB1019(final CHB_1019.Request reqData) {
        repository.REQ_CHB_1019(reqData);
    }
    public void reqCHB1020(final CHB_1020.Request reqData) {
        repository.REQ_CHB_1020(reqData);
    }
    public void reqCHB1021(final CHB_1021.Request reqData) {
        repository.REQ_CHB_1021(reqData);
    }
    public void reqCHB1022(final CHB_1022.Request reqData) {
        repository.REQ_CHB_1022(reqData);
    }
    public void reqCHB1023(final CHB_1023.Request reqData) {
        repository.REQ_CHB_1023(reqData);
    }
    public void reqCHB1024(final CHB_1024.Request reqData) {
        repository.REQ_CHB_1024(reqData);
    }
    public void reqCHB1025(final CHB_1025.Request reqData) {
        repository.REQ_CHB_1025(reqData);
    }
    public void reqCHB1026(final CHB_1026.Request reqData) {
        repository.REQ_CHB_1026(reqData);
    }
    public void reqCHB1027(final CHB_1027.Request reqData) {
        repository.REQ_CHB_1027(reqData);
    }

    public VehicleVO getMainVehicleFromDB() throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<VehicleVO> future = es.getListeningExecutorService().submit(() -> {
            VehicleVO vehicleVO = null;
            try {
                vehicleVO = dbVehicleRepository.getMainVehicleFromDB();
            } catch (Exception ignore) {
                ignore.printStackTrace();
                vehicleVO = null;
            }
            return vehicleVO;
        });

        try {
            return future.get();
        } finally {
            es.shutDownExcutor();
        }
    }

    /**
     * 결제 수단 카드명 조회
     * @param paymtCardVOList
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<String> getPaymtCardNm(List<PaymtCardVO> paymtCardVOList) throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<List<String>> future = es.getListeningExecutorService().submit(() -> {
            List<String> list = new ArrayList<>();
            try {
                list = paymtCardVOList.stream().map(PaymtCardVO::getCardName).collect(toList());
            } catch (Exception ignore) {
                ignore.printStackTrace();
                list = null;
            }
            return list;
        });

        try {
            return future.get();
        } finally {
            es.shutDownExcutor();
        }
    }

    /**
     * 옵션 VO 조회
     * @param optCd
     * @return
     */
    public OptionVO getOptionVO(String optCd, List<OptionVO> optionList) {

        if (!TextUtils.isEmpty(optCd)) {
            for (OptionVO optVo : optionList) {
                if (optVo.getOptionCode().equalsIgnoreCase(optCd)) {
                    return optVo;
                }
            }
        }

        return null;
    }

    public boolean isValidCHB1015(){
        return RES_CHB_1015!=null&&RES_CHB_1015.getValue()!=null&&RES_CHB_1015.getValue().data!=null;
    }

    public boolean isSignInYN() {
//        return true;
        return isValidCHB1015() &&
                StringUtil.isValidString(RES_CHB_1015.getValue().data.getSignInYN()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES);
    }
}