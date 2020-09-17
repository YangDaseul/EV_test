package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.RepairHistVO;
import com.genesis.apps.comm.model.vo.RepairReserveVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_REQ_1014
 * @Brief service + 정비 이력
 */
public class REQ_1014 extends BaseData {
    /**
     * @brief REQ_1014 요청 항목
     * @see #vin 차대번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String menuId, String vin){
            this.vin = vin;
            setData(APIInfo.GRA_REQ_1014.getIfCd(), menuId);
        }
    }

    /**
     * @brief REQ_1014 응답 항목
     * @see #rsvtStatList 이력리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("rsvtStatList")
        private List<ReserveHistList> rsvtStatList;
    }


    @EqualsAndHashCode(callSuper = false)
    public @Data
    class ReserveHistList extends BaseData{
        @Expose
        @SerializedName("autoRsvtStat")
        private RepairHistVO autoRsvtStat;
        @Expose
        @SerializedName("hthRsvtStat")
        private RepairHistVO hthRsvtStat;
        @Expose
        @SerializedName("rpshRsvtStat")
        private RepairHistVO rpshRsvtStat;
    }
}
