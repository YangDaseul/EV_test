package com.genesis.apps.comm.model.api;

import com.genesis.apps.comm.model.constants.VariableType;
import com.genesis.apps.comm.model.vo.MenuVO;
import com.genesis.apps.ui.common.activity.GAWebActivity;
import com.genesis.apps.ui.common.activity.PaymentWebViewActivity;
import com.genesis.apps.ui.intro.IntroActivity;
import com.genesis.apps.ui.intro.PermissionsActivity;
import com.genesis.apps.ui.main.AlarmCenterActivity;
import com.genesis.apps.ui.main.AlarmCenterSearchActivity;
import com.genesis.apps.ui.main.BarcodeActivity;
import com.genesis.apps.ui.main.DigitalWalletActivity;
import com.genesis.apps.ui.main.ServiceNetworkActivity;
import com.genesis.apps.ui.main.ServiceNetworkPriceActivity;
import com.genesis.apps.ui.main.SimilarCarActivity;
import com.genesis.apps.ui.main.SimilarCarContractDetailActivity;
import com.genesis.apps.ui.main.SimilarCarContractHistoryActivity;
import com.genesis.apps.ui.main.contents.ContentsDetailWebActivity;
import com.genesis.apps.ui.main.contents.ContentsSearchActivity;
import com.genesis.apps.ui.main.home.BtrBluehandsActivity;
import com.genesis.apps.ui.main.home.BtrBluehandsListActivity;
import com.genesis.apps.ui.main.home.BtrBluehandsMapActivity;
import com.genesis.apps.ui.main.home.BtrConslHistActivity;
import com.genesis.apps.ui.main.home.BtrConsultTypeActivity;
import com.genesis.apps.ui.main.home.BtrServiceInfoActivity;
import com.genesis.apps.ui.main.home.LeasingCarHistActivity;
import com.genesis.apps.ui.main.home.LeasingCarHistDetailActivity;
import com.genesis.apps.ui.main.home.LeasingCarInfoActivity;
import com.genesis.apps.ui.main.home.LeasingCarRegisterInputActivity;
import com.genesis.apps.ui.main.home.LeasingCarVinRegisterActivity;
import com.genesis.apps.ui.main.home.MyCarActivity;
import com.genesis.apps.ui.main.home.MyLocationActivity;
import com.genesis.apps.ui.main.home.RegisterUsedCarActivity;
import com.genesis.apps.ui.main.home.SearchAddressActivity;
import com.genesis.apps.ui.main.home.WarrantyRepairGuideActivity;
import com.genesis.apps.ui.main.insight.InsightExpnInputActivity;
import com.genesis.apps.ui.main.insight.InsightExpnMainActivity;
import com.genesis.apps.ui.main.insight.InsightExpnMembershipActivity;
import com.genesis.apps.ui.main.insight.InsightExpnModifyActivity;
import com.genesis.apps.ui.main.service.CarWashHistoryActivity;
import com.genesis.apps.ui.main.service.CarWashSearchActivity;
import com.genesis.apps.ui.main.service.CardManageActivity;
import com.genesis.apps.ui.main.service.ChargeFindActivity;
import com.genesis.apps.ui.main.service.ChargeReserveHistoryActivity;
import com.genesis.apps.ui.main.service.MaintenanceReserveActivity;
import com.genesis.apps.ui.main.service.ServiceAirport2ApplyActivity;
import com.genesis.apps.ui.main.service.ServiceAutocare2ApplyActivity;
import com.genesis.apps.ui.main.service.ServiceChargeBtrReqActivity;
import com.genesis.apps.ui.main.service.ServiceDriveHistoryActivity;
import com.genesis.apps.ui.main.service.ServiceDriveReqActivity;
import com.genesis.apps.ui.main.service.ServiceDriveReqCompleteActivity;
import com.genesis.apps.ui.main.service.ServiceDriveReqResultActivity;
import com.genesis.apps.ui.main.service.ServiceHomeToHome2ApplyActivity;
import com.genesis.apps.ui.main.service.ServiceRelapse3Activity;
import com.genesis.apps.ui.main.service.ServiceRelapseApply1Activity;
import com.genesis.apps.ui.main.service.ServiceRelapseApply2Activity;
import com.genesis.apps.ui.main.service.ServiceRelapseApplyExampleActivity;
import com.genesis.apps.ui.main.service.ServiceRelapseHistoryActivity;
import com.genesis.apps.ui.main.service.ServiceRelapseReqResultActivity;
import com.genesis.apps.ui.main.service.ServiceRemoteListActivity;
import com.genesis.apps.ui.main.service.ServiceRemoteRegisterActivity;
import com.genesis.apps.ui.main.service.ServiceRepair2ApplyActivity;
import com.genesis.apps.ui.main.service.ServiceRepair2PreCheckActivity;
import com.genesis.apps.ui.main.service.ServiceRepairImageActivity;
import com.genesis.apps.ui.main.service.ServiceRepairReserveHistoryActivity;
import com.genesis.apps.ui.main.service.ServiceReviewActivity;
import com.genesis.apps.ui.main.service.ServiceSOSApplyActivity;
import com.genesis.apps.ui.main.service.ServiceSOSApplyInfoActivity;
import com.genesis.apps.ui.main.service.ServiceSOSPayInfoActivity;
import com.genesis.apps.ui.main.service.ServiceSOSRouteInfoActivity;
import com.genesis.apps.ui.myg.MyGCouponActivity;
import com.genesis.apps.ui.myg.MyGCreditUseListActivity;
import com.genesis.apps.ui.myg.MyGEntranceActivity;
import com.genesis.apps.ui.myg.MyGGAActivity;
import com.genesis.apps.ui.myg.MyGHomeActivity;
import com.genesis.apps.ui.myg.MyGMembershipActivity;
import com.genesis.apps.ui.myg.MyGMembershipCardPasswordActivity;
import com.genesis.apps.ui.myg.MyGMembershipExtncActivity;
import com.genesis.apps.ui.myg.MyGMembershipInfoActivity;
import com.genesis.apps.ui.myg.MyGMembershipUseCaseActivity;
import com.genesis.apps.ui.myg.MyGMembershipUseListActivity;
import com.genesis.apps.ui.myg.MyGMenuActivity;
import com.genesis.apps.ui.myg.MyGNotiActivity;
import com.genesis.apps.ui.myg.MyGOilIntegrationActivity;
import com.genesis.apps.ui.myg.MyGOilPointActivity;
import com.genesis.apps.ui.myg.MyGOilTermActivity;
import com.genesis.apps.ui.myg.MyGPrivilegeApplyActivity;
import com.genesis.apps.ui.myg.MyGPrivilegeStateActivity;
import com.genesis.apps.ui.myg.MyGTerms0013Activity;
import com.genesis.apps.ui.myg.MyGTerms0014Activity;
import com.genesis.apps.ui.myg.MyGTerms1000Activity;
import com.genesis.apps.ui.myg.MyGTerms2000Activity;
import com.genesis.apps.ui.myg.MyGTermsActivity;
import com.genesis.apps.ui.myg.MyGVersioniActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public enum APPIAInfo {
    DEFAULT("default", null, VariableType.QUICK_MENU_CODE_NONE, "기본",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    LOG01("LOG01", MyGEntranceActivity.class, VariableType.QUICK_MENU_CODE_NONE, "로그인 화면",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    INT01("INT01", IntroActivity.class, VariableType.QUICK_MENU_CODE_NONE, "스플래시",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    INT02("INT02", PermissionsActivity.class, VariableType.QUICK_MENU_CODE_NONE, "접근 권한 알림",VariableType.QUICK_MENU_CATEGORY_NONE,""), //TODO 로그인/회원가입과 IA 아이디가 같음..
    INT02_P01("INT02_P01", null, VariableType.QUICK_MENU_CODE_NONE, "접근권한 설정 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    INT03("INT03", null, VariableType.QUICK_MENU_CODE_NONE, "서비스 가입",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    INT03_01("INT03_01", null, VariableType.QUICK_MENU_CODE_NONE, "제네시스 앱 이용약관",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    INT03_P01("INT03_P01", null, VariableType.QUICK_MENU_CODE_NONE, "서비스 가입 실패 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    INT04("INT04", null, VariableType.QUICK_MENU_CODE_NONE, "제네시스 멤버스 가입",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    POP01("POP01", null, VariableType.QUICK_MENU_CODE_NONE, "업데이트 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    POP02("POP02", null, VariableType.QUICK_MENU_CODE_NONE, "네트워크 불안정 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    ALRM01("ALRM01", AlarmCenterActivity.class, VariableType.QUICK_MENU_CODE_NV, "알림센터",VariableType.QUICK_MENU_CATEGORY_HOME,""),
    ALRM01_01("ALRM01_01", null, VariableType.QUICK_MENU_CODE_NONE, "알림 상세",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    ALRM01_SRCH01("ALRM01_SRCH01", AlarmCenterSearchActivity.class, VariableType.QUICK_MENU_CODE_NONE, "알림 검색",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    Bcode01("Bcode01", BarcodeActivity.class, VariableType.QUICK_MENU_CODE_NV, "바코드",VariableType.QUICK_MENU_CATEGORY_HOME, VariableType.VEHICLE_CODE_GN),
    GM01("GM01", null, VariableType.QUICK_MENU_CODE_NONE, "메인 1 Home (로그인/차량보유)",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM02("GM02", null, VariableType.QUICK_MENU_CODE_NONE, "메인 1 Home (로그인/예약대기)",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM03("GM03", null, VariableType.QUICK_MENU_CODE_NONE, "메인 1 Home (로그인/차량미보유)",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM04("GM04", null, VariableType.QUICK_MENU_CODE_NONE, "메인 1 Home (비로그인)",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM01_01("GM01_01", MyLocationActivity.class, VariableType.QUICK_MENU_CODE_OV, "내 차 위치",VariableType.QUICK_MENU_CATEGORY_HOME,""),
    GM01_02("GM01_02", WarrantyRepairGuideActivity.class, VariableType.QUICK_MENU_CODE_OV, "보증 수리 안내",VariableType.QUICK_MENU_CATEGORY_HOME,""),
    GM01_03("GM01_03", null, VariableType.QUICK_MENU_CODE_NONE, "공유 미리보기",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM01_04("GM01_04", null, VariableType.QUICK_MENU_CODE_NONE, "데이터마일스 상세",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_BTO1("GM_BTO1", GAWebActivity.class, VariableType.QUICK_MENU_CODE_NONE, "견적내기",VariableType.QUICK_MENU_CATEGORY_NONE,""), //2021-03-02 요청으로 삭제
    GM_BTO2("GM_BTO2", null, VariableType.QUICK_MENU_CODE_NONE, "BTO",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM02_CTR01("GM02_CTR01", SimilarCarContractHistoryActivity.class, VariableType.QUICK_MENU_CODE_NONE, "구매 계약 내역",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM02_CTR01_P01("GM02_CTR01_P01", SimilarCarContractDetailActivity.class, VariableType.QUICK_MENU_CODE_NONE, "차량 가격 정보 상세 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM02_INV01("GM02_INV01", SimilarCarActivity.class, VariableType.QUICK_MENU_CODE_NONE, "유사 재고 조회 / 예약",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM02_BF01("GM02_BF01", null, VariableType.QUICK_MENU_CODE_NONE, "대기고객 혜택",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    PAY01("PAY01", DigitalWalletActivity.class, VariableType.QUICK_MENU_CODE_OV, "디지털 월렛", VariableType.QUICK_MENU_CATEGORY_HOME,VariableType.VEHICLE_CODE_EV),
    PAY01_AOS01("PAY01_AOS01", null, VariableType.QUICK_MENU_CODE_NONE, "맴버쉽 직접 등록", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    PAY01_AOS02("PAY01_AOS02", null, VariableType.QUICK_MENU_CODE_NONE, "주 사용카드 변경", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    PAY01_AOS03("PAY01_AOS03", null, VariableType.QUICK_MENU_CODE_NONE, "맴버쉽 관리", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    PAY02("PAY02", null, VariableType.QUICK_MENU_CODE_NONE, "맴버십 바코드", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    PAY03("PAY03", null, VariableType.QUICK_MENU_CODE_NONE, "결제 수단 관리", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    PAY04_PAY01("PAY04_PAY01", null, VariableType.QUICK_MENU_CODE_NONE, "미수금 결제", VariableType.QUICK_MENU_CATEGORY_NONE, ""),
    PAY05_PSW01("PAY05_PSW01", null, VariableType.QUICK_MENU_CODE_NONE, "선불 교통카드 비밀번호 등록", VariableType.QUICK_MENU_CATEGORY_NONE, ""),

    GM_BT01_P01("GM_BT01_P01", BtrServiceInfoActivity.class, VariableType.QUICK_MENU_CODE_NONE, "버틀러 서비스 안내 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_BT01_P02("GM_BT01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "버틀러 신청 안내 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_BT02("GM_BT02", BtrBluehandsActivity.class, VariableType.QUICK_MENU_CODE_NONE, "전담 블루핸즈/버틀러",VariableType.QUICK_MENU_CATEGORY_HOME,""), //2021-01-16 요청으로 삭제
    GM_BT03("GM_BT03", BtrBluehandsMapActivity.class, VariableType.QUICK_MENU_CODE_NONE, "블루핸즈 위치 상세",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_BT04("GM_BT04", BtrConsultTypeActivity.class, VariableType.QUICK_MENU_CODE_NONE, "버틀러 1:1 문의",VariableType.QUICK_MENU_CATEGORY_HOME,""), //2021-01-16 요청으로 삭제
    GM_BT04_P01("GM_BT04_P01", null, VariableType.QUICK_MENU_CODE_NONE, "버틀러 문의 종료 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_BT05("GM_BT05", BtrConslHistActivity.class, VariableType.QUICK_MENU_CODE_NONE, "상담 이력",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_BT06_P01("GM_BT06_P01", null, VariableType.QUICK_MENU_CODE_NONE, "GPS 설정 안내 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_BT06("GM_BT06", null, VariableType.QUICK_MENU_CODE_NONE, "버틀러 변경",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_BT06_01("GM_BT06_01", null, VariableType.QUICK_MENU_CODE_NONE, "블루핸즈 필터",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_BT06_02("GM_BT06_02", BtrBluehandsListActivity.class, VariableType.QUICK_MENU_CODE_NONE, "블루핸즈 목록 보기",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    GM_CARLST01("GM_CARLST01", MyCarActivity.class, VariableType.QUICK_MENU_CODE_CV, "차량 상세",VariableType.QUICK_MENU_CATEGORY_HOME,""),
    GM_CARLST_P01("GM_CARLST_P01", null, VariableType.QUICK_MENU_CODE_NONE, "차량번호 수정 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_CARLST_P02("GM_CARLST_P02", null, VariableType.QUICK_MENU_CODE_NONE, "차량 삭제 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_CARLST_01("GM_CARLST_01", LeasingCarVinRegisterActivity.class, VariableType.QUICK_MENU_CODE_NV, "렌트/리스 실운행자 등록 확인",VariableType.QUICK_MENU_CATEGORY_HOME,""),
    GM_CARLST_01_01("GM_CARLST_01_01", LeasingCarRegisterInputActivity.class, VariableType.QUICK_MENU_CODE_NONE, "등록 신청",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_CARLST_01_B01("GM_CARLST_01_B01", null, VariableType.QUICK_MENU_CODE_NONE, "전담블루핸즈/버틀러 선택 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_CARLST_01_B02("GM_CARLST_01_B02", null, VariableType.QUICK_MENU_CODE_NONE, "블루핸즈 필터",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_CARLST_01_B03("GM_CARLST_01_B03", null, VariableType.QUICK_MENU_CODE_NONE, "블루핸즈 목록 보기",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_CARLST_01_A01("GM_CARLST_01_A01", SearchAddressActivity.class, VariableType.QUICK_MENU_CODE_NONE, "수령지 주소 검색",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_CARLST_01_result("GM_CARLST_01_result", LeasingCarHistDetailActivity.class, VariableType.QUICK_MENU_CODE_NONE, "상세 페이지",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_CARLST_01_P01("GM_CARLST_01_P01", LeasingCarInfoActivity.class, VariableType.QUICK_MENU_CODE_NONE, "유의사항",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_CARLST_01_P02("GM_CARLST_01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "계약서 종류",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_CARLST_01_P03("GM_CARLST_01_P03", null, VariableType.QUICK_MENU_CODE_NONE, "대여기간",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_CARLST_01_P04("GM_CARLST_01_P04", null, VariableType.QUICK_MENU_CODE_NONE, "신청 초기화 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_CARLST_01_P05("GM_CARLST_01_P05", null, VariableType.QUICK_MENU_CODE_NONE, "신청 취소 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_CARLST_02("GM_CARLST_02", LeasingCarHistActivity.class, VariableType.QUICK_MENU_CODE_NONE, "렌트/리스 실운행자 등록 내역",VariableType.QUICK_MENU_CATEGORY_NONE),
    GM_CARLST_03("GM_CARLST_03", RegisterUsedCarActivity.class, VariableType.QUICK_MENU_CODE_NV, "내 차 등록",VariableType.QUICK_MENU_CATEGORY_HOME),
    GM_CARLST_03_P01("GM_CARLST_03_P01", null, VariableType.QUICK_MENU_CODE_NONE, "중고차 안내사항",VariableType.QUICK_MENU_CATEGORY_NONE),
    GM_CARLST_04("GM_CARLST_04", null, VariableType.QUICK_MENU_CODE_NONE, "차량 상세",VariableType.QUICK_MENU_CATEGORY_NONE),

    GM_CARLST_02("GM_CARLST_02", LeasingCarHistActivity.class, VariableType.QUICK_MENU_CODE_NONE, "렌트/리스 실운행자 등록 내역",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_CARLST_03("GM_CARLST_03", RegisterUsedCarActivity.class, VariableType.QUICK_MENU_CODE_NV, "중고차 등록",VariableType.QUICK_MENU_CATEGORY_HOME,""),
    GM_CARLST_03_P01("GM_CARLST_03_P01", null, VariableType.QUICK_MENU_CODE_NONE, "중고차 안내사항",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    GM_CARLST_04("GM_CARLST_04", null, VariableType.QUICK_MENU_CODE_NONE, "차량 상세",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    TM01("TM01", null, VariableType.QUICK_MENU_CODE_NONE, "메인 2 Insight (로그인/차량보유)",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    TM02("TM02", null, VariableType.QUICK_MENU_CODE_NONE, "메인 2 Insight (로그인/예약대기)",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    TM03("TM03", null, VariableType.QUICK_MENU_CODE_NONE, "메인 2 Insight (로그인/차량미보유)",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    TM04("TM04", null, VariableType.QUICK_MENU_CODE_NONE, "메인 2 Insight (비로그인)",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    TM_INS01("TM_INS01", null, VariableType.QUICK_MENU_CODE_NONE, "차량 인사이트 상세",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    TM_EXPS01("TM_EXPS01", InsightExpnMainActivity.class, VariableType.QUICK_MENU_CODE_OV, "차계부",VariableType.QUICK_MENU_CATEGORY_INSIGHT,""),
    TM_EXPS01_P01("TM_EXPS01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "삭제 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    TM_EXPS01_01("TM_EXPS01_01", InsightExpnInputActivity.class, VariableType.QUICK_MENU_CODE_OV, "지출내역 입력",VariableType.QUICK_MENU_CATEGORY_INSIGHT,""),
    TM_EXPS01_P02("TM_EXPS01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "입력 취소 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    TM_EXPS01_02("TM_EXPS01_02", InsightExpnModifyActivity.class, VariableType.QUICK_MENU_CODE_NONE, "지출 내역 수정",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    TM_EXPS01_02_P01("TM_EXPS01_02_P01", null, VariableType.QUICK_MENU_CODE_NONE, "지출대상 차량 수정 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    MG_CON03("MG_CON03", InsightExpnMembershipActivity.class, VariableType.QUICK_MENU_CODE_NONE, "멤버십 사용안내",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    TM_EXPS01_P03("TM_EXPS01_P03", null, VariableType.QUICK_MENU_CODE_NONE, "수정 취소 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM01("SM01", null, VariableType.QUICK_MENU_CODE_NONE, "메인 3 Service",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM02("SM02", null, VariableType.QUICK_MENU_CODE_NONE, "상품 전체 보기",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM03("SM03", null, VariableType.QUICK_MENU_CODE_NONE, "상품 상세",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM04("SM04", null, VariableType.QUICK_MENU_CODE_NONE, "미로그인",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_SNFIND01("SM_SNFIND01", ServiceNetworkActivity.class, VariableType.QUICK_MENU_CODE_0000, "서비스 네트워크 찾기",VariableType.QUICK_MENU_CATEGORY_SERVICE,""),
    SM_SNFIND01_P01("SM_SNFIND01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "정비 예약하기",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_SNFIND01_P02("SM_SNFIND01_P02", ServiceNetworkPriceActivity.class, VariableType.QUICK_MENU_CODE_NONE, "대표 가격 보기",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_SNFIND02("SM_SNFIND02", null, VariableType.QUICK_MENU_CODE_NONE, "서비스 네트워크 필터",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_SNFIND03("SM_SNFIND03", null, VariableType.QUICK_MENU_CODE_NONE, "서비스 네트워크 목록",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_R_RSV01("SM_R_RSV01", MaintenanceReserveActivity.class, VariableType.QUICK_MENU_CODE_OV, "정비 예약하기",VariableType.QUICK_MENU_CATEGORY_SERVICE,""),
    SM_R_RSV01_P01("SM_R_RSV01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "정비내용 선택 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_01("SM_R_RSV02_01", ServiceAutocare2ApplyActivity.class, VariableType.QUICK_MENU_CODE_NONE, "오토케어",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_01_P01("SM_R_RSV02_01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "오토케어 예약 서비스 선택 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_01_A01("SM_R_RSV02_01_A01", null, VariableType.QUICK_MENU_CODE_NONE, "오토케어 주소 검색",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_01_A02("SM_R_RSV02_01_A02", null, VariableType.QUICK_MENU_CODE_NONE, "오토케어 지도 주소 입력 지도",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_01_P02("SM_R_RSV02_01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "오토케어 예약 희망일 선택 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_02("SM_R_RSV02_02", ServiceAirport2ApplyActivity.class, VariableType.QUICK_MENU_CODE_NONE, "에어포트",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_03("SM_R_RSV02_03", ServiceHomeToHome2ApplyActivity.class, VariableType.QUICK_MENU_CODE_NONE, "홈투홈",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_03_P01("SM_R_RSV02_03_P01", null, VariableType.QUICK_MENU_CODE_NONE, "홈투홈 서비스 선택 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_03_A01("SM_R_RSV02_03_A01", null, VariableType.QUICK_MENU_CODE_NONE, "홈투홈 픽업 주소 검색",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_03_A02("SM_R_RSV02_03_A02", null, VariableType.QUICK_MENU_CODE_NONE, "홈투홈 픽업 주소 입력 지도",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_03_A03("SM_R_RSV02_03_A03", null, VariableType.QUICK_MENU_CODE_NONE, "홈투홈 딜리버리 주소 검색",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_03_P02("SM_R_RSV02_03_P02", null, VariableType.QUICK_MENU_CODE_NONE, "홈투홈 예약 희망일 선택 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_03_A04("SM_R_RSV02_03_A04", null, VariableType.QUICK_MENU_CODE_NONE, "홈투홈 딜리버리 주소 입력 지도",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV_P01("SM_R_RSV_P01", null, VariableType.QUICK_MENU_CODE_NONE, "모바일 정비 불가 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_04("SM_R_RSV02_04", ServiceRepair2ApplyActivity.class, VariableType.QUICK_MENU_CODE_NONE, "정비소 예약",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_04_A01("SM_R_RSV02_04_A01", null, VariableType.QUICK_MENU_CODE_NONE, "정비소 설정(지도)",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_04_A02("SM_R_RSV02_04_A02", null, VariableType.QUICK_MENU_CODE_NONE, "정비소 위치/필터",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_04_P01("SM_R_RSV02_04_P01", null, VariableType.QUICK_MENU_CODE_NONE, "정비 예약일 선택 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_04_A03("SM_R_RSV02_04_A03", null, VariableType.QUICK_MENU_CODE_NONE, "정비소 목록",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV02_04_Q02("SM_R_RSV02_04_Q02", ServiceRepair2PreCheckActivity.class, VariableType.QUICK_MENU_CODE_NONE, "사전문진표 작성",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_R_RSV_P02("SM_R_RSV_P02", null, VariableType.QUICK_MENU_CODE_NONE, "GPS 설정 안내 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV_P03("SM_R_RSV_P03", null, VariableType.QUICK_MENU_CODE_NONE, "정비 예약 취소 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV03("SM_R_RSV03", null, VariableType.QUICK_MENU_CODE_NONE, "3단계 예약 정보 확인",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV04("SM_R_RSV04", null, VariableType.QUICK_MENU_CODE_NONE, "4단계 예약완료",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV05("SM_R_RSV05", ServiceRepairReserveHistoryActivity.class, VariableType.QUICK_MENU_CODE_OV, "정비 예약 내역/이력",VariableType.QUICK_MENU_CATEGORY_SERVICE,""),
    SM_R_RSV05_P01("SM_R_RSV05_P01", null, VariableType.QUICK_MENU_CODE_NONE, "예약 취소 사유 선택 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_RSV05_P02("SM_R_RSV05_P02", null, VariableType.QUICK_MENU_CODE_NONE, "예약 취소 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_R_RSV05_S01("SM_R_RSV05_S01", null, VariableType.QUICK_MENU_CODE_NONE, "픽업/딜리버리 이력 보기",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_HISTORY01("SM_R_HISTORY01", null, VariableType.QUICK_MENU_CODE_NONE, "정비 이력",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_R_HISTORY01_S01("SM_R_HISTORY01_S01", null, VariableType.QUICK_MENU_CODE_NONE, "픽업/딜리버리 이력 보기",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_R_HISTORY02("SM_R_HISTORY02", ServiceRepairImageActivity.class, VariableType.QUICK_MENU_CODE_NONE, "수리 사진",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    R_REMOTE01("R_REMOTE01", null, VariableType.QUICK_MENU_CODE_NONE, "원격 진단",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    R_REMOTE01_P01("R_REMOTE01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "차량문제 선택 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    R_REMOTE01_P02("R_REMOTE01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "경고등 선택 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    R_REMOTE01_P03("R_REMOTE01_P03", null, VariableType.QUICK_MENU_CODE_NONE, "원격진단 신청 종료 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    R_REMOTE01_P04("R_REMOTE01_P04", null, VariableType.QUICK_MENU_CODE_NONE, "긴급출동 접수 상태 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    R_REMOTE01_P05("R_REMOTE01_P05", null, VariableType.QUICK_MENU_CODE_NONE, "긴급출동 출동 상태 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    R_REMOTE01_P06("R_REMOTE01_P06", null, VariableType.QUICK_MENU_CODE_NONE, "원격진단 신청불가 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_EMGC_P01("SM_EMGC_P01", null, VariableType.QUICK_MENU_CODE_NONE, "GPS 설정 안내 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_EMGC01("SM_EMGC01", ServiceSOSApplyActivity.class, VariableType.QUICK_MENU_CODE_OV, "긴급출동 신청",VariableType.QUICK_MENU_CATEGORY_SERVICE,""),

    SM_EMGC01_P01("SM_EMGC01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "차량문제 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_EMGC01_P02("SM_EMGC01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "도로구분 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_EMGC01_01("SM_EMGC01_01", null, VariableType.QUICK_MENU_CODE_NONE, "주소 설정",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_EMGC01_02("SM_EMGC01_02", null, VariableType.QUICK_MENU_CODE_NONE, "주소 검색",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_EMGC01_P03("SM_EMGC01_P03", null, VariableType.QUICK_MENU_CODE_NONE, "긴급출동 종료 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_EMGC01_P04("SM_EMGC01_P04", ServiceSOSPayInfoActivity.class, VariableType.QUICK_MENU_CODE_NONE, "추가 비용 안내 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_EMGC02("SM_EMGC02", ServiceSOSApplyInfoActivity.class, VariableType.QUICK_MENU_CODE_NONE, "긴급출동 접수",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_EMGC02_P01("SM_EMGC02_P01", null, VariableType.QUICK_MENU_CODE_NONE, "긴급출동 취소 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_EMGC03("SM_EMGC03", ServiceSOSRouteInfoActivity.class, VariableType.QUICK_MENU_CODE_NONE, "긴급출동 현황",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_EMGC03_P01("SM_EMGC03_P01", null, VariableType.QUICK_MENU_CODE_NONE, "긴급출동 현황 레이어 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_REMOTE01("SM_REMOTE01", ServiceRemoteRegisterActivity.class, VariableType.QUICK_MENU_CODE_NONE, "원격 진단 신청",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_REMOTE02("SM_REMOTE02", ServiceRemoteListActivity.class, VariableType.QUICK_MENU_CODE_NONE, "원격진단 신청 내역",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_FLAW01("SM_FLAW01", ServiceRelapseHistoryActivity.class, VariableType.QUICK_MENU_CODE_OV, "하자 재발 통보 내역",VariableType.QUICK_MENU_CATEGORY_SERVICE,""),
    SM_FLAW02("SM_FLAW02", ServiceRelapseApply1Activity.class, VariableType.QUICK_MENU_CODE_NONE, "1단계 개인정보",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_FLAW03("SM_FLAW03", null, VariableType.QUICK_MENU_CODE_NONE, "주소 검색",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_FLAW03_01("SM_FLAW03_01", null, VariableType.QUICK_MENU_CODE_NONE, "하자재발통보 종료 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_FLAW05("SM_FLAW05", ServiceRelapseApply2Activity.class, VariableType.QUICK_MENU_CODE_NONE, "2단계 대상자동차",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_FLAW05_P01("SM_FLAW05_P01", ServiceRelapseApplyExampleActivity.class, VariableType.QUICK_MENU_CODE_NONE, "자동차 등록증 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_FLAW05_P02("SM_FLAW05_P02", null, VariableType.QUICK_MENU_CODE_NONE, "운행 지역(시/도) 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_FLAW05_P03("SM_FLAW05_P03", null, VariableType.QUICK_MENU_CODE_NONE, "운행 지역(시/군/구) 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_FLAW06("SM_FLAW06", ServiceRelapse3Activity.class, VariableType.QUICK_MENU_CODE_NONE, "3단계 동일수리내역",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_FLAW06_P01("SM_FLAW06_P01", null, VariableType.QUICK_MENU_CODE_NONE, "하자 구분 선택 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_FLAW06_P02("SM_FLAW06_P02", null, VariableType.QUICK_MENU_CODE_NONE, "하자재발통보 약관 동의 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_FLAW06_P04("SM_FLAW06_P04", null, VariableType.QUICK_MENU_CODE_NONE, "개인정보수집 약관 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_FLAW06_P05("SM_FLAW06_P05", null, VariableType.QUICK_MENU_CODE_NONE, "자동차 관리법 약관 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_FLAW07("SM_FLAW07", ServiceRelapseReqResultActivity.class, VariableType.QUICK_MENU_CODE_NONE, "하자 재발 통보 현황",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CW01_A01("SM_CW01_A01", null, VariableType.QUICK_MENU_CODE_NONE, "세차 예약",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CW01_A02("SM_CW01_A02", CarWashSearchActivity.class, VariableType.QUICK_MENU_CODE_NONE, "세차 지점 검색",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CW01("SM_CW01", CarWashHistoryActivity.class, VariableType.QUICK_MENU_CODE_NONE, "세차 서비스 예약 내역",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_CW01_P01("SM_CW01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "예약 취소 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CW01_P02("SM_CW01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "서비스 지점 코드 입력 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_DRV01("SM_DRV01", ServiceDriveReqActivity.class, VariableType.QUICK_MENU_CODE_OV, "대리운전",VariableType.QUICK_MENU_CATEGORY_SERVICE,""),
    SM_DRV01_A01("SM_DRV01_A01", null, VariableType.QUICK_MENU_CODE_NONE, "대리운전 출발지 선택 지도",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_DRV01_A02("SM_DRV01_A02", null, VariableType.QUICK_MENU_CODE_NONE, "대리운전 출발지 검색",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_DRV01_A03("SM_DRV01_A03", null, VariableType.QUICK_MENU_CODE_NONE, "대리운전 도착지 검색",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_DRV01_A04("SM_DRV01_A04", null, VariableType.QUICK_MENU_CODE_NONE, "대리운전 도착지 선택 지도",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_DRV01_P01("SM_DRV01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "대리운전 신청 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_DRV02("SM_DRV02", ServiceDriveReqResultActivity.class, VariableType.QUICK_MENU_CODE_NONE, "대리운전 신청 정보 확인",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_DRV03("SM_DRV03", PaymentWebViewActivity.class, VariableType.QUICK_MENU_CODE_NONE, "대리운전 결제",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_DRV04("SM_DRV04", ServiceDriveReqCompleteActivity.class, VariableType.QUICK_MENU_CODE_NONE, "대리운전 신청완료",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_DRV05("SM_DRV05", ServiceDriveHistoryActivity.class, VariableType.QUICK_MENU_CODE_NONE, "대리운전 신청 내역",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_DRV06("SM_DRV06", ServiceDriveReqResultActivity.class, VariableType.QUICK_MENU_CODE_NONE, "신청 상태 확인",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_DRV01_P02("SM_DRV01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "대리운전 신청 취소 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_DRV01_P03("SM_DRV01_P03", null, VariableType.QUICK_MENU_CODE_NONE, "대리운전 예약 취소 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_REVIEW01("SM_REVIEW01", null, VariableType.QUICK_MENU_CODE_NONE, "서비스 리뷰",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_REVIEW01_P01("SM_REVIEW01_P01", ServiceReviewActivity.class, VariableType.QUICK_MENU_CODE_NONE, "이용후기 (세차)",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_REVIEW01_P02("SM_REVIEW01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "서비스 리뷰 종료 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_REVIEW01_P03("SM_REVIEW01_P03", ServiceReviewActivity.class, VariableType.QUICK_MENU_CODE_NONE, "이용후기 (대리)",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_REVIEW01_P04("SM_REVIEW01_P04", ServiceReviewActivity.class, VariableType.QUICK_MENU_CODE_NONE, "이용후기 (픽업앤충전)", VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_EVSS01("SM_EVSS01", ChargeFindActivity.class, VariableType.QUICK_MENU_CODE_CV, "충전소 찾기", VariableType.QUICK_MENU_CATEGORY_SERVICE, VariableType.VEHICLE_CODE_EV),
    SM_EVSS01_P01("SM_EVSS01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "충전소 찾기 리스트 검색 필터", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_EVSS02("SM_EVSS02", null, VariableType.QUICK_MENU_CODE_NONE, "충전소 찾기 지도", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_EVSS02_P01("SM_EVSS02_P01", null, VariableType.QUICK_MENU_CODE_NONE, "충전소 찾기 지도 검색 필터", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_EVSS03("SM_EVSS03", null, VariableType.QUICK_MENU_CODE_NONE, "주소 검색", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_EVSS04("SM_EVSS04", null, VariableType.QUICK_MENU_CODE_NONE, "충전소 상세정보(공통)", VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_EVSB01("SM_EVSB01", null, VariableType.QUICK_MENU_CODE_NONE, "충전소 예약", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_EVSB01_P01("SM_EVSB01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "예약희망일 선택 팝업", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_EVSB01_P02("SM_EVSB01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "충전소 예약완료 팝업", VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_EVSB02("SM_EVSB02", ChargeReserveHistoryActivity.class, VariableType.QUICK_MENU_CODE_NONE, "충전소 예약 내역", VariableType.QUICK_MENU_CATEGORY_NONE, ""),

    SM_CGRV01("SM_CGRV01", null, VariableType.QUICK_MENU_CODE_NONE, "픽업앤충전 서비스", VariableType.QUICK_MENU_CATEGORY_SERVICE,""),
    SM_CGRV01_P01("SM_CGRV01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "서비스 이용 동의 팝업", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CGRV01_P02("SM_CGRV01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "차량 키 전달 방식 선택 팝업", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CGRV01_01("SM_CGRV01_01", null, VariableType.QUICK_MENU_CODE_NONE, "지도 화면", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CGRV01_02("SM_CGRV01_02", null, VariableType.QUICK_MENU_CODE_NONE, "주소 검색", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CGRV01_P03("SM_CGRV01_P03", null, VariableType.QUICK_MENU_CODE_NONE, "예약 희망일/옵션 선택 팝업", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CGRV02("SM_CGRV02", null, VariableType.QUICK_MENU_CODE_NONE, "결제 정보 확인", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CGRV03("SM_CGRV03", null, VariableType.QUICK_MENU_CODE_NONE, "픽업앤충전 서비스 신청 완료", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CGRV01_P04("SM_CGRV01_P04", null, VariableType.QUICK_MENU_CODE_NONE, "픽업앤충전 서비스 소개 팝업", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CGRV02_P01("SM_CGRV02_P01", CardManageActivity.class, VariableType.QUICK_MENU_CODE_NONE, "결제수단 관리", VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_CGRV04_01("SM_CGRV04_01", null, VariableType.QUICK_MENU_CODE_NONE, "신청내역 현황/예약 정보 없음", VariableType.QUICK_MENU_CATEGORY_SERVICE,""),
    SM_CGRV04_02("SM_CGRV04_02", null, VariableType.QUICK_MENU_CODE_NONE, "신청내역 현황/예약 예약완료", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CGRV04_03("SM_CGRV04_03", null, VariableType.QUICK_MENU_CODE_NONE, "신청내역 현황/예약 픽업중/서비스중/딜리버리중", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CGRV04_04("SM_CGRV04_04", null, VariableType.QUICK_MENU_CODE_NONE, "신청내역 이력 정보 없음", VariableType.QUICK_MENU_CATEGORY_SERVICE,""),
    SM_CGRV04_05("SM_CGRV04_05", null, VariableType.QUICK_MENU_CODE_NONE, "신청내역 이력 정보 있음", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CGRV04_06("SM_CGRV04_06", null, VariableType.QUICK_MENU_CODE_NONE, "사진보기", VariableType.QUICK_MENU_CATEGORY_NONE,""),

    SM_CGGO01("SM_CGGO01", null, VariableType.QUICK_MENU_CODE_NONE, "찾아가는 충전 서비스", VariableType.QUICK_MENU_CATEGORY_SERVICE,""),
    SM_CGGO01_P01("SM_CGGO01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "서비스 이용 동의 팝업", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CGGO01_P02("SM_CGGO01_P02", null, VariableType.QUICK_MENU_CODE_NONE, "도로 구분 팝업", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CGGO01_01("SM_CGGO01_01", null, VariableType.QUICK_MENU_CODE_NONE, "지도 화면", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CGGO01_02("SM_CGGO01_02", null, VariableType.QUICK_MENU_CODE_NONE, "주소 검색", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CGGO01_P03("SM_CGGO01_P03", null, VariableType.QUICK_MENU_CODE_NONE, "찾아가는 충전 서비스 소개 팝업", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CGGO02("SM_CGGO02", null, VariableType.QUICK_MENU_CODE_NONE, "신청완료", VariableType.QUICK_MENU_CATEGORY_NONE,""),
    SM_CGGO03("SM_CGGO03", null, VariableType.QUICK_MENU_CODE_NONE, "서비스 이용 중", VariableType.QUICK_MENU_CATEGORY_NONE,""),

    RM01("RM01", null, VariableType.QUICK_MENU_CODE_NONE, "스토어(로그인)",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    RM02("RM02", null, VariableType.QUICK_MENU_CODE_NONE, "스토어(비로그인)",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    MG_CON02_P01("MG_CON02_P01", null, VariableType.QUICK_MENU_CODE_NONE, "포인트 연동 종료 팝업",VariableType.QUICK_MENU_CATEGORY_NONE),
    MG_NOTICE01("MG_NOTICE01", MyGNotiActivity.class, VariableType.QUICK_MENU_CODE_NV, "공지사항",VariableType.QUICK_MENU_CATEGORY_MYG),
    MG_MENU01("MG_MENU01", MyGTerms1000Activity.class, VariableType.QUICK_MENU_CODE_NV, "이용약관",VariableType.QUICK_MENU_CATEGORY_MYG),
    MG_MENU02("MG_MENU02", MyGTerms2000Activity.class, VariableType.QUICK_MENU_CODE_NV, "개인정보처리방침",VariableType.QUICK_MENU_CATEGORY_MYG),
    MG_MENU03("MG_MENU03", MyGTermsActivity.class, VariableType.QUICK_MENU_CODE_NV, "오픈소스 라이선스",VariableType.QUICK_MENU_CATEGORY_MYG),
    MG_MENU04("MG_MENU04", MyGTerms0013Activity.class, VariableType.QUICK_MENU_CODE_NV, "개인정보 수집 이용·약관",VariableType.QUICK_MENU_CATEGORY_MYG),
    MG_VERSION01("MG_VERSION01", MyGVersioniActivity.class, VariableType.QUICK_MENU_CODE_NV, "버전 정보",VariableType.QUICK_MENU_CATEGORY_MYG);

    CM_LIFE01("CM_LIFE01", ContentsDetailWebActivity.class, VariableType.QUICK_MENU_CODE_NONE, "라이프 스타일 컨텐츠 상세",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    CM_EVENT01("CM_EVENT01", null, VariableType.QUICK_MENU_CODE_NONE, "이벤트 상세",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    CM_SRCH01("CM_SRCH01", ContentsSearchActivity.class, VariableType.QUICK_MENU_CODE_NONE, "콘텐츠 검색",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    MG01("MG01", MyGHomeActivity.class, VariableType.QUICK_MENU_CODE_NV, "마이 페이지",VariableType.QUICK_MENU_CATEGORY_MYG,""),
    MG02("MG02", MyGHomeActivity.class, VariableType.QUICK_MENU_CODE_NONE, "마이 페이지",VariableType.QUICK_MENU_CATEGORY_MYG,""),
    MG03("MG03", MyGHomeActivity.class, VariableType.QUICK_MENU_CODE_NONE, "마이 페이지",VariableType.QUICK_MENU_CATEGORY_MYG,""),
    MG04("MG04", null, VariableType.QUICK_MENU_CODE_NONE, "마이 페이지",VariableType.QUICK_MENU_CATEGORY_MYG,""),
    MG_GA00("MG_GA00", MyGMenuActivity.class, VariableType.QUICK_MENU_CODE_NONE, "메뉴 검색",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    MG_GA01("MG_GA01", MyGGAActivity.class, VariableType.QUICK_MENU_CODE_NV, "내 정보 상세보기",VariableType.QUICK_MENU_CATEGORY_MYG,""),
    MG_MEMBER01("MG_MEMBER01", MyGMembershipActivity.class, VariableType.QUICK_MENU_CODE_NV, "멤버십",VariableType.QUICK_MENU_CATEGORY_MYG,""),

    MG_MEMBER01_P01("MG_MEMBER01_P01", MyGMembershipExtncActivity.class, VariableType.QUICK_MENU_CODE_NONE, "소멸 예정 포인트 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    MG_MEMBER02("MG_MEMBER02", MyGMembershipUseCaseActivity.class, VariableType.QUICK_MENU_CODE_NV, "포인트 사용 제휴처 안내",VariableType.QUICK_MENU_CATEGORY_MYG,""),
    MG_MEMBER01_P02("MG_MEMBER01_P02", MyGMembershipInfoActivity.class, VariableType.QUICK_MENU_CODE_NONE, "멤버십 카드 안내 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    MG_MEMBER04("MG_MEMBER04", MyGMembershipUseListActivity.class, VariableType.QUICK_MENU_CODE_NV, "포인트 사용 내역",VariableType.QUICK_MENU_CATEGORY_MYG,""),
    MG_CP01("MG_CP01", MyGCreditUseListActivity.class, VariableType.QUICK_MENU_CODE_NONE, "충전 크레딧 사용 내역",VariableType.QUICK_MENU_CODE_NONE, ""),

    MG_MEMBER03("MG_MEMBER03", MyGMembershipCardPasswordActivity.class, VariableType.QUICK_MENU_CODE_NONE, "카드 비밀번호 변경",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    MG_BF01("MG_BF01", MyGCouponActivity.class, VariableType.QUICK_MENU_CODE_NONE, "혜택/쿠폰",VariableType.QUICK_MENU_CATEGORY_NONE,""), //2021-03-04 요청으로 메뉴 제거
    MG_BF01_01("MG_BF01_01", null, VariableType.QUICK_MENU_CODE_NONE, "사용 내역",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    MG_BF01_02("MG_BF01_02", null, VariableType.QUICK_MENU_CODE_NONE, "설문",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    MG_PRVI01("MG_PRVI01", null, VariableType.QUICK_MENU_CODE_NONE, "프리빌리지 차량 목록",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    MG_PRVI02("MG_PRVI02", MyGPrivilegeApplyActivity.class, VariableType.QUICK_MENU_CODE_NONE, "프리빌리지 신청",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    MG_PRVI03("MG_PRVI03", null, VariableType.QUICK_MENU_CODE_NONE, "프리빌리지 혜택",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    MG_PRVI04("MG_PRVI04", null, VariableType.QUICK_MENU_CODE_NONE, "프리빌리지 현황",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    MG_PRVI05("MG_PRVI05", MyGPrivilegeStateActivity.class, VariableType.QUICK_MENU_CODE_NONE, "프리빌리지 현황(멤버스 차량)",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    MG_CON01("MG_CON01", MyGOilPointActivity.class, VariableType.QUICK_MENU_CODE_NONE, "주유 포인트 조회",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    MG_CON01_P01("MG_CON01_P01", null, VariableType.QUICK_MENU_CODE_NONE, "포인트 연동 해제 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    MG_CON02("MG_CON02", MyGOilIntegrationActivity.class, VariableType.QUICK_MENU_CODE_NONE, "포인트 연동 안내",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    MG_CON02_01("MG_CON02_01", MyGOilTermActivity.class, VariableType.QUICK_MENU_CODE_NONE, "포인트 연동 동의",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    MG_CON02_02("MG_CON02_02", null, VariableType.QUICK_MENU_CODE_NONE, "포인트 연동 동의 상세",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    MG_CON02_03("MG_CON02_03", null, VariableType.QUICK_MENU_CODE_NONE, "포인트 연동 제3자 제공",VariableType.QUICK_MENU_CATEGORY_NONE,""),

    MG_CON02_P01("MG_CON02_P01", null, VariableType.QUICK_MENU_CODE_NONE, "포인트 연동 종료 팝업",VariableType.QUICK_MENU_CATEGORY_NONE,""),
    MG_NOTICE01("MG_NOTICE01", MyGNotiActivity.class, VariableType.QUICK_MENU_CODE_NV, "공지사항",VariableType.QUICK_MENU_CATEGORY_MYG,""),
    MG_MENU01("MG_MENU01", MyGTerms1000Activity.class, VariableType.QUICK_MENU_CODE_NV, "이용약관",VariableType.QUICK_MENU_CATEGORY_MYG,""),
    MG_MENU02("MG_MENU02", MyGTerms2000Activity.class, VariableType.QUICK_MENU_CODE_NV, "개인정보처리방침",VariableType.QUICK_MENU_CATEGORY_MYG,""),
    MG_MENU03("MG_MENU03", MyGTermsActivity.class, VariableType.QUICK_MENU_CODE_NV, "오픈소스 라이선스",VariableType.QUICK_MENU_CATEGORY_MYG,""),
    MG_MENU04("MG_MENU04", MyGTerms0013Activity.class, VariableType.QUICK_MENU_CODE_NV, "개인정보 수집·이용 약관",VariableType.QUICK_MENU_CATEGORY_MYG,""),
    MG_VERSION01("MG_VERSION01", MyGVersioniActivity.class, VariableType.QUICK_MENU_CODE_NV, "버전 정보",VariableType.QUICK_MENU_CATEGORY_MYG,"");

    private String id;
    private Class activity;
    private int quickLevel;
    private int category;
    private String description;
    private String evCd;

    APPIAInfo(String id, Class activity, int quickLevel, String description, int category, String evCd) {
        this.id = id;
        this.activity = activity;
        this.quickLevel = quickLevel;
        this.description = description;
        this.category = category;
        this.evCd = evCd;
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

    public static List<MenuVO> getQuickMenus(final int limit, final String evCd) {
        List<APPIAInfo> menuList = Arrays.asList(APPIAInfo.values()).stream()
                .filter(data -> data.quickLevel>VariableType.QUICK_MENU_CODE_NONE&&data.quickLevel<=limit&&(data.evCd.equalsIgnoreCase("")||data.evCd.equalsIgnoreCase(evCd)))
                .sorted(Comparator.comparing(APPIAInfo::getCategory).thenComparing(APPIAInfo::getDescription))
                .collect(Collectors.toList());
        List<MenuVO> list = new ArrayList<>();
        boolean[] isCategory={false, false, false, false};
        for(APPIAInfo menu : menuList){
            MenuVO menuVO = new MenuVO();
            menuVO.setCode(menu.getId());
            menuVO.setName(menu.getDescription());
            menuVO.setActivity(menu.getActivity());

            try {
                if (!isCategory[menu.getCategory()]) {
                    switch (menu.getCategory()) {
                        case VariableType.QUICK_MENU_CATEGORY_HOME:
                            menuVO.setCategory("HOME");
                            break;
                        case VariableType.QUICK_MENU_CATEGORY_INSIGHT:
                            menuVO.setCategory("INSIGHT");
                            break;
                        case VariableType.QUICK_MENU_CATEGORY_SERVICE:
                            menuVO.setCategory("SERVICE");
                            break;
                        case VariableType.QUICK_MENU_CATEGORY_MYG:
                            menuVO.setCategory("마이 페이지");
                            break;
                    }
                    isCategory[menu.getCategory()] = true;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
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

    public static APPIAInfo findCode(Class activity){
        return Arrays.asList(APPIAInfo.values()).stream().filter(data->data.getActivity()==activity).findAny().orElse(DEFAULT);
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getEvCd() {
        return evCd;
    }

    public void setEvCd(String evCd) {
        this.evCd = evCd;
    }
}
