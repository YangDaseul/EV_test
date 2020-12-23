package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @brief 토픽 정보
 * @author hjpark
 * @see #topicNm 토픽명
 */
@Entity
@AllArgsConstructor
public @Data
class TopicVO extends BaseData {

    public TopicVO(){

    }

    @PrimaryKey
    @NonNull
    @Expose
    @SerializedName("topicNm")
    private String topicNm;
}
