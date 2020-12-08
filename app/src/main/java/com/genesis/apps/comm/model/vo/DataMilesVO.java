package com.genesis.apps.comm.model.vo;

import com.genesis.apps.comm.model.BaseData;
import com.genesis.apps.comm.model.api.developers.Detail;
import com.genesis.apps.comm.model.api.developers.Dtc;
import com.genesis.apps.comm.model.api.developers.Replacements;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Class Name : DataMilesVO
 * <p>
 * 데이터 마일스를 표시하기 위한 VO Class.
 *
 * @author Ki-man Kim
 * @since 2020-12-04
 */
@EqualsAndHashCode(callSuper = false)
public
@Data
class DataMilesVO extends BaseData {

    /**
     * 데이터 상태를 표시하는 Enum Class.
     */
    public enum STATUS {
        NONE,
        SUCCESS,
        FAIL
    }

    public enum UBI_STATUS {
        NONE,           // 상태값 없음.
        JOIN,           // 가입 상태
        NOT_JOIN,       // 가입 안된 상태, 가입 가능
        NOT_SUPPORTED   // 가입 안된 상태, 가입 불가
    }

    private String carId;

    /**
     * 해당 차량의 UBI 가입 상태 값.
     */
    private UBI_STATUS ubiStatus = UBI_STATUS.NONE;

    /**
     * 안전운점 점수 데이터 상태.
     */
    private STATUS detailStatus = STATUS.NONE;

    /**
     * 안전운점 점수 데이터.
     */
    private Detail.Response drivingScoreDetail;

    /**
     * 소모품 현황 데이터 상태.
     */
    private STATUS replacementsStatus = STATUS.NONE;

    /**
     * 소모품 현황 데이터.
     */
    private Replacements.Response replacements;

    /**
     * 고장 코드 데이터.
     */
    private Dtc.Response dtc;

    public DataMilesVO(String carId) {
        this.carId = carId;
    }
} // end of class DataMilesVO
