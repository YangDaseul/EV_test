package com.genesis.apps.comm.model.constants;

public class VariableType {
    public static final boolean isHardCoding = true;
    public static final Double[] DEFAULT_POSITION={37.463936, 127.042953};
    public static final int ACTIVITY_TRANSITION_ANIMATION_NONE = 0;
    public static final int ACTIVITY_TRANSITION_ANIMATION_ZOON = 1;
    public static final int ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE = 3;
    public static final int ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE = 4;

    public static final String COMMON_MEANS_YES = "Y";
    public static final String COMMON_MEANS_NO = "N";

    public static final String KEY_NAME_TERM_VO="termVO";
    public static final String KEY_NAME_LOGIN_AUTH_UUID="authUuid";
    public static final String KEY_NAME_LOGIN_TOKEN_CODE="tokenCode";
    public static final String TERM_SERVICE_JOIN_GRA0001 = "GRA0001"; //제네시스 앱 이용 약관
    public static final String TERM_SERVICE_JOIN_GRA0002 = "GRA0002"; //제네시스 앱 개인정보 처리 방침
    public static final String TERM_SERVICE_JOIN_GRA0003 = "GRA0003"; //개인정보 수집/이용에 대한 동의
    public static final String TERM_SERVICE_JOIN_GRA0004 = "GRA0004"; //제네시스 멤버십 가입 약관
    public static final String TERM_SERVICE_JOIN_GRA0005 = "GRA0005"; //광고성 정보 수신 동의(선택)
    public static final String TERM_SERVICE_JOIN_GRA0013 = "GRA0013"; //개인정보 수집/이용(선택약관)

    public static final String TERM_SERVICE_JOIN_MYP0001 = "MYP0001"; //개인정보수집및이용
    public static final String TERM_SERVICE_JOIN_MYP0002 = "MYP0002"; //개인정보취급위탁에대한동의
    public static final String TERM_SERVICE_JOIN_MYP0003 = "MYP0003"; //제네시스사이트약관
    public static final String TERM_SERVICE_JOIN_MYP0004 = "MYP0004"; //선택적개인정보수집에대한안내

    public static final String TERM_SERVICE_JOIN_BLM0001 = "GRA0006"; //블루멤버스 회원약관
    public static final String TERM_SERVICE_JOIN_BLM0002 = "GRA0007"; //블루멤버스 개인정보 처리 방침
    public static final String TERM_SERVICE_JOIN_BLM0003 = "GRA0008"; //블루멤버스 광고성 정보 수신에 대한 안내


    public static final String TERM_OIL_JOIN_SOIL0003 = "SOIL0003"; //광고성 정보 수신 동의(선택)
    public static final String TERM_OIL_JOIN_HDOL0005 = "HDOL0005"; //광고성 정보 수신 동의(선택)
    public static final String TERM_OIL_JOIN_GSCT0005 = "GSCT0005"; //광고성 정보 수신 동의(선택)


    public static final String NOTI_CODE_NOTI = "NOTI"; //일반공지
    public static final String NOTI_CODE_ANNC = "ANNC"; //필독공지
    public static final String NOTI_CODE_EMGR = "EMGR"; //긴급공지


    public static final String WEATHER_NAME_SKY = "sky";
    public static final String WEATHER_NAME_PTY = "pty";
    public static final String WEATHER_NAME_LGT = "lgt";

    public static final int HOME_TIME_DAY = 1;
    public static final int HOME_TIME_NIGHT = 2;

    //OS 코드 정의
    public static final String OS_DIVICE_CODE = "A";

