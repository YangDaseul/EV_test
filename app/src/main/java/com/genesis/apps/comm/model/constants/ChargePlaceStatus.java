package com.genesis.apps.comm.model.constants;

/**
 * Enum Name : ChargePlaceStatus
 * 충전소 상태 코드
 *
 * @author Ki-man Kim
 * @since 2021-04-12
 */
public enum ChargePlaceStatus {
    /**
     * 상태 불문명.
     */
    UNKNOWN("상태 불문명"),
    /**
     * 운영중.
     */
    OPEN("운영중"),
    /**
     * 운영중지.
     */
    CLOSE("운영중지"),
    /**
     * 점검중.
     */
    READY("준비중"),
    /**
     * 운영중지.
     */
    INACTIVE("운영중지"),
    /**
     * 점검중.
     */
    CHECKING("점검중"),
    /**
     * 상태 미확인.
     */
    UNINDENTIFIED("상태미확인");

    private String title;

    ChargePlaceStatus(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
} // end of enum ChargePlaceStatus
