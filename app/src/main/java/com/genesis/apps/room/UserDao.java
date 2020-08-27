package com.genesis.apps.room;

import androidx.room.Dao;
import androidx.room.Query;

import com.genesis.apps.comm.model.vo.UserVO;

@Dao
public interface UserDao extends BaseDao<UserVO> {
//    @Query("SELECT value FROM DeviceDTO WHERE name = :name")
//    String get(String name);

//    @Query("SELECT * from UserVO LIMIT 1")
//    UserVO select();
    @Query("SELECT * from UserVO WHERE _id=1")
    UserVO select();

    @Query("DELETE from UserVO")
    void deleteAll();
}
