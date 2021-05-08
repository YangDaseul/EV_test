package com.genesis.apps.comm.model.repo;

import android.text.TextUtils;

import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.developers.CarConnectVO;
import com.genesis.apps.comm.util.StringUtil;
import com.genesis.apps.room.DatabaseHolder;

import java.util.List;

import javax.inject.Inject;

public class DBVehicleRepository {
    private DatabaseHolder databaseHolder;

    @Inject
    public DBVehicleRepository(DatabaseHolder databaseHolder){
        this.databaseHolder = databaseHolder;
    }

    public boolean deleteAllVehicle(){
        boolean isDel = false;
        try {
            databaseHolder.getDatabase().vehicleDao().deleteAll();
            isDel = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isDel;
    }

    public List<VehicleVO> getVehicleListAll(){
        return databaseHolder.getDatabase().vehicleDao().selectAll();
    }

    public List<VehicleVO> getVehicleList(String type){
        return databaseHolder.getDatabase().vehicleDao().select(type);
    }

    public VehicleVO getVehicle(String vin){
        return databaseHolder.getDatabase().vehicleDao().selectVin(vin);
    }

    public boolean setVehicleList(List<VehicleVO> list, String custGbCd){
        boolean isUpdate = false;
        try{
            if(list!=null) {
                for (int i = 0; i < list.size(); i++) {
                    list.get(i).setCustGbCd(custGbCd);
                }
                databaseHolder.getDatabase().vehicleDao().insertAndDeleteInTransaction(list, custGbCd);
            }else{
                databaseHolder.getDatabase().vehicleDao().deleteCustGbCd(custGbCd);
            }
            isUpdate=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpdate;
    }


    public boolean setVehicle(VehicleVO vehicleVO, String custGbCd){
        boolean isUpdate = false;
        try{
            if(vehicleVO!=null) {
                vehicleVO.setCustGbCd(custGbCd);
                databaseHolder.getDatabase().vehicleDao().insertAndDeleteInTransaction(vehicleVO, custGbCd);
            }else{
                databaseHolder.getDatabase().vehicleDao().deleteCustGbCd(custGbCd);
            }
            isUpdate=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpdate;
    }


    /**
     * @brief 주 이용 차량 확인
     * 지정된 리스트에서 주 이용 차량을 확인
     * @param list 지정된 리스트
     * @return 주 이용 차량
     */
    public VehicleVO getMainVehicleVO(List<VehicleVO> list){
        VehicleVO mainVehicle = null;

        repeat:
        for(VehicleVO row : list){
            switch (row.getCustGbCd()){
                case VariableType.MAIN_VEHICLE_TYPE_OV:
                    //소유차량이고 주 이용차량이면 해당 차량 사용
                    String delYn = row.getDelYn();
                    String delExpYn = row.getDelExpYn();
                    //삭제예정 차량이 아닌고
                    if(!(!TextUtils.isEmpty(delYn)&&delYn.equalsIgnoreCase(VariableType.COMMON_MEANS_YES))&&!(!TextUtils.isEmpty(delExpYn)&&delExpYn.equalsIgnoreCase(VariableType.COMMON_MEANS_YES))) {
                        if (row.getMainVhclYn().equalsIgnoreCase(VariableType.MAIN_VEHICLE_Y)) {
                            mainVehicle = row;
                            break repeat;
                        } else if (mainVehicle == null || !mainVehicle.getCustGbCd().equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_OV)) {//소유차량이고 주 이용 차량이 아니면 일단 임시 저장
                            mainVehicle = row;
                        }
                    }
                    break;
                case VariableType.MAIN_VEHICLE_TYPE_CV:
                    if(mainVehicle==null|| (!mainVehicle.getCustGbCd().equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_CV)&&!mainVehicle.getCustGbCd().equalsIgnoreCase(VariableType.MAIN_VEHICLE_TYPE_OV))){
                        mainVehicle = row;
                    }
                    break;
                default:
                    if(mainVehicle==null){
                        mainVehicle = row;
                    }
                    break;
            }
        }

        if(mainVehicle!=null){
            for (VehicleVO row : list) {
                if (!StringUtil.isValidString(mainVehicle.getVin()).equalsIgnoreCase(StringUtil.isValidString(row.getVin()))) {
                    row.setMainVhclYn(VariableType.MAIN_VEHICLE_N);
                    updateVehicle(row);
                }
            }
            mainVehicle.setMainVhclYn(VariableType.MAIN_VEHICLE_Y);
            updateVehicle(mainVehicle);
        }

        return mainVehicle;
    }

    public VehicleVO getMainVehicleFromDB(){
        return getMainVehicleVO(getVehicleListAll());
    }

    public VehicleVO getMainVehicleSimplyFromDB(){
        return databaseHolder.getDatabase().vehicleDao().selectMainVehicle();
    }

    public List<VehicleVO> getVehicleList(){
        return databaseHolder.getDatabase().vehicleDao().selectMyCarList();
    }

    public List<VehicleVO> getVehicleListEV(){
        return databaseHolder.getDatabase().vehicleDao().selectEVCarList();
    }

    public List<VehicleVO> getInsightVehicleList(){
        return databaseHolder.getDatabase().vehicleDao().selectInsightExpnList();
    }

    public void updateVehicle(VehicleVO vehicleVO){
        databaseHolder.getDatabase().vehicleDao().update(vehicleVO);
    }

    public CarConnectVO getCarConnect(String vin){
        return databaseHolder.getDatabase().carConnectDao().select(vin);
    }

    public void insertOrUpdateCarConnect(List<CarConnectVO> list){
        databaseHolder.getDatabase().carConnectDao().insertOrUpdate(list);
    }

    public void updateCarConnect(boolean result, String carId){
        databaseHolder.getDatabase().carConnectDao().updateResult(result, carId);
    }

}
