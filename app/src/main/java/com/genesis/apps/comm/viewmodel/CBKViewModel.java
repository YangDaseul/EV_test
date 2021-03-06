package com.genesis.apps.comm.viewmodel;

import android.text.TextUtils;

import com.genesis.apps.comm.model.api.gra.CBK_1001;
import com.genesis.apps.comm.model.api.gra.CBK_1002;
import com.genesis.apps.comm.model.api.gra.CBK_1005;
import com.genesis.apps.comm.model.api.gra.CBK_1006;
import com.genesis.apps.comm.model.api.gra.CBK_1007;
import com.genesis.apps.comm.model.api.gra.CBK_1008;
import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.repo.CBKRepo;
import com.genesis.apps.comm.model.repo.DBVehicleRepository;
import com.genesis.apps.comm.model.vo.ExpnVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.excutor.ExecutorService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import lombok.Data;

public @Data
class CBKViewModel extends ViewModel {

    private final CBKRepo repository;
    private final DBVehicleRepository dbVehicleRepository;
    private final SavedStateHandle savedStateHandle;

    private MutableLiveData<NetUIResponse<CBK_1001.Response>> RES_CBK_1001;//서버로부터 전달받은 차량 데이터로 별도 가공되어있지 않음
    private MutableLiveData<NetUIResponse<CBK_1002.Response>> RES_CBK_1002;
    private MutableLiveData<NetUIResponse<CBK_1005.Response>> RES_CBK_1005;
    private MutableLiveData<NetUIResponse<CBK_1006.Response>> RES_CBK_1006;
    private MutableLiveData<NetUIResponse<CBK_1007.Response>> RES_CBK_1007;
    private MutableLiveData<NetUIResponse<CBK_1008.Response>> RES_CBK_1008;

    //CBK-1001로부터 전달받은 차량 데이터를 주 이용차량을 가장 앞으로 가공시킨 리스트, 차계부 메인 및 수정 페이지에서 해당 데이터 참조함
    private MutableLiveData<List<VehicleVO>> vehicleList;


    @ViewModelInject
    CBKViewModel(
            CBKRepo repository,
            DBVehicleRepository dbVehicleRepository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.dbVehicleRepository = dbVehicleRepository;
        this.savedStateHandle = savedStateHandle;

        RES_CBK_1001 = repository.RES_CBK_1001;
        RES_CBK_1002 = repository.RES_CBK_1002;
        RES_CBK_1005 = repository.RES_CBK_1005;
        RES_CBK_1006 = repository.RES_CBK_1006;
        RES_CBK_1007 = repository.RES_CBK_1007;
        RES_CBK_1008 = repository.RES_CBK_1008;
        vehicleList = new MutableLiveData<>();
    }

    public void reqCBK1001(final CBK_1001.Request reqData) {
        repository.REQ_CBK_1001(reqData);
    }

    public void reqCBK1002(final CBK_1002.Request reqData) {
        repository.REQ_CBK_1002(reqData);
    }

    public void reqCBK1005(final CBK_1005.Request reqData) {
        repository.REQ_CBK_1005(reqData);
    }

    public void reqCBK1006(final CBK_1006.Request reqData) {
        repository.REQ_CBK_1006(reqData);
    }

    public void reqCBK1007(final CBK_1007.Request reqData) {
        repository.REQ_CBK_1007(reqData);
    }

    public void reqCBK1008(final CBK_1008.Request reqData) {
        repository.REQ_CBK_1008(reqData);
    }


