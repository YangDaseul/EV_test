package com.genesis.apps.comm.model.constants;

import com.genesis.apps.comm.model.gra.api.CMN_0001;
import com.genesis.apps.comm.model.gra.api.CMN_0002;
import com.genesis.apps.comm.model.gra.api.LGN_0001;
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

    public static CMN_0001.Response CMN_0001 = new Gson().fromJson("{\n" +
            "  \"rtCd\": \"0000\",\n" +
            "  \"rtMsg\": \"Success\",\n" +
            "  \"appVer\": \"01.00.00\",\n" +
            "  \"appUpdType\": \"X\",\n" +
            "  \"notiDt\": \"20200910\",\n" +
            "  \"notiList\": [\n" +
            "    {\n" +
            "      \"notiCd\": \"ANNC\",\n" +
            "      \"seqNo\": \"2020091000000015\",\n" +
            "      \"notiTtl\": \"필독공지1\",\n" +
            "      \"notiCont\": \"필독공지내용1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"notiCd\": \"NOTI\",\n" +
            "      \"seqNo\": \"2020091000000014\",\n" +
            "      \"notiTtl\": \"일반공지1\",\n" +
            "      \"notiCont\": \"일반공지내용1\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"notiCd\": \"ANNC\",\n" +
            "      \"seqNo\": \"2020091000000013\",\n" +
            "      \"notiTtl\": \"긴급공지1\",\n" +
            "      \"notiCont\": \"긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용1긴급공지내용2\"\n" +
            "    }\n" +
            "  ]\n" +
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
            "      \"lnkUseYn\": \"N\"\n" +
            "    },\n" +
            "    {\n" +
            "      \"wthrCd\": \"SKY_1\",\n" +
            "      \"msgTypCd\": \"TXT\",\n" +
            "      \"lnkUseYn\": \"N\"\n" +
            "    }\n" +
            "  ]\n" +
            "}",CMN_0002.Response.class);;
}
