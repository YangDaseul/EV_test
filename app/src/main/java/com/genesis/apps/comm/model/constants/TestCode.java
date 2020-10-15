package com.genesis.apps.comm.model.constants;

import com.genesis.apps.comm.model.gra.api.BTR_1001;
import com.genesis.apps.comm.model.gra.api.BTR_1008;
import com.genesis.apps.comm.model.gra.api.BTR_2001;
import com.genesis.apps.comm.model.gra.api.BTR_2002;
import com.genesis.apps.comm.model.gra.api.BTR_2003;
import com.genesis.apps.comm.model.gra.api.CMN_0001;
import com.genesis.apps.comm.model.gra.api.CMN_0002;
import com.genesis.apps.comm.model.gra.api.GNS_1001;
import com.genesis.apps.comm.model.gra.api.GNS_1002;
import com.genesis.apps.comm.model.gra.api.GNS_1003;
import com.genesis.apps.comm.model.gra.api.GNS_1004;
import com.genesis.apps.comm.model.gra.api.GNS_1005;
import com.genesis.apps.comm.model.gra.api.GNS_1010;
import com.genesis.apps.comm.model.gra.api.LGN_0001;
import com.genesis.apps.comm.model.gra.api.LGN_0003;
import com.genesis.apps.comm.model.gra.api.LGN_0005;
import com.genesis.apps.comm.model.gra.api.MYP_1005;
import com.google.gson.Gson;

public class TestCode {

