package com.genesis.apps.room;

import com.genesis.apps.comm.model.vo.NotiInfoVO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;


@Dao
public abstract class NotiInfoDao implements BaseDao<NotiInfoVO> {
    @Query("SELECT * FROM NotiInfoVO WHERE cateCd=:cateCd ORDER BY notDt DESC")
    public abstract List<NotiInfoVO> select(String cateCd);

    @Query("SELECT * FROM NotiInfoVO ORDER BY notDt DESC")
    public abstract List<NotiInfoVO> selectAll();

    @Query("SELECT * FROM NotiInfoVO where cateNm LIKE :search OR title LIKE :search OR contents LIKE :search ORDER BY notDt DESC")
    public abstract List<NotiInfoVO> selectSearch(String search);

    @Query("DELETE from NotiInfoVO")
    public abstract void deleteAll();


    @Transaction
    public void insertAndDeleteInTransaction(List<NotiInfoVO> list){
        deleteAll();
        insert(list);
    }
}