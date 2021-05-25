package com.genesis.apps.comm.viewmodel;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.api.gra.SOS_1001;
import com.genesis.apps.comm.model.api.gra.SOS_1002;
import com.genesis.apps.comm.model.api.gra.SOS_1003;
import com.genesis.apps.comm.model.api.gra.SOS_1004;
import com.genesis.apps.comm.model.api.gra.SOS_1005;
import com.genesis.apps.comm.model.api.gra.SOS_1006;
import com.genesis.apps.comm.model.api.gra.SOS_3001;
import com.genesis.apps.comm.model.api.gra.SOS_3002;
import com.genesis.apps.comm.model.api.gra.SOS_3003;
import com.genesis.apps.comm.model.api.gra.SOS_3004;
import com.genesis.apps.comm.model.api.gra.SOS_3005;
import com.genesis.apps.comm.model.api.gra.SOS_3006;
import com.genesis.apps.comm.model.api.gra.SOS_3007;
import com.genesis.apps.comm.model.api.gra.SOS_3008;
import com.genesis.apps.comm.model.api.gra.SOS_3011;
import com.genesis.apps.comm.model.api.gra.SOS_3013;
import com.genesis.apps.comm.model.constants.ChargeBtrStatus;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.repo.DBVehicleRepository;
import com.genesis.apps.comm.model.repo.SOSRepo;
import com.genesis.apps.comm.model.vo.TermVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.carlife.PayInfoVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.comm.util.excutor.ExecutorService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import lombok.Data;

public @Data
class SOSViewModel extends ViewModel {

    private final SOSRepo repository;
    private final DBVehicleRepository dbVehicleRepository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<SOS_1001.Response>> RES_SOS_1001;
    private MutableLiveData<NetUIResponse<SOS_1002.Response>> RES_SOS_1002;
    private MutableLiveData<NetUIResponse<SOS_1003.Response>> RES_SOS_1003;
    private MutableLiveData<NetUIResponse<SOS_1004.Response>> RES_SOS_1004;
    private MutableLiveData<NetUIResponse<SOS_1005.Response>> RES_SOS_1005;
    private MutableLiveData<NetUIResponse<SOS_1006.Response>> RES_SOS_1006;

    private MutableLiveData<NetUIResponse<SOS_3001.Response>> RES_SOS_3001;
    private MutableLiveData<NetUIResponse<SOS_3002.Response>> RES_SOS_3002;
    private MutableLiveData<NetUIResponse<SOS_3003.Response>> RES_SOS_3003;
    private MutableLiveData<NetUIResponse<SOS_3004.Response>> RES_SOS_3004;
    private MutableLiveData<NetUIResponse<SOS_3005.Response>> RES_SOS_3005;
    private MutableLiveData<NetUIResponse<SOS_3006.Response>> RES_SOS_3006;
    private MutableLiveData<NetUIResponse<SOS_3007.Response>> RES_SOS_3007;
    private MutableLiveData<NetUIResponse<SOS_3008.Response>> RES_SOS_3008;
    private MutableLiveData<NetUIResponse<SOS_3011.Response>> RES_SOS_3011;
    private MutableLiveData<NetUIResponse<SOS_3013.Response>> RES_SOS_3013;

    private MutableLiveData<List<VehicleVO>> vehicleList;

    @ViewModelInject
    SOSViewModel(
            SOSRepo repository,
            DBVehicleRepository dbVehicleRepository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.dbVehicleRepository = dbVehicleRepository;
        this.savedStateHandle = savedStateHandle;

        RES_SOS_1001 = repository.RES_SOS_1001;
        RES_SOS_1002 = repository.RES_SOS_1002;
        RES_SOS_1003 = repository.RES_SOS_1003;
        RES_SOS_1004 = repository.RES_SOS_1004;
        RES_SOS_1005 = repository.RES_SOS_1005;
        RES_SOS_1006 = repository.RES_SOS_1006;
        RES_SOS_3001 = repository.RES_SOS_3001;
        RES_SOS_3002 = repository.RES_SOS_3002;
        RES_SOS_3003 = repository.RES_SOS_3003;
        RES_SOS_3004 = repository.RES_SOS_3004;
        RES_SOS_3005 = repository.RES_SOS_3005;
        RES_SOS_3006 = repository.RES_SOS_3006;
        RES_SOS_3007 = repository.RES_SOS_3007;
        RES_SOS_3008 = repository.RES_SOS_3008;
        RES_SOS_3011 = repository.RES_SOS_3011;
        RES_SOS_3013 = repository.RES_SOS_3013;
        vehicleList = new MutableLiveData<>();
    }

