package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.CarVO;
import com.genesis.apps.comm.model.vo.MainHistVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
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
    class Request extends BaseRequest{
        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String vin){
            this.vin = vin;
            setData(APIInfo.GRA_LGN_0003.getIfCd());
        }
    }

    /**
     * @author hjpark
     * @brief LGN_0003 응답 항목
     * @see #mainNo 접수번호
     * HSW 시스템 정의
     * @see #mainDt 정비일자
     * (형식 YYYY-MM-DD)
     * @see #mainNetNm 정비망명
     * @see #mainStatsNm 정비상태명
     * HSW 시스템 정의
     * @see #mainStatsCd 정비상태코드
     * HSW 시스템 정의
     * @see #mainHistList 정비이력정보
     * 리스트 정보 (최대 최근3개)
     * @see #butlNm 버틀러 성명
     * @see #butlSpotNm 버틀러 지점명
     * @see #butlSubsDt 버틀러 신칭일
     * (형식 YYYY-MM-DD)
     * @see #virtRecptNo 가접수번호
     * 긴급출동 신청 가점수번호
     * @see #newNotiCnt 새 알림목록 개수
     * 읽지 않은 새 알림목로 개수
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse{
        @Expose
        @SerializedName("mainNo")
        private String mainNo;
        @Expose
        @SerializedName("mainDt")
        private String mainDt;
        @Expose
        @SerializedName("mainNetNm")
        private String mainNetNm;
        @Expose
        @SerializedName("mainStatsNm")
        private String mainStatsNm;
        @Expose
        @SerializedName("mainStatsCd")
        private String mainStatsCd;

        @Expose
        @SerializedName("mainHistList")
        private List<MainHistVO> mainHistList;

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
        @SerializedName("virtRecptNo")
        private String virtRecptNo;

        @Expose
        @SerializedName("newNotiCnt")
        private String newNotiCnt;
    }
}
