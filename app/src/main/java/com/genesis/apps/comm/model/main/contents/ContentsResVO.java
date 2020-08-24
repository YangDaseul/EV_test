package com.genesis.apps.comm.model.main.contents;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.hmns.playmap.PlayMapPoint;
import com.hmns.playmap.network.PlayMapRestApi;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper=false)
@AllArgsConstructor
public @Data class ContentsResVO extends BaseData {

    @Expose
    @SerializedName("imgUrl")
    private String imgUrl;
    @Expose
    @SerializedName("title")
    private String title;
    @Expose
    @SerializedName("contents")
    private String contents;
    @Expose
    @SerializedName("category")
    private String category;

}
