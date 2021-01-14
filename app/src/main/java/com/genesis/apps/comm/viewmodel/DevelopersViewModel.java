package com.genesis.apps.comm.viewmodel;

import android.text.TextUtils;

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
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.excutor.ExecutorService;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import androidx.hilt.Assisted;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import lombok.Data;

import static com.genesis.apps.comm.model.constants.VariableType.MAIN_VEHICLE_TYPE_OV;

public @Data
class DevelopersViewModel extends ViewModel {
    /**
     * Developers 와 My차고 GRA-GNS-1010 연동으로 오는 쿠폰 정보의 소모품 코드와 매핑하기 위해 만든 enum class.
     */
    public enum SEST {
        /**
         * 엔진오일/필터
         */
        ENGINE_OIL_FILTER(1, "11"),
        /**
         * 에어 크리너
         */
        AIR_CLEANER(2, "0"),
        /**
         * 에어컨 필터
         */
        AIR_CONDITIONING_FILTER(3, "13"),
        /**
         * 브레이크 오일
         */
        BREAK_OIL(9, "34");

        /**
         * Developers API 에서 사용하는 소모품 코드.
         */
        public final int devCode;

        /**
         * My차고 API 인 GRA-GNS-1010의 전문에서 전달되는 소모품 쿠폰 정보의 소모품 코드.
         * 맵핑 코드가 없는 경우 0으로 처리.
         */
        public final String graGns1010Code;

        SEST(int devCode, String graGns1010Code) {
            this.devCode = devCode;
            this.graGns1010Code = graGns1010Code;
        }
    } // end of enum class SEST

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

    public List<CarConnectVO> checkCarId(String userId) throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<List<CarConnectVO>> future = es.getListeningExecutorService().submit(() -> {
            List<CarConnectVO> targetList = new ArrayList<>();
            if (!TextUtils.isEmpty(userId)) {
                try {
                    targetList = getDeveloperCarInfo(getTargetList());
                    //car id가 없는 소유 차량이 1대 이상 있으면
                    if (targetList != null && targetList.size() > 0) {
                        //제네시스 앱 CARID가 발급되어있는지 확인하고 발급되어 있지 않으면 신청 진행
                        updateCarInfoToDevelopers(targetList, userId);
                        //제네시스 앱 CARID가 발급되어있는지 최종 확인하고 car id를 DB에 바로 갱신
                        updateCarInfoToLocal(targetList, userId);
                    }
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }
            }
            return targetList;
        });

