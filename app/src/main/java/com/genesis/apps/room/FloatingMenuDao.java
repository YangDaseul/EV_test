package com.genesis.apps.room;

import com.genesis.apps.comm.model.vo.FloatingMenuVO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;


@Dao
public abstract class FloatingMenuDao implements BaseDao<FloatingMenuVO> {
    @Query("SELECT * FROM FloatingMenuVO WHERE custGbCd=:custGbCd")
    public abstract List<FloatingMenuVO> select(String custGbCd);

    @Query("DELETE from FloatingMenuVO")
    public abstract void deleteAll();

    @Query("DELETE FROM FloatingMenuVO WHERE custGbCd =:custGbCd")
    public abstract void deleteCustGbCd(String custGbCd);

    @Transaction
    public void insertAndDeleteInTransaction(List<FloatingMenuVO> list, String custGbCd){
        deleteCustGbCd(custGbCd);
        insert(list);
    }
}