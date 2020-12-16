package com.genesis.apps.room;

import com.genesis.apps.comm.model.vo.QuickMenuVO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class QuickMenuDao implements BaseDao<QuickMenuVO> {
    @Query("SELECT * FROM QuickMenuVO WHERE custGbCd=:custGbCd order by nttOrd asc")
    public abstract List<QuickMenuVO> select(String custGbCd);

    @Query("DELETE from QuickMenuVO")
    public abstract void deleteAll();

    @Query("DELETE FROM QuickMenuVO WHERE custGbCd =:custGbCd")
    public abstract void deleteCustGbCd(String custGbCd);

    @Transaction
    public void insertAndDeleteInTransaction(List<QuickMenuVO> list, String custGbCd){
        deleteCustGbCd(custGbCd);
        insert(list);
    }
}
