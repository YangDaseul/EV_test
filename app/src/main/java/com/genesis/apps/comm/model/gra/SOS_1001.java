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
 * @file GRA_SOS_1001
 * @Brief Genesis + 긴급출동 접수현황 체크
 */
public class SOS_1001 extends BaseData {
    /**
     * @brief SOS_1001 요청 항목
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest{
        public Request(String menuId){
            setData(APIInfo.GRA_SOS_1001.getIfCd(), menuId);
        }
    }

    /**
     * @brief SOS_1001 응답 항목
     * @see #subspYn 신청여부
     * 긴급출동 신청 진행 중 여부 (Y: 진행중  N:진행중 없음)
     * @see #tmpAcptNo 갸접수번호
     * 진행 중인 경우 가접수번호 필수
     * @see #pgrsStusCd 진행상태코드
     * 가접수번호가 있을 경우 필수.
     * 진행상태 - (R:신청, -> W:접수,-> S:출동,-> E:완료, C:취소)
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("subspYn")
        private String subspYn;
        @Expose
        @SerializedName("tmpAcptNo")
        private String tmpAcptNo;
        @Expose
        @SerializedName("pgrsStusCd")
        private String pgrsStusCd;
    }
}
