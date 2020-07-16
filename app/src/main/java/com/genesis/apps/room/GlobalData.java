package com.genesis.apps.room;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class GlobalData {
    public static final String GLOBAL_DATA_NEW_APP_VERSION = "newAppVersion";

    public GlobalData(@NonNull String name, String value) {
        this.name = name;
        this.value = value;
    }
    @PrimaryKey
    @NonNull
    String name;
    String value;

    @NonNull
    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
