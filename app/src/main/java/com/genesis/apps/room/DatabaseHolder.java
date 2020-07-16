package com.genesis.apps.room;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseHolder {
    private AppDatabase db = null;

    public DatabaseHolder() {

    }

    public synchronized boolean isOpen() {
        return db != null && db.isOpen();
    }

    public synchronized AppDatabase getDatabase() {
        if (isOpen() == false) {
            throw new IllegalStateException("open first");
        }
        if (db == null) {
            throw new IllegalStateException("Database is null");
        }
        return db;
    }

    public synchronized void openDatabase(Context context) {
        if (isOpen()) {
            return;
        }
        db = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "AppDatabase")
                .allowMainThreadQueries()
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Log.d("TESTDB", "databaseBuilder.onCreate");
//                        db.execSQL("INSERT INTO expiredate_info (startDate, endDate, limitCount, excuteCount)  VALUES ('', '', 0,0);");
                    }

                    @Override
                    public void onOpen(@NonNull SupportSQLiteDatabase db) {
                        super.onOpen(db);
                        Log.d("TESTDB", "databaseBuilder.onOpen");
                    }
                })
                .build();
        db.globalDataDao().selectAll();
    }

}
