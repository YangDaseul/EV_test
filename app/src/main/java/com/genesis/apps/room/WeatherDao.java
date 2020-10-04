package com.genesis.apps.room;

import com.genesis.apps.comm.model.vo.WeatherVO;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;

@Dao
public abstract class WeatherDao implements BaseDao<WeatherVO> {
    @Query("SELECT * FROM WeatherVO")
    public abstract List<WeatherVO> selectAll();

    @Query("DELETE from WeatherVO")
    public abstract void deleteAll();

    @Query("DELETE FROM WeatherVO WHERE wthrCd =:wthrCd")
    public abstract void deleteCd(String wthrCd);

    @Transaction
    public void insertAndDeleteInTransaction(List<WeatherVO> list){
        deleteAll();
        insert(list);
    }
}
