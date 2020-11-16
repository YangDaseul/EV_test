package com.genesis.apps.comm.model.api;

import com.genesis.apps.comm.net.HttpRequest;

public enum APIInfo {

    GRA_CMN_0001("인트로", "CMN-0001", HttpRequest.METHOD_POST, "/graapi/nl/cmn/intro.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_CMN_0002("컨텐츠다운로드", "CMN-0002", HttpRequest.METHOD_POST, "/graapi/nl/cmn/contentDn.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_CMN_0003("서비스약관조회", "CMN-0003", HttpRequest.METHOD_POST, "/graapi/nl/cmn/svcTerms.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_CMN_0004("서비스 약관 상세조회", "CMN-0004", HttpRequest.METHOD_POST, "/graapi/nl/cmn/svcTermsDetail.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_MBR_0001("서비스가입요청", "MBR-0001", HttpRequest.METHOD_POST, "/graapi/nl/mbr/svcJoin.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_LGN_0001("로그인+서비스 로그인/접속", "LGN-0001", HttpRequest.METHOD_POST, "/graapi/nl/lgn/svcLogin.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_LGN_0002("로그인+Main 화면 주행거리", "LGN-0002", HttpRequest.METHOD_POST, "/graapi/lgn/mainDrivDistInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_LGN_0003("로그인+Main 화면 정비 현황", "LGN-0003", HttpRequest.METHOD_POST, "/graapi/lgn/mainMaintainInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_LGN_0004("PUSH ID 변경 요청", "LGN-0004", HttpRequest.METHOD_POST, "/graapi/lgn/updPushId.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_LGN_0005("로그인+날씨예보", "LGN-0005", HttpRequest.METHOD_POST, "/graapi/nl/lgn/weatherInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_LGN_0006("로그인+퀵메뉴", "LGN-0006", HttpRequest.METHOD_POST, "/graapi/nl/lgn/quickMenu.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_LGN_0007("로그인+ 토픽", "LGN-0007", HttpRequest.METHOD_POST, "/graapi/lgn/topic.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_MYP_0001("계정 정보 요청", "MYP-0001", HttpRequest.METHOD_POST, "/graapi/myp/acctInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_MYP_0004("마케팅 수신동의 변경 요청", "MYP-0004", HttpRequest.METHOD_POST, "/graapi/myp/mrktAgree.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_MYP_0005("휴대폰 번호 변경 요청", "MYP-0005", HttpRequest.METHOD_POST, "/graapi/myp/celphNoReg.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_MYP_1003("MyG+ 블루멤버스 정보", "MYP-1003", HttpRequest.METHOD_POST, "/graapi/myg/blueMbr.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_MYP_1004("MyG+ 혜택/쿠폰", "MYP-1004", HttpRequest.METHOD_POST, "/graapi/myg/coupon.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_MYP_1005("MyG+ 프리빌리지", "MYP-1005", HttpRequest.METHOD_POST, "/graapi/myg/privilege.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_MYP_1006("MyG+ 정유사 포인트", "MYP-1006", HttpRequest.METHOD_POST, "/graapi/myg/oilPoint.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_MYP_1007("MyG+ 스토어", "MYP-1007", HttpRequest.METHOD_POST, "/graapi/myg/store.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_MYP_2001("MyG+ 멤버쉽정보(사용가능포인트, 소멸예정포인트,보유카드수)", "MYP-2001", HttpRequest.METHOD_POST, "/graapi/myp/mbrshInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_MYP_2002("MyG+ 멤버쉽 포인트 사용내역", "MYP-2002", HttpRequest.METHOD_POST, "/graapi/myp/mbrshPontUseHist.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_MYP_2003("MyG+ 포인트 사용내역처 안내", "MYP-2003", HttpRequest.METHOD_POST, "/graapi/myp/pontUseInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_MYP_2004("MyG+ 멤버쉽 카드 안내 요청", "MYP-2004", HttpRequest.METHOD_POST, "/graapi/myp/mbrshCardInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_MYP_2005("MyG+ 카드비밀번호 변경", "MYP-2005", HttpRequest.METHOD_POST, "/graapi/myp/uptCardPwd.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_MYP_2006("MyG+ 소멸예정 포인트내역", "MYP-2006", HttpRequest.METHOD_POST, "/graapi/myp/pontToBeExtncDtl.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),


    GRA_MYP_8001("MyG+ 이용약관", "MYP-8001", HttpRequest.METHOD_POST, "/graapi/myp/trmsInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
//    GRA_MYP_8002("MyG+ 개인정보처리방침", "MYP-8002", HttpRequest.METHOD_POST, "/graapi/myp/privacyPolicy.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
//    GRA_MYP_8003("MyG+ 오픈소스라이센스", "MYP-8003", HttpRequest.METHOD_POST, "/graapi/myp/openLicense.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_MYP_8004("MyG+ 버전정보", "MYP-8004", HttpRequest.METHOD_POST, "/graapi/myp/verInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_MYP_8005("MyG+ 공지사항", "MYP-8005", HttpRequest.METHOD_POST, "/graapi/myp/notiList.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_NOT_0001("알림센터+알림목록 조회", "NOT-0001", HttpRequest.METHOD_POST, "/graapi/lgn/notiInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_NOT_0002("알림센터+알림내용 읽기", "NOT-0002", HttpRequest.METHOD_POST, "/graapi/lgn/readNoti.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_NOT_0003("알림센터+ 신규알림 목록수", "NOT-0003", HttpRequest.METHOD_POST, "/graapi/not/newNoti.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_OIL_0001("MyG+ 정유사 약관요청", "OIL-0001", HttpRequest.METHOD_POST, "/graapi/myp/oilSvcTermsList.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_OIL_0002("MyG+ 정유사 연동요청", "OIL-0002", HttpRequest.METHOD_POST, "/graapi/myp/oilCompLinkConn.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_OIL_0003("MyG+ 정유사 연동해제", "OIL-0003", HttpRequest.METHOD_POST, "/graapi/myp/oilCompLinkClose.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_OIL_0004("MyG+ 정유사 약관 상세", "OIL-0004", HttpRequest.METHOD_POST, "/graapi/myp/oilSvcTermDetail.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_OIL_0005("MyG+ 정유사  재연동요청", "OIL-0005", HttpRequest.METHOD_POST, "/graapi/myp/oilCompLinkReconn.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_BAR_1001("바코드 (카드리스트)", "BAR-1001", HttpRequest.METHOD_POST, "/graapi/bar/barList.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_GNS_1001("My차고 + 차량목록조회", "GNS-1001", HttpRequest.METHOD_POST, "/graapi/gns/myVinSerach.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_GNS_1002("My차고 + 차량번호수정", "GNS-1002", HttpRequest.METHOD_POST, "/graapi/gns/myVinUpdate.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_GNS_1003("My차고 + 차량삭제", "GNS-1003", HttpRequest.METHOD_POST, "/graapi/gns/myVinDelete.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_GNS_1004("My차고 + 주차량 지정", "GNS-1004", HttpRequest.METHOD_POST, "/graapi/gns/myVinDesignate.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_GNS_1005("My차고 + 복원하기", "GNS-1005", HttpRequest.METHOD_POST, "/graapi/gns/myVinRestore.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_GNS_1006("My차고 + 렌트/리스 실운행자 신청", "GNS-1006", HttpRequest.METHOD_POST, "/graapi/gns/myRentRegistration.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_GNS_1007("My차고 + 렌트/리스 실운행자 내역", "GNS-1007", HttpRequest.METHOD_POST, "/graapi/gns/myRentDetail.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_GNS_1008("My차고 + 렌트/리스 계약서이미지 등록", "GNS-1008", HttpRequest.METHOD_POST, "/graapi/gns/myRentContractImg.do", HttpRequest.CONTENT_TYPE_MULTIPART2, HttpRequest.CHARSET_UTF8),
    GRA_GNS_1009("My차고 + 렌트/리스 재직증명서 이미지 등록", "GNS-1009", HttpRequest.METHOD_POST, "/graapi/gns/myRentCertificateImg.do", HttpRequest.CONTENT_TYPE_MULTIPART2, HttpRequest.CHARSET_UTF8),
    GRA_GNS_1010("My차고 + 모빌리티케어 쿠폰", "GNS-1010", HttpRequest.METHOD_POST, "/graapi/gns/myRentMobilityCoupon.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_GNS_1011("My차고 + 렌트/리스 실운행자 확인", "GNS-1011", HttpRequest.METHOD_POST, "/graapi/gns/myRentOwnerConfirm.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_GNS_1012("My차고 + 렌트/리스 실운행자 상세", "GNS-1012", HttpRequest.METHOD_POST, "/graapi/gns/myRentOwnerDetail.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_GNS_1013("My차고 + 렌트/리스 수정하기", "GNS-1013", HttpRequest.METHOD_POST, "/graapi/gns/myRentUpdate.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_GNS_1014("My차고 + 렌트/리스 신청취소", "GNS-1014", HttpRequest.METHOD_POST, "/graapi/gns/myRentCancel.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_GNS_1015("My차고 + 렌트/리스 재등록", "GNS-1015", HttpRequest.METHOD_POST, "/graapi/gns/myRentRetry.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_SOS_1001("Genesis + 긴급출동 접수현황 체크", "SOS-1001", HttpRequest.METHOD_POST, "/graapi/sos/receStatus.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_SOS_1002("Genesis + 긴급출동 신청", "SOS-1002", HttpRequest.METHOD_POST, "/graapi/sos/receReg.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_SOS_1003("Genesis + 추가 비용 안내", "SOS-1003", HttpRequest.METHOD_POST, "/graapi/sos/receGuide.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_SOS_1004("Genesis + 신청 취소", "SOS-1004", HttpRequest.METHOD_POST, "/graapi/sos/receCancel.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_SOS_1005("Genesis + 신청 상태 확인", "SOS-1005", HttpRequest.METHOD_POST, "/graapi/sos/receDtStatus.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_SOS_1006("Genesis + 긴급출동 현황(위치)", "SOS-1006", HttpRequest.METHOD_POST, "/graapi/sos/receLocation.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),


    GRA_VOC_1001("Genesis + 하자재발 차대번호 조회", "VOC-1001", HttpRequest.METHOD_POST, "/graapi/voc/vinSearch.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_VOC_1002("Genesis + 하자재발 신청", "VOC-1002", HttpRequest.METHOD_POST, "/graapi/voc/defectRegistration.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_VOC_1003("Genesis + 하자재발 내역(목록)", "VOC-1003", HttpRequest.METHOD_POST, "/graapi/voc/defectStatus.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_VOC_1004("Genesis + 하자재발 약관조회", "VOC-1004", HttpRequest.METHOD_POST, "/graapi/voc/termSearch.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_VOC_1005("Genesis + 하자재발 약관상세", "VOC-1005", HttpRequest.METHOD_POST, "/graapi/voc/termDetail.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_WRT_1001("Genesis +보증수리안내", "WRT-1001", HttpRequest.METHOD_POST, "/graapi/wrt/guide.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_CTT_1001("contents + 컨텐츠조회", "CTT-1001", HttpRequest.METHOD_POST, "/graapi/nl/ctt/contentsLookup.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_CTT_1002("contents + 컨텐츠평가", "CTT-1002", HttpRequest.METHOD_POST, "/graapi/ctt/contentsEvaluation.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_CTT_1004("contents + 컨텐츠 상세", "CTT-1004", HttpRequest.METHOD_POST, "/graapi/nl/ctt/contentsDetail.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_BTR_1001("Genesis + 버틀러 조회", "BTR-1001", HttpRequest.METHOD_POST, "/graapi/btr/btlrInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_BTR_1008("Genesis + 블루핸즈 검색요청", "BTR-1008", HttpRequest.METHOD_POST, "/graapi/btr/blehdList.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_BTR_1009("Genesis + 블루핸즈 변경/신청 요청", "BTR-1009", HttpRequest.METHOD_POST, "/graapi/btr/blehdUpdt.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_BTR_1010("Genesis + 버틀러 안내조회", "BTR-1010", HttpRequest.METHOD_POST, "/graapi/btr//btlrGuide.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_BTR_2001("Genesis + 상담유형 및 카테고리 작성", "BTR-2001", HttpRequest.METHOD_POST, "/graapi/btr/cdValInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_BTR_2002("Genesis + 상담문의 작성", "BTR-2002", HttpRequest.METHOD_POST, "/graapi/btr/gnsBtrCslt.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_BTR_2003("Genesis + 이력 조회", "BTR-2003", HttpRequest.METHOD_POST, "/graapi/btr/gnsBtrCsltBkgd.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),


    GRA_CBK_1001("insight + 차계부 차량조회", "CBK-1001", HttpRequest.METHOD_POST, "/graapi/cbk/carbkSearch.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_CBK_1002("insight + 차계부 조회", "CBK-1002", HttpRequest.METHOD_POST, "/graapi/cbk/carbkInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_CBK_1005("insight + 최근누적거리요청", "CBK-1005", HttpRequest.METHOD_POST, "/graapi/cbk/accmMilgInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_CBK_1006("insight + 차계부 등록", "CBK-1006", HttpRequest.METHOD_POST, "/graapi/cbk/carbkRgstr.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_CBK_1007("insight + 차계부 삭제", "CBK-1007", HttpRequest.METHOD_POST, "/graapi/cbk/carbkDel.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_CBK_1008("insight + 차계부 수정", "CBK-1008", HttpRequest.METHOD_POST, "/graapi/cbk/carbkUpd.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),


    GRA_REQ_1001("service + 서비스메인 - 정비상태", "REQ-1001", HttpRequest.METHOD_POST, "/graapi/req/svcMain.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_REQ_1002("service + 서비스메인 - 네트워크 찾기", "REQ-1002", HttpRequest.METHOD_POST, "/graapi/req/svcFind.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_REQ_1003("service + 서비스메인 - 정비내용조회(업체선택)", "REQ-1003", HttpRequest.METHOD_POST, "/graapi/req/rparTypList.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_REQ_1004("service + 정비예약 - 정비내용조회(업체미선택)", "REQ-1004", HttpRequest.METHOD_POST, "/graapi/req/rparTypDbList.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_REQ_1005("service + 정비예약 - 정비1단계", "REQ-1005", HttpRequest.METHOD_POST, "/graapi/req/rparChkStaus.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_REQ_1007("service + 정비예약 - 오토케어 예약신청", "REQ-1007", HttpRequest.METHOD_POST, "/graapi/req/autoReserv.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_REQ_1008("service + 정비예약 - 홈투홈 예약가능일", "REQ-1008", HttpRequest.METHOD_POST, "/graapi/req/availBookHome.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_REQ_1009("service + 정비예약 - 홈투홈 예약신청", "REQ-1009", HttpRequest.METHOD_POST, "/graapi/req/subsBookHome.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_REQ_1010("service + 정비예약 - 정비소 예약가능일", "REQ-1010", HttpRequest.METHOD_POST, "/graapi/req/availBookAsn.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_REQ_1011("service + 정비예약 - 정비소 정비만 조회", "REQ-1011", HttpRequest.METHOD_POST, "/graapi/req/searchAsnDept.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_REQ_1012("service + 정비예약 - 정비소 예약신청", "REQ-1012", HttpRequest.METHOD_POST, "/graapi/req/subsBookAsn.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_REQ_1013("service + 정비예약 현황", "REQ-1013", HttpRequest.METHOD_POST, "/graapi/req/rparStatus.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_REQ_1014("service + 정비 이력", "REQ-1014", HttpRequest.METHOD_POST, "/graapi/req/rparHist.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_REQ_1015("service + 예약 취소", "REQ-1015", HttpRequest.METHOD_POST, "/graapi/req/reservCancel.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),

    GRA_PUB_1001("Home + 우편번호 조회", "PUB-1001", HttpRequest.METHOD_POST, "/graapi/pub/epost.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_PUB_1002("Home + 시도조회", "PUB-1002", HttpRequest.METHOD_POST, "/graapi/pub/sido.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_PUB_1003("Home + 구군조회", "PUB-1003", HttpRequest.METHOD_POST, "/graapi/pub/gugun.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),


    GRA_STO_1001("Genesis + 유사재고차량", "STO-1001", HttpRequest.METHOD_POST, "/graapi/sto/smlrStckVhcl.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_STO_1002("Genesis + BTO 웹뷰", "STO-1002", HttpRequest.METHOD_POST, "/graapi/nl/sto/btoWebview.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),




    GRA_DDS_1001("Genesis + 대리운전 진행상태 확인", "DDS-1001", HttpRequest.METHOD_POST, "/graapi/dds/ddsProgsCheck.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_DDS_1002("Genesis + 대리운전 신청 정보 등록", "DDS-1002", HttpRequest.METHOD_POST, "/graapi/dds/ddsInfoRgist.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_DDS_1003("Genesis + 대리운전 신청 내용 리스트", "DDS-1003", HttpRequest.METHOD_POST, "/graapi/dds/ddsRgistInfoList.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_DDS_1004("Genesis + 대리운전 취소 요청", "DDS-1004", HttpRequest.METHOD_POST, "/graapi/dds/ddsCnclReqst.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_DDS_1005("Genesis + 대리운전 평가 등록", "DDS-1005", HttpRequest.METHOD_POST, "/graapi/dds/ddsCommentRgist.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_DDS_1006("Genesis + 수동 배정 요청", "DDS-1006", HttpRequest.METHOD_POST, "/graapi/dds/ddsAssignReqst.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),


    ROADWIN_SERVICE_AREA_CHECK("서비스 가능 지역 확인", "", HttpRequest.METHOD_GET, "/serviceAreaCheck.php", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    ROADWIN_WORK("기사 위치 확인", "", HttpRequest.METHOD_GET, "/work/w.php", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    ROADWIN_CHECK_PRICE("가격 정보 확인", "", HttpRequest.METHOD_GET, "/checkPrice.php", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),


    GRA_IST_1001("홈상단 영역", "IST-1001", HttpRequest.METHOD_POST, "/graapi/nl/ist/homeTopInsgt.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_IST_1002("인사이트 – 차계부 인사이트", "IST-1002", HttpRequest.METHOD_POST, "/graapi/ist/inscbk.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_IST_1003("인사이트 – 인사이트 1 영역", "IST-1003", HttpRequest.METHOD_POST, "/graapi/ist/insgt1.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_IST_1004("인사이트 – 인사이트 2 영역", "IST-1004", HttpRequest.METHOD_POST, "/graapi/ist/insgt2.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_IST_1005("인사이트 – 인사이트 3 영역", "IST-1005", HttpRequest.METHOD_POST, "/graapi/ist/insgt3.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),



    GRA_WSH_1001("service + 소낙스 세차이용권 조회", "WSH-1001", HttpRequest.METHOD_POST, "/graapi/nl/wsh/godsInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_WSH_1002("service + 소낙스 세차이용권 선택(지점찾기)", "WSH-1002", HttpRequest.METHOD_POST, "/graapi/wsh/brnhInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_WSH_1003("service + 소낙스 세차예약", "WSH-1003", HttpRequest.METHOD_POST, "/graapi/wsh/makeRsvt.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_WSH_1004("service + 소낙스 세차예약 내역", "WSH-1004", HttpRequest.METHOD_POST, "/graapi/wsh/rsvtInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_WSH_1005("service + 소낙스 직원에게 확인받기", "WSH-1005", HttpRequest.METHOD_POST, "/graapi/wsh/getCfrmByStaff.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_WSH_1006("service + 소낙스 예약취소", "WSH-1006", HttpRequest.METHOD_POST, "/graapi/wsh/cnclRsvt.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_WSH_1007("service + 소낙스 평가지 요청", "WSH-1007", HttpRequest.METHOD_POST, "/graapi/wsh/evlpFormsInfo.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8),
    GRA_WSH_1008("service + 소낙스 평가 요청", "WSH-1008", HttpRequest.METHOD_POST, "/graapi/wsh/rqstEvlp.do", HttpRequest.CONTENT_TYPE_JSON, HttpRequest.CHARSET_UTF8)


    ;


    private String description;
    private String ifCd;
    private String reqType;
    private String URI;
    private String contentsType;
    private String charSet;

    APIInfo(String description, String ifCd, String reqType, String URI, String contentsType, String charSet) {
        this.description = description;
        this.ifCd = ifCd;
        this.reqType = reqType;
        this.URI = URI;
        this.contentsType = contentsType;
        this.charSet = charSet;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIfCd() {
        return ifCd;
    }

    public void setIfCd(String ifCd) {
        this.ifCd = ifCd;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getURI() {
        return URI;
    }

    public void setURI(String URI) {
        this.URI = URI;
    }

    public String getContentsType() {
        return contentsType;
    }

    public void setContentsType(String contentsType) {
        this.contentsType = contentsType;
    }

    public String getCharSet() {
        return charSet;
    }

    public void setCharSet(String charSet) {
        this.charSet = charSet;
    }
//    public static APIInfo findCode(int code){
//        return Arrays.asList(APIInfo.values()).stream().filter(data->data.getCode()==code).findAny().orElse(REQ_CODE_DEFAULT);
//    }

}