package com.genesis.apps.fcm;

/**
 * @brief 푸쉬 알림 선택 시 콜백 정의
 * 푸쉬를 선택했을 때 카테고리별 페이지 이동이 필요한 함수를
 * 구현하여 사용 중
 */
public interface FcmCallback {
    void callbackCarWash();
    void callbackGPS();
    void callbackInsuranceExpire();
    void callbackETC();
}
