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
 * @see #stcYn S트래픽여부
 * Y:S-트래픽충전기 N:E-PIT충전기
 * @see #chgrId 충전기ID
 * 화면표시는 01, 02로 표시…..
 * @see #chgSpeedCd 충전속도코드
 * 001: 초급속
 * 010: 급속
 * 100: 완속
 * @see #chgStusCd 충전기상태코드
 * 0: 알수 없음
 * 1: 통신이상
 * 2: 충전대기
 * 3: 충전중
 * 4: 운영중지
 * 5: 점검중
 * 6: 예약중
 * 9: 상태 미확인
 * @see #reservYn 예약가능여부
 * E-PIT 충전소인 경우 : N로 설정 (예약기능 없음)
 * - 화면에 표시하지 않음
 * S-트래픽 충전소인 경우 : Y/N 가능
 * - Y:예약가능 N:예약불가
 */
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public @Data
class ChargerEptVO extends BaseData {
    @Expose
    @SerializedName("stcYn")
    private String stcYn;
    @Expose
    @SerializedName("chgrId")
    private String chgrId;
    @Expose
    @SerializedName("chgSpeedCd")
    private String chgSpeedCd;
    @Expose
    @SerializedName("chgrStusCd")
    private String chgrStusCd;
    @Expose
    @SerializedName("reservYn")
    private String reservYn;
} // end of class ChargerEptVO
