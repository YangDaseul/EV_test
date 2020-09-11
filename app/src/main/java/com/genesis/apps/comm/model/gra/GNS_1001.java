package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.VehicleVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_GNS_1001
 * @Brief My차고 + 차량목록조회
 */
public class GNS_1001 extends BaseData {
    /**
     * @brief GNS_1001 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        public Request(String menuId){
            setData(APIInfo.GRA_GNS_1001.getIfCd(), menuId);
        }
    }

    /**
     * @brief GNS_1001 응답 항목
     * @see #ownVhclCnt 소유차량수
     * @see #ownVhclList 소유차량목록
     * @see #actoprRgstYn 실운행자 등록 여부
     * Y:실운행자등록건이 있음(1건이상) N: 실운행자등록건이 없음
     * Y이면 실운행자 내역조회 메뉴  N면 실운행자 등록메뉴
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("ownVhclCnt")
        private String ownVhclCnt;
        @Expose
        @SerializedName("ownVhclList")
        private List<VehicleVO> ownVhclList;//TODO 항목명 변경 상태에 따라 수정 여지 있음
        @Expose
        @SerializedName("actoprRgstYn")
        private String actoprRgstYn;
    }
}
