package com.genesis.apps.comm.model.api;

import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.MenuVO;
import com.genesis.apps.ui.common.activity.GAWebActivity;
import com.genesis.apps.ui.main.AlarmCenterActivity;
import com.genesis.apps.ui.main.BarcodeActivity;
import com.genesis.apps.ui.main.ServiceNetworkActivity;
import com.genesis.apps.ui.main.ServiceTermDetailActivity;
import com.genesis.apps.ui.main.SimilarCarActivity;
import com.genesis.apps.ui.main.SimilarCarContractHistoryActivity;
import com.genesis.apps.ui.main.home.BtrBluehandsActivity;
import com.genesis.apps.ui.main.home.BtrConsultTypeActivity;
import com.genesis.apps.ui.main.home.LeasingCarVinRegisterActivity;
import com.genesis.apps.ui.main.home.MyCarActivity;
import com.genesis.apps.ui.main.home.MyLocationActivity;
import com.genesis.apps.ui.main.home.RegisterUsedCarActivity;
import com.genesis.apps.ui.main.home.WarrantyRepairGuideActivity;
import com.genesis.apps.ui.main.insight.InsightExpnInputActivity;
import com.genesis.apps.ui.main.insight.InsightExpnMainActivity;
import com.genesis.apps.ui.main.service.ServiceDriveReqActivity;
import com.genesis.apps.ui.main.service.ServiceRelapseHistoryActivity;
import com.genesis.apps.ui.main.service.ServiceRepairReserveHistoryActivity;
import com.genesis.apps.ui.main.service.ServiceReviewActivity;
import com.genesis.apps.ui.main.service.ServiceSOSApplyActivity;
import com.genesis.apps.ui.myg.MyGGAActivity;
import com.genesis.apps.ui.myg.MyGMembershipActivity;
import com.genesis.apps.ui.myg.MyGMembershipUseCaseActivity;
import com.genesis.apps.ui.myg.MyGMembershipUseListActivity;
import com.genesis.apps.ui.myg.MyGNotiActivity;
import com.genesis.apps.ui.myg.MyGTermsActivity;
import com.genesis.apps.ui.myg.MyGVersioniActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum APPIAInfo {
    DEFAULT("default", null, VariableType.QUICK_MENU_CODE_NONE, "기본"),
    INT01("INT01", null, VariableType.QUICK_MENU_CODE_NONE, "스플래시"),
    INT02("INT02", null, VariableType.QUICK_MENU_CODE_NONE, "접근 권한 알림"), //TODO 로그인/회원가입과 IA 아이디가 같음..
    INT02_P01("INT02_P01", null, VariableType.QUICK_MENU_CODE_NONE, "접근권한 설정 팝업"),
    INT03("INT03", null, VariableType.QUICK_MENU_CODE_NONE, "서비스 가입"),
    INT03_01("INT03_01", null, VariableType.QUICK_MENU_CODE_NONE, "제네시스 앱 이용약관"),
    INT03_P01("INT03_P01", null, VariableType.QUICK_MENU_CODE_NONE, "서비스 가입 실패 팝업"),
    INT04("INT04", null, VariableType.QUICK_MENU_CODE_NONE, "블루멤버스 가입"),
    POP01("POP01", null, VariableType.QUICK_MENU_CODE_NONE, "업데이트 팝업"),
    POP02("POP02", null, VariableType.QUICK_MENU_CODE_NONE, "네트워크 불안정 팝업"),
    ALRM01("ALRM01", AlarmCenterActivity.class, VariableType.QUICK_MENU_CODE_NV, "알림 센터"),
    ALRM01_01("ALRM01_01", null, VariableType.QUICK_MENU_CODE_NONE, "알림 상세"),
    ALRM01_SRCH01("ALRM01_SRCH01", null, VariableType.QUICK_MENU_CODE_NONE, "알림 검색"),
    Bcode01("Bcode01", BarcodeActivity.class, VariableType.QUICK_MENU_CODE_NV, "바코드"),
    GM01("GM01", null, VariableType.QUICK_MENU_CODE_NONE, "메인 1 Home (로그인/차량보유)"),
    GM02("GM02", null, VariableType.QUICK_MENU_CODE_NONE, "메인 1 Home (로그인/예약대기)"),
    GM03("GM03", null, VariableType.QUICK_MENU_CODE_NONE, "메인 1 Home (로그인/차량미보유)"),
    GM04("GM04", null, VariableType.QUICK_MENU_CODE_NONE, "메인 1 Home (비로그인)"),
    GM01_01("GM01_01", MyLocationActivity.class, VariableType.QUICK_MENU_CODE_OV, "주차 위치 확인"),
    GM01_02("GM01_02", WarrantyRepairGuideActivity.class, VariableType.QUICK_MENU_CODE_OV, "보증 수리 안내"),
    GM01_03("GM01_03", null, VariableType.QUICK_MENU_CODE_NONE, "공유 미리보기"),
    GM_BTO1("GM_BTO1", GAWebActivity.class, VariableType.QUICK_MENU_CODE_0000, "BTO 차량 목록"),
    GM_BTO2("GM_BTO2", null, VariableType.QUICK_MENU_CODE_NONE, "BTO"),
    GM02_CTR01("GM02_CTR01", SimilarCarContractHistoryActivity.class, VariableType.QUICK_MENU_CODE_NONE, "구매 계약 내역"),
    GM02_CTR01_P01("GM02_CTR01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "차량 가격 정보 상세 팝업"),
    GM02_INV01("GM02_INV01", SimilarCarActivity.class, VariableType.QUICK_MENU_CODE_NONE, "유사 재고 조회 / 예약"),
    GM02_BF01("GM02_BF01", null, VariableType.QUICK_MENU_CODE_NONE, "대기고객 혜택"),

    GM_BT01_P01("GM_BT01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "버틀러 서비스 안내 팝업"),
    GM_BT01_P02("GM_BT01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "버틀러 신청 안내 팝업"),
    GM_BT02("GM_BT02", BtrBluehandsActivity.class, VariableType.QUICK_MENU_CODE_OV, "전담 블루핸즈/버틀러"),
    GM_BT03("GM_BT03", null, VariableType.QUICK_MENU_CODE_NONE, "블루핸즈 위치 상세"),
    GM_BT04("GM_BT04", BtrConsultTypeActivity.class, VariableType.QUICK_MENU_CODE_OV, "버틀러 1:1 문의"),
    GM_BT04_P01("GM_BT04_P01", null, VariableType.QUICK_MENU_CODE_NONE, "버틀러 문의 종료 팝업"),
    GM_BT05("GM_BT05", null, VariableType.QUICK_MENU_CODE_NONE, "상담 이력"),
    GM_BT06_P01("GM_BT06_P01", null, VariableType.QUICK_MENU_CODE_NONE, "GPS 설정 안내 팝업"),
    GM_BT06("GM_BT06", null, VariableType.QUICK_MENU_CODE_NONE, "버틀러 변경"),
    GM_BT06_01("GM_BT06_01", null, VariableType.QUICK_MENU_CODE_NONE, "블루핸즈 필터"),
    GM_BT06_02("GM_BT06_02", null, VariableType.QUICK_MENU_CODE_NONE, "블루핸즈 목록 보기"),

    GM_CARLST01("GM_CARLST01", MyCarActivity.class, VariableType.QUICK_MENU_CODE_CV, "My 차고"),
    GM_CARLST_P01("GM_CARLST_P01", null, VariableType.QUICK_MENU_CODE_NONE, "차량 번호 수정 팝업"),
    GM_CARLST_P02("GM_CARLST_P02", null, VariableType.QUICK_MENU_CODE_NONE, "차량 삭제 팝업"),
    GM_CARLST_01("GM_CARLST_01", LeasingCarVinRegisterActivity.class, VariableType.QUICK_MENU_CODE_NV, "렌트/리스 실 운행자 등록 확인"),
    GM_CARLST_01_01("GM_CARLST_01_01", null, VariableType.QUICK_MENU_CODE_NONE, "등록 신청"),
    GM_CARLST_01_B01("GM_CARLST_01_B01", null, VariableType.QUICK_MENU_CODE_NONE, "전담블루핸즈/버틀러 선택 팝업"),
    GM_CARLST_01_B02("GM_CARLST_01_B02", null, VariableType.QUICK_MENU_CODE_NONE, "블루핸즈 필터"),
    GM_CARLST_01_B03("GM_CARLST_01_B03", null, VariableType.QUICK_MENU_CODE_NONE, "블루핸즈 목록 보기"),
    GM_CARLST_01_A01("GM_CARLST_01_A01", null, VariableType.QUICK_MENU_CODE_NONE, "수령지 주소 검색"),
    GM_CARLST_01_result("GM_CARLST_01_result", null, VariableType.QUICK_MENU_CODE_NONE, "상세 페이지"),
    GM_CARLST_01_P01("GM_CARLST_01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "유의사항"),
    GM_CARLST_01_P02("GM_CARLST_01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "계약서 종류"),
    GM_CARLST_01_P03("GM_CARLST_01_P03", null, VariableType.QUICK_MENU_CODE_NONE, "대여기간"),
    GM_CARLST_01_P04("GM_CARLST_01_P04", null, VariableType.QUICK_MENU_CODE_NONE, "신청 초기화 팝업"),
    GM_CARLST_01_P05("GM_CARLST_01_P05", null, VariableType.QUICK_MENU_CODE_NONE, "신청 취소 팝업"),

    GM_CARLST_02("GM_CARLST_02", null, VariableType.QUICK_MENU_CODE_NONE, "렌트/리스 실 운행자 등록 내역"),
    GM_CARLST_03("GM_CARLST_03", RegisterUsedCarActivity.class, VariableType.QUICK_MENU_CODE_NV, "중고차 등록"),
    GM_CARLST_03_P01("GM_CARLST_03_P01", null, VariableType.QUICK_MENU_CODE_NONE, "중고차 안내사항"),
    GM_CARLST_04("GM_CARLST_04", null, VariableType.QUICK_MENU_CODE_NONE, "차량 상세"),

    TM01("TM01", null, VariableType.QUICK_MENU_CODE_NONE, "메인 2 Insight (로그인/차량보유)"),
    TM02("TM02", null, VariableType.QUICK_MENU_CODE_NONE, "메인 2 Insight (로그인/예약대기)"),
    TM03("TM03", null, VariableType.QUICK_MENU_CODE_NONE, "메인 2 Insight (로그인/차량미보유)"),
    TM04("TM04", null, VariableType.QUICK_MENU_CODE_NONE, "메인 2 Insight (비로그인)"),

    TM_INS01("TM_INS01", null, VariableType.QUICK_MENU_CODE_NONE, "차량 인사이트 상세"),
    TM_EXPS01("TM_EXPS01", InsightExpnMainActivity.class, VariableType.QUICK_MENU_CODE_OV, "차계부"),
    TM_EXPS01_P01("TM_EXPS01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "삭제 팝업"),
    TM_EXPS01_01("TM_EXPS01_01", InsightExpnInputActivity.class, VariableType.QUICK_MENU_CODE_OV, "지출내역 입력"),
    TM_EXPS01_P02("TM_EXPS01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "입력 취소 팝업"),
    TM_EXPS01_02("TM_EXPS01_02", null, VariableType.QUICK_MENU_CODE_NONE, "지출 내역 수정"),
    TM_EXPS01_02_P01("TM_EXPS01_02_P01", null, VariableType.QUICK_MENU_CODE_NONE, "지출대상 차량 수정 팝업"),

    TM_EXPS01_P03("TM_EXPS01_P03", null, VariableType.QUICK_MENU_CODE_NONE, "수정 취소 팝업"),
    SM01("SM01", null, VariableType.QUICK_MENU_CODE_NONE, "메인 3 Service"),
    SM02("SM02", null, VariableType.QUICK_MENU_CODE_NONE, "상품 전체 보기"),
    SM03("SM03", null, VariableType.QUICK_MENU_CODE_NONE, "상품 상세"),
    SM_SNFIND01("SM_SNFIND01", ServiceNetworkActivity.class, VariableType.QUICK_MENU_CODE_0000, "서비스 네트워크 찾기"),
    SM_SNFIND01_P01("SM_SNFIND01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "정비 예약하기"),
    SM_SNFIND02("SM_SNFIND02", null, VariableType.QUICK_MENU_CODE_NONE, "서비스 네트워크 필터"),
    SM_SNFIND03("SM_SNFIND03", null, VariableType.QUICK_MENU_CODE_NONE, "서비스 네트워크 목록"),
    SM_R_RSV01("SM_R_RSV01", null, VariableType.QUICK_MENU_CODE_NONE, "1단계 정비 항목/ 서비스 항목"),
    SM_R_RSV01_P01("SM_R_RSV01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "정비내용 선택 팝업"),
    SM_R_RSV02_01("SM_R_RSV02_01", null, VariableType.QUICK_MENU_CODE_NONE, "오토케어"),
    SM_R_RSV02_01_P01("SM_R_RSV02_01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "오토케어 예약 서비스 선택 팝업"),
    SM_R_RSV02_01_A01("SM_R_RSV02_01_A01", null, VariableType.QUICK_MENU_CODE_NONE, "오토케어 주소 검색"),
    SM_R_RSV02_01_A02("SM_R_RSV02_01_A02", null, VariableType.QUICK_MENU_CODE_NONE, "오토케어 지도 주소 입력 지도"),
    SM_R_RSV02_01_P02("SM_R_RSV02_01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "오토케어 예약 희망일 선택 팝업"),
    SM_R_RSV02_02("SM_R_RSV02_02", null, VariableType.QUICK_MENU_CODE_NONE, "에어포트"),
    SM_R_RSV02_03("SM_R_RSV02_03", null, VariableType.QUICK_MENU_CODE_NONE, "홈투홈"),
    SM_R_RSV02_03_P01("SM_R_RSV02_03_P01", null, VariableType.QUICK_MENU_CODE_NONE, "홈투홈 서비스 선택 팝업"),
    SM_R_RSV02_03_A01("SM_R_RSV02_03_A01", null, VariableType.QUICK_MENU_CODE_NONE, "홈투홈 픽업 주소 검색"),
    SM_R_RSV02_03_A02("SM_R_RSV02_03_A02", null, VariableType.QUICK_MENU_CODE_NONE, "홈투홈 픽업 주소 입력 지도"),
    SM_R_RSV02_03_A03("SM_R_RSV02_03_A03", null, VariableType.QUICK_MENU_CODE_NONE, "홈투홈 딜리버리 주소 검색"),
    SM_R_RSV02_03_P02("SM_R_RSV02_03_P02", null, VariableType.QUICK_MENU_CODE_NONE, "홈투홈 예약 희망일 선택 팝업"),
    SM_R_RSV02_03_A04("SM_R_RSV02_03_A04", null, VariableType.QUICK_MENU_CODE_NONE, "홈투홈 딜리버리 주소 입력 지도"),
    SM_R_RSV_P01("SM_R_RSV_P01", null, VariableType.QUICK_MENU_CODE_NONE, "모바일 정비 불가 팝업"),
    SM_R_RSV02_04("SM_R_RSV02_04", null, VariableType.QUICK_MENU_CODE_NONE, "정비소 예약"),
    SM_R_RSV02_04_A01("SM_R_RSV02_04_A01", null, VariableType.QUICK_MENU_CODE_NONE, "정비소 설정(지도)"),
    SM_R_RSV02_04_A02("SM_R_RSV02_04_A02", null, VariableType.QUICK_MENU_CODE_NONE, "정비소 위치/필터"),
    SM_R_RSV02_04_P01("SM_R_RSV02_04_P01", null, VariableType.QUICK_MENU_CODE_NONE, "정비 예약일 선택 팝업"),
    SM_R_RSV02_04_A03("SM_R_RSV02_04_A03", null, VariableType.QUICK_MENU_CODE_NONE, "정비소 목록"),
    SM_R_RSV_P02("SM_R_RSV_P02", null, VariableType.QUICK_MENU_CODE_NONE, "GPS 설정 안내 팝업"),
    SM_R_RSV_P03("SM_R_RSV_P03", null, VariableType.QUICK_MENU_CODE_NONE, "정비 예약 취소 팝업"),
    SM_R_RSV03("SM_R_RSV03", null, VariableType.QUICK_MENU_CODE_NONE, "3단계 예약 정보 확인"),
    SM_R_RSV04("SM_R_RSV04", null, VariableType.QUICK_MENU_CODE_NONE, "4단계 예약완료"),
    SM_R_RSV05("SM_R_RSV05", ServiceRepairReserveHistoryActivity.class, VariableType.QUICK_MENU_CODE_OV, "정비 예약 내역"),
    SM_R_RSV05_P01("SM_R_RSV05_P01", null, VariableType.QUICK_MENU_CODE_NONE, "예약 취소 사유 선택 팝업"),
    SM_R_RSV05_P02("SM_R_RSV05_P02", null, VariableType.QUICK_MENU_CODE_NONE, "예약 취소 팝업"),

    SM_R_RSV05_S01("SM_R_RSV05_S01", null, VariableType.QUICK_MENU_CODE_NONE, "픽업/딜리버리 이력 보기"),
    SM_R_HISTORY01("SM_R_HISTORY01", null, VariableType.QUICK_MENU_CODE_NONE, "정비 이력"),
    SM_R_HISTORY01_S01("SM_R_HISTORY01_S01", null, VariableType.QUICK_MENU_CODE_NONE, "픽업/딜리버리 이력 보기"),
    R_REMOTE01("R_REMOTE01", null, VariableType.QUICK_MENU_CODE_NONE, "원격 진단"),
    R_REMOTE01_P01("R_REMOTE01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "차량문제 선택 팝업"),
    R_REMOTE01_P02("R_REMOTE01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "경고등 선택 팝업"),
    R_REMOTE01_P03("R_REMOTE01_P03", null, VariableType.QUICK_MENU_CODE_NONE, "원격진단 신청 종료 팝업"),

    R_REMOTE01_P04("R_REMOTE01_P04", null, VariableType.QUICK_MENU_CODE_NONE, "긴급출동 접수 상태 팝업"),
    R_REMOTE01_P05("R_REMOTE01_P05", null, VariableType.QUICK_MENU_CODE_NONE, "긴급출동 출동 상태 팝업"),
    R_REMOTE01_P06("R_REMOTE01_P06", null, VariableType.QUICK_MENU_CODE_NONE, "원격진단 신청불가 팝업"),
    SM_EMGC_P01("SM_EMGC_P01", null, VariableType.QUICK_MENU_CODE_NONE, "GPS 설정 안내 팝업"),
    SM_EMGC01("SM_EMGC01", ServiceSOSApplyActivity.class, VariableType.QUICK_MENU_CODE_OV, "긴급출동 신청"),

    SM_EMGC01_P01("SM_EMGC01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "차량문제 팝업"),
    SM_EMGC01_P02("SM_EMGC01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "도로구분 팝업"),
    SM_EMGC01_01("SM_EMGC01_01", null, VariableType.QUICK_MENU_CODE_NONE, "주소 설정"),
    SM_EMGC01_02("SM_EMGC01_02", null, VariableType.QUICK_MENU_CODE_NONE, "주소 검색"),
    SM_EMGC01_P03("SM_EMGC01_P03", null, VariableType.QUICK_MENU_CODE_NONE, "긴급출동 종료 팝업"),
    SM_EMGC01_P04("SM_EMGC01_P04", null, VariableType.QUICK_MENU_CODE_NONE, "추가 비용 안내 팝업"),

    SM_EMGC02("SM_EMGC02", null, VariableType.QUICK_MENU_CODE_NONE, "긴급출동 접수"),
    SM_EMGC02_P01("SM_EMGC02_P01", null, VariableType.QUICK_MENU_CODE_NONE, "긴급출동 취소 팝업"),
    SM_EMGC03("SM_EMGC03", null, VariableType.QUICK_MENU_CODE_NONE, "긴급출동 현황"),
    SM_EMGC03_P01("SM_EMGC03_P01", null, VariableType.QUICK_MENU_CODE_NONE, "긴급출동 현황 레이어 팝업"),
    SM_FLAW01("SM_FLAW01", ServiceRelapseHistoryActivity.class, VariableType.QUICK_MENU_CODE_OV, "하자 재발 통보 내역"),
    SM_FLAW02("SM_FLAW02", null, VariableType.QUICK_MENU_CODE_NONE, "1단계 개인정보"),
    SM_FLAW03("SM_FLAW03", null, VariableType.QUICK_MENU_CODE_NONE, "주소 검색"),

    SM_FLAW03_01("SM_FLAW03_01", null, VariableType.QUICK_MENU_CODE_NONE, "하자재발통보 종료 팝업"),
    SM_FLAW05("SM_FLAW05", null, VariableType.QUICK_MENU_CODE_NONE, "2단계 대상자동차"),

    SM_FLAW05_P01("SM_FLAW05_P01", null, VariableType.QUICK_MENU_CODE_NONE, "자동차 등록증 팝업"),
    SM_FLAW05_P02("SM_FLAW05_P02", null, VariableType.QUICK_MENU_CODE_NONE, "운행 지역(시/도) 팝업"),
    SM_FLAW05_P03("SM_FLAW05_P03", null, VariableType.QUICK_MENU_CODE_NONE, "운행 지역(시/군/구) 팝업"),

    SM_FLAW06("SM_FLAW06", null, VariableType.QUICK_MENU_CODE_NONE, "3단계 동일수리내역"),

    SM_FLAW06_P01("SM_FLAW06_P01", null, VariableType.QUICK_MENU_CODE_NONE, "하자 구분 선택 팝업"),
    SM_FLAW06_P02("SM_FLAW06_P02", null, VariableType.QUICK_MENU_CODE_NONE, "하자재발통보 약관 동의 팝업"),
    SM_FLAW06_P04("SM_FLAW06_P04", null, VariableType.QUICK_MENU_CODE_NONE, "개인정보수집 약관 팝업"),
    SM_FLAW06_P05("SM_FLAW06_P05", null, VariableType.QUICK_MENU_CODE_NONE, "자동차 관리법 약관 팝업"),

    SM_FLAW07("SM_FLAW07", null, VariableType.QUICK_MENU_CODE_NONE, "하자 재발 통보 현황"),
    SM_CW01_A01("SM_CW01_A01", null, VariableType.QUICK_MENU_CODE_NONE, "세차 예약"),
    SM_CW01_A02("SM_CW01_A02", null, VariableType.QUICK_MENU_CODE_NONE, "세차 지점 검색"),
    SM_CW01("SM_CW01", null, VariableType.QUICK_MENU_CODE_NONE, "세차 서비스 예약 내역"),

    SM_CW01_P01("SM_CW01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "예약 취소 팝업"),
    SM_CW01_P02("SM_CW01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "서비스 지점 코드 입력 팝업"),

    SM_DRV01("SM_DRV01", ServiceDriveReqActivity.class, VariableType.QUICK_MENU_CODE_OV, "대리운전"),
    SM_DRV01_A01("SM_DRV01_A01", null, VariableType.QUICK_MENU_CODE_NONE, "대리운전 출발지 선택 지도"),
    SM_DRV01_A02("SM_DRV01_A02", null, VariableType.QUICK_MENU_CODE_NONE, "대리운전 출발지 검색"),
    SM_DRV01_A03("SM_DRV01_A03", null, VariableType.QUICK_MENU_CODE_NONE, "대리운전 도착지 검색"),
    SM_DRV01_A04("SM_DRV01_A04", null, VariableType.QUICK_MENU_CODE_NONE, "대리운전 도착지 선택 지도"),

    SM_DRV01_P01("SM_DRV01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "대리운전 신청 팝업"),

    SM_DRV02("SM_DRV02", null, VariableType.QUICK_MENU_CODE_NONE, "대리운전 신청 정보 확인"),
    SM_DRV03("SM_DRV03", null, VariableType.QUICK_MENU_CODE_NONE, "대리운전 결제"),
    SM_DRV04("SM_DRV04", null, VariableType.QUICK_MENU_CODE_NONE, "대리운전 신청완료"),
    SM_DRV05("SM_DRV05", null, VariableType.QUICK_MENU_CODE_NONE, "대리운전 신청 내역"),

    SM_DRV01_P02("SM_DRV01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "대리운전 신청 취소 팝업"),
    SM_DRV01_P03("SM_DRV01_P03", null, VariableType.QUICK_MENU_CODE_NONE, "대리운전 예약 취소 팝업"),

    SM_REVIEW01("SM_REVIEW01", null, VariableType.QUICK_MENU_CODE_NONE, "서비스 리뷰"),

    SM_REVIEW01_P01("SM_REVIEW01_P01", ServiceReviewActivity.class, VariableType.QUICK_MENU_CODE_NONE, "이용후기 (세차)"),
    SM_REVIEW01_P02("SM_REVIEW01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "서비스 리뷰 종료 팝업"),
    SM_REVIEW01_P03("SM_REVIEW01_P03", ServiceReviewActivity.class, VariableType.QUICK_MENU_CODE_NONE, "이용후기 (대리)"),

    CM01("CM01", null, VariableType.QUICK_MENU_CODE_NONE, "메인 4 Contents (로그인/차량보유)"),
    CM02("CM02", null, VariableType.QUICK_MENU_CODE_NONE, "메인 4 Contents (로그인/예약대기)"),
    CM03("CM03", null, VariableType.QUICK_MENU_CODE_NONE, "메인 4 Contents (로그인/차량미보유)"),
    CM04("CM04", null, VariableType.QUICK_MENU_CODE_NONE, "메인 4 Contents (비로그인)"),

    CM_LIFE01("CM_LIFE01", null, VariableType.QUICK_MENU_CODE_NONE, "라이프 스타일 컨텐츠 상세"),
    CM_EVENT01("CM_EVENT01", null, VariableType.QUICK_MENU_CODE_NONE, "이벤트 상세"),

    CM_SRCH01("CM_SRCH01", null, VariableType.QUICK_MENU_CODE_NONE, "콘텐츠 검색"),
    MG01("MG01", null, VariableType.QUICK_MENU_CODE_NONE, "My G 홈(로그인/차량보유)"),
    MG02("MG02", null, VariableType.QUICK_MENU_CODE_NONE, "My G 홈(로그인/예약대기)"),
    MG03("MG03", null, VariableType.QUICK_MENU_CODE_NONE, "My G 홈(로그인/차량미보유)"),
    MG04("MG04", null, VariableType.QUICK_MENU_CODE_NONE, "My G 홈(비로그인)"),
    MG_GA00("MG_GA00", null, VariableType.QUICK_MENU_CODE_NONE, "메뉴 검색"),
    MG_GA01("MG_GA01", MyGGAActivity.class, VariableType.QUICK_MENU_CODE_NV, "제네시스 어카운트"),
    MG_MEMBER01("MG_MEMBER01", MyGMembershipActivity.class, VariableType.QUICK_MENU_CODE_NV, "멤버십"),

    MG_MEMBER01_P01("MG_MEMBER01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "소멸 예정 포인트 팝업"),
    MG_MEMBER02("MG_MEMBER02", MyGMembershipUseCaseActivity.class, VariableType.QUICK_MENU_CODE_NV, "포인트 사용 제휴처 안내"),
    MG_MEMBER01_P02("MG_MEMBER01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "멤버십 카드 안내 팝업"),
    MG_MEMBER04("MG_MEMBER04", MyGMembershipUseListActivity.class, VariableType.QUICK_MENU_CODE_NV, "포인트 사용 내역"),

    MG_MEMBER03("MG_MEMBER03", null, VariableType.QUICK_MENU_CODE_NONE, "카드 비밀번호 변경"),
    MG_BF01("MG_BF01", null, VariableType.QUICK_MENU_CODE_OV, "혜택/쿠폰"),
    MG_BF01_01("MG_BF01_01", null, VariableType.QUICK_MENU_CODE_NONE, "사용 내역"),
    MG_BF01_02("MG_BF01_02", null, VariableType.QUICK_MENU_CODE_NONE, "설문"),
    MG_PRVI01("MG_PRVI01", null, VariableType.QUICK_MENU_CODE_NONE, "프리빌리지 차량 목록"),

    MG_PRVI02("MG_PRVI02", null, VariableType.QUICK_MENU_CODE_NONE, "프리빌리지 신청"),
    MG_PRVI03("MG_PRVI03", null, VariableType.QUICK_MENU_CODE_NONE, "프리빌리지 혜택"),
    MG_PRVI04("MG_PRVI04", null, VariableType.QUICK_MENU_CODE_NONE, "프리빌리지 현황"),
    MG_CON01("MG_CON01", null, VariableType.QUICK_MENU_CODE_NONE, "주유 포인트 조회"),

    MG_CON01_P01("MG_CON01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "포인트 연동 해제 팝업"),
    MG_CON02("MG_CON02", null, VariableType.QUICK_MENU_CODE_NONE, "포인트 연동 안내"),

    MG_CON02_01("MG_CON02_01", null, VariableType.QUICK_MENU_CODE_NONE, "포인트 연동 동의"),
    MG_CON02_02("MG_CON02_02", null, VariableType.QUICK_MENU_CODE_NONE, "포인트 연동 동의 상세"),
    MG_CON02_03("MG_CON02_03", null, VariableType.QUICK_MENU_CODE_NONE, "포인트 연동 제3자 제공"),

    MG_CON02_P01("MG_CON02_P01", null, VariableType.QUICK_MENU_CODE_NONE, "포인트 연동 종료 팝업"),
    MG_NOTICE01("MG_NOTICE01", MyGNotiActivity.class, VariableType.QUICK_MENU_CODE_NV, "공지사항"),
    MG_MENU01("MG_MENU01", ServiceTermDetailActivity.class, VariableType.QUICK_MENU_CODE_NV, "이용약관"),
    MG_MENU02("MG_MENU02", ServiceTermDetailActivity.class, VariableType.QUICK_MENU_CODE_NV, "개인정보처리방침"),
    MG_MENU03("MG_MENU03", MyGTermsActivity.class, VariableType.QUICK_MENU_CODE_NV, "오픈소스 라이선스"),
    MG_VERSION01("MG_VERSION01", MyGVersioniActivity.class, VariableType.QUICK_MENU_CODE_NV, "버전 정보");

    private String id;
    private Class activity;
    private int quickLevel;
    private String description;

    APPIAInfo(String id, Class activity, int quickLevel, String description) {
        this.id = id;
        this.activity = activity;
        this.quickLevel = quickLevel;
        this.description = description;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static List<MenuVO> getQuickMenus(final int limit) {
        List<APPIAInfo> menuList = Arrays.asList(APPIAInfo.values()).stream().filter(data -> data.quickLevel>VariableType.QUICK_MENU_CODE_NONE&&data.quickLevel<=limit).collect(Collectors.toList());;
        List<MenuVO> list = new ArrayList<>();
        for(APPIAInfo menu : menuList){
            MenuVO menuVO = new MenuVO();
            menuVO.setCode(menu.getId());
            menuVO.setName(menu.getDescription());
            menuVO.setActivity(menu.getActivity());
            list.add(menuVO);
        }
        return list;
    }

    public Class getActivity() {
        return activity;
    }

    public void setActivity(Class activity) {
        this.activity = activity;
    }

    public static APPIAInfo findCode(String id){
        return Arrays.asList(APPIAInfo.values()).stream().filter(data->data.getId().equalsIgnoreCase(id)).findAny().orElse(DEFAULT);
    }
}
