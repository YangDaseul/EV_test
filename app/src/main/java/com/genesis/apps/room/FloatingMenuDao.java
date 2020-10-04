package com.genesis.apps.room;

import com.genesis.apps.comm.model.vo.FloatingMenuVO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;


@Dao
public abstract class FloatingMenuDao implements BaseDao<FloatingMenuVO> {
    @Query("SELECT * FROM FloatingMenuVO WHERE type=:type")
    public abstract List<FloatingMenuVO> select(String type);

    @Query("DELETE from FloatingMenuVO")
    public abstract void deleteAll();

    @Query("DELETE FROM FloatingMenuVO WHERE type =:type")
    public abstract void deleteType(String type);

    @Transaction
    public void insertAndDeleteInTransaction(List<FloatingMenuVO> list, String type){
        deleteType(type);
        insert(list);
    }
}