package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.RepairTypeVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_REQ_1004
 * @Brief service + 정비예약 - 정비내용조회(업체미선택)
 */
public class REQ_1004 extends BaseData {
    /**
     * @brief REQ_1004 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        public Request(String menuId){
            setData(APIInfo.GRA_REQ_1004.getIfCd(), menuId);
        }
    }
    /**
     * @brief REQ_1004 응답 항목
     * @see #rparTypList 정비내용리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("rparTypList")
        private List<RepairTypeVO> rparTypList;
    }
}
