package com.genesis.apps.comm.model.api.etc;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file AbnormalCheck
 * @Brief 긴급공지확인
 */
public class AbnormalCheck extends BaseData {

    /**
     * @author hjpark
     * @brief AbnormalCheck 요청 항목

     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseData {
        public Request() {
            
        }
    }

    /**
     * @author hjpark
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseData {
        @Expose
        @SerializedName("abnormal")
        private boolean abnormal;
        @Expose
        @SerializedName("title")
        private String title;
        @Expose
        @SerializedName("message")
        private String message;
    }
}
