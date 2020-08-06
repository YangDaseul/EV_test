package com.genesis.apps.comm.model.map;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @brief 경로 탐색 응답 데이터

 */
@EqualsAndHashCode(callSuper = false)
public @Data
class FindPathResVO extends BaseData {

    @Expose
    @SerializedName("PosList")
    private ArrayList<PosList> PosList;
    @Expose
    @SerializedName("RgData")
    private ArrayList<RgData> RgDataList;
    @Expose
    @SerializedName("RpOpt")
    private RpOpt RpOpt;
    @Expose
    @SerializedName("Summary")
    private Summary Summary;
    @Expose
    @SerializedName("TraInfo")
    private ArrayList<TraInfo> TraInfoList;


    public @Data
    class PosList extends BaseData {
        @Expose
        @SerializedName("X")
        private float X;
        @Expose
        @SerializedName("Y")
        private float Y;
        @Expose
        @SerializedName("index")
        private int index;
    }

    public @Data
    class RgData extends BaseData {
        @Expose
        @SerializedName("EndIdx")
        private int EndIdx;
        @Expose
        @SerializedName("PreTurn")
        private int PreTurn;
        @Expose
        @SerializedName("RgDistance")
        private int RgDistance;
        @Expose
        @SerializedName("RgTime")
        private int RgTime;
        @Expose
        @SerializedName("Speed")
        private int Speed;
        @Expose
        @SerializedName("StartIndex")
        private int StartIndex;
        @Expose
        @SerializedName("TurnCODE")
        private int TurnCODE;
        @Expose
        @SerializedName("TurnName")
        private ArrayList<TurnName> TurnName;
    }

    public @Data
    class TurnName extends BaseData {
        @Expose
        @SerializedName("Category")
        private int Category;
        @Expose
        @SerializedName("Name")
        private String Name;
    }

    public @Data
    class RpOpt extends BaseData {
        @Expose
        @SerializedName("FeeOption")
        private int FeeOption;
        @Expose
        @SerializedName("RoadOption")
        private int RoadOption;
        @Expose
        @SerializedName("RouteOption")
        private int RouteOption;
    }

    public @Data
    class Summary extends BaseData {
        @Expose
        @SerializedName("TotalDistance")
        private int TotalDistance;
        @Expose
        @SerializedName("TotalFee")
        private int TotalFee;
        @Expose
        @SerializedName("TotalTime")
        private int TotalTime;
    }

    public @Data
    class TraInfo extends BaseData {
        @Expose
        @SerializedName("EndIndex")
        private int EndIndex;
        @Expose
        @SerializedName("RoadType")
        private int RoadType;
        @Expose
        @SerializedName("Speed")
        private int Speed;
        @Expose
        @SerializedName("StartIndex")
        private int StartIndex;
        @Expose
        @SerializedName("TraLinkID")
        private int TraLinkID;
    }
}
