package com.genesis.apps.comm.model.api.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.APIInfo;
import com.genesis.apps.comm.model.api.BaseRequest;
import com.genesis.apps.comm.model.api.BaseResponse;
import com.genesis.apps.comm.model.vo.carlife.BookingVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

public class CHB_1023 extends BaseData {
    /**
     * @brief CHB_1023 요청 항목
     * @see #vin        차대번호
     * @see #ordrStrtDt 주문시작일
     * @see #ordrEndDt  주문종료일
     * @see #pgSize 페이지목록수
     * @see #pgNo   조회 페이지번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;
        @Expose
        @SerializedName("ordrStrtDt")
        private String ordrStrtDt;
        @Expose
        @SerializedName("ordrEndDt")
        private String ordrEndDt;
        @Expose
        @SerializedName("pgSize")
        private int pgSize;
        @Expose
        @SerializedName("pgNo")
        private int pgNo;

        public Request(String menuId, String vin, String ordrStrtDt, String ordrEndDt, int pgSize, int pgNo){
            this.vin = vin;
            this.ordrStrtDt = ordrStrtDt;
            this.ordrEndDt = ordrEndDt;
            this.pgSize = pgSize;
            this.pgNo = pgNo;
            setData(APIInfo.GRA_CHB_1023.getIfCd(), menuId);
        }
    }

    /**
     * @brief CHB_1023 응답 항목
     * @see #totalCount 전체 목록수
     * @see #totalPage  전체 페이지수
     * @see #page   현 페이지
     * @see #pageSize   페이지 목록수
     * @see #currentSize    현 페이지 목록수
     * @see #firstYN    첫페이지 여부
     * @see #lastYN 마지막 페이지 여부
     * @see #bookingList    신청이력 목록
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("totalCount")
        private int totalCount;
        @Expose
        @SerializedName("totalPage")
        private int totalPage;
        @Expose
        @SerializedName("page")
        private int page;
        @Expose
        @SerializedName("pageSize")
        private int pageSize;
        @Expose
        @SerializedName("currentSize")
        private int currentSize;
        @Expose
        @SerializedName("firstYN")
        private String firstYN;
        @Expose
        @SerializedName("lastYN")
        private String lastYN;
        @Expose
        @SerializedName("bookingList")
        private List<BookingVO> bookingList;
    }
}
