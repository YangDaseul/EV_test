package com.genesis.apps.room;

import androidx.room.Dao;
import androidx.room.Query;

import com.genesis.apps.comm.model.vo.MenuVO;

import java.util.List;

@Dao
public interface MenuDao extends BaseDao<MenuVO>{

//    @Query("SELECT * from MenuVO")
//    MenuVO select();

    @Query("SELECT * FROM MenuVO ORDER BY _id DESC")
    List<MenuVO> selectAll();

    @Query("DELETE from MenuVO")
    void deleteAll();

    @Query("DELETE FROM MenuVO WHERE name =:name")
    void deleteName(String name);

    @Query("DELETE FROM MenuVO WHERE _id IN (SELECT * FROM (SELECT _id FROM MenuVO ORDER BY _id DESC LIMIT 20, 100000))")
    void deleteAuto();

}
