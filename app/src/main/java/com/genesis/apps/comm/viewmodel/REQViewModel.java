package com.genesis.apps.comm.viewmodel;

import android.text.TextUtils;
import android.text.format.DateUtils;

import com.genesis.apps.comm.model.api.gra.REQ_1001;
import com.genesis.apps.comm.model.api.gra.REQ_1002;
import com.genesis.apps.comm.model.api.gra.REQ_1003;
import com.genesis.apps.comm.model.api.gra.REQ_1004;
import com.genesis.apps.comm.model.api.gra.REQ_1005;
import com.genesis.apps.comm.model.api.gra.REQ_1007;
import com.genesis.apps.comm.model.api.gra.REQ_1008;
import com.genesis.apps.comm.model.api.gra.REQ_1009;
import com.genesis.apps.comm.model.api.gra.REQ_1010;
import com.genesis.apps.comm.model.api.gra.REQ_1011;
import com.genesis.apps.comm.model.api.gra.REQ_1012;
import com.genesis.apps.comm.model.api.gra.REQ_1013;
import com.genesis.apps.comm.model.api.gra.REQ_1014;
import com.genesis.apps.comm.model.api.gra.REQ_1015;
import com.genesis.apps.comm.model.api.gra.REQ_1016;
import com.genesis.apps.comm.model.api.gra.REQ_1017;
import com.genesis.apps.comm.model.api.gra.REQ_1018;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.repo.DBVehicleRepository;
import com.genesis.apps.comm.model.repo.REQRepo;
import com.genesis.apps.comm.model.vo.BtrVO;
import com.genesis.apps.comm.model.vo.CouponVO;
import com.genesis.apps.comm.model.vo.RepairGroupVO;
import com.genesis.apps.comm.model.vo.RepairHistVO;
import com.genesis.apps.comm.model.vo.RepairTypeVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.excutor.ExecutorService;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import lombok.Data;

import static java.util.stream.Collectors.toList;

public @Data
class REQViewModel extends ViewModel {

    private final REQRepo repository;
    private final DBVehicleRepository dbVehicleRepository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<REQ_1001.Response>> RES_REQ_1001;
    private MutableLiveData<NetUIResponse<REQ_1002.Response>> RES_REQ_1002;
    private MutableLiveData<NetUIResponse<REQ_1003.Response>> RES_REQ_1003;
    private MutableLiveData<NetUIResponse<REQ_1004.Response>> RES_REQ_1004;
    private MutableLiveData<NetUIResponse<REQ_1005.Response>> RES_REQ_1005;
    private MutableLiveData<NetUIResponse<REQ_1007.Response>> RES_REQ_1007;
    private MutableLiveData<NetUIResponse<REQ_1008.Response>> RES_REQ_1008;
    private MutableLiveData<NetUIResponse<REQ_1009.Response>> RES_REQ_1009;
    private MutableLiveData<NetUIResponse<REQ_1010.Response>> RES_REQ_1010;
    private MutableLiveData<NetUIResponse<REQ_1011.Response>> RES_REQ_1011;
    private MutableLiveData<NetUIResponse<REQ_1012.Response>> RES_REQ_1012;
    private MutableLiveData<NetUIResponse<REQ_1013.Response>> RES_REQ_1013;
    private MutableLiveData<NetUIResponse<REQ_1014.Response>> RES_REQ_1014;
    private MutableLiveData<NetUIResponse<REQ_1015.Response>> RES_REQ_1015;
    private MutableLiveData<NetUIResponse<REQ_1016.Response>> RES_REQ_1016;
    private MutableLiveData<NetUIResponse<REQ_1017.Response>> RES_REQ_1017;
    private MutableLiveData<NetUIResponse<REQ_1018.Response>> RES_REQ_1018;


    private MutableLiveData<List<VehicleVO>> vehicleList;

