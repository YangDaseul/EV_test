package com.genesis.apps.room;

import android.text.TextUtils;

import com.genesis.apps.comm.model.vo.AddressVO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;


@Dao
public abstract class AddressDao implements BaseDao<AddressVO> {

    @Query("SELECT * FROM AddressVO ORDER BY _id DESC")
    public abstract List<AddressVO> selectAll();

    @Query("DELETE from AddressVO")
    public abstract void deleteAll();

    @Query("DELETE FROM AddressVO WHERE addrRoad =:addrRoad")
    public abstract void deleteName(String addrRoad);

    @Query("DELETE FROM AddressVO WHERE addr=:addr and title=:title")
    public abstract void deleteAddr(String addr, String title);


    @Query("DELETE FROM AddressVO WHERE _id IN (SELECT * FROM (SELECT _id FROM AddressVO ORDER BY _id DESC LIMIT 20, 100000))")
    public abstract void deleteAuto();

    @Transaction
    public void insertAndDeleteInTransaction(AddressVO addressVO) {
        if (!TextUtils.isEmpty(addressVO.getAddrRoad())) {
            deleteName(addressVO.getAddrRoad());
        } else if (!TextUtils.isEmpty(addressVO.getAddr())) {
            deleteAddr(addressVO.getAddr(),
                      (TextUtils.isEmpty(addressVO.getTitle()) ? "" : addressVO.getTitle()));
        }
        insert(addressVO);
        deleteAuto();
    }
}