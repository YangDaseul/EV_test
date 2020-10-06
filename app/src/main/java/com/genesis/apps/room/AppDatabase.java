package com.genesis.apps.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.genesis.apps.comm.model.vo.DeviceDTO;
import com.genesis.apps.comm.model.vo.FloatingMenuVO;
import com.genesis.apps.comm.model.vo.MenuVO;
import com.genesis.apps.comm.model.vo.QuickMenuVO;
import com.genesis.apps.comm.model.vo.UserVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.WeatherVO;

@Database(entities = {GlobalData.class, UserVO.class, MenuVO.class, WeatherVO.class, QuickMenuVO.class, FloatingMenuVO.class, VehicleVO.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GlobalDataDao globalDataDao();
    public abstract UserDao userDao();
    public abstract MenuDao menuDao();
    public abstract WeatherDao weatherDao();
    public abstract QuickMenuDao quickMenuDao();
    public abstract FloatingMenuDao floatingMenuDao();
    public abstract VehicleDao vehicleDao();
}