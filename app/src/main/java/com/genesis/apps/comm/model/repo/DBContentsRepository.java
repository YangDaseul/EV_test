package com.genesis.apps.comm.model.repo;

import com.genesis.apps.comm.model.vo.DownMenuVO;
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
    
    
}
