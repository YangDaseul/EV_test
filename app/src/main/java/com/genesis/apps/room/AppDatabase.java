package com.genesis.apps.room;

import com.genesis.apps.comm.model.vo.AddressVO;
import com.genesis.apps.comm.model.vo.CardVO;
import com.genesis.apps.comm.model.vo.ContentsVO;
import com.genesis.apps.comm.model.vo.DownMenuVO;
import com.genesis.apps.comm.model.vo.MenuVO;
import com.genesis.apps.comm.model.vo.NotiInfoVO;
import com.genesis.apps.comm.model.vo.QuickMenuVO;
import com.genesis.apps.comm.model.vo.UserVO;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.genesis.apps.comm.model.vo.WeatherVO;
import com.genesis.apps.comm.model.vo.developers.CarConnectVO;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {GlobalData.class, UserVO.class, MenuVO.class, WeatherVO.class, QuickMenuVO.class, DownMenuVO.class, VehicleVO.class, CardVO.class, NotiInfoVO.class, ContentsVO.class, AddressVO.class, CarConnectVO.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GlobalDataDao globalDataDao();
    public abstract UserDao userDao();
    public abstract MenuDao menuDao();
    public abstract WeatherDao weatherDao();
    public abstract QuickMenuDao quickMenuDao();
    public abstract DownMenuDao downMenuDao();
    public abstract VehicleDao vehicleDao();
    public abstract CardDao cardDao();
    public abstract NotiInfoDao notiInfoDao();
    public abstract ContentsDao contentsDao();
    public abstract AddressDao addressDao();
    public abstract CarConnectDao carConnectDao();
}