    //주 이용차량 상태 정의
    public static final String MAIN_VEHICLE_TYPE_0000 = "0000"; //미회원
    public static final String MAIN_VEHICLE_TYPE_CV = "CV"; //계약차량소유
    public static final String MAIN_VEHICLE_TYPE_NV = "NV"; //소유차량없음
    public static final String MAIN_VEHICLE_TYPE_OV = "OV";  //차량소유
    //퀵메뉴
    public static final int QUICK_MENU_CODE_OV=4;
    public static final int QUICK_MENU_CODE_CV=3;
    public static final int QUICK_MENU_CODE_NV=2;
    public static final int QUICK_MENU_CODE_0000=1;
    public static final int QUICK_MENU_CODE_NONE=0;
    public static int getQuickMenuCode(String custGbCd){
        switch (custGbCd){
            case MAIN_VEHICLE_TYPE_OV:
                return QUICK_MENU_CODE_OV;
            case MAIN_VEHICLE_TYPE_CV:
                return QUICK_MENU_CODE_CV;
            case MAIN_VEHICLE_TYPE_NV:
                return QUICK_MENU_CODE_NV;
            case MAIN_VEHICLE_TYPE_0000:
                return QUICK_MENU_CODE_0000;
            default:
                return QUICK_MENU_CODE_NONE;
        }
    }
    public static final int QUICK_MENU_CATEGORY_HOME=0;
    public static final int QUICK_MENU_CATEGORY_INSIGHT=1;
    public static final int QUICK_MENU_CATEGORY_SERVICE=2;
    public static final int QUICK_MENU_CATEGORY_MYG=3;
    public static final int QUICK_MENU_CATEGORY_NONE=10;

    public static final String VEHICLE_CODE_GN="GN"; //내연기관
    public static final String VEHICLE_CODE_EV="EV"; //전기차


    //주 이용차량 유무
    public static final String MAIN_VEHICLE_Y = "Y";  //주 이용 차량
    public static final String MAIN_VEHICLE_N = "N";  //주 이용 차량 X
    //차량 삭제 유무
    public static final String DELETE_EXPIRE_Y = "Y";  //삭제예정차량
    public static final String DELETE_EXPIRE_N = "N";  //정상차량

    //인사이트 메시지 유형
    public static final String MAIN_HOME_INSIGHT_TXT = "TXT"; //텍스트만 노출
    public static final String MAIN_HOME_INSIGHT_IMG = "IMG"; //이미지만 노출
    public static final String MAIN_HOME_INSIGHT_TXL = "TXL"; //텍스트+링크
    public static final String MAIN_HOME_INSIGHT_IML = "IML"; //이미지+링크
    public static final String MAIN_HOME_INSIGHT_TIL = "TIL"; //텍스트+이미지+링크
    public static final String MAIN_HOME_INSIGHT_SYS = "SYS"; //고정된 메시지 유형?


    public static final String INSIGHT_EXPN_DIV_CODE_1000 = "주유"; //주유
    public static final String INSIGHT_EXPN_DIV_CODE_2000 = "정비"; //정비
    public static final String INSIGHT_EXPN_DIV_CODE_3000 = "세차"; //세차
    public static final String INSIGHT_EXPN_DIV_CODE_4000 = "주차"; //주차
    public static final String INSIGHT_EXPN_DIV_CODE_5000 = "통행"; //통행
    public static final String INSIGHT_EXPN_DIV_CODE_6000 = "보험"; //보험
    public static final String INSIGHT_EXPN_DIV_CODE_7000 = "세금"; //세금
    public static final String INSIGHT_EXPN_DIV_CODE_8000 = "용품"; //용품
    public static final String INSIGHT_EXPN_DIV_CODE_9000 = "기타"; //기타

    public static String getExpnDivCd(String result) {
        switch (result) {
            case INSIGHT_EXPN_DIV_CODE_1000:
                return "1000";
            case INSIGHT_EXPN_DIV_CODE_2000:
                return "2000";
            case INSIGHT_EXPN_DIV_CODE_3000:
                return "3000";
            case INSIGHT_EXPN_DIV_CODE_4000:
                return "4000";
            case INSIGHT_EXPN_DIV_CODE_5000:
                return "5000";
            case INSIGHT_EXPN_DIV_CODE_6000:
                return "6000";
            case INSIGHT_EXPN_DIV_CODE_7000:
                return "7000";
            case INSIGHT_EXPN_DIV_CODE_8000:
                return "8000";
            case INSIGHT_EXPN_DIV_CODE_9000:
            default:
                return "9000";
        }
    }


    //버틀러신청코드유형
    public static final String BTR_APPLY_CODE_1000 = "1000"; //신규신청
    public static final String BTR_APPLY_CODE_2000 = "2000"; //전담버틀러
    public static final String BTR_APPLY_CODE_3000 = "3000"; //변경신청중
    public static final String BTR_CHANGE_REQUEST_YES = "Y"; //변경신청중
    public static final String BTR_CNSL_BADGE_YES = "Y"; //상담뱃지여부


