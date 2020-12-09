package com.genesis.apps.room;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.genesis.apps.comm.model.vo.BtoVO;

import java.util.List;


@Dao
public abstract class BtoDao implements BaseDao<BtoVO> {
    @Query("SELECT * FROM BtoVO WHERE mdlNm=:mdlNm")
    public abstract BtoVO select(String mdlNm);

    @Query("DELETE from BtoVO")
    public abstract void deleteAll();

    @Query("DELETE FROM BtoVO")
    public abstract void delete();

    @Transaction
    public void insertAndDeleteInTransaction(List<BtoVO> list){
        delete();
        insert(list);
    }
}