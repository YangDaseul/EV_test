package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.MainHistVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author hjpark
 * @file GRA_LGN_0003
 * @Brief 차량 보유 시 섭비스 로그인 및 Main 화면 정보 요청, 주행거리 요청 (차량 정보/주행 정보/소모품 정보)
 */
public class LGN_0003 extends BaseData {
    /**
     * @author hjpark
     * @brief LGN_0003 요청 항목
     * @see #vin 차대번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest{
        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String menuId, String vin){
            this.vin = vin;
            setData(APIInfo.GRA_LGN_0003.getIfCd(), menuId);
        }
    }

    /**
     * @author hjpark
     * @brief LGN_0003 응답 항목
     * @see #acptNo 접수번호
     * HSW 시스템 정의
     * @see #asnCd 정비일자
     * (형식 YYYY-MM-DD)
     * @see #asnNm 정비망명
     * @see #asnStatsNm 정비상태명
     * HSW 시스템 정의
     * @see #asnStatsCd 정비상태코드
     * HSW 시스템 정의
     * @see #asnHistList 정비이력정보
     * 리스트 정보 (최대 최근3개)
     * @see #butlNm 버틀러 성명
     * @see #butlSpotNm 버틀러 지점명
     * @see #butlSubsDt 버틀러 신칭일
     * (형식 YYYY-MM-DD)
     *
     * @see #butlSubsCd 버틀러 신청코드
     * 1000: 신규신청 : 버틀러가 없는 경우
     * 2000: 전담버틀러 : 현재의 버틀러
     * 3000: 변경신청중 : 버틀러를 변경신청했지만 지정이 안된 경우
     *
     * @see #virtRecptNo 가접수번호
     * 긴급출동 신청 가점수번호
     *
     * @see #grteeAnn 보증수리안내
     * @see #newNotiCnt 새 알림목록 개수
     * 읽지 않은 새 알림목로 개수
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("acptNo")
        private String acptNo;
        @Expose
        @SerializedName("asnCd")
        private String asnCd;
        @Expose
        @SerializedName("asnNm")
        private String asnNm;
        @Expose
        @SerializedName("asnStatsNm")
        private String asnStatsNm;
        @Expose
        @SerializedName("asnStatsCd")
        private String asnStatsCd;

        @Expose
        @SerializedName("asnHistList")
        private List<MainHistVO> asnHistList;

        @Expose
        @SerializedName("butlNm")
        private String butlNm;
        @Expose
        @SerializedName("butlSpotNm")
        private String butlSpotNm;
        @Expose
        @SerializedName("butlSubsDt")
        private String butlSubsDt;
        @Expose
        @SerializedName("butlSubsCd")
        private String butlSubsCd;

        @Expose
        @SerializedName("virtRecptNo")
        private String virtRecptNo;

        @Expose
        @SerializedName("grteeAnn")
        private String grteeAnn;

        @Expose
        @SerializedName("newNotiCnt")
        private String newNotiCnt;
    }
}
