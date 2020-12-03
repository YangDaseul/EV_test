package com.genesis.apps.room;

import com.genesis.apps.comm.model.vo.DownMenuVO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;


@Dao
public abstract class DownMenuDao implements BaseDao<DownMenuVO> {
    @Query("SELECT * FROM DownMenuVO WHERE custGbCd=:custGbCd")
    public abstract List<DownMenuVO> select(String custGbCd);

    @Query("DELETE from DownMenuVO")
    public abstract void deleteAll();

    @Query("DELETE FROM DownMenuVO WHERE custGbCd =:custGbCd")
    public abstract void deleteCustGbCd(String custGbCd);

    @Transaction
    public void insertAndDeleteInTransaction(List<DownMenuVO> list, String custGbCd){
        deleteCustGbCd(custGbCd);
        insert(list);
    }
}