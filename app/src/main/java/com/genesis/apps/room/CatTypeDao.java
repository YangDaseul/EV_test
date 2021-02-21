package com.genesis.apps.room;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.genesis.apps.comm.model.vo.CatTypeVO;

import java.util.List;

@Dao
public abstract class CatTypeDao implements BaseDao<CatTypeVO> {
    @Query("SELECT * FROM CatTypeVO")
    public abstract List<CatTypeVO> selectAll();

    @Query("SELECT * FROM CatTypeVO WHERE cd=:cd")
    public abstract CatTypeVO select(String cd);

    @Query("SELECT cd FROM CatTypeVO WHERE cdNm=:cdNm")
    public abstract String selectCd(String cdNm);

    @Query("DELETE from CatTypeVO")
    public abstract void deleteAll();

    @Transaction
    public void insertAndDeleteInTransaction(List<CatTypeVO> list){
        deleteAll();
        insert(list);
    }

}