    //MY차고 차량 삭제 사유 유형
    public static final String MY_CAR_DELETION_REASON_SELL = "1";
    public static final String MY_CAR_DELETION_REASON_SCRAPPED = "2";
    public static final String MY_CAR_DELETION_REASON_TRANSFER = "4";
    public static final String MY_CAR_DELETION_REASON_ETC = "7";


    //모빌리티케어 쿠폰 정보
    public static final String COUPON_CODE_ENGINE = "11";//엔진오일세트
    public static final String COUPON_CODE_FILTER = "13";//에어컨필터
    public static final String COUPON_CODE_BREAK_PAD = "32";//브레이크패드
    public static final String COUPON_CODE_WIPER = "33";//와이퍼블레이드
    public static final String COUPON_CODE_BREAK_OIL = "34";//브레이크오일
    public static final String COUPON_CODE_PICKUP_DELIVERY = "61";//픽업앤딜리버리
    public static final String COUPON_CODE_SONAKS = "99";//프리미엄 소낙스 세차 이용권

    //버틀러 상담유형 코드
    public static final String BTR_CNSL_CODE_CNSL = "CNSL";//상담구분코드
    public static final String BTR_CNSL_CODE_LARGE = "LGCT";//대분류코드
    public static final String BTR_CNSL_CODE_MEDIUM = "MDCT";//중분류코드
    public static final String BTR_CNSL_CODE_SMALL = "SMCT"; //소분류코드

    public static final String BTR_REQUEST_CALL = "[전화응답요청]";
    public static final String BTR_REQUEST_APP = "[APP응답요청]";


    //렌트 리스
    public static final String LEASING_CAR_CSMR_SCN_CD_1 = "1"; //법인
    public static final String LEASING_CAR_CSMR_SCN_CD_14 = "14"; //개인

    public static final String LEASING_CAR_PERIOD_12 = "12"; //12개월
    public static final String LEASING_CAR_PERIOD_24 = "24"; //24개월
    public static final String LEASING_CAR_PERIOD_36 = "36"; //36개월
    public static final String LEASING_CAR_PERIOD_48 = "48"; //48개월
    public static final String LEASING_CAR_PERIOD_ETC = "99"; //99개월

    public static final String LEASING_CAR_APRV_STATUS_CODE_AGREE = "Y"; //승인
    public static final String LEASING_CAR_APRV_STATUS_CODE_REJECT = "N"; //반려
    public static final String LEASING_CAR_APRV_STATUS_CODE_WAIT = "W"; //승인요청대기


    //블루핸즈 필터 코드
    public static final String BTR_FILTER_CODE_A = "A";//제네시스 전담
    public static final String BTR_FILTER_CODE_C = "C";//종합
    public static final String BTR_FILTER_CODE_D = "D";//전문
    public static final String BTR_FILTER_CODE_P = "P";//일반

    //서비스 긴급출동 고장구분코드
    public static final String SERVICE_SOS_FIT_CODE_010101 = "차량 시동이 걸리지 않아요."; //시동불가
    public static final String SERVICE_SOS_FIT_CODE_020110 = "경고등 점등이 이상해요."; //경고등 점등
    public static final String SERVICE_SOS_FIT_CODE_020104 = "오일이 뚝뚝 떨어져요.(누유 현상)"; //오일 누유
    public static final String SERVICE_SOS_FIT_CODE_020108 = "창문에 문제가 생겼어요."; //윈도우 작동 불량
    public static final String SERVICE_SOS_FIT_CODE_040101 = "견인이 필요합니다."; //견인
    public static final String SERVICE_SOS_FIT_CODE_990100 = "기타"; //기타

    public static String getFltCd(String result) {
        switch (result) {
            case SERVICE_SOS_FIT_CODE_010101:
                return "010101";
            case SERVICE_SOS_FIT_CODE_020110:
                return "020110";
            case SERVICE_SOS_FIT_CODE_020104:
                return "020104";
            case SERVICE_SOS_FIT_CODE_020108:
                return "020108";
            case SERVICE_SOS_FIT_CODE_040101:
                return "040101";
            case SERVICE_SOS_FIT_CODE_990100:
            default:
                return "990100";
        }
    }

