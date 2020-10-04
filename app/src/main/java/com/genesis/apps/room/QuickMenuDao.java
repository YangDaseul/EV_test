package com.genesis.apps.room;

import com.genesis.apps.comm.model.vo.QuickMenuVO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class QuickMenuDao implements BaseDao<QuickMenuVO> {
    @Query("SELECT * FROM QuickMenuVO WHERE type=:type")
    public abstract List<QuickMenuVO> select(String type);

    @Query("DELETE from QuickMenuVO")
    public abstract void deleteAll();

    @Query("DELETE FROM QuickMenuVO WHERE type =:type")
    public abstract void deleteType(String type);

    @Transaction
    public void insertAndDeleteInTransaction(List<QuickMenuVO> list, String type){
        deleteType(type);
        insert(list);
    }
}
