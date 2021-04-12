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
     * 예약 가능.
     */
    ABLE_BOOK("예약가능"),

    /**
     * 예약 마감.
     */
    FINISH_BOOK("예약마감"),

    /**
     * 점검중
     */
    CHECKING("");

    private String name;

    ChargePlaceStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
} // end of enum ChargePlaceStatus
