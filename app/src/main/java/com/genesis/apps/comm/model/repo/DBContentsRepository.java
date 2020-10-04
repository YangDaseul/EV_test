package com.genesis.apps.comm.model.repo;

import com.genesis.apps.comm.model.vo.FloatingMenuVO;
import com.genesis.apps.comm.model.vo.QuickMenuVO;
import com.genesis.apps.comm.model.vo.WeatherVO;
import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.room.DatabaseHolder;
import com.genesis.apps.room.GlobalData;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

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

    public List<QuickMenuVO> getQuickMenu(String type){
        return databaseHolder.getDatabase().quickMenuDao().select(type);
    }

    public boolean setQuickMenu(List<QuickMenuVO> list, String type){
        boolean isUpdate = false;
        try{
            for(int i=0; i<list.size();i++){
                list.get(i).setType(type);
            }
            databaseHolder.getDatabase().quickMenuDao().insertAndDeleteInTransaction(list, type);
            isUpdate=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpdate;
    }


    public List<FloatingMenuVO> getFloatingMenu(String type){
        return databaseHolder.getDatabase().floatingMenuDao().select(type);
    }

    public boolean setFloatingMenu(List<FloatingMenuVO> list, String type){
        boolean isUpdate = false;
        try{
            for(int i=0; i<list.size();i++){
                list.get(i).setType(type);
            }
            databaseHolder.getDatabase().floatingMenuDao().insertAndDeleteInTransaction(list, type);
            isUpdate=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpdate;
    }
    
    
}
