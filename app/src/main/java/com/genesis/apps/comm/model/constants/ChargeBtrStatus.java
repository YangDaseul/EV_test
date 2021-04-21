package com.genesis.apps.comm.model.constants;

import java.util.Arrays;

/**
 * 픽업앤충전 서비스 상태 코드
 * @author ljeun
 * @since 2021. 4. 16.
 */
public enum ChargeBtrStatus {
    STATUS_0000("0000", "신청가능"),
    STATUS_1000("1000", "예약완료"),
    STATUS_2000("2000", "픽업중"),
    STATUS_3000("3000", "서비스중"),
    STATUS_4000("4000", "딜리버리중"),
    STATUS_5000("5000", "이용완료"),
    STATUS_6000("6000", "예약취소");

    private String stusCd;
    private String stusNm;

    ChargeBtrStatus(String stusCd, String stusNm) {
        this.stusCd = stusCd;
        this.stusNm = stusNm;
    }

    public static ChargeBtrStatus findCode(String stusCd) {
        return Arrays.asList(ChargeBtrStatus.values()).stream().filter(data -> data.getStusCd().equalsIgnoreCase(stusCd)).findAny().orElse(STATUS_0000);
    }

    public String getStusCd() {
        return stusCd;
    }

    public void setStusCd(String stusCd) {
        this.stusCd = stusCd;
    }

    public String getStusNm() {
        return stusNm;
    }

    public void setStusNm(String stusNm) {
        this.stusNm = stusNm;
    }
}
