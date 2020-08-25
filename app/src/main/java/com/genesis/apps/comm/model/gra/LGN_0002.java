package com.genesis.apps.comm.model.gra;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.vo.CarVO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @file GRA_LGN_0002
 * @Brief 차량 보유 시 서비스 로그인 및 Main 화면 정보 요청, 정비현황 요청 (정비상태/정비이력/마이버틀러/긴급출동/알림 새로운 메시지  개수)
 * @author hjpark
 */
public class LGN_0002 extends BaseData {

    /**
     * @brief LGN_0001 요청 항목
     * @author hjpark
     * @see #vin 차대번호
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Request extends BaseRequest {
        @Expose
        @SerializedName("vin")
        private String vin;

        public Request(String vin){
            this.vin = vin;
            setData(APIInfo.GRA_LGN_0002.getIfCd());
        }
    }

    /**
     * @brief LGN_0001 응답 항목
     * @author hjpark
     * @see #carVO 차량 정보
     *
     * @see #totalDrivDist 총 주행거리(KM)
     * @see #lastDrivDist 최근 주행거리(KM)
     * @see #canDrivDist 주행 가능거리(KM)
     *
     * @see #partCd 소모품 코드
     * HSW 시스템 정의 코드
     * @see #partNm 소모품 명
     * HSW 시스템 정의 코드
     * @see #nextExchgDrivDist 다음 교환시점 주행거리(KM)
     * @see #exchgDrivDist 교환시점 주행거리(KM)
     * @see #alertYn 경고 여부
     * 경고 여부, 교환 시점 경고 여부 ?
     * 경고 : Y, 정상 : N
     */
    @EqualsAndHashCode(callSuper = true)
    public @Data
    class Response extends BaseResponse {
        @Expose
        @SerializedName("carVO")
        private CarVO carVO;
        @Expose
        @SerializedName("totalDrivDist")
        private String totalDrivDist;
        @Expose
        @SerializedName("lastDrivDist")
        private String lastDrivDist;
        @Expose
        @SerializedName("canDrivDist")
        private String canDrivDist;
        @Expose
        @SerializedName("partCd")
        private String partCd;
        @Expose
        @SerializedName("partNm")
        private String partNm;
        @Expose
        @SerializedName("nextExchgDrivDist")
        private String nextExchgDrivDist;
        @Expose
        @SerializedName("exchgDrivDist")
        private String exchgDrivDist;
        @Expose
        @SerializedName("alertYn")
        private String alertYn;
    }
}
