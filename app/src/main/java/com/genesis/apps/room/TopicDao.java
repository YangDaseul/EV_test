package com.genesis.apps.room;

import com.genesis.apps.comm.model.vo.TopicVO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;


@Dao
public abstract class TopicDao implements BaseDao<TopicVO> {
    @Query("SELECT * FROM TopicVO")
    public abstract List<TopicVO> selectAll();

    @Query("DELETE from TopicVO")
    public abstract void deleteAll();

    @Transaction
    public void insertAndDeleteInTransaction(List<TopicVO> list){
        deleteAll();
        insert(list);
    }
}