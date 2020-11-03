package com.genesis.apps.comm.model.gra.api;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.gra.APIInfo;
import com.genesis.apps.comm.model.gra.BaseRequest;
import com.genesis.apps.comm.model.gra.BaseResponse;
import com.genesis.apps.comm.model.vo.WashBrnVO;
import com.genesis.apps.comm.model.vo.WashGoodsVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @file GRA_WSH_1002
 * @Brief service + 소낙스 세차이용권 선택
 * @author hjpark
 */
public class WSH_1002 extends BaseData {
    public static final String GOODS_SEQ_NUM = "godsSeqNo";  //상품일련번호 intent로 전달용
    public static final String CUST_X = "custX";  //고객 현재 위치 intent로 전달용
    public static final String CUST_Y = "custY";  //고객 현재 위치 intent로 전달용
    public static final String BRANCH_INDEX = "branchIndex";  //지점 정보. 선택된 것의 index. intent로 전달용
    public static final String BRANCH_LIST = "branchList";  //지점 정보 전체. intent로 전달용

    /**
     * @brief WSH_1002의 요청 항목
     * @author hjpark
     * @see #godsSeqNo 상품일련번호
     * @see #cmpyCd 업체코드            소낙스  : SONAX
     * @see #custX 고객위치 x
     * @see #custY 고객위치 y
     * @see #addrNm 주소명             시도군 검색시
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest {

        @Expose
        @SerializedName("godsSeqNo")
        private String godsSeqNo;
        @Expose
        @SerializedName("cmpyCd")
        private String cmpyCd;

        @Expose
        @SerializedName("custX")
        private String custX;

        @Expose
        @SerializedName("custY")
        private String custY;

        @Expose
        @SerializedName("addrNm")
        private String addrNm;

        public Request(String menuId, String godsSeqNo, String cmpyCd, String custX, String custY, String addrNm){
            this.godsSeqNo = godsSeqNo;
            this.cmpyCd = cmpyCd;
            this.custX = custX;
            this.custY = custY;
            this.addrNm = addrNm;

            setData(APIInfo.GRA_WSH_1002.getIfCd(), menuId);
        }
    }

    /**
     * @brief WSH_1002의 응답 항목
     * @author hjpark
     * @see #brnhList 지점리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("brnhList")
        private List<WashBrnVO> brnhList;
    }
}
