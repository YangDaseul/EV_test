package com.genesis.apps.comm.net;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class NetUIResponse<T> {
    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    public final int message;

    private NetUIResponse(@NonNull Status status, @Nullable T data, int message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> NetUIResponse<T> success(@NonNull T data) {
        return new NetUIResponse<>(Status.SUCCESS, data, 0);
    }

    public static <T> NetUIResponse<T> error(int msg, @Nullable T data) {
        return new NetUIResponse<>(Status.ERROR, data, msg);
    }

    public static <T> NetUIResponse<T> loading(@Nullable T data) {
        return new NetUIResponse<>(Status.LOADING, data, 0);
    }


    public enum Status { SUCCESS, ERROR, LOADING }

}
