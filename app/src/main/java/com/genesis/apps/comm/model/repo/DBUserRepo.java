package com.genesis.apps.comm.model.repo;

import com.genesis.apps.comm.model.vo.UserVO;
import com.genesis.apps.room.DatabaseHolder;

import javax.inject.Inject;

public class DBUserRepo {
    private DatabaseHolder databaseHolder;

    @Inject
    public DBUserRepo(DatabaseHolder databaseHolder){
        this.databaseHolder = databaseHolder;
    }

    public UserVO getUserVO(){
        return databaseHolder.getDatabase().userDao().select();
    }

    public boolean setUserVO(UserVO data){
        boolean isUpdate = false;
        try{
            databaseHolder.getDatabase().userDao().deleteAll();
            databaseHolder.getDatabase().userDao().insert(data);
            isUpdate=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isUpdate;
    }

    public boolean clearUserInfo(){
        boolean isClear = false;
        try{
            databaseHolder.getDatabase().userDao().deleteAll();
            isClear=true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return isClear;
    }

}
