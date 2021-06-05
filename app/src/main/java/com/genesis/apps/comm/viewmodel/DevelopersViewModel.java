package com.genesis.apps.comm.viewmodel;

import android.text.TextUtils;

import com.genesis.apps.comm.model.api.developers.Agreements;
import com.genesis.apps.comm.model.api.developers.CarCheck;
import com.genesis.apps.comm.model.api.developers.CarConnect;
import com.genesis.apps.comm.model.api.developers.CarId;
import com.genesis.apps.comm.model.api.developers.CarList;
import com.genesis.apps.comm.model.api.developers.CheckJoinCCS;
import com.genesis.apps.comm.model.api.developers.Detail;
import com.genesis.apps.comm.model.api.developers.Distance;
import com.genesis.apps.comm.model.api.developers.Dtc;
import com.genesis.apps.comm.model.api.developers.Dte;
import com.genesis.apps.comm.model.api.developers.Odometer;
import com.genesis.apps.comm.model.api.developers.Odometers;
import com.genesis.apps.comm.model.api.developers.ParkLocation;
import com.genesis.apps.comm.model.api.developers.Replacements;
import com.genesis.apps.comm.model.api.developers.Target;
import com.genesis.apps.comm.model.constants.GAInfo;
import com.genesis.apps.comm.model.repo.DBVehicleRepository;
import com.genesis.apps.comm.model.repo.DevelopersRepo;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.developers.CarConnectVO;
import com.genesis.apps.comm.model.vo.developers.CarVO;
import com.genesis.apps.comm.model.vo.developers.OdometerVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.comm.util.DateUtil;
import com.genesis.apps.comm.util.QueryString;
import com.genesis.apps.comm.util.excutor.ExecutorService;
import com.genesis.apps.room.ResultCallback;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
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
    private MutableLiveData<NetUIResponse<Odometers.Response>> RES_ODOMETERS;
    private MutableLiveData<NetUIResponse<ParkLocation.Response>> RES_PARKLOCATION;
    private MutableLiveData<NetUIResponse<Distance.Response>> RES_DISTANCE;
    private MutableLiveData<NetUIResponse<CarCheck.Response>> RES_CAR_CHECK;
    private MutableLiveData<NetUIResponse<CarId.Response>> RES_CAR_ID;
    private MutableLiveData<NetUIResponse<CarConnect.Response>> RES_CAR_CONNECT;
    private MutableLiveData<NetUIResponse<Agreements.Response>> RES_CAR_AGREEMENTS;


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
        RES_ODOMETERS = repository.RES_ODOMETERS;
        RES_PARKLOCATION = repository.RES_PARKLOCATION;
        RES_DISTANCE = repository.RES_DISTANCE;
        RES_CAR_CHECK = repository.RES_CAR_CHECK;
        RES_CAR_ID = repository.RES_CAR_ID;
        RES_CAR_CONNECT = repository.RES_CAR_CONNECT;
        RES_CAR_AGREEMENTS = repository.RES_CAR_AGREEMENTS;
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
    public void reqOdometers(final Odometers.Request reqData) {
        repository.REQ_ODOMETERS(reqData);
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

    public void reqAgreementsAsync(final Agreements.Request reqData) {
        repository.REQ_AGREEMENTS_ASYNC(reqData);
    }

    public Boolean reqAgreements(Agreements.Request reqData, boolean isUpdate) throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<Boolean> future = es.getListeningExecutorService().submit(() -> {
            Boolean result = false;
            Agreements.Response response = repository.REQ_AGREEMENTS(reqData);
            if (response != null) {
                try {
                    result = response.getData().getResult() != 0;
                    if (isUpdate) {
                        updateCarConnectResult(result, reqData.getCarId());
                    }
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }
            }
            return result;
        });

        try {
            return future.get();
        } finally {
            es.shutDownExcutor();
        }
    }

    //누적주행거리 get
    public int getOdometerValue(){
        int value=0;
        try {
            if(getRES_ODOMETER().getValue()!=null&&getRES_ODOMETER().getValue().data!=null&&getRES_ODOMETER().getValue().data.getOdometers()!=null&&getRES_ODOMETER().getValue().data.getOdometers().size()>0){
                OdometerVO odometerVO = getRES_ODOMETER().getValue().data.getOdometers().stream().max(Comparator.comparingInt(data -> Integer.parseInt(data.getDate()))).orElse(null);
                if(odometerVO!=null){
                    value = (int)odometerVO.getValue();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            value=0;
        }
        return value;
    }

    public boolean updateCarConnectResult(boolean result, String carId){
        try {
            dbVehicleRepository.updateCarConnect(result, carId);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * @brief Developers에 소유 차량에 대한 carId 확인 요청
     */
    public void checkVehicleCarId(String userId, String accessToken, ResultCallback callback) {
        ExecutorService es = new ExecutorService("");
        Futures.addCallback(es.getListeningExecutorService().submit(() -> {
            try {
                if (!TextUtils.isEmpty(accessToken)&&!TextUtils.isEmpty(userId)) {
                    List<CarConnectVO> targetList = checkJoinCCS(userId);
                    if(targetList!=null&&targetList.size()>0){
                        checkCarId(userId, accessToken, targetList);
                    }
                }
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            return true;
        }), new FutureCallback<Boolean>() {
            @Override
            public void onSuccess(@NullableDecl Boolean isSuccess) {
                callback.onSuccess(isSuccess);
                es.shutDownExcutor();
            }

            @Override
            public void onFailure(Throwable t) {
                callback.onSuccess(true);
                es.shutDownExcutor();
            }
        }, es.getUiThreadExecutor());
    }


    public List<CarConnectVO> checkJoinCCS(String userId) throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<List<CarConnectVO>> future = es.getListeningExecutorService().submit(() -> {
            List<VehicleVO> list = new ArrayList<>();
            try {
                list.addAll(dbVehicleRepository.getVehicleList(MAIN_VEHICLE_TYPE_OV));
            }catch (Exception e){

            }
            List<CarConnectVO> targetList = new ArrayList<>();
            if(list!=null&&list.size()>0) {
                for(VehicleVO vehicleVO : list) {
                    String carId = "";
                    try {
                        carId = dbVehicleRepository.getCarConnect(vehicleVO.getVin()).getCarId();
                    }catch (Exception e){
                        carId = null;
                    }
                    if(TextUtils.isEmpty(carId)) {
                        //carId가 없는 경우에만 developers 가입 절차 진행
                        CheckJoinCCS.Response response = repository.REQ_CHECK_JOIN_CCS(new CheckJoinCCS.Request(userId, vehicleVO.getVin()));
                        if (response != null) {
                            try {
                                if (!TextUtils.isEmpty(response.getCarId()) && response.isMaster()) {
                                    targetList.add(new CarConnectVO(vehicleVO.getVin(), "", response.getCarId(), 2, "genesis"));
                                }
                            } catch (Exception ignore) {
                                ignore.printStackTrace();
                            }
                        }
                    }
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


    public List<CarConnectVO> checkCarId(String userId, String accessToken, List<CarConnectVO> targetList) throws ExecutionException, InterruptedException {
        ExecutorService es = new ExecutorService("");
        Future<List<CarConnectVO>> future = es.getListeningExecutorService().submit(() -> {
                try {
                    //연결상태 확인 대상이 1개라도 있으면
                    if (targetList != null && targetList.size() > 0) {
                        //제네시스 앱 CARID가 발급되어있는지 확인하고 발급되어 있지 않으면 신청 진행
                        registerCarIdToDevelopers(targetList, userId);
                        //제네시스 앱 CARID가 발급되어있는지 최종 확인하고 car id를 DB에 갱신
                        updateCarIdToLocal(targetList, userId, accessToken);
                    }
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                }
            return targetList;
        });

        try {
            return future.get();
        } finally {
            es.shutDownExcutor();
        }
    }

    private void updateCarIdToLocal(List<CarConnectVO> targetList, String userId, String accessToken) {
        if (targetList != null && targetList.size() > 0 && !TextUtils.isEmpty(userId)) {
            CarId.Response carIdResLast = repository.REQ_SYNC_CAR_ID(new CarId.Request(userId));

            if(carIdResLast != null) { //네트워크 통신 장애 시에는 carid를 갱신하지 않음
                if (carIdResLast.getCars() != null && carIdResLast.getCars().size() > 0) {
                    for (int i = 0; i < targetList.size(); i++) {
                        for (CarVO carVO : carIdResLast.getCars()) {
                            if (targetList.get(i).getVin().equalsIgnoreCase(carVO.getVin())) {
                                targetList.get(i).setCarId(carVO.getCarId());
                                try {
                                    targetList.get(i).setResult(reqAgreements(new Agreements.Request(userId, carVO.getCarId(), accessToken), false));
                                } catch (Exception ignore) {

                                }
                                break;
                            }
                        }
                    }
                }
                dbVehicleRepository.insertOrUpdateCarConnect(targetList);
            }
        }
    }


//    /**
//     * @biref Developers CardID 획득 대상 차량 확인
//     * DB를 기준으로 소유차량인데 CARID가 없는 차량을 리스트로 출력
//     */
//    private List<CarConnectVO> getTargetList() {
//        List<CarConnectVO> targetList = new ArrayList<>();
//        List<VehicleVO> list = new ArrayList<>();
//        try {
//            //로컬 db에 있는 소유차량 로드
//            list.addAll(dbVehicleRepository.getVehicleList(MAIN_VEHICLE_TYPE_OV));
//            for (VehicleVO vehicleVO : list) {
//                try {
//                    CarConnectVO carConnectVO = dbVehicleRepository.getCarConnect(vehicleVO.getVin());//로컬 db의 vin을 기준으로 현재 디벨로퍼스 테이블에서 관리하고 있는 차량정보를 조회
//                    if ((carConnectVO == null || TextUtils.isEmpty(carConnectVO.getCarId()))
//                            && !TextUtils.isEmpty(vehicleVO.getVin())) {//로컬 db에 등록된 차대번호에 해당하는 carId가 있는지 확인
//
//                        //carId가 없는 차량을 리스트에 추가
//                        targetList.add(new CarConnectVO(vehicleVO.getVin(), "", "", 2, ""));
//                    }else if(carConnectVO != null&&!TextUtils.isEmpty(carConnectVO.getCarId())&& !TextUtils.isEmpty(vehicleVO.getVin())){
//                        //차량정보가 있고 카아이디가 있고 vin도 있으면 (해당 carid가 유효한 carid인지 확인 필요
//                        targetList.add(new CarConnectVO(vehicleVO.getVin(), "", carConnectVO.getCarId(), 2, ""));
//                    }
//                } catch (Exception ignore) {
//
//                }
//            }
//
//        } catch (Exception ignore) {
//
//        }
//        return targetList;
//    }


//    private List<CarConnectVO> getDeveloperCarInfo(List<CarConnectVO> targetList) {
//
//        List<CarConnectVO> newTargetList = new ArrayList<>();
//
//        if (targetList != null && targetList.size() > 0) {
//            //GCS에 등록된 차량 리스트 확인
//            CarCheck.Response carCheckRes = repository.REQ_SYNC_CAR_CHECK(new CarCheck.Request());
//            //GCS에 등록된 차량이 있으면
//            if (carCheckRes != null && carCheckRes.getCars() != null && carCheckRes.getCars().size() > 0) {
//                //GCS차량 정보를 TARGETLIST에 SET 진행
//                for (CarVO carVO : carCheckRes.getCars()) {
//                    for (int i = 0; i < targetList.size(); i++) {
//                        if (carVO.getVin().equalsIgnoreCase(targetList.get(i).getVin())) {
//                            if(!TextUtils.isEmpty(targetList.get(i).getMasterCarId())&&(targetList.get(i).getMasterCarId().equalsIgnoreCase(carVO.getCarId()))){
//                                //DB에 있는 카아이디와 서버로부터 받은 카 아이디가 같으면
//
//                            }else{
//                                //다르거나 없으면 (가입 혹은 재가입 대상)
//                                targetList.get(i).setMasterCarId(TextUtils.isEmpty(carVO.getCarId()) ? "" : carVO.getCarId());
//                                targetList.get(i).setCarName(TextUtils.isEmpty(carVO.getCarName()) ? "" : carVO.getCarName());
//                                newTargetList.add(targetList.get(i));
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return newTargetList;
//    }

    private void registerCarIdToDevelopers(List<CarConnectVO> targetList, String userId) {
        if (targetList != null && targetList.size() > 0 && !TextUtils.isEmpty(userId)) {
            //제네시스 앱 CARID가 발급되어있는지 확인하고 발급되어 있지 않으면 신청 진행
            CarId.Response carIdRes = repository.REQ_SYNC_CAR_ID(new CarId.Request(userId));
            List<CarConnectVO> reqConnectList = new ArrayList<>();

            for(CarConnectVO target : targetList){
                CarVO carVO;
                try{
                    carVO = carIdRes.getCars().stream().filter(data -> data.getVin().equalsIgnoreCase(target.getVin())).findAny().orElse(null);
                }catch (Exception e){
                    carVO = null;
                }
                if(carVO==null){
                    CarConnectVO carConnectVO = new CarConnectVO("", target.getMasterCarId(), "", 2, "genesis");
                    reqConnectList.add(carConnectVO);
                }
            }

            if(reqConnectList.size() > 0){
                CarConnect.Response carConnectRes = repository.REQ_SYNC_CAR_CONNECT(new CarConnect.Request(reqConnectList, userId));
            }

//            for (int i = 0; i < targetList.size(); i++) {
//                CarConnectVO target = targetList.get(i);
//                if(carIdRes==null||carIdRes.getCars()==null||carIdRes.getCars().size()<1){
//                    CarConnectVO carConnectVO = new CarConnectVO("", target.getMasterCarId(), "", 2, target.getCarName());
//                    reqConnectList.add(carConnectVO);
//                }else{
//                    //대상의 차대번호와 우리 서비스에 등록된 차량 목록의 차대번호와 일치하는 차량이 있는지 확인 (있으면 ccs 연결 대상이 아님)
//                    CarVO carVO = carIdRes.getCars().stream().filter(data -> data.getVin().equalsIgnoreCase(target.getVin())).findAny().orElse(null);
//                    if(carVO==null){
//                        CarConnectVO carConnectVO = new CarConnectVO("", target.getMasterCarId(), "", 2, target.getCarName());
//                        reqConnectList.add(carConnectVO);
//                    }else if(TextUtils.isEmpty(carVO.getCarId())){
//                        //목록에 있더라도 카 아이디가 유효한지 확인. 없으면 연결 대상
//                        CarConnectVO carConnectVO = new CarConnectVO("", target.getMasterCarId(), "", 2, target.getCarName());
//                        reqConnectList.add(carConnectVO);
//                    }
//
//
//
////                    for(CarVO carVO : carIdRes.getCars()) {
////                        if (targetList.get(i).getVin().equalsIgnoreCase(carVO.getVin())) {
////                            if (TextUtils.isEmpty(carVO.getCarId())) {
////                                CarConnectVO carConnectVO = new CarConnectVO("", targetList.get(i).getMasterCarId(), "", 2, targetList.get(i).getCarName());
////                                list.add(carConnectVO);
////                            }
////                        }
////                    }
//                }
//            }
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
            if (!TextUtils.isEmpty(vin))
                carConnectVO = dbVehicleRepository.getCarConnect(vin);

            if (carConnectVO != null)
                carId = carConnectVO.getCarId();

        } catch (Exception e) {

        }

        return carId;
    }

    public CarConnectVO getCarConnectVO(String vin) {
        CarConnectVO carConnectVO = null;
        try {
            if (!TextUtils.isEmpty(vin))
                carConnectVO = dbVehicleRepository.getCarConnect(vin);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return carConnectVO;
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
        Future<VehicleVO> future = es.getListeningExecutorService().submit(() -> {
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
        } finally {
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

    /**
     * @param token  CCSP ACCESS TOKEN
     * @param userId CCSP USER ID
     * @param carId  CCS CAR ID
     * @return 데이터 마일즈 상세 URL
     * @biref 데이터마일즈 상세 페이지 URL 확인
     */
    public String getDataMilesDetailUrl(String token, String userId, String carId) {
        return GAInfo.GA_DATAMILES_DETAIL_URL + getParams(token, userId, carId).getQuery();
    }

    /**
     * @param token  CCSP ACCESS TOKEN
     * @param userId CCSP USER ID
     * @param carId  CCS CAR ID
     * @return 데이터 마일즈 약관 동의 URL
     * @biref 데이터마일즈 약관동의 페이지 URL 확인
     */
    public String getDataMilesAgreementsUrl(String token, String userId, String carId) {
        return GAInfo.GA_DATAMILES_AGREEMENTS_URL + getParams(token, userId, carId).getQuery();
    }

    public QueryString getParams(String token, String userId, String carId) {
        QueryString q = new QueryString();
        q.add(GAInfo.GA_DATAMILES_KEY_TOKEN, token);
        q.add(GAInfo.GA_DATAMILES_KEY_USER_ID, userId);
        q.add(GAInfo.GA_DATAMILES_KEY_CAR_ID, carId);
        return q;
    }

    public enum CCSSTAT {
        STAT_AGREEMENT,
        STAT_DISAGREEMENT,
        STAT_DISABLE
    }

    public CCSSTAT checkCarInfoToDevelopers(String vin, String userId) {
        CarConnectVO carConnectVO = getCarConnectVO(vin);
        //ccs 연동된 상태일 경우
        if (carConnectVO != null && !TextUtils.isEmpty(carConnectVO.getCarId())) {
            if (carConnectVO.isResult()) {
                return CCSSTAT.STAT_AGREEMENT;
            } else {
                return CCSSTAT.STAT_DISAGREEMENT;
            }
        } else {
            //ccs 미 연동 상태일 경우
            return CCSSTAT.STAT_DISABLE;
        }
    }


//    public CCSSTAT checkCarInfoToDevelopers(String vin, String userId) {
//        CarConnectVO carConnectVO = getCarConnectVO(vin);
//        //ccs 연동된 상태일 경우
//        if (carConnectVO != null && !TextUtils.isEmpty(carConnectVO.getCarId())) {
//            if (carConnectVO.isResult()) {
//                return CCSSTAT.STAT_AGREEMENT;
//            } else {
//                //정보제공 미동의상태 인 경우
//                boolean result = false;
//
//                try {
//                    //정보 동의를 상태를 확인
//                    result = reqAgreements(new Agreements.Request(userId, carConnectVO.getCarId()), true);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                if (result) {
//                    //동의가 된 경우
//                    return CCSSTAT.STAT_AGREEMENT;
//                } else {
//                    //동의되지 않은 경우
//                    return CCSSTAT.STAT_DISAGREEMENT;
//                }
//            }
//        } else {
//            //ccs 미 연동 상태일 경우
//            return CCSSTAT.STAT_DISABLE;
//        }
//    }


}