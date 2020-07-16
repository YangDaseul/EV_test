package com.genesis.apps.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {GlobalData.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract GlobalDataDao globalDataDao();
}