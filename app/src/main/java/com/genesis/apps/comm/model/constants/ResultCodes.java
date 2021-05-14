package com.genesis.apps.comm.model.constants;

import java.util.Arrays;

public enum ResultCodes {
    RES_CODE_NETWORK(-1,"네트워크 통신 에러"),
    REQ_CODE_EMPTY_INTENT(-2,"intent 데이터 없음"),
    REQ_CODE_IGNORE(-3,"메시지 무시"),
    REQ_CODE_NORMAL(1,"정상"),
    REQ_CODE_ADDR_ZIP(11,"주소(우편) 정보"),
    REQ_CODE_BTR(12,"버틀러 정보"),
    REQ_CODE_ADDR_FILTER(13,"필터 정보"),
    REQ_CODE_LEASING_CAR_CANCEL(14,"렌트 리스 신청 취소"),
    REQ_CODE_LEASING_CAR_RE_APPLY(15,"렌트 리스 재신청"),
    REQ_CODE_INSIGHT_EXPN_ADD(16,"차계부 입력"),
    REQ_CODE_INSIGHT_EXPN_MODIFY(17,"차계부 수정"),
    REQ_CODE_SERVICE_SOS_MAP(18,"주소 정보 입력"),
    REQ_CODE_EXIT_ACTIVITY(19,"페이지 종료"),

    REQ_CODE_SERVICE_RESERVE_AUTOCARE(20,"오토케어 예약 완료"),
    REQ_CODE_SERVICE_RESERVE_HOMETOHOME(21,"홈투홈 예약 완료"),
    REQ_CODE_SERVICE_RESERVE_REPAIR(22,"정비 예약 완료"),
    REQ_CODE_SERVICE_RESERVE_REMOTE(23,"원격진단 예약 완료"),
    REQ_CODE_SERVICE_NETWORK_RESERVE(24,"서비스 네트워크 예약 진행"),

    REQ_CODE_OIL_INTEGRATION_SUCCESS(25,"정유소 포인트 연동 성공"),
    REQ_CODE_TS_AUTH(26,"중고차 등록 완료"),
    REQ_CODE_ADDR_ZIP_PRIVILEGE(27,"프리빌리지 주소(우편) 정보"),
    REQ_CODE_APPLY_RELAPSE(28,"하자재발신청완료"),
    REQ_CODE_APPLY_RELAPSE_EXIT(29,"하자재발신청종료"),

    REQ_CODE_CONTENTS_RELOAD(30,"컨텐츠 Reload Action"),
    REQ_CODE_SERVICE_WASH_RESERVATION_FINISH(31,"소낙스 예약 완료"),

    REQ_CODE_CHARGE_RESERVATION_FINISH(32,"충전소 예약 완료"),
    
    REQ_CODE_SERVICE_CHARGE_BTR_RESERVATION_FINISH(50,"픽업앤충전 예약 완료"),
    REQ_CODE_SERVICE_CHARGE_BTR_RESERVATION_FAIL(51,"픽업앤충전 예약 실패"),

    REQ_CODE_PAYMENT_CARD_CHANGE(60,"결제 카드 목록 변경"),
    REQ_CODE_BLUEWALNUT_PAYMENT_SUCC(61,"블루월넛 결제 요청 성공"),
    REQ_CODE_BLUEWALNUT_PAYMENT_FAIL(62,"블루월넛 결제 요청 실패"),
    REQ_CODE_BLUEWALNUT_PAYMENT_FINISH(63,"블루월넛 결제 요청 종료"),

    REQ_CODE_PAYMENT_SUCC(1001,"결제 성공"),
    REQ_CODE_PAYMENT_FAIL(1002,"결제 실패"),
    REQ_CODE_PAYMENT_CANCEL(1003,"결제 취소")
    ;

    private int code;
    private String description;

    ResultCodes(int code, String description){
        this.code = code;
        this.description = description;
    }

    public static ResultCodes findCode(int code){
        return Arrays.asList(ResultCodes.values()).stream().filter(data->data.getCode()==code).findAny().orElse(REQ_CODE_NORMAL);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
