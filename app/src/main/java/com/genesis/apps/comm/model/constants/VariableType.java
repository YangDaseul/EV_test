package com.genesis.apps.comm.model.constants;

public class VariableType {
    public static final int ACTIVITY_TRANSITION_ANIMATION_NONE=0;
    public static final int ACTIVITY_TRANSITION_ANIMATION_ZOON=1;
    public static final int ACTIVITY_TRANSITION_ANIMATION_HORIZONTAL_SLIDE=3;
    public static final int ACTIVITY_TRANSITION_ANIMATION_VERTICAL_SLIDE=4;


    public static final String NOTI_CODE_NOTI = "NOTI"; //일반공지
    public static final String NOTI_CODE_ANNC = "ANNC"; //필독공지
    public static final String NOTI_CODE_EMGR = "EMGR"; //긴급공지


    public static final String WEATHER_NAME_SKY="sky";
    public static final String WEATHER_NAME_PTY="pty";
    public static final String WEATHER_NAME_LGT="lgt";

    //OS 코드 정의
    public static final String OS_DIVICE_CODE = "A";

    //주 이용차량 상태 정의
    public static final String MAIN_VEHICLE_TYPE_0000 = "0000"; //미회원
    public static final String MAIN_VEHICLE_TYPE_CV = "CV"; //계약차량소유
    public static final String MAIN_VEHICLE_TYPE_NV = "NV"; //소유차량없음
    public static final String MAIN_VEHICLE_TYPE_OV= "OV";  //차량소유
    //주 이용차량 유무
    public static final String MAIN_VEHICLE_Y= "Y";  //주 이용 차량
    public static final String MAIN_VEHICLE_N= "N";  //주 이용 차량 X
    //차량 삭제 유무
    public static final String DELETE_EXPIRE_Y= "Y";  //삭제예정차량
    public static final String DELETE_EXPIRE_N= "N";  //정상차량

    //인사이트 메시지 유형
    public static final String MAIN_HOME_INSIGHT_TXT ="TXT"; //텍스트만 노출
    public static final String MAIN_HOME_INSIGHT_IMG ="IMG"; //이미지만 노출
    public static final String MAIN_HOME_INSIGHT_TXL ="TXL"; //텍스트+링크
    public static final String MAIN_HOME_INSIGHT_IML ="IML"; //이미지+링크
    public static final String MAIN_HOME_INSIGHT_TIL ="TIL"; //텍스트+이미지+링크
    public static final String MAIN_HOME_INSIGHT_SYS ="SYS"; //고정된 메시지 유형?


    //버틀러신청코드유형
    public static final String BTR_APPLY_CODE_1000 = "1000"; //신규신청
    public static final String BTR_APPLY_CODE_2000 = "2000"; //전담버틀러
    public static final String BTR_APPLY_CODE_3000 = "3000"; //변경신청중
}