    //서비스 긴급출동 도로구분코드
    public static final String SERVICE_SOS_AREA_CLS_CODE_R = "일반 도로"; //일반도로
    public static final String SERVICE_SOS_AREA_CLS_CODE_H = "자동차 전용 고속 도로"; //자동차전용도로

    public static String getAreaClsCd(String result) {
        switch (result) {
            case SERVICE_SOS_AREA_CLS_CODE_R:
                return "R";
            case SERVICE_SOS_AREA_CLS_CODE_H:
            default:
                return "H";
        }
    }

    public static final String SERVICE_SOS_STATUS_CODE_R = "R"; //신청
    public static final String SERVICE_SOS_STATUS_CODE_W = "W"; //접수
    public static final String SERVICE_SOS_STATUS_CODE_S = "S"; //출동
    public static final String SERVICE_SOS_STATUS_CODE_E = "E"; //완료
    public static final String SERVICE_SOS_STATUS_CODE_C = "C"; //취소


    /**
     * 원격 진단 안내 여부 결과 코드.
     */
    public static final String SERVICE_REMOTE_RES_CODE_9000 = "9000"; // 시스템 오류.
    public static final String SERVICE_REMOTE_RES_CODE_9019 = "9019"; // 전문필수 항목 누락.
    public static final String SERVICE_REMOTE_RES_CODE_9020 = "9020"; // 전문필수 항목 무결성 오류.

    /**
     * 원격 진단 가능 여부 결과 코드.
     */
    public static final String SERVICE_REMOTE_RES_CODE_2401 = "2401"; // 원격 신청이 되어 있거나 진단 진행 중인경우.
    public static final String SERVICE_REMOTE_RES_CODE_2402 = "2402"; // 원격 진단 이용 대상 차량이 아닌 경우.
    public static final String SERVICE_REMOTE_RES_CODE_2403 = "2403"; // 원격 진단 이용 가능 시간이 아님.
    public static final String SERVICE_REMOTE_RES_CODE_2404 = "2404"; // GCS 서비스 가입 회원이 아닌 경우.
    public static final String SERVICE_REMOTE_RES_CODE_2405 = "2405"; // 긴급 출동이 접수 또는 진행중인 경우.

    //서비스 카케어 쿠폰 리스트 (REQ-1005 응답)
    public static final String SERVICE_CAR_CARE_COUPON_CODE_ENGINE = "11"; //엔진오일
    public static final String SERVICE_CAR_CARE_COUPON_CODE_AC_FILTER = "13"; //에어컨필터
    public static final String SERVICE_CAR_CARE_COUPON_CODE_BREAK_PAD = "32";
    public static final String SERVICE_CAR_CARE_COUPON_CODE_WIPER = "33"; //와이퍼 블레이드
    public static final String SERVICE_CAR_CARE_COUPON_CODE_BREAK_OIL = "34";
    public static final String SERVICE_CAR_CARE_COUPON_CODE_NAVIGATION = "42";
    public static final String SERVICE_CAR_CARE_COUPON_CODE_PICKUP_DELIVERY = "61";
    public static final String SERVICE_CAR_CARE_COUPON_CODE_WASH_SONAKS = "99";

    //서비스 정비 내용
    public static final String SERVICE_REPAIR_CODE_AC = "AC"; //냉난방부품
    public static final String SERVICE_REPAIR_CODE_BA = "BA"; //일반부품
    public static final String SERVICE_REPAIR_CODE_CS = "CS"; //소모성부품
    public static final String SERVICE_REPAIR_CODE_EG = "EG"; //엔진주요부품
    public static final String SERVICE_REPAIR_CODE_GT = "GT"; //기타
    public static final String SERVICE_REPAIR_CODE_PT = "PT"; //동력전달주요부품

    //서비스 예약유형 코드
    public static final String SERVICE_RESERVATION_TYPE_AUTOCARE = "AUTO"; //오토케어
    public static final String SERVICE_RESERVATION_TYPE_AIRPORT = "ARPT"; //에어포트
    public static final String SERVICE_RESERVATION_TYPE_HOMETOHOME = "HTOH"; //홈투홈
    public static final String SERVICE_RESERVATION_TYPE_RPSH = "RPSH"; //정비소