    public static LGN_0001.Response LGN_0001 = new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"성공\",\n" +
            "  \"custNo\": \"0000\",\n" +
            "  \"custGbCd\": \"0000\",\n" +
            "  \"pushIdChgYn\": \"Y\",\n" +
            "  \"custMgmtNo\": \"12345612341\",\n" +
            "  \"custNm\": \"박현준\",\n" +
            "  \"celphNo\": \"01086029612\",\n" +
            "  \"ownVhclList\": [\n" +
            "    {\n" +
            "      \"csmrCarRelCd\": \"1\",\n" +
            "      \"vin\": \"AWJDIWHD234213KJ\",\n" +
            "      \"carRgstNo\": \"16조6840\",\n" +
            "      \"mdlCd\": \"JX\",\n" +
            "      \"mdlNm\": \"GV80\",\n" +
            "      \"saleMdlNm\": \"GV80 디젤 3.0 5인승 19인치 기본디자인 2WD 오토\",\n" +
            "      \"xrclCtyNo\": \"12345678\",\n" +
            "      \"xrclCtyNm\": \"WHITE\",\n" +
            "      \"mainVhclYn\": \"N\",\n" +
            "      \"vhclImgUri\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"csmrCarRelCd\": \"2\",\n" +
            "      \"vin\": \"AWJD3332232323KJ\",\n" +
            "      \"carRgstNo\": \"161조6842\",\n" +
            "      \"mdlCd\": \"JX2\",\n" +
            "      \"mdlNm\": \"GV90\",\n" +
            "      \"saleMdlNm\": \"GV90 디젤 3.0 5인승 19인치 기본디자인 2WD 오토\",\n" +
            "      \"xrclCtyNo\": \"22245678\",\n" +
            "      \"xrclCtyNm\": \"BLACK\",\n" +
            "      \"mainVhclYn\": \"N\",\n" +
            "      \"vhclImgUri\": \"\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"ctrctVhclList\": [\n" +
            "    {\n" +
            "      \"saleMdlCd\": \"AAAAAA\",\n" +
            "      \"mdlCd\": \"JX\",\n" +
            "      \"mdlNm\": \"GV80\",\n" +
            "      \"saleMdlNm\": \"GV80 디젤 3.0 5인승 19인치 기본디자인 2WD 오토\",\n" +
            "      \"xrclCtyNo\": \"12345678\",\n" +
            "      \"xrclCtyNm\": \"WHITE\",\n" +
            "      \"ieclCtyNo\": \"12345678\",\n" +
            "      \"ieclCtyNm\": \"WHITE\",\n" +
            "      \"vhclImgUri\": \"\",\n" +
            "      \"ctrctNo\": \"21234\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"saleMdlCd\": \"AAAAAA\",\n" +
            "      \"mdlCd\": \"JX\",\n" +
            "      \"mdlNm\": \"GV80\",\n" +
            "      \"saleMdlNm\": \"GV80 디젤 3.0 5인승 19인치 기본디자인 2WD 오토\",\n" +
            "      \"xrclCtyNo\": \"12345678\",\n" +
            "      \"xrclCtyNm\": \"WHITE\",\n" +
            "      \"ieclCtyNo\": \"12345678\",\n" +
            "      \"ieclCtyNm\": \"WHITE\",\n" +
            "      \"vhclImgUri\": \"\",\n" +
            "      \"ctrctNo\": \"21234\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"dftVhclInfo\": [\n" +
            "    {\n" +
            "      \"mdlCd\": \"JX\",\n" +
            "      \"mdlNm\": \"GV80\",\n" +
            "      \"saleMdlNm\": \"GV80 디젤 3.0 5인승 19인치 기본디자인 2WD 오토\",\n" +
            "      \"xrclCtyNo\": \"12345678\",\n" +
            "      \"vhclImgUri\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"mdlCd\": \"JX2\",\n" +
            "      \"mdlNm\": \"GV90\",\n" +
            "      \"saleMdlNm\": \"GV90 디젤 3.0 5인승 19인치 기본디자인 2WD 오토\",\n" +
            "      \"xrclCtyNo\": \"99999999\",\n" +
            "      \"vhclImgUri\": \"\"\n" +
            "    }\n" +
            "  ]\n" +
            "}", LGN_0001.Response.class);

    public static LGN_0003.Response LGN_0003 = new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"성공\",\n" +
            "  \"acptNo\": \"A1414\",\n" +
            "  \"asnDt\": \"20201101\",\n" +
            "  \"asnNm\": \"블루핸즈0호점\",\n" +
            "  \"asnStatsNm\": \"차량 정비 중\",\n" +
            "  \"asnStatsCd\": \"1111\",\n" +
            "  \"asnHistList\": [\n" +
            "    {\n" +
            "      \"carRgstNo\": \"16조2841\",\n" +
            "      \"carGbNm\": \"GV80\",\n" +
            "      \"drivDist\": \"100000\",\n" +
            "      \"asnNm\": \"블루핸즈1호점\",\n" +
            "      \"pbzAddr\": \"도봉로1\",\n" +
            "      \"repTn\": \"021111111\",\n" +
            "      \"arrivDt\": \"20201021\",\n" +
            "      \"asnHist\": \"오일교체1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"carRgstNo\": \"16조2842\",\n" +
            "      \"carGbNm\": \"GV81\",\n" +
            "      \"drivDist\": \"100002\",\n" +
            "      \"asnNm\": \"블루핸즈2호점\",\n" +
            "      \"pbzAddr\": \"도봉로2\",\n" +
            "      \"repTn\": \"0222221222\",\n" +
            "      \"arrivDt\": \"20201022\",\n" +
            "      \"asnHist\": \"오일교체2\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"carRgstNo\": \"16조2843\",\n" +
            "      \"carGbNm\": \"GV82\",\n" +
            "      \"drivDist\": \"100003\",\n" +
            "      \"asnNm\": \"블루핸즈3호점\",\n" +
            "      \"pbzAddr\": \"도봉로3\",\n" +
            "      \"repTn\": \"023333333\",\n" +
            "      \"arrivDt\": \"20201023\",\n" +
            "      \"asnHist\": \"오일교체3\"\n" +
            "    }\n" +
            "  ],\n" +
            "  \"butlSubsCd\": \"3000\",\n" +
            "  \"virtRecptNo\": \"1111\"\n" +
            "}", LGN_0003.Response.class);

    public static LGN_0005.Response LGN_0005 = new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"성공\",\n" +
            "  \"fcstDtm\": \"20201008220011\",\n" +
            "  \"nx\": \"37.463936\",\n" +
            "  \"ny\": \"127.042953\",\n" +
            "  \"t1h\": \"10\",\n" +
            "  \"sky\": \"4\",\n" +
            "  \"reh\": \"50\",\n" +
            "  \"pty\": \"1\",\n" +
            "  \"lgt\": \"0\",\n" +
            "  \"dayCd\": \"1\"\n" +
            "}", LGN_0005.Response.class);

