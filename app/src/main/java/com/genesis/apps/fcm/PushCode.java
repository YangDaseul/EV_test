package com.genesis.apps.fcm;


import java.util.Arrays;

/**
 * @brief FCM MESSAGE CODE 정의
 * 서버가 전송하는 푸쉬 코드 정의
 * 푸쉬 코드 추가시 해당 클래스에 데이터 추가
 * 참조하는 클래스 {@link }, {@link }, {@link }에
 * //TODO 2020-07-16 PUSH 정책에 따라 변동
 */
public enum PushCode {
    CAT_00("00","차량등록알림(회원가입 전)"),
    CAT_01("01","소유자 회원가입알림 (회원 가입 전)"),
    CAT_02("02","등록명의자 회원가입알림 (회원 가입 후, 차량 등록 화인 후)"),
    CAT_03("03","APP 설치 알림 (회원 가입 후, 차량 등록 확인 후)"),
    CAT_05("05","키 공유 완료 알림"),
    CAT_06("06","키 삭제 알림 (공유 해지)"),
    CAT_07("07","키 삭제 완료 알림 (공유 해지)"),
    CAT_08("08","소유자 단말 일시정지 완료 알림"),
    CAT_09("09","소유자 단말 일시정지 해제 완료 알림"),
    CAT_0A("0A","소유자 단말 일시정지 후 말소 알림"),
    CAT_0B("0B","공유자 단말 일시정지 완료 알림"),
    CAT_0C("0C","공유자 단말 일시정지 해제 완료 알림"),
    CAT_0D("0D","미사용 카테고리"),
    CAT_0E("0E","미사용 카테고리"),
    CAT_0Z("0Z","관리자 테스트(디지털키 센터에서 고객님 핸드폰의 PUSH메시지 동작 여부를 확인하고 있습니다)"),
    CAT_21("21","소유자 단말 일시정지 해제"),
    CAT_22("22","공유자 단말 일시정지"),
    CAT_23("23","공유자 단말 일시정지 해제"),

    CAT_50("50","보험 만기 알림"),
    CAT_0X("0X","신규 서비스 알림(방문세차, 캐롯보험)"),

    CAT_04("04","키 공유 알림"),

    CAT_11("11","소유자 키 삭제 (서비스 해지)"),

    CAT_33("33","회원정보(전화번호) 변경 시 기존 회원 탈퇴 처리 (키 초기화)"),

    CAT_10("10","키 삭제 (공유 해지)"),
    CAT_12("12","공유자 키 삭제 (서비스 해지)"),
    CAT_13("13","소유자 키 삭제 (소유 상태 변경)"),
    CAT_14("14","공유자 키 삭제 (소유 상태 변경)"),

    CAT_20("20","소유자 단말 일시정지"),

    CAT_30("30","소유자 키 삭제 (키 초기화)"),
    CAT_31("31","공유자 키 삭제 (키 초기화)"),
    CAT_32("32","미사용 카테고리"),

    CAT_G1("G1","GPS"),

    CAT_40("40","방문세차(출발) 알림"),
    CAT_41("41","방문세차(도착) 알림"),
    CAT_42("42","방문세차(완료) 알림"),
    CAT_43("43","방문세차(취소) 알림"),

    CAT_0K("0K","회원가입알림(시승)"),

    CAT_DEFAULT("","알수없음");

    private String category;
    private String description;

    PushCode(String category, String description){
        this.category = category;
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static PushCode findCode(String category){
        return Arrays.asList(PushCode.values()).stream().filter(data->data.getCategory().equalsIgnoreCase(category)).findAny().orElse(CAT_DEFAULT);
    }

}
