package com.genesis.apps.room;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.genesis.apps.comm.model.vo.FamilyAppVO;

import java.util.List;


@Dao
public abstract class FamilyAppDao implements BaseDao<FamilyAppVO> {
    @Query("SELECT * FROM FamilyAppVO order by nttOrd asc")
    public abstract List<FamilyAppVO> select();

    @Query("DELETE FROM FamilyAppVO")
    public abstract void delete();

    @Transaction
    public void insertAndDeleteInTransaction(List<FamilyAppVO> list){
        delete();
        insert(list);
    }
}