        try {
            return future.get();
        } finally {
            es.shutDownExcutor();
        }
    }

    private void updateCarInfoToLocal(List<CarConnectVO> targetList, String userId) {
        if (targetList != null && targetList.size() > 0 && !TextUtils.isEmpty(userId)) {
            CarId.Response carIdResLast = repository.REQ_SYNC_CAR_ID(new CarId.Request(userId));
            if (carIdResLast != null && carIdResLast.getCars() != null && carIdResLast.getCars().size() > 0) {
                for (int i = 0; i < targetList.size(); i++) {
                    for (CarVO carVO : carIdResLast.getCars()) {
                        if (targetList.get(i).getVin().equalsIgnoreCase(carVO.getVin())) {
                            targetList.get(i).setCarId(carVO.getCarId());
                        }
                    }
                }
            }
            dbVehicleRepository.insertOrUpdateCarConnect(targetList);
        }
    }


    /**
     * @biref Developers CardID 획득 대상 차량 확인
     * DB를 기준으로 소유차량인데 CARID가 없는 차량을 리스트로 출력
     */
    private List<CarConnectVO> getTargetList() {
        List<CarConnectVO> targetList = new ArrayList<>();
        List<VehicleVO> list = new ArrayList<>();
        try {
            //로컬 db에 있는 소유차량 로드
            list.addAll(dbVehicleRepository.getVehicleList(MAIN_VEHICLE_TYPE_OV));
            for (VehicleVO vehicleVO : list) {
                try {
                    CarConnectVO carConnectVO = dbVehicleRepository.getCarConnect(vehicleVO.getVin());
                    if ((carConnectVO == null || TextUtils.isEmpty(carConnectVO.getCarId()))
                            && !TextUtils.isEmpty(vehicleVO.getVin())) {//로컬 db에 등록된 차대번호에 해당하는 carId가 있는지 확인

                        //carId가 없는 차량을 리스트에 추가
                        targetList.add(new CarConnectVO(vehicleVO.getVin(), "", "", "", ""));
                    }
                } catch (Exception ignore) {

                }
            }

        } catch (Exception ignore) {

        }
        return targetList;
    }


    private List<CarConnectVO> getDeveloperCarInfo(List<CarConnectVO> targetList) {
        if (targetList != null && targetList.size() > 0) {
            //GCS에 등록된 차량 리스트 확인
            CarCheck.Response carCheckRes = repository.REQ_SYNC_CAR_CHECK(new CarCheck.Request());
            //GCS에 등록된 차량이 있으면
            if (carCheckRes != null && carCheckRes.getCars() != null && carCheckRes.getCars().size() > 0) {
                //GCS차량 정보를 TARGETLIST에 SET 진행
                for (CarVO carVO : carCheckRes.getCars()) {
                    for (int i = 0; i < targetList.size(); i++) {
                        if (carVO.getVin().equalsIgnoreCase(targetList.get(i).getVin())) {
                            targetList.get(i).setMasterCarId(TextUtils.isEmpty(carVO.getCarId()) ? "" : carVO.getCarId());
                            targetList.get(i).setCarName(TextUtils.isEmpty(carVO.getCarName()) ? "" : carVO.getCarName());
                        }
                    }
                }
            }
        }
        return targetList;
    }

    private void updateCarInfoToDevelopers(List<CarConnectVO> targetList, String userId) {
        if (targetList != null && targetList.size() > 0 && !TextUtils.isEmpty(userId)) {
            //제네시스 앱 CARID가 발급되어있는지 확인하고 발급되어 있지 않으면 신청 진행
            CarId.Response carIdRes = repository.REQ_SYNC_CAR_ID(new CarId.Request(userId));
            if (carIdRes != null && carIdRes.getCars() != null && carIdRes.getCars().size() > 0) {
                for (int i = 0; i < targetList.size(); i++) {
                    for (CarVO carVO : carIdRes.getCars()) {
                        if (targetList.get(i).getVin().equalsIgnoreCase(carVO.getVin())) {
                            if (TextUtils.isEmpty(carVO.getCarId())) {
                                //car id가 비어있으면 가입 신청 필요
                                CarConnectVO carConnectVO = new CarConnectVO("", targetList.get(i).getMasterCarId(), "", "2", targetList.get(i).getCarName());
                                CarConnect.Response carConnectRes = repository.REQ_SYNC_CAR_CONNECT(new CarConnect.Request(carConnectVO, userId));
                            }
//                                    else{
//                                        //car id가 있으면 해당 값을 저장
//                                        targetList.get(i).setCarId(carVO.getCarId());
//                                    }
                        }
                    }
                }
            }
        }
    }


    /**
     * @param value
     * @return
     * @brief 거리 단위 확인
     */
    public String getDistanceUnit(int value) {
        String unit = "km";
        switch (value) {
            case 0:
                unit = " feet";
                break;
            case 2:
                unit = " meter";
                break;
            case 3:
                unit = " miles";
                break;
            case 1:
            default:
                unit = " km";
                break;
        }
        return unit;
    }

    public String getCarId(String vin) {
        CarConnectVO carConnectVO = null;
        String carId = "";
        try {
            if(!TextUtils.isEmpty(vin))
                carConnectVO = dbVehicleRepository.getCarConnect(vin);

            if (carConnectVO != null)
                carId = carConnectVO.getCarId();

        } catch (Exception e) {

        }

        return carId;
    }

    public String getDateYyyyMMdd(int day) {
        Calendar calendar = Calendar.getInstance(Locale.getDefault());

        if (day != 0) calendar.add(Calendar.DATE, day);

        String date = "";

        try {
            date = DateUtil.getDate(calendar.getTime(), DateUtil.DATE_FORMAT_yyyyMMdd);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return date;
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

    /**
     * 주행거리 단위 포함 Foramt String 변환 함수.
     *
     * @param distance 거리
     * @param unit     Developers 에서 정의된 거리 단위 ID
     * @return 변환된 거리 단위 포함 String.
     */
    public static String getDistanceFormatByUnit(int distance, int unit) {
        String format;
        switch (unit) {
            case 0:
                format = "%,3d feet";
                break;
            case 2:
                format = "%,3d meter";
                break;
            case 3:
                format = "%,3d miles";
                break;
            case 1:
            default:
                format = "%,3d km";
                break;
        }
        return String.format(format, distance);
    }

    /**
     * Developer API의 소모품 코드를 맵핑되어 있는 GRA-GNS-1010 전문의 서비스 코드로 반환해주는 함수.
     *
     * @param sestCode Developer API 소모품 코드.
     * @return GRA-GNS-1010 전문의 서비스 코드. 맵핑정보가 없다면 0으로 반환.
     */
    public static String getServiceCodeBySestCode(int sestCode) {
        String result = "0";
        for (SEST item : SEST.values()) {
            if (item.devCode == sestCode) {
                result = item.graGns1010Code;
                break;
            }
        }
        return result;
    }

}