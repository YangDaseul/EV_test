package com.genesis.apps.comm.viewmodel;

import android.text.TextUtils;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import com.genesis.apps.comm.model.api.developers.CarCheck;
import com.genesis.apps.comm.model.api.developers.CarConnect;
import com.genesis.apps.comm.model.api.developers.CarId;
import com.genesis.apps.comm.model.api.developers.CarList;
import com.genesis.apps.comm.model.api.developers.Detail;
import com.genesis.apps.comm.model.api.developers.Distance;
import com.genesis.apps.comm.model.api.developers.Dtc;
import com.genesis.apps.comm.model.api.developers.Dte;
import com.genesis.apps.comm.model.api.developers.Odometer;
import com.genesis.apps.comm.model.api.developers.ParkLocation;
import com.genesis.apps.comm.model.api.developers.Replacements;
import com.genesis.apps.comm.model.api.developers.Target;
import com.genesis.apps.comm.model.repo.DBVehicleRepository;
import com.genesis.apps.comm.model.repo.DevelopersRepo;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.developers.CarConnectVO;
import com.genesis.apps.comm.model.vo.developers.CarVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.excutor.ExecutorService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import lombok.Data;

import static com.genesis.apps.comm.model.constants.VariableType.MAIN_VEHICLE_TYPE_OV;

public @Data
class DevelopersViewModel extends ViewModel {

    private final DevelopersRepo repository;
    private final SavedStateHandle savedStateHandle;

    private final DBVehicleRepository dbVehicleRepository;

    private MutableLiveData<NetUIResponse<Dtc.Response>> RES_DTC;
    private MutableLiveData<NetUIResponse<Replacements.Response>> RES_REPLACEMENTS;
    private MutableLiveData<NetUIResponse<Target.Response>> RES_TARGET;
    private MutableLiveData<NetUIResponse<Detail.Response>> RES_DETAIL;
    private MutableLiveData<NetUIResponse<CarList.Response>> RES_CARLIST;
    private MutableLiveData<NetUIResponse<Dte.Response>> RES_DTE;
    private MutableLiveData<NetUIResponse<Odometer.Response>> RES_ODOMETER;
    private MutableLiveData<NetUIResponse<ParkLocation.Response>> RES_PARKLOCATION;
    private MutableLiveData<NetUIResponse<Distance.Response>> RES_DISTANCE;
    private MutableLiveData<NetUIResponse<CarCheck.Response>> RES_CAR_CHECK;
    private MutableLiveData<NetUIResponse<CarId.Response>> RES_CAR_ID;
    private MutableLiveData<NetUIResponse<CarConnect.Response>> RES_CAR_CONNECT;
    

    @ViewModelInject
    DevelopersViewModel(
            DevelopersRepo repository,
            DBVehicleRepository dbVehicleRepository,
            @Assisted SavedStateHandle savedStateHandle) {
        this.repository = repository;
        this.savedStateHandle = savedStateHandle;
        this.dbVehicleRepository = dbVehicleRepository;
        RES_DTC = repository.RES_DTC;
        RES_REPLACEMENTS = repository.RES_REPLACEMENTS;
        RES_TARGET = repository.RES_TARGET;
        RES_DETAIL = repository.RES_DETAIL;
        RES_CARLIST = repository.RES_CARLIST;
        RES_DTE = repository.RES_DTE;
        RES_ODOMETER = repository.RES_ODOMETER;
        RES_PARKLOCATION = repository.RES_PARKLOCATION;
        RES_DISTANCE = repository.RES_DISTANCE;
        RES_CAR_CHECK = repository.RES_CAR_CHECK;
        RES_CAR_ID = repository.RES_CAR_ID;
        RES_CAR_CONNECT = repository.RES_CAR_CONNECT;
    }

    public void reqDtc(final Dtc.Request reqData) {
        repository.REQ_DTC(reqData);
    }

    public void reqReplacements(final Replacements.Request reqData) {
        repository.REQ_REPLACEMENTS(reqData);
    }

    public void reqTarget(final Target.Request reqData) {
        repository.REQ_TARGET(reqData);
    }

    public void reqDetail(final Detail.Request reqData) {
        repository.REQ_DETAIL(reqData);
    }

    public void reqCarList(final CarList.Request reqData) {
        repository.REQ_CARLIST(reqData);
    }

    public void reqDte(final Dte.Request reqData) {
        repository.REQ_DTE(reqData);
    }

    public void reqOdometer(final Odometer.Request reqData) {
        repository.REQ_ODOMETER(reqData);
    }

    public void reqParkLocation(final ParkLocation.Request reqData) {
        repository.REQ_PARKLOCATION(reqData);
    }

    public void reqDistance(final Distance.Request reqData) {
        repository.REQ_DISTANCE(reqData);
    }

