package com.genesis.apps.room;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.genesis.apps.comm.model.vo.ContentsVO;

import java.util.List;

@Dao
public abstract class ContentsDao implements BaseDao<ContentsVO> {
    @Query("SELECT * FROM ContentsVO WHERE catCd=:catCd")
    public abstract List<ContentsVO> select(String catCd);

    @Query("SELECT * FROM ContentsVO")
    public abstract List<ContentsVO> selectAll();

    @Query("DELETE from ContentsVO")
    public abstract void deleteAll();

    @Transaction
    public void insertAndDeleteInTransaction(List<ContentsVO> list){
        deleteAll();
        insert(list);
    }
}