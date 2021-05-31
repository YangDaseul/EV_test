package com.genesis.apps.comm.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.api.gra.DTW_1001;
import com.genesis.apps.comm.model.api.gra.DTW_1002;
import com.genesis.apps.comm.model.api.gra.DTW_1003;
import com.genesis.apps.comm.model.api.gra.DTW_1004;
import com.genesis.apps.comm.model.api.gra.DTW_1007;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.repo.DBVehicleRepository;
import com.genesis.apps.comm.model.repo.DTWRepo;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.carlife.PayInfoVO;
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
class DTWViewModel extends ViewModel {

    private final DTWRepo repository;
    private final DBVehicleRepository dbVehicleRepository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<DTW_1001.Response>> RES_DTW_1001;
    private MutableLiveData<NetUIResponse<DTW_1002.Response>> RES_DTW_1002;
    private MutableLiveData<NetUIResponse<DTW_1003.Response>> RES_DTW_1003;
    private MutableLiveData<NetUIResponse<DTW_1004.Response>> RES_DTW_1004;
    private MutableLiveData<NetUIResponse<DTW_1007.Response>> RES_DTW_1007;

    private MutableLiveData<List<VehicleVO>> vehicleList;

    @ViewModelInject
    DTWViewModel(
            DTWRepo repository,
            DBVehicleRepository dbVehicleRepository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.dbVehicleRepository = dbVehicleRepository;
        this.savedStateHandle = savedStateHandle;

        RES_DTW_1001 = repository.RES_DTW_1001;
        RES_DTW_1002 = repository.RES_DTW_1002;
        RES_DTW_1003 = repository.RES_DTW_1003;
        RES_DTW_1004 = repository.RES_DTW_1004;
        RES_DTW_1007 = repository.RES_DTW_1007;

        vehicleList = new MutableLiveData<>();
    }

    public void reqDTW1001(final DTW_1001.Request reqData) {
        repository.REQ_DTW_1001(reqData);
    }
    public void reqDTW1002(final DTW_1002.Request reqData) {
        repository.REQ_DTW_1002(reqData);
    }
    public void reqDTW1003(final DTW_1003.Request reqData) {
        repository.REQ_DTW_1003(reqData);
    }
    public void reqDTW1004(final DTW_1004.Request reqData) {
        repository.REQ_DTW_1004(reqData);
    }
    public void reqDTW1007(final DTW_1007.Request reqData) {
        repository.REQ_DTW_1007(reqData);
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

    public boolean isValidDTW1001(){
        return RES_DTW_1001!=null&&RES_DTW_1001.getValue()!=null&&RES_DTW_1001.getValue().data!=null;
    }

    /**
     * 간편결제 가입 여부
     *
     * @return
     */
    public boolean isPayInfo() {
        boolean isJoin = false;
        if (isValidDTW1001() && RES_DTW_1001.getValue().data.getPayInfo() != null) {
            try {
                PayInfoVO payInfoVO = RES_DTW_1001.getValue().data.getPayInfo();
                // TODO : 회원가입상태가 y고 연동된 카드수가 1개 이상이면 Y, 등록 카드 갯수도 체크할지 확인 필요!!
                isJoin = VariableType.COMMON_MEANS_YES.equalsIgnoreCase(StringUtil.isValidString(payInfoVO.getSignInYn())) && StringUtil.isValidInteger(payInfoVO.getCardCount()) > 0;
            } catch (Exception e) {
                isJoin = false;
            }
        }
        return isJoin;
    }


    public boolean isValidDTW1003(){
        return RES_DTW_1003!=null&&RES_DTW_1003.getValue()!=null&&RES_DTW_1003.getValue().data!=null;
    }

    /**
     * 간편결제 카드 리스트
     *
     * @return
     */
    public List<PaymtCardVO> getPaymtCardList() {

        List<PaymtCardVO> list = new ArrayList<>();

        if(isValidDTW1003()){
            try{
                list.addAll(RES_DTW_1003.getValue().data.getCardList());
            }catch (Exception e){
                list = new ArrayList<>();
            }
        }
        return list;
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

    public PaymtCardVO getPaymtCardVO(String cardNm) {

        if (isValidDTW1003() && RES_DTW_1003.getValue().data.getCardList() != null) {
            for (PaymtCardVO vo : RES_DTW_1003.getValue().data.getCardList()) {
                if (vo.getCardName().equalsIgnoreCase(cardNm)) {
                    return vo;
                }
            }
        }

        return null;
    }
}