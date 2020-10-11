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

    @Query("SELECT * FROM VehicleVO WHERE vin=:vin")
    public abstract VehicleVO selectVin(String vin);

    //소유차량 우선, 주 이용 차량이고 , 서버에서 준 리스트 순서대로 정렬
    @Query("SELECT * FROM VehicleVO WHERE custGbCd='OV' OR custGbCd='CV' ORDER BY custGbCd DESC, mainVhclYn DESC, _id ASC")
    public abstract List<VehicleVO> selectMyCarList();

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