    public void reqCarCheck(final CarCheck.Request reqData) {
        repository.REQ_CAR_CHECK(reqData);
    }

    public void reqCarId(final CarId.Request reqData) {
        repository.REQ_CAR_ID(reqData);
    }

    public void reqCarConnect(final CarConnect.Request reqData) {
        repository.REQ_CAR_CONNECT(reqData);
    }

    //todo void로 변경필요, 각 response vo에 에러 타입 추가 필요, intro에 적용 및 전문 테스트 필요
    public List<CarConnectVO> checkCarId(String userId) throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<List<CarConnectVO>> future = es.getListeningExecutorService().submit(()->{
            List<CarConnectVO> targetList = new ArrayList<>();
            List<VehicleVO> list = new ArrayList<>();
            try {
                //로컬 db에 있는 소유차량 로드
                list.addAll(dbVehicleRepository.getVehicleList(MAIN_VEHICLE_TYPE_OV));

                for(VehicleVO vehicleVO : list){
                    try {
                        CarConnectVO carConnectVO = dbVehicleRepository.getCarConnect(vehicleVO.getVin());
                        if((carConnectVO==null||TextUtils.isEmpty(carConnectVO.getCarId()))
                                &&!TextUtils.isEmpty(vehicleVO.getVin())){//로컬 db에 등록된 차대번호에 해당하는 carId가 있는지 확인

                            //carId가 없는 차량을 리스트에 추가
                            targetList.add(new CarConnectVO("","","","", vehicleVO.getVin()));
                        }
                    } catch (Exception e) {

                    }
                }

                //car id가 없는 소유 차량이 1대 이상 있으면
                if(targetList!=null&&targetList.size()>0){
                    //GCS에 등록된 차량 리스트 확인
                    CarCheck.Response carCheckRes = repository.REQ_SYNC_CAR_CHECK(new CarCheck.Request());
                    //GCS에 등록된 차량이 있으면
                    if(carCheckRes!=null&&carCheckRes.getCars()!=null&&carCheckRes.getCars().size()>0){
                        //GCS차량 정보를 TARGETLIST에 SET 진행
                        for(CarVO carVO : carCheckRes.getCars()){
                            for(int i=0; i<targetList.size(); i++){
                                if(carVO.getVin().equalsIgnoreCase(targetList.get(i).getVin())){
                                    targetList.get(i).setMasterCarId(carVO.getCarId());
                                    targetList.get(i).setCarName(carVO.getCarName());
                                }
                            }
                        }
                    }


                    //제네시스 앱 CARID가 발급되어있는지 확인하고 발급되어 있지 않으면 신청 진행
                    CarId.Response carIdRes = repository.REQ_SYNC_CAR_ID(new CarId.Request(userId));
                    if(carIdRes!=null&&carIdRes.getCars()!=null&&carIdRes.getCars().size()>0){
                        for(int i=0; i<targetList.size(); i++){
                            for(CarVO carVO : carIdRes.getCars()){
                                if(targetList.get(i).getVin().equalsIgnoreCase(carVO.getVin())){
                                    if(TextUtils.isEmpty(carVO.getCarId())){
                                        //car id가 비어있으면 가입 신청 필요
                                        CarConnectVO carConnectVO = new CarConnectVO("",targetList.get(i).getMasterCarId(),"","2",targetList.get(i).getCarName());
                                        CarConnect.Response carConnectRes = repository.REQ_SYNC_CAR_CONNECT(new CarConnect.Request(carConnectVO));
                                    }
//                                    else{
//                                        //car id가 있으면 해당 값을 저장
//                                        targetList.get(i).setCarId(carVO.getCarId());
//                                    }
                                }
                            }
                        }
                    }



                    //제네시스 앱 CARID가 발급되어있는지 최종 확인하고 car id를 DB에 바로 갱신
                    CarId.Response carIdResLast = repository.REQ_SYNC_CAR_ID(new CarId.Request(userId));
                    if(carIdResLast!=null&&carIdResLast.getCars()!=null&&carIdResLast.getCars().size()>0){
                        for(int i=0; i<targetList.size(); i++){
                            for(CarVO carVO : carIdResLast.getCars()){
                                if(targetList.get(i).getVin().equalsIgnoreCase(carVO.getVin())){
                                    targetList.get(i).setCarId(carVO.getCarId());
                                }
                            }
                        }
                    }
                    dbVehicleRepository.insertOrUpdateCarConnect(targetList);
                }
            } catch (Exception ignore) {
                ignore.printStackTrace();
            }
            return targetList;
        });

        try {
            return future.get();
        }finally {
            //todo 테스트 필요
            es.shutDownExcutor();
        }
    }

}