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
        SUCCESS,
        FAIL,
        NOT_SUPPORTED,
        NONE
    }

    private String carId;


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