    public void reqSOS1001(final SOS_1001.Request reqData) {
        repository.REQ_SOS_1001(reqData);
    }

    public void reqSOS1002(final SOS_1002.Request reqData) {
        repository.REQ_SOS_1002(reqData);
    }

    public void reqSOS1003(final SOS_1003.Request reqData) {
        repository.REQ_SOS_1003(reqData);
    }

    public void reqSOS1004(final SOS_1004.Request reqData) {
        repository.REQ_SOS_1004(reqData);
    }

    public void reqSOS1005(final SOS_1005.Request reqData) {
        repository.REQ_SOS_1005(reqData);
    }

    public void reqSOS1006(final SOS_1006.Request reqData) {
        repository.REQ_SOS_1006(reqData);
    }

    public void reqSOS3001(final SOS_3001.Request reqData) {
        repository.REQ_SOS_3001(reqData);
    }

    public void reqSOS3002(final SOS_3002.Request reqData) {
        repository.REQ_SOS_3002(reqData);
    }

    public void reqSOS3003(final SOS_3003.Request reqData) {
        repository.REQ_SOS_3003(reqData);
    }

    public void reqSOS3004(final SOS_3004.Request reqData) {
        repository.REQ_SOS_3004(reqData);
    }

    public void reqSOS3005(final SOS_3005.Request reqData) {
        repository.REQ_SOS_3005(reqData);
    }

    public void reqSOS3006(final SOS_3006.Request reqData) {
        repository.REQ_SOS_3006(reqData);
    }

    public void reqSOS3007(final SOS_3007.Request reqData) {
        repository.REQ_SOS_3007(reqData);
    }

    public void reqSOS3008(final SOS_3008.Request reqData) {
        repository.REQ_SOS_3008(reqData);
    }

    public void reqSOS3011(final SOS_3011.Request reqData) {
        repository.REQ_SOS_3011(reqData);
    }

    public void reqSOS3013(final SOS_3013.Request reqData) {
        repository.REQ_SOS_3013(reqData);
    }
    
    public VehicleVO getMainVehicleFromDB() throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<VehicleVO> future = es.getListeningExecutorService().submit(()->{
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
        }finally {
            es.shutDownExcutor();
        }
    }

    public boolean isValidSOS3001(){
        return RES_SOS_3001!=null&&RES_SOS_3001.getValue()!=null&&RES_SOS_3001.getValue().data!=null&&RES_SOS_3001.getValue()!=null&&RES_SOS_3001.getValue().data!=null;
    }

    //찾아가는 충전 서비스 정보제공동의 유무
    public boolean isTrmsAgmtYn() {
        return isValidSOS3001() &&
                RES_SOS_3001.getValue().data.getEvSvcTerm() != null &&
                StringUtil.isValidString(RES_SOS_3001.getValue().data.getEvSvcTerm().getTrmsAgmtYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES);
    }

    //찾아가는 충전 서비스 신청 가능 유무
    public boolean isUseYn() {
        return isValidSOS3001() &&
                RES_SOS_3001.getValue().data.getSosStus() != null &&
                StringUtil.isValidString(RES_SOS_3001.getValue().data.getSosStus().getUseYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES);
    }

    //찾아가는 충전 서비스 신청 유무
    public boolean isSubspYn() {
        return isValidSOS3001() &&
                RES_SOS_3001.getValue().data.getSosStus() != null &&
                StringUtil.isValidString(RES_SOS_3001.getValue().data.getSosStus().getSubspYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES);
    }

