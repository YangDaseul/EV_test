package com.genesis.apps.room;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

import com.genesis.apps.comm.model.vo.FloatingMenuVO;
import com.genesis.apps.comm.model.vo.MenuVO;
import com.genesis.apps.comm.model.vo.VehicleVO;

import java.util.List;


@Dao
public abstract class VehicleDao implements BaseDao<VehicleVO> {

    @Query("SELECT * FROM VehicleVO ORDER BY _id DESC")
    public abstract List<VehicleVO> selectAll();

    @Query("SELECT * FROM VehicleVO WHERE custGbCd=:custGbCd")
    public abstract List<VehicleVO> select(String custGbCd);

    @Query("DELETE from VehicleVO")
    public abstract void deleteAll();

    @Query("DELETE FROM VehicleVO WHERE custGbCd =:custGbCd")
    public abstract void deleteCustGbCd(String custGbCd);

    @Transaction
    public void insertAndDeleteInTransaction(List<VehicleVO> list, String custGbCd){
        deleteCustGbCd(custGbCd);
        insert(list);
    }
}