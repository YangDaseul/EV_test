package com.genesis.apps.comm.model.repo;

import com.genesis.apps.comm.model.vo.AlarmMsgTypeVO;
import com.genesis.apps.comm.model.vo.BtoVO;
import com.genesis.apps.comm.model.vo.CatTypeVO;
import com.genesis.apps.comm.model.vo.DownMenuVO;
import com.genesis.apps.comm.model.vo.FamilyAppVO;
import com.genesis.apps.comm.model.vo.QuickMenuVO;
import com.genesis.apps.comm.model.vo.WeatherVO;
import com.genesis.apps.room.DatabaseHolder;

import java.util.List;

import javax.inject.Inject;

public class DBContentsRepository {
    private DatabaseHolder databaseHolder;

//    public final MutableLiveData<NetUIResponse<String>> data = new MutableLiveData<>();

    @Inject
    public DBContentsRepository(DatabaseHolder databaseHolder){
        this.databaseHolder = databaseHolder;
    }

    public List<AlarmMsgTypeVO> getAlarmMsgTypeList(){
        return databaseHolder.getDatabase().alarmMsgTypeDao().selectAll();
    }

    public AlarmMsgTypeVO getAlarmMsgType(String cd){
        return databaseHolder.getDatabase().alarmMsgTypeDao().select(cd);
    }

    public String getAlarmMsgTypeCd(String cdNm) {
        return databaseHolder.getDatabase().alarmMsgTypeDao().selectCd(cdNm);
    }

    public boolean setAlarmMsgTypeList(List<AlarmMsgTypeVO> list){
        boolean isUpdate = false;
        try{
            databaseHolder.getDatabase().alarmMsgTypeDao().insertAndDeleteInTransaction(list);
            isUpdate=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpdate;
    }

    public List<CatTypeVO> getCatTypeList(){
        return databaseHolder.getDatabase().catTypeDao().selectAll();
    }

    public String getCatTypeCd(String cdNm) {
        return databaseHolder.getDatabase().catTypeDao().selectCd(cdNm);
    }

    public boolean setCatTypeList(List<CatTypeVO> list){
        boolean isUpdate = false;
        try{
            databaseHolder.getDatabase().catTypeDao().insertAndDeleteInTransaction(list);
            isUpdate=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpdate;
    }

    public List<WeatherVO> getWeatherList(){
        return databaseHolder.getDatabase().weatherDao().selectAll();
    }

    public WeatherVO getWeatherRandom(String dbCode){
        return databaseHolder.getDatabase().weatherDao().selectRandom(dbCode);
    }

    public boolean setWeatherList(List<WeatherVO> list){
        boolean isUpdate = false;
        try{
            databaseHolder.getDatabase().weatherDao().insertAndDeleteInTransaction(list);
            isUpdate=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpdate;
    }

    public List<QuickMenuVO> getQuickMenu(String custGbCd){
        return databaseHolder.getDatabase().quickMenuDao().select(custGbCd);
    }

    public boolean setQuickMenu(List<QuickMenuVO> list, String custGbCd){
        boolean isUpdate = false;
        try{
            for(int i=0; i<list.size();i++){
                list.get(i).setCustGbCd(custGbCd);
            }
            databaseHolder.getDatabase().quickMenuDao().insertAndDeleteInTransaction(list, custGbCd);
            isUpdate=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpdate;
    }


    public List<DownMenuVO> getDownMenu(String custGbCd){
        return databaseHolder.getDatabase().downMenuDao().select(custGbCd);
    }

    public boolean setDownMenu(List<DownMenuVO> list, String custGbCd){
        boolean isUpdate = false;
        try{
            for(int i=0; i<list.size();i++){
                list.get(i).setCustGbCd(custGbCd);
            }
            databaseHolder.getDatabase().downMenuDao().insertAndDeleteInTransaction(list, custGbCd);
            isUpdate=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpdate;
    }

    public List<FamilyAppVO> getFamilyApp(){
        return databaseHolder.getDatabase().familyAppDao().select();
    }

    public boolean setFamilyApp(List<FamilyAppVO> list){
        boolean isUpdate = false;
        try{
            databaseHolder.getDatabase().familyAppDao().insertAndDeleteInTransaction(list);
            isUpdate=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpdate;
    }

    public BtoVO getBto(String mdlNm){
        return databaseHolder.getDatabase().btoDao().select(mdlNm);
    }

    public boolean setBto(List<BtoVO> list){
        boolean isUpdate = false;
        try{
            databaseHolder.getDatabase().btoDao().insertAndDeleteInTransaction(list);
            isUpdate=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpdate;
    }

}