    public List<String> getInsightVehicleList(List<VehicleVO> vehicleVOList) throws ExecutionException, InterruptedException{
        ExecutorService es = new ExecutorService("");
        Future<List<String>> future = es.getListeningExecutorService().submit(()->{
            List<String> list = new ArrayList<>();
            try {
                //db에서 주 이용차량 확인
                VehicleVO mainVehicle = getDbVehicleRepository().getMainVehicleSimplyFromDB();
                //주이용차량이 있고 차대번호가 정상적으로 있으면
                if(mainVehicle!=null&&!TextUtils.isEmpty(mainVehicle.getVin())){
                    for(int i=0; i<vehicleVOList.size(); i++){
                        if(vehicleVOList.get(i).getVin().equalsIgnoreCase(mainVehicle.getVin())){ //주 이용 차량을
                            if(i>0) Collections.swap(vehicleVOList, 0, i); //제일 앞으로 이동
                            break;
                        }
                    }
                }

                vehicleList.postValue(vehicleVOList);
                for(VehicleVO vehicleVO : vehicleVOList){
                    list.add(vehicleVO.getMdlNm() + (TextUtils.isEmpty(vehicleVO.getCarRgstNo()) ? "" : " "+vehicleVO.getCarRgstNo()));
                }
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            return list;
        });

        try {
            return future.get();
        }finally {
            //todo 테스트 필요
            es.shutDownExcutor();
        }
    }


    public List<String> getYearRecently1Years(){
        List<String> years = new ArrayList<>();

        for(int i=0; i<12; i++){
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -i);
            years.add(cal.get(Calendar.YEAR) + "년 "+(cal.get(Calendar.MONTH)+1)+"월");
        }
        return years;
    }






    public List<String> getYearRecently5Years(){

        List<String> years = new ArrayList<>();

        for(int i=0; i<5; i++){
            Calendar cal = Calendar.getInstance();
            years.add(cal.get(Calendar.YEAR) - i+"");
        }
        return years;
    }

    public String getCurrentDateyyyyMM(){
        Calendar cal = Calendar.getInstance();
        return String.format(Locale.getDefault(),"%d%02d", cal.get(Calendar.YEAR), cal.get(Calendar.MONTH)+1);
    }

    public String getCurrentMM(){
        Calendar cal = Calendar.getInstance();
        return String.format(Locale.getDefault(),"%d월", cal.get(Calendar.MONTH)+1);
    }

    public String getCurrentYYYY(){
        Calendar cal = Calendar.getInstance();
        return String.format(Locale.getDefault(),"%d년", cal.get(Calendar.YEAR));
    }

    public List<ExpnVO> getExpnList(List<ExpnVO> oriList) throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<List<ExpnVO>> future = es.getListeningExecutorService().submit(() -> {

            final List<String> distinctExpnDtm = oriList.stream().map(x -> x.getExpnDtm().substring(0, 8)).distinct().sorted().collect(Collectors.toList());
            try {

                for (int i = 0; i < distinctExpnDtm.size(); i++) {
                    for (int n = 0; n < oriList.size(); n++) {
                        String subExpnDtm = "";
                        try {
                            subExpnDtm = oriList.get(n).getExpnDtm().substring(0, 8);
                        } catch (Exception e) {
                            subExpnDtm = "";
                        }
                        if (subExpnDtm.equalsIgnoreCase(distinctExpnDtm.get(i))) {
                            oriList.get(n).setFirst(true);
                            break;
                        }
                    }
                }
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            oriList.sort(Comparator.comparing(ExpnVO::getExpnDtm).reversed());
            return oriList;
        });

        try {
            return future.get();
        } finally {
            //todo 테스트 필요
            es.shutDownExcutor();
        }
    }


    public VehicleVO getMainVehicleSimplyFromDB() throws ExecutionException, InterruptedException {
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

    public boolean isVisibleAccmMilg(String expnDivCd){
        return expnDivCd.equalsIgnoreCase(VariableType.INSIGHT_EXPN_DIV_CODE_1000)||expnDivCd.equalsIgnoreCase(VariableType.INSIGHT_EXPN_DIV_CODE_1100)||expnDivCd.equalsIgnoreCase(VariableType.INSIGHT_EXPN_DIV_CODE_2000);
    }

}