package com.genesis.apps.room;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Update;

@Dao
public interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(T obj);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(T... obj);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(List<T> obj);

    @Update
    void update(T obj);

    @Delete
    void delete(T obj);
}