    public static String getRsvtTypNm(String rsvtTypCd) {
        switch (rsvtTypCd) {
            case SERVICE_RESERVATION_TYPE_AUTOCARE:
                return "오토케어";
            case SERVICE_RESERVATION_TYPE_AIRPORT:
                return "에어포트";
            case SERVICE_RESERVATION_TYPE_HOMETOHOME:
                return "홈투홈";
            case SERVICE_RESERVATION_TYPE_RPSH:
            default:
                return "서비스 네트워크";
        }
    }


    //서비스 홈투홈 픽업구분코드
    public static final String SERVICE_HOMETOHOME_PCKP_DIV_NM_PICKUP = "픽업 서비스"; //픽업
    public static final String SERVICE_HOMETOHOME_PCKP_DIV_NM_PICKUP_DELIVERY = "픽업 + 딜리버리 서비스"; //픽업+딜리버리
    public static final String SERVICE_HOMETOHOME_PCKP_DIV_CD_PICKUP = "1"; //픽업
    public static final String SERVICE_HOMETOHOME_PCKP_DIV_CD_PICKUP_DELIVERY = "3"; //픽업+딜리버리


    public static String getPckpDivCd(String result) {
        switch (result) {
            case SERVICE_HOMETOHOME_PCKP_DIV_NM_PICKUP:
                return "1";
            case SERVICE_HOMETOHOME_PCKP_DIV_NM_PICKUP_DELIVERY:
            default:
                return "3";
        }
    }

    public static final String SERVICE_REPAIR_CANCEL_CODE_NM_1 = "예약 변경";
    public static final String SERVICE_REPAIR_CANCEL_CODE_NM_2 = "타 업체 입고";
    public static final String SERVICE_REPAIR_CANCEL_CODE_NM_3 = "입고 불가";
    public static final String SERVICE_REPAIR_CANCEL_CODE_NM_4 = "대기 과다";
    public static final String SERVICE_REPAIR_CANCEL_CODE_NM_5 = "기타";
    public static final String SERVICE_REPAIR_CANCEL_CODE_CD_1 = "1";
    public static final String SERVICE_REPAIR_CANCEL_CODE_CD_2 = "2";
    public static final String SERVICE_REPAIR_CANCEL_CODE_CD_3 = "3";
    public static final String SERVICE_REPAIR_CANCEL_CODE_CD_4 = "4";
    public static final String SERVICE_REPAIR_CANCEL_CODE_CD_5 = "5";

    public static String getRsvtCnclCd(String result) {
        String rsvtCnclCd = "";

        switch (result) {
            case SERVICE_REPAIR_CANCEL_CODE_NM_1:
                rsvtCnclCd = SERVICE_REPAIR_CANCEL_CODE_CD_1;
                break;
            case SERVICE_REPAIR_CANCEL_CODE_NM_2:
                rsvtCnclCd = SERVICE_REPAIR_CANCEL_CODE_CD_2;
                break;
            case SERVICE_REPAIR_CANCEL_CODE_NM_3:
                rsvtCnclCd = SERVICE_REPAIR_CANCEL_CODE_CD_3;
                break;
            case SERVICE_REPAIR_CANCEL_CODE_NM_4:
                rsvtCnclCd = SERVICE_REPAIR_CANCEL_CODE_CD_4;
                break;
            case SERVICE_REPAIR_CANCEL_CODE_NM_5:
            default:
                rsvtCnclCd = SERVICE_REPAIR_CANCEL_CODE_CD_5;
                break;
        }
        return rsvtCnclCd;
    }

    public static final String SERVICE_REMOTE_CHECK_ITEM_NM_BATTERY="01";
    public static final String SERVICE_REMOTE_CHECK_ITEM_NM_ENGINE="02";
    public static final String SERVICE_REMOTE_CHECK_ITEM_NM_BREAK="03";
    public static final String SERVICE_REMOTE_CHECK_ITEM_NM_MISSION="04";
    public static final String SERVICE_REMOTE_CHECK_ITEM_NM_TPMS="05";
}
