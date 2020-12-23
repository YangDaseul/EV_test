package com.genesis.apps.comm.model.repo;

import com.genesis.apps.comm.model.vo.TopicVO;
import com.genesis.apps.comm.model.vo.UserVO;
import com.genesis.apps.room.DatabaseHolder;

import java.util.ArrayList;
import java.util.List;

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

    public List<TopicVO> getTopicList(){
        List<TopicVO> list=null;
        try{
            list = databaseHolder.getDatabase().topicDao().selectAll();
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public void insertTopicList(List<String> oriList){
        List<TopicVO> list=new ArrayList<>();
        for(String topic : oriList){
            list.add(new TopicVO(topic));
        }
        databaseHolder.getDatabase().topicDao().insertAndDeleteInTransaction(list);
    }
}
