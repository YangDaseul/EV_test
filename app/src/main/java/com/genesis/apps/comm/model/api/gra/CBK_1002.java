package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.ExpnVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_CBK_1002
 * @Brief insight + 차계부 조회
 */
public class CBK_1002 extends BaseData {
    /**
     * @brief CBK_1002 요청 항목
     * @see #vin 차대번호
     * @see #basYymm (조회)기준년월
     * null값인 경우 금년금월
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("basYymm")
        private String basYymm;
        @Expose
        @SerializedName("pageNo")
        private String pageNo;
        @Expose
        @SerializedName("searchCnt")
        private String searchCnt;

        public Request(String menuId, String vin, String basYymm, String pageNo, String searchCnt){
            this.vin = vin;
            this.basYymm = basYymm;
            this.pageNo = pageNo;
            this.searchCnt = searchCnt;
            setData(APIInfo.GRA_CBK_1002.getIfCd(), menuId);
        }
    }

    /**
     * @brief CBK_1002 응답 항목
     * @see #totCnt 지출내역수
     * 해당년월 지출내역 수
     * @see #delYn 삭제여부
     * Y:삭제완료/N:삭제대기/null:복원 혹은 삭제취소 상태
     * @see #refulSumAmt 주유합계금액
     * 해당월 주유 지출금액 총합
     * @see #rparSumAmt 정비합계금액
     * 해당월 정비 지출금액 총합
     * @see #carWshSumAmt 세차합계금액
     * 해당월 세차 지출금액 총합
     * @see #etcSumAmt 기타합계금액
     * 해당월 그외카케고리 지출금액 총합
     * 금월 지출총합
     * @see #expnList 지출리스트
     * 금월 지출내역 (최근순으로)
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("totCnt")
        private String totCnt;
        @Expose
        @SerializedName("delYn")
        private String delYn;
        @Expose
        @SerializedName("refulSumAmt")
        private String refulSumAmt;
        @Expose
        @SerializedName("rparSumAmt")
        private String rparSumAmt;
        @Expose
        @SerializedName("carWshSumAmt")
        private String carWshSumAmt;
        @Expose
        @SerializedName("etcSumAmt")
        private String etcSumAmt;
        @Expose
        @SerializedName("expnList")
        private List<ExpnVO> expnList;
    }
}
