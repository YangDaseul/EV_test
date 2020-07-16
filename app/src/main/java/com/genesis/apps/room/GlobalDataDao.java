package com.genesis.apps.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface GlobalDataDao {
    @Query("SELECT value FROM GlobalData WHERE name = :name")
    String get(String name);

    @Query("SELECT value FROM GlobalData WHERE name = :name")
    int getInt(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GlobalData data);

    @Query("SELECT * from GlobalData")
    List<GlobalData> selectAll();
}