    @ViewModelInject
    REQViewModel(
            REQRepo repository,
            DBVehicleRepository dbVehicleRepository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.dbVehicleRepository = dbVehicleRepository;
        this.savedStateHandle = savedStateHandle;

        RES_REQ_1001 = repository.RES_REQ_1001;
        RES_REQ_1002 = repository.RES_REQ_1002;
        RES_REQ_1003 = repository.RES_REQ_1003;
        RES_REQ_1004 = repository.RES_REQ_1004;
        RES_REQ_1005 = repository.RES_REQ_1005;
        RES_REQ_1007 = repository.RES_REQ_1007;
        RES_REQ_1008 = repository.RES_REQ_1008;
        RES_REQ_1009 = repository.RES_REQ_1009;
        RES_REQ_1010 = repository.RES_REQ_1010;
        RES_REQ_1011 = repository.RES_REQ_1011;
        RES_REQ_1012 = repository.RES_REQ_1012;
        RES_REQ_1013 = repository.RES_REQ_1013;
        RES_REQ_1014 = repository.RES_REQ_1014;
        RES_REQ_1015 = repository.RES_REQ_1015;
        RES_REQ_1016 = repository.RES_REQ_1016;
        RES_REQ_1017 = repository.RES_REQ_1017;
        RES_REQ_1018 = repository.RES_REQ_1018;

        vehicleList = new MutableLiveData<>();
    }

    public void reqREQ1001(final REQ_1001.Request reqData) {
        repository.REQ_REQ_1001(reqData);
    }

    public void reqREQ1002(final REQ_1002.Request reqData) {
        repository.REQ_REQ_1002(reqData);
    }

    public void reqREQ1003(final REQ_1003.Request reqData) {
        repository.REQ_REQ_1003(reqData);
    }

    public void reqREQ1004(final REQ_1004.Request reqData) {
        repository.REQ_REQ_1004(reqData);
    }

    public void reqREQ1005(final REQ_1005.Request reqData) {
        repository.REQ_REQ_1005(reqData);
    }

    public void reqREQ1007(final REQ_1007.Request reqData) {
        repository.REQ_REQ_1007(reqData);
    }

    public void reqREQ1008(final REQ_1008.Request reqData) {
        repository.REQ_REQ_1008(reqData);
    }

    public void reqREQ1009(final REQ_1009.Request reqData) {
        repository.REQ_REQ_1009(reqData);
    }

    public void reqREQ1010(final REQ_1010.Request reqData) {
        repository.REQ_REQ_1010(reqData);
    }

    public void reqREQ1011(final REQ_1011.Request reqData) {
        repository.REQ_REQ_1011(reqData);
    }

    public void reqREQ1012(final REQ_1012.Request reqData) {
        repository.REQ_REQ_1012(reqData);
    }

    public void reqREQ1013(final REQ_1013.Request reqData) {
        repository.REQ_REQ_1013(reqData);
    }

    public void reqREQ1014(final REQ_1014.Request reqData) {
        repository.REQ_REQ_1014(reqData);
    }

    public void reqREQ1015(final REQ_1015.Request reqData) {
        repository.REQ_REQ_1015(reqData);
    }

    public void reqREQ1016(final REQ_1016.Request reqData) {
        repository.REQ_REQ_1016(reqData);
    }

    public void reqREQ1017(final REQ_1017.Request reqData) {
        repository.REQ_REQ_1017(reqData);
    }

    public void reqREQ1018(final REQ_1018.Request reqData) {
        repository.REQ_REQ_1018(reqData);
    }


