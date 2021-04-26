package com.genesis.apps.room;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class DatabaseHolder {
    private AppDatabase db = null;

    public DatabaseHolder() {

    }

    public synchronized boolean isOpen() {
        return db != null && db.isOpen();
    }

    public synchronized AppDatabase getDatabase() {
        if (!isOpen()) {
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
                .addMigrations(MIGRATION_1_2)
                .addMigrations(MIGRATION_2_3)
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



    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `new_CarConnectVO` (`vin` TEXT NOT NULL, `carId` TEXT, `masterCarId` TEXT, `carGrantType` INTEGER NOT NULL, `carName` TEXT, `result` INTEGER NOT NULL, PRIMARY KEY(`vin`))");
            database.execSQL("INSERT INTO new_CarConnectVO (vin, carId, masterCarId, carGrantType, carName, result) " + "SELECT vin, carId, masterCarId, 2, carName, result FROM CarConnectVO");
            database.execSQL("DROP TABLE CarConnectVO");
            database.execSQL("ALTER TABLE new_CarConnectVO RENAME TO CarConnectVO");
        }
    };

    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE VehicleVO ADD COLUMN evCd TEXT");
            database.execSQL("ALTER TABLE VehicleVO ADD COLUMN spCd TEXT");
        }
    };

}
