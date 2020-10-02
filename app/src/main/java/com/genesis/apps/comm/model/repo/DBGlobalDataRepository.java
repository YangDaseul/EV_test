package com.genesis.apps.comm.model.repo;

import com.genesis.apps.comm.net.NetUIResponse;
import com.genesis.apps.room.DatabaseHolder;
import com.genesis.apps.room.GlobalData;

import javax.inject.Inject;

import androidx.lifecycle.MutableLiveData;

public class DBGlobalDataRepository {
    private DatabaseHolder databaseHolder;

    public final MutableLiveData<NetUIResponse<String>> data = new MutableLiveData<>();

    @Inject
    public DBGlobalDataRepository(DatabaseHolder databaseHolder){
        this.databaseHolder = databaseHolder;
    }

    public String select(String keyName){
        return databaseHolder.getDatabase().globalDataDao().get(keyName);
    }

    public boolean update(String keyName, String value){
        boolean isUpdate = false;
        try{
            GlobalData data = new GlobalData(keyName, value);
            databaseHolder.getDatabase().globalDataDao().insert(data);
            isUpdate=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpdate;
    }


}