    //찾아가는 충전 서비스 잔여 횟수 확인
    public boolean isCnt() {
        int cnt = 0;

        if(isValidSOS3001() && RES_SOS_3001.getValue().data.getSosStus() != null){
            try{
                cnt = Integer.parseInt(RES_SOS_3001.getValue().data.getSosStus().getDataCount());
            }catch (Exception e){
                cnt = 0;
            }
        }

        return cnt>0;
    }

    //간편결제 가입 및 카드 등록 여부 확인
    public boolean isPayInfo() {
        boolean isJoin=false;
        if(isValidSOS3001() && RES_SOS_3001.getValue().data.getPayInfo() != null){
            try{
                PayInfoVO payInfoVO = RES_SOS_3001.getValue().data.getPayInfo();
                //회원가입상태가 y고 연동된 카드수가 1개 이상이면 Y
                isJoin = VariableType.COMMON_MEANS_YES.equalsIgnoreCase(StringUtil.isValidString(payInfoVO.getSignInYn()))&&StringUtil.isValidInteger(payInfoVO.getCardCount())>0;
            }catch (Exception e){
                isJoin = false;
            }
        }
        return isJoin;
    }


    public String getPgrsStusCd(){
        String pgrsStusCd = "";

        if(isValidSOS3001() && RES_SOS_3001.getValue().data.getSosStus() != null){
            try{
                pgrsStusCd = RES_SOS_3001.getValue().data.getSosStus().getPgrsStusCd();
            }catch (Exception e){
                pgrsStusCd = "";
            }
        }
        return pgrsStusCd;
    }

    public String getTmpAcptNo(){
        String tmpAcptNo = "";

        if(isValidSOS3001() && RES_SOS_3001.getValue().data.getSosStus() != null){
            try{
                tmpAcptNo = RES_SOS_3001.getValue().data.getSosStus().getTmpAcptNo();
            }catch (Exception e){
                tmpAcptNo = "";
            }
        }
        return tmpAcptNo;
    }

    public List<TermVO> getChargeTermVO(){
        List<TermVO> list = new ArrayList<>();

        if(isValidSOS3001() && RES_SOS_3001.getValue().data.getEvSvcTerm() != null){
            try{
                list.addAll(RES_SOS_3001.getValue().data.getEvSvcTerm().getTermList());
            }catch (Exception e){
                list = new ArrayList<>();
            }
        }
        return list;
    }

//    // 픽업앤충전 정보제공동의 유무
//    public boolean isPrvcyInfoAgmtYn() {
//        return isValidSOS3001() &&
//                RES_SOS_3001.getValue().data.getChbStus() != null &&
//                StringUtil.isValidString(RES_SOS_3001.getValue().data.getChbStus().getPrvcyInfoAgmtYn()).equalsIgnoreCase(VariableType.COMMON_MEANS_YES);
//    }

//    public String getChbTermCont() {
//        String termCont = null;
//        if (isValidSOS3001() && RES_SOS_3001.getValue().data.getChbStus() != null) {
//            try {
//                termCont = RES_SOS_3001.getValue().data.getChbStus().getScrnCntn();
//            } catch (Exception e) {
//                termCont = null;
//            }
//        }
//        return termCont;
//    }

    // 픽업앤충전 신청 유무
    public boolean isChbApplyYn() {

        if (isValidSOS3001() && RES_SOS_3001.getValue().data.getChbStus() != null) {
            switch (ChargeBtrStatus.findCode(StringUtil.isValidString(RES_SOS_3001.getValue().data.getChbStus().getStatus()))) {
                case STATUS_1000:
                case STATUS_1500:
                case STATUS_2000:
                case STATUS_3000:
                case STATUS_4000:
                    return true;
            }
        }

        return false;
    }

    // 픽업앤충전 신청 상태 정보
    public String getChbStusCd() {
        String chbStusCd = "";

        if (isValidSOS3001() && RES_SOS_3001.getValue().data.getChbStus() != null) {
            try {
                chbStusCd = RES_SOS_3001.getValue().data.getChbStus().getStatus();
            } catch (Exception e) {
                chbStusCd = "";
            }
        }
        return chbStusCd;
    }
}