//    public static CMN_0001.Response CMN_0001 = new Gson().fromJson("{\n" +
//            "  \"rtCd\": \"0000\",\n" +
//            "  \"rtMsg\": \"Success\",\n" +
//            "  \"appVer\": \"01.00.00\",\n" +
//            "  \"appUpdType\": \"X\",\n" +
//            "  \"notiDt\": \"20200910\",\n" +
//            "  \"notiList\": [\n" +
//            "    {\n" +
//            "      \"notiCd\": \"ANNC\",\n" +
//            "      \"seqNo\": \"2020091000000015\",\n" +
//            "      \"notiTtl\": \"필독공지1\",\n" +
//            "      \"notiCont\": \"필독공지내용1\"\n" +
//            "    },\n" +
//            "    {\n" +
//            "      \"notiCd\": \"NOTI\",\n" +
//            "      \"seqNo\": \"2020091000000014\",\n" +
//            "      \"notiTtl\": \"일반공지1\",\n" +
//            "      \"notiCont\": \"일반공지내용1\"\n" +
//            "    },\n" +
//            "    {\n" +
//            "      \"notiCd\": \"ANNC\",\n" +
//            "      \"seqNo\": \"2020091000000013\",\n" +
//            "      \"notiTtl\": \"긴급공지1\",\n" +
//            "      \"notiCont\": \"긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용2\"\n" +
//            "    }\n" +
//            "  ]\n" +
//            "}", CMN_0001.Response.class);

    public static CMN_0001.Response CMN_0001 = new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"Success\",\n" +
            "  \"appVer\": \"01.00.00\",\n" +
            "  \"appUpdType\": \"X\",\n" +
            "  \"notiDt\": \"20200910\"" +
            "}", CMN_0001.Response.class);

    public static CMN_0002.Response CMN_0002 = new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"Success\",\n" +
            "  \"menu0000\": {\n" +
            "    \"menuList\": [\n" +
            "      {\n" +
            "        \"menuId\": \"GM04\",\n" +
            "        \"upMenuId\": \"GM04\",\n" +
            "        \"menuNm\": \"HOME (비로그인)\",\n" +
            "        \"menuTypCd\": \"NA\",\n" +
            "        \"scrnTypCd\": \"PG\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"qckMenuList\": []\n" +
            "  },\n" +
            "  \"menuOV\": {\n" +
            "    \"menuList\": [\n" +
            "      {\n" +
            "        \"menuId\": \"GM01\",\n" +
            "        \"upMenuId\": \"GM01\",\n" +
            "        \"menuNm\": \"HOME (로그인/차량보유)\",\n" +
            "        \"menuTypCd\": \"NA\",\n" +
            "        \"scrnTypCd\": \"PG\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"menuId\": \"INT01\",\n" +
            "        \"upMenuId\": \"INT01\",\n" +
            "        \"menuNm\": \"스플래시\",\n" +
            "        \"menuTypCd\": \"NA\",\n" +
            "        \"scrnTypCd\": \"PG\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"qckMenuList\": [\n" +
            "      {\n" +
            "        \"menuId\": \"MENU0001\",\n" +
            "        \"menuNm\": \"메뉴0001\",\n" +
            "        \"qckMenuDivCd\": \"IM\",\n" +
            "        \"wvYn\": \"N\",\n" +
            "        \"nttOrd\": \"1\"\n" +
            "      },\n" +
            "      {\n" +
            "        \"menuId\": \"MENU0002\",\n" +
            "        \"menuNm\": \"메뉴0002\",\n" +
            "        \"qckMenuDivCd\": \"OM\",\n" +
            "        \"wvYn\": \"Y\",\n" +
            "        \"lnkUri\": \"http://www.genesis.com/priv?id=G000001\",\n" +
            "        \"nttOrd\": \"2\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  \"menuCV\": {\n" +
            "    \"menuList\": [\n" +
            "      {\n" +
            "        \"menuId\": \"GM02\",\n" +
            "        \"upMenuId\": \"GM02\",\n" +
            "        \"menuNm\": \"HOME (로그인/예약대기)\",\n" +
            "        \"menuTypCd\": \"NA\",\n" +
            "        \"scrnTypCd\": \"PG\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"qckMenuList\": [\n" +
            "      {\n" +
            "        \"menuId\": \"MENU0001\",\n" +
            "        \"menuNm\": \"메뉴0001\",\n" +
            "        \"qckMenuDivCd\": \"IM\",\n" +
            "        \"wvYn\": \"N\",\n" +
            "        \"nttOrd\": \"1\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  \"menuNV\": {\n" +
            "    \"menuList\": [\n" +
            "      {\n" +
            "        \"menuId\": \"GM03\",\n" +
            "        \"upMenuId\": \"GM03\",\n" +
            "        \"menuNm\": \"HOME (로그인/차량미보유)\",\n" +
            "        \"menuTypCd\": \"NA\",\n" +
            "        \"scrnTypCd\": \"PG\"\n" +
            "      }\n" +
            "    ],\n" +
            "    \"qckMenuList\": [\n" +
            "      {\n" +
            "        \"menuId\": \"MENU0001\",\n" +
            "        \"menuNm\": \"메뉴0001\",\n" +
            "        \"qckMenuDivCd\": \"IM\",\n" +
            "        \"wvYn\": \"N\",\n" +
            "        \"nttOrd\": \"1\"\n" +
            "      }\n" +
            "    ]\n" +
            "  },\n" +
            "  \"wthrInsgtList\": [\n" +
            "    {\n" +
            "      \"wthrCd\": \"SKY_1\",\n" +
            "      \"msgTypCd\": \"TXT\",\n" +
            "      \"txtMsg\": \"나들이 가기\n좋은 날씨네요.\",\n" +
            "      \"lnkUseYn\": \"N\"\n" +
            "    },\n" +

            "    {\n" +
            "      \"wthrCd\": \"SKY_1\",\n" +
            "      \"msgTypCd\": \"TXT\",\n" +
            "      \"txtMsg\": \"드라이브하기\n좋은 날씨네요.\",\n" +
            "      \"lnkUseYn\": \"N\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"wthrCd\": \"PTY_145\",\n" +
            "      \"msgTypCd\": \"TXT\",\n" +
            "      \"txtMsg\": \"우산은 챙기셨나요?\n빗길 운전 조심하세요\",\n" +
            "      \"lnkUseYn\": \"N\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"wthrCd\": \"PTY_145\",\n" +
            "      \"msgTypCd\": \"TXT\",\n" +
            "      \"txtMsg\": \"차안에서 빗소리를 들을수 있겠네요.\n빗길운전 조심하세요!\",\n" +
            "      \"lnkUseYn\": \"N\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"wthrCd\": \"PTY_145\",\n" +
            "      \"msgTypCd\": \"TXT\",\n" +
            "      \"txtMsg\": \"세차는 다음에 하세요,\n비오는 날 드라이브도 운치 있어요\",\n" +
            "      \"lnkUseYn\": \"N\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"wthrCd\": \"SKY_1\",\n" +
            "      \"msgTypCd\": \"TXT\",\n" +
            "      \"txtMsg\": \"맑고 화창한\n날씨입니다.\",\n" +
            "      \"lnkUseYn\": \"N\"\n" +
            "    }\n" +
            "  ]\n" +
            "}",CMN_0002.Response.class);


    public static GNS_1001.Response GNS_1001 = new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"성공\",\n" +
            "  \"actoprRgstYn\": \"Y\",\n" +
            "  \"ownVhclCnt\": \"3\",\n" +
            "  \"ownVhclList\": [\n" +
            "    {\n" +
            "      \"vin\": \"V141548641564\",\n" +
            "      \"carRgstNo\": \"16조6840\",\n" +
            "      \"mainVhclYn\": \"N\",\n" +
            "      \"delYn\": \"N\",\n" +
            "      \"csmrCarRelCd\": \"1\",\n" +
            "      \"mdlCd\": \"JX1\",\n" +
            "      \"mdlNm\": \"GV80\",\n" +
            "      \"saleMdlNm\": \"GV80 디젤 3.0 5인승 19인치 기본디자인 2WD 오토\",\n" +
            "      \"xrclCtyNo\": \"UYH\",\n" +
            "      \"xrclCtyNm\": \"??\",\n" +
            "      \"usedCarYn\": \"N\",\n" +
            "      \"delExpYn\": \"N\",\n" +
            "      \"delExpDay\": \"\",\n" +
            "      \"vhclImgUri\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"vin\": \"V141548641564\",\n" +
            "      \"carRgstNo\": \"16조6840\",\n" +
            "      \"mainVhclYn\": \"Y\",\n" +
            "      \"delYn\": \"N\",\n" +
            "      \"csmrCarRelCd\": \"1\",\n" +
            "      \"mdlCd\": \"JX2\",\n" +
            "      \"mdlNm\": \"GV82\",\n" +
            "      \"saleMdlNm\": \"GV82 디젤 3.0 5인승 19인치 기본디자인 2WD 오토\",\n" +
            "      \"xrclCtyNo\": \"UYH2\",\n" +
            "      \"xrclCtyNm\": \"??\",\n" +
            "      \"usedCarYn\": \"N\",\n" +
            "      \"delExpYn\": \"N\",\n" +
            "      \"delExpDay\": \"\",\n" +
            "      \"vhclImgUri\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"vin\": \"V141548641534\",\n" +
            "      \"carRgstNo\": \"16조6843\",\n" +
            "      \"mainVhclYn\": \"N\",\n" +
            "      \"delYn\": \"N\",\n" +
            "      \"csmrCarRelCd\": \"1\",\n" +
            "      \"mdlCd\": \"JX3\",\n" +
            "      \"mdlNm\": \"GV83\",\n" +
            "      \"saleMdlNm\": \"GV83 디젤 3.0 5인승 19인치 기본디자인 2WD 오토\",\n" +
            "      \"xrclCtyNo\": \"UYH2\",\n" +
            "      \"xrclCtyNm\": \"??\",\n" +
            "      \"usedCarYn\": \"N\",\n" +
            "      \"delExpYn\": \"Y\",\n" +
            "      \"delExpDay\": \"3\",\n" +
            "      \"vhclImgUri\": \"\"\n" +
            "    }\n" +
            "  ]\n" +
            "}",GNS_1001.Response.class);

    public static GNS_1004.Response GNS_1004 = new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"성공\"\n" +
            "}",GNS_1004.Response.class);

    public static GNS_1002.Response GNS_1002 = new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"성공\"\n" +
            "}",GNS_1002.Response.class);


    public static GNS_1003.Response GNS_1003 = new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"성공\"\n" +
            "}",GNS_1003.Response.class);


    public static GNS_1005.Response GNS_1005 = new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"성공\"\n" +
            "}",GNS_1005.Response.class);

    public static GNS_1010.Response GNS_1010 = new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"성공\",\n" +
            "  \"cpnList\": [\n" +
            "    {\n" +
            "      \"itemDivCd\": \"11\",\n" +
            "      \"itemNm\": \"\",\n" +
            "      \"remCnt\": \"1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"itemDivCd\": \"13\",\n" +
            "      \"itemNm\": \"\",\n" +
            "      \"remCnt\": \"2\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"itemDivCd\": \"32\",\n" +
            "      \"itemNm\": \"\",\n" +
            "      \"remCnt\": \"3\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"itemDivCd\": \"33\",\n" +
            "      \"itemNm\": \"\",\n" +
            "      \"remCnt\": \"4\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"itemDivCd\": \"34\",\n" +
            "      \"itemNm\": \"브레이크오일343434\",\n" +
            "      \"remCnt\": \"5\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"itemDivCd\": \"61\",\n" +
            "      \"itemNm\": \"\",\n" +
            "      \"remCnt\": \"6\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"itemDivCd\": \"99\",\n" +
            "      \"itemNm\": \"\",\n" +
            "      \"remCnt\": \"7\"\n" +
            "    }\n" +
            "  ]\n" +
            "}\n",GNS_1010.Response.class);


    public static MYP_1005.Response MYP_1005 = new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"성공\",\n" +
            "  \"mbrshJoinYn\": \"Y\",\n" +
            "  \"pvilList\": [\n" +
            "    {\n" +
            "      \"vin\": \"111111111\",\n" +
            "      \"carRgstNo\": \"16조68404\",\n" +
            "      \"mdlCd\": \"GV80\",\n" +
            "      \"mdlNm\": \"가솔린1111111\",\n" +
            "      \"saleMdlNm\": \"디젤가솔린1111111\",\n" +
            "      \"xrclCtyNo\": \"\",\n" +
            "      \"xrclCtyNm\": \"\",\n" +
            "      \"joinPsblCd\": \"0\",\n" +
            "      \"stusCd\": \"0000\",\n" +
            "      \"stusMsg\": \"정상\",\n" +
            "      \"serviceUrl\": \"\",\n" +
            "      \"serviceDetailUrl\": \"\",\n" +
            "      \"joinPsblCd\": \"0\",\n" +
            "      \"joinPsblCd\": \"0\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"vin\": \"2222222\",\n" +
            "      \"carRgstNo\": \"16조6842\",\n" +
            "      \"mdlCd\": \"GV80\",\n" +
            "      \"mdlNm\": \"가솔린22222\",\n" +
            "      \"saleMdlNm\": \"디젤가솔린22222\",\n" +
            "      \"xrclCtyNo\": \"\",\n" +
            "      \"xrclCtyNm\": \"\",\n" +
            "      \"joinPsblCd\": \"0\",\n" +
            "      \"stusCd\": \"0000\",\n" +
            "      \"stusMsg\": \"정상\",\n" +
            "      \"serviceUrl\": \"\",\n" +
            "      \"serviceDetailUrl\": \"\",\n" +
            "      \"joinPsblCd\": \"0\",\n" +
            "      \"joinPsblCd\": \"0\"\n" +
            "    }\n" +
            "  ]\n" +
            "}\n", MYP_1005.Response.class);


    public static BTR_1001.Response BTR_1001 = new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"Success\",\n" +
            "  \"vin\": \"VIN_000001\",\n" +
            "  \"custMgmtNo\": \"CSMR_000001\",\n" +
            "  \"asnCd\": \"블루핸즈 의왕\",\n" +
            "  \"asnNm\": \"블루핸즈 의왕\",\n" +
            "  \"repTn\": \"02-112-1124\",\n" +
            "  \"pbzAdr\": \"인천광역시부평구\",\n" +
            "  \"mapXcooNm\": \"37.463936\",\n" +
            "  \"mapYcooNm\": \"127.042953\",\n" +
            "  \"btlrNm\": \"박문수\",\n" +
            "  \"celphNo\": \"01022223333\",\n" +
            "  \"bltrChgYn\": \"C\",\n" +
            "  \"cnsltBdgYn\": \"N\"\n" +
            "}", BTR_1001.Response.class);

    public static BTR_2001.Response BTR_2001_1= new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"Success\",\n" +
            "  \"cdReqCd\": \"CNSL\",\n" +
            "  \"cdList\": [\n" +
            "    {\n" +
            "      \"cdValId\": \"1\",\n" +
            "      \"cdValNm\": \"문의\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"cdValId\": \"2\",\n" +
            "      \"cdValNm\": \"칭찬\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"cdValId\": \"3\",\n" +
            "      \"cdValNm\": \"불만\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"cdValId\": \"4\",\n" +
            "      \"cdValNm\": \"제안\"\n" +
            "    }\n" +
            "  ]\n" +
            "}",BTR_2001.Response.class);


    public static BTR_2001.Response BTR_2001_2= new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"Success\",\n" +
            "  \"cdReqCd\": \"LGCT\",\n" +
            "  \"conslCd\": \"1\",\n" +
            "  \"lgrCatCd\": \"\",\n" +
            "  \"mdlCatCd\": \"\",\n" +
            "  \"cdList\": [\n" +
            "    {\n" +
            "      \"cdValId\": \"1000\",\n" +
            "      \"cdValNm\": \"정비\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"cdValId\": \"2000\",\n" +
            "      \"cdValNm\": \"제네시스멤버십\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"cdValId\": \"3000\",\n" +
            "      \"cdValNm\": \"품질\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"cdValId\": \"4000\",\n" +
            "      \"cdValNm\": \"판매\"\n" +
            "    }\n" +
            "  ]\n" +
            "}",BTR_2001.Response.class);

    public static BTR_2001.Response BTR_2001_3= new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"Success\",\n" +
            "  \"cdReqCd\": \"MDCT\",\n" +
            "  \"conslCd\": \"\",\n" +
            "  \"lgrCatCd\": \"1000\",\n" +
            "  \"mdlCatCd\": \"\",\n" +
            "  \"cdList\": [\n" +
            "    {\n" +
            "      \"cdValId\": \"1001\",\n" +
            "      \"cdValNm\": \"차량수리/관리\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"cdValId\": \"1002\",\n" +
            "      \"cdValNm\": \"예약\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"cdValId\": \"1003\",\n" +
            "      \"cdValNm\": \"정비시설\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"cdValId\": \"1003\",\n" +
            "      \"cdValNm\": \"리콜/사전점검\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"cdValId\": \"1005\",\n" +
            "      \"cdValNm\": \"서비스네트워크\"\n" +
            "    }\n" +
            "  ]\n" +
            "}",BTR_2001.Response.class);


    public static BTR_2001.Response BTR_2001_4= new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"Success\",\n" +
            "  \"cdReqCd\": \"SMCT\",\n" +
            "  \"conslCd\": \"\",\n" +
            "  \"lgrCatCd\": \"\",\n" +
            "  \"mdlCatCd\": \"1001\",\n" +
            "  \"cdList\": [\n" +
            "    {\n" +
            "      \"cdValId\": \"1101\",\n" +
            "      \"cdValNm\": \"보증기간\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"cdValId\": \"1102\",\n" +
            "      \"cdValNm\": \"부품관련\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"cdValId\": \"1103\",\n" +
            "      \"cdValNm\": \"수리비 및 수리기간\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"cdValId\": \"1104\",\n" +
            "      \"cdValNm\": \"점검 및 교환주기\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"cdValId\": \"1105\",\n" +
            "      \"cdValNm\": \"옵션 사용 방법\"\n" +
            "    }\n" +
            "  ]\n" +
            "}",BTR_2001.Response.class);

    public static BTR_2002.Response BTR_2002 = new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"성공\"\n" +
            "}",BTR_2002.Response.class);


    public static BTR_2003.Response BTR_2003 = new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"Success\",\n" +
            "  \"conslList\": [\n" +
            "    {\n" +
            "      \"seqNo\": \"1\",\n" +
            "      \"catNm\": \"문의/제네시스멤버십\",\n" +
            "      \"conslDt\": \"2020.09.23\",\n" +
            "      \"conslTtl\": \"작성\",\n" +
            "      \"conslCont\": \"[전화응답요청]이상없습니다.\",\n" +
            "      \"respDt\": \"2020.10.12\",\n" +
            "      \"respCont\": \"안녕하세요 고객님\\n테스트입니다.22\\n감사합니다.\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"seqNo\": \"2\",\n" +
            "      \"catNm\": \"문의/제네시스멤버십\",\n" +
            "      \"conslDt\": \"2020.09.23\",\n" +
            "      \"conslTtl\": \"작성\",\n" +
            "      \"conslCont\": \"[전화응답요청]이상없습니다.\",\n" +
            "      \"respDt\": \"\",\n" +
            "      \"respCont\": \"안녕하세요 고객님\\n텍스트를 넣었습니다.1\\n감사합니다.\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"seqNo\": \"3\",\n" +
            "      \"catNm\": \"문의/제네시스멤버십\",\n" +
            "      \"conslDt\": \"2020.09.14\",\n" +
            "      \"conslTtl\": \"작성\",\n" +
            "      \"conslCont\": \"[APP응답요청]이상없습니다.\",\n" +
            "      \"respDt\": \"2020.10.12\",\n" +
            "      \"respCont\": \"안녕하세요 고객님\\n테스트입니다.\\n감사합니다.\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"seqNo\": \"4\",\n" +
            "      \"catNm\": \"문의/제네시스멤버십\",\n" +
            "      \"conslDt\": \"2020.09.17\",\n" +
            "      \"conslTtl\": \"작성\",\n" +
            "      \"conslCont\": \"[APP응답요청]이상없습니다.\",\n" +
            "      \"respDt\": \"\",\n" +
            "      \"respCont\": \"\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"seqNo\": \"5\",\n" +
            "      \"catNm\": \"문의/제네시스멤버십\",\n" +
            "      \"conslDt\": \"2020.09.14\",\n" +
            "      \"conslTtl\": \"작성\",\n" +
            "      \"conslCont\": \"[APP응답요청]이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.이상없습니다.\",\n" +
            "      \"respDt\": \"\",\n" +
            "      \"respCont\": \"2020년 09월 14일 에 유선 상담 완료\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"seqNo\": \"6\",\n" +
            "      \"catNm\": \"문의/제네시스멤버십\",\n" +
            "      \"conslDt\": \"2020.09.14\",\n" +
            "      \"conslTtl\": \"작성\",\n" +
            "      \"conslCont\": \"이상없습니다.\",\n" +
            "      \"respDt\": \"\",\n" +
            "      \"respCont\": \"2020년 09월 14일 에 유선 상담 완료\"\n" +
            "    }\n" +
            "  ]\n" +
            "}",BTR_2003.Response.class);



    public static BTR_1008.Response BTR_1008 = new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"Success\",\n" +
            "  \"asnList\": [\n" +
            "    {\n" +
            "      \"asnCd\": \"Z14Z48\",\n" +
            "      \"asnNm\": \"(주)영남중공업\",\n" +
            "      \"repTn\": \"028559966\",\n" +
            "      \"pbzAdr\": \"서울특별시 금천구 시흥대로153길 26 (독산동)\",\n" +
            "      \"mapXcooNm\": \"126.8989417\",\n" +
            "      \"mapYcooNm\": \"37.4745083\",\n" +
            "      \"dist\": \"1.6\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"asnCd\": \"Z14Z47\",\n" +
            "      \"asnNm\": \"(주)현대중공업\",\n" +
            "      \"repTn\": \"020000000\",\n" +
            "      \"pbzAdr\": \"인천광역시 금천구 시흥대로153길 26 (독산동)\",\n" +
            "      \"mapXcooNm\": \"126.8989417\",\n" +
            "      \"mapYcooNm\": \"37.4745083\",\n" +
            "      \"dist\": \"4.6\"\n" +
            "    }\n" +
            "  ]\n" +
            "}",BTR_1008.Response.class);

}