    public VehicleVO getMainVehicle() throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<VehicleVO> future = es.getListeningExecutorService().submit(()->{
            VehicleVO vehicleVO = null;
            try {
                vehicleVO = dbVehicleRepository.getMainVehicleSimplyFromDB();
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


    /**
     * @brief 정비유형리스트 중 정비유형명 리스트 반환
     * @param repairTypeVOList
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<String> getRepairTypeNm(List<RepairTypeVO> repairTypeVOList) throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<List<String>> future = es.getListeningExecutorService().submit(()->{
            List<String> list = new ArrayList<>();
            try {
                list = repairTypeVOList.stream().map(RepairTypeVO::getRparTypNm).collect(toList());
            } catch (Exception ignore) {
                ignore.printStackTrace();
                list = null;
            }
            return list;
        });

        try {
            return future.get();
        }finally {
            es.shutDownExcutor();
        }
    }

    /**
     * @biref 정비유형명 해당하는 코드 반환
     * @param repairTypeNm 정비유형코드
     * @param repairTypeVOList 정비유형리스트
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public RepairTypeVO getRepairTypeCd(String repairTypeNm, List<RepairTypeVO> repairTypeVOList) throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<RepairTypeVO> future = es.getListeningExecutorService().submit(()->{
            RepairTypeVO repairTypeVO=null;
            try {
                repairTypeVO = repairTypeVOList.stream().filter(data -> (data.getRparTypNm().equalsIgnoreCase(repairTypeNm))).findFirst().get();
            } catch (Exception ignore) {
                ignore.printStackTrace();
                repairTypeVO = null;
            }
            return repairTypeVO;
        });

        try {
            return future.get();
        }finally {
            es.shutDownExcutor();
        }
    }



    /**
     * @brief 정비소 리스트 중 명칭 확인
     * @param repairTypeVOList
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public List<String> getRpshGrpNmList(List<RepairGroupVO> repairTypeVOList) throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<List<String>> future = es.getListeningExecutorService().submit(()->{
            List<String> list = new ArrayList<>();
            try {
                list = repairTypeVOList.stream().map(RepairGroupVO::getRpshGrpNm).collect(toList());
            } catch (Exception ignore) {
                ignore.printStackTrace();
                list = null;
            }
            return list;
        });

        try {
            return future.get();
        }finally {
            es.shutDownExcutor();
        }
    }

    /**
     * @biref 정비소명에 해당하는 코드 반환
     * @param rpshGrpNm 정비소코드
     * @param list 정비소리스트
     * @return
     * @throws ExecutionException
     * @throws InterruptedException
     */
    public RepairGroupVO getRpshGrpCd(String rpshGrpNm, List<RepairGroupVO> list) throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<RepairGroupVO> future = es.getListeningExecutorService().submit(()->{
            RepairGroupVO repairGroupVO=null;
            try {
                repairGroupVO = list.stream().filter(data -> (data.getRpshGrpNm().equalsIgnoreCase(rpshGrpNm))).findFirst().get();
            } catch (Exception ignore) {
                ignore.printStackTrace();
                repairGroupVO = null;
            }
            return repairGroupVO;
        });

        try {
            return future.get();
        }finally {
            es.shutDownExcutor();
        }
    }







    public boolean checkCoupon(List<CouponVO> couponList, String itemDivCd){
        int remCnt=0;

        for (CouponVO couponVO : couponList) {
            if(couponVO.getItemDivCd().equalsIgnoreCase(itemDivCd)){
                try{
                    remCnt = Integer.parseInt(couponVO.getRemCnt());
                }catch (Exception e){
                    e.printStackTrace();
                }
                break;
            }
        }
        return remCnt>0;
    }

    /**
     * @brief 오토케어 예약서비스에서 사용하는 리스트 선별
     * @param couponList 전체 쿠폰 리스트
     * @return
     */
    public List<CouponVO> getAutocareCouponList(List<CouponVO> couponList){

        List<CouponVO> list = new ArrayList<>();
        list.add(new CouponVO(VariableType.SERVICE_CAR_CARE_COUPON_CODE_ENGINE, "","0","0","0", ""));
        list.add(new CouponVO(VariableType.SERVICE_CAR_CARE_COUPON_CODE_AC_FILTER, "","0","0","0", ""));
        list.add(new CouponVO(VariableType.SERVICE_CAR_CARE_COUPON_CODE_WIPER, "","0","0","0", ""));
        list.add(new CouponVO(VariableType.SERVICE_CAR_CARE_COUPON_CODE_NAVIGATION, "","0","0","0", ""));

        for(int i=0; i<list.size(); i++){
            for(CouponVO couponVO : couponList){
                if(list.get(i).getItemDivCd().equalsIgnoreCase(couponVO.getItemDivCd())){
                    list.set(i, couponVO);
                }
            }
        }

        return list;
    }

    /**
     * @brief 쿠폰 선택 결과 확인
     *
     * @param couponList
     * @param itemDivCd
     * @return
     */
    public String getAutocareSelectCouponStatus(List<CouponVO> couponList, String itemDivCd){

        String retv=VariableType.COMMON_MEANS_NO;

        for(CouponVO couponVO : couponList){
            if(couponVO.getItemDivCd().equalsIgnoreCase(itemDivCd)){
                retv = VariableType.COMMON_MEANS_YES;
                break;
            }
        }

        return retv;
    }


//    public List<RepairHistVO> getRepairHistList(List<RepairHistVO> oriList) throws ExecutionException, InterruptedException {
//        ExecutorService es = new ExecutorService("");
//        Future<List<RepairHistVO>> future = es.getListeningExecutorService().submit(() -> {
//
//            //날짜 sort 값 초기화
//            for(int i=0; i<oriList.size(); i++){
//                oriList.get(i).setFirst(false);
//            }
//
//            final List<String> wrhsDtmList = oriList.stream().map(x -> x.getWrhsDtm().substring(0, 8)).distinct().sorted().collect(Collectors.toList());
//            try {
//
//                for (int i = 0; i < wrhsDtmList.size(); i++) {
//                    for (int n = 0; n < oriList.size(); n++) {
//                        String subWrhsDtm = "";
//                        try {
//                            subWrhsDtm = oriList.get(n).getWrhsDtm().substring(0, 8);
//                        } catch (Exception e) {
//                            subWrhsDtm = "";
//                        } finally {
//                            if (subWrhsDtm.equalsIgnoreCase(wrhsDtmList.get(i))) {
//                                oriList.get(n).setFirst(true);
//                                break;
//                            }
//                        }
//                    }
//                }
//            } catch (Exception ignore) {
//                ignore.printStackTrace();
//            }
//            oriList.sort(Comparator.comparing(RepairHistVO::getWrhsDtm).reversed());
//            return oriList;
//        });
//
//        try {
//            return future.get();
//        } finally {
//            //todo 테스트 필요
//            es.shutDownExcutor();
//        }
//    }

    public List<RepairHistVO> getRepairHistList(List<RepairHistVO> oriList) throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<List<RepairHistVO>> future = es.getListeningExecutorService().submit(() -> {
            try {
                for(int i=0; i<oriList.size(); i++){
                    oriList.get(i).setRepairImage(
                            !TextUtils.isEmpty(oriList.get(i).getVhclInoutNo())
                                    &&!TextUtils.isEmpty(oriList.get(i).getWrhsNo())
                                    &&!TextUtils.isEmpty(oriList.get(i).getWrhsDtm())
                                    &&(DateUtil.getDiffMillis(oriList.get(i).getWrhsDtm(), DateUtil.DATE_FORMAT_yyyyMMdd) < DateUtils.DAY_IN_MILLIS*90));
                }
                oriList.sort(Comparator.comparing(RepairHistVO::getWrhsDtm).reversed());
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            return oriList;
        });

        try {
            return future.get();
        } finally {
            //todo 테스트 필요
            es.shutDownExcutor();
        }
    }












    public BtrVO getBtrVO(String asnCd){

        if(RES_REQ_1002.getValue()!=null&&RES_REQ_1002.getValue().data!=null&&RES_REQ_1002.getValue().data.getAsnList()!=null) {
            for (BtrVO btrVO : RES_REQ_1002.getValue().data.getAsnList()) {
                if (btrVO.getAsnCd().equalsIgnoreCase(asnCd)) {
                    return btrVO;
                }
            }
        }

        return null;
    }
    
    
    
}