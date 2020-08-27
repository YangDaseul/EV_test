package com.genesis.apps.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.genesis.apps.comm.model.vo.DeviceDTO;
import com.genesis.apps.comm.model.vo.UserVO;

@Database(entities = {GlobalData.class, UserVO.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GlobalDataDao globalDataDao();
    public abstract UserDao userDao();
}