package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.MembershipPointVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_MYP_2002
 * @Brief MyG+ 멤버쉽 포인트 사용내역
 */
public class MYP_2002 extends BaseData {
    /**
     * @author hjpark
     * @brief MYP_2002 요청 항목
     * @see #mbrshMbrMgmtNo 블루멤버스 회원고유번호
     * @see #transSrtDt 거래시작일자
     * YYYYMMDD
     * @see #transEndDt 거래종료일자
     * YYYYMMDD
     * @see #pageNo 페이지번호
     * @see #searchCnt 조회건수
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("mbrshMbrMgmtNo")
        private String mbrshMbrMgmtNo;
        @Expose
        @SerializedName("transSrtDt")
        private String transSrtDt;
        @Expose
        @SerializedName("transEndDt")
        private String transEndDt;
        @Expose
        @SerializedName("pageNo")
        private String pageNo;
        @Expose
        @SerializedName("searchCnt")
        private String searchCnt;
        @Expose
        @SerializedName("transTypCd")
        private String transTypCd;


        public Request(String menuId, String mbrshMbrMgmtNo, String transSrtDt, String transEndDt, String pageNo, String searchCnt, String transTypCd){
            this.mbrshMbrMgmtNo = mbrshMbrMgmtNo;
            this.transSrtDt = transSrtDt;
            this.transEndDt = transEndDt;
            this.pageNo = pageNo;
            this.searchCnt = searchCnt;
            this.transTypCd = transTypCd;
            setData(APIInfo.GRA_MYP_2002.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief MYP_2002 응답 항목
     * @see #blueMbrYn 블루멤버스 회원 여부
     * 회원 : Y, 비회원 : N
     * @see #mbrshMbrMgmtNo 블루멤버스 회원고유번호
     * @see #transTotCnt 거래전체건수
     *
     * @see #transList 거래목록
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("blueMbrYn")
        private String blueMbrYn;

        @Expose
        @SerializedName("mbrshMbrMgmtNo")
        private String mbrshMbrMgmtNo;
        @Expose
        @SerializedName("transTotCnt")
        private String transTotCnt;
        @Expose
        @SerializedName("usedBlueMbrPoint")
        private String usedBlueMbrPoint;
        @Expose
        @SerializedName("savedBlueMbrPoint")
        private String savedBlueMbrPoint;
        @Expose
        @SerializedName("transList")
        private List<MembershipPointVO> transList;
    }
}
