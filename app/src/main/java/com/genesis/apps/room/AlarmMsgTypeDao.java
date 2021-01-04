package com.genesis.apps.room;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.genesis.apps.comm.model.vo.AlarmMsgTypeVO;

import java.util.List;

@Dao
public abstract class AlarmMsgTypeDao implements BaseDao<AlarmMsgTypeVO> {
    @Query("SELECT * FROM AlarmMsgTypeVO")
    public abstract List<AlarmMsgTypeVO> selectAll();

    @Query("SELECT * FROM AlarmMsgTypeVO WHERE cd=:cd")
    public abstract AlarmMsgTypeVO select(String cd);

    @Query("SELECT cd FROM AlarmMsgTypeVO WHERE cdNm=:cdNm")
    public abstract String selectCd(String cdNm);

    @Query("DELETE from AlarmMsgTypeVO")
    public abstract void deleteAll();

    @Transaction
    public void insertAndDeleteInTransaction(List<AlarmMsgTypeVO> list){
        deleteAll();
        insert(list);
    }

}