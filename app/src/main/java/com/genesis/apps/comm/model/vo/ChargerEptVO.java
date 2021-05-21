package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Ki-man Kim
 * @brief service + E-PIT 충전소 상세 조회
 * @see #cpid 환경부-충전기ID
 * cpid 필드는 환경부 충전기 ID
 * @see #cpnm 충전기명
 * @see #chargeUcost 충전가격
 * @see #chargeDiv 충전기속도구분
 * SUPER 초고속 (260KW이상)
 * HIGH 고속 (260KW 미만 140KW 이상)
 * SLOW 완속
 * @see #useYn 충전기운영여부
 * Y: 운영 N:미운영
 * @see #statusCd 충전기상태코드
 * UNKNOWN 상태 불분명
 * AVAILABLE 충전가능
 * CHARGING 충전중
 * OUTOFORDER 고장/점검
 * COM_ERROR 통신장애
 * DISCONNECTION 통신미연결
 * CHARGED 충전종료
 * PLANNED 계획정지
 * RESERVED 예약
 * @see #reservYn 예약가능여부
 * 환경부-기관ID = 'ST' (s-트래픽) 인경우에 'Y'
 * 그 외는 'N'
 * - S트래픽인 아닌경우는 화면에 표시하지 않음
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ChargerEptVO extends BaseData {
    @Expose
    @SerializedName("cpid")
    private String cpid;
    @Expose
    @SerializedName("cpnm")
    private String cpnm;
    @Expose
    @SerializedName("chargeUcost")
    private String chargeUcost;
    @Expose
    @SerializedName("chargeDiv")
    private String chargeDiv;
    @Expose
    @SerializedName("useYn")
    private String useYn;
    @Expose
    @SerializedName("statusCd")
    private String statusCd;
    @Expose
    @SerializedName("reservYn")
    private String reservYn;
} // end of class ChargerEptVO
