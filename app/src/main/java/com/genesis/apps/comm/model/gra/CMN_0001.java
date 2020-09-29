package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.NotiVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * @author hjpark
 * @file GRA_CMN_0001
 * @Brief 앱 실행시 인트로 과정에서 필요한 정보를 제공한다.
 */
public class CMN_0001 extends BaseData {
    /**
     * @author hjpark
     * @brief CMN_0001의 요청 항목
     * @see #appVer 현재앱버전
     * 앱의 현재 버전정보가 있을 경우 필수.
     * (강제 업데이트 여부 체크에 사용)
     * @see #termOSGbCd 단말os구분코드
     * 단말 OS 구분 코드
     * A: 안드로이드  I:아이폰 : E:기타
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    static class Request extends BaseRequest {
        @Expose
        @SerializedName("appVer")
        private String appVer;
        @Expose
        @SerializedName("termOSGbCd")
        private String termOSGbCd;

        public Request(String menuId, String appVer) {
            this.appVer = appVer;
            this.termOSGbCd = "A";
            setData(APIInfo.GRA_CMN_0001.getIfCd(),menuId);
        }
    }

    /**
     * @author hjpark
     * @brief CMN_0001의 응답 항목
     * @see #appVer 앱 버전
     * 앱의 최신 버전
     * @see #appUpdType 앱 업데이트구분
     * O : 선택, M : 필수(강제update), X: 그외
     * @see #notiDt 공지최근일자
     * YYYYMMDD
     * - 일반공지(NOTI)의 최근 등록일자
     * @see #notiList 공지리스트
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("appVer")
        private String appVer;
        @Expose
        @SerializedName("appUpdType")
        private String appUpdType;
        @Expose
        @SerializedName("notiDt")
        private String notiDt;
        @Expose
        @SerializedName("notiList")
        private List<NotiVO> notiList;
    }

}
