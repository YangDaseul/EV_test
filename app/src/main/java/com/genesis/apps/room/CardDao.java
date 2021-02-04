package com.genesis.apps.room;

import com.genesis.apps.comm.model.vo.CardVO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;


@Dao
public abstract class CardDao implements BaseDao<CardVO> {
    @Query("SELECT * FROM CardVO WHERE cardNo=:cardNo")
    public abstract List<CardVO> select(String cardNo);

    @Query("SELECT * FROM CardVO ORDER BY orderNumber ASC")
    public abstract List<CardVO> selectAll();

    @Query("DELETE from CardVO")
    public abstract void deleteAll();

    @Query("DELETE FROM CardVO WHERE cardNo =:cardNo")
    public abstract void deleteCustGbCd(String cardNo);

    @Transaction
    public void insertAndDeleteInTransaction(List<CardVO> list){
        deleteAll();
        insert(list);